import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.JSONArray;
import org.json.JSONObject;
import scala.Tuple2;
import scala.Tuple3;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 1/11/17.
 */
public class DataExtractor {

    public static final int PAGE_SUMMARY = 7;

    public static void extractTexts(String filename){
        PDFTextStripperCustom pdfStripper = null;
        PDDocument pdDoc = null;

        File file = new File(filename);

        List<Tuple2<String,Integer>> mapThemes = new ArrayList<Tuple2<String, Integer>>();
        List<List<Tuple2<String,Integer>>> mapSubThemes = new ArrayList<List<Tuple2<String, Integer>>>();

        List<List<Tuple3<String,Point2D,Point2D>>> legendsPerPage = new ArrayList<List<Tuple3<String, Point2D, Point2D>>>();

        try {
            PDFParser parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            pdfStripper = new PDFTextStripperCustom();
            pdDoc = new PDDocument(parser.getDocument());

            int nbPage = pdDoc.getNumberOfPages();

            for(int i=0; i<nbPage ;i++){

                pdfStripper.setStartPage(i);

                pdfStripper.setEndPage(i);

                String parsedText = pdfStripper.getText(pdDoc);

                if(i == PAGE_SUMMARY){          // Sommaire global
                    System.out.println("   Sommaire");
                    mapThemes = pdfStripper.extractSummary();
                }

                    // TODO : Moche
                List<Integer> pageSubSumary = new ArrayList<Integer>();
                for(Tuple2<String,Integer> t: mapThemes){
                    pageSubSumary.add(t._2);
                }

                if(pageSubSumary.contains(i)){ // Sommaire des thèmes
                    System.out.println("   Sous-Sommaire");
                    mapSubThemes.add(pdfStripper.extractSubThemes());

                }

                legendsPerPage.add(pdfStripper.extractLegends());
            }

           parseJson(filename,nbPage,mapThemes,mapSubThemes,legendsPerPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseJson(String filepath,
                                   int nbPage,
                                   List<Tuple2<String,Integer>> mapThemes,
                                   List<List<Tuple2<String,Integer>>> mapSubThemes,
                                   List<List<Tuple3<String,Point2D,Point2D>>> legends ){


        JSONObject jobj = new JSONObject();

        String[] tmp = filepath.split("/");
        String filename = tmp[tmp.length-1];
        jobj.put("document_name",filename);
        jobj.put("nbPages",nbPage);

        JSONArray list = new JSONArray();

        int i=0;
        for(Tuple2<String,Integer> theme: mapThemes){
            JSONObject jsonTheme = new JSONObject();
            jsonTheme.put("theme",theme._1);
            jsonTheme.put("page",theme._2);

            JSONArray listSubTheme = new JSONArray();

            for(Tuple2<String,Integer> subTheme:mapSubThemes.get(i)){
                JSONObject jsonSubTheme = new JSONObject();
                jsonSubTheme.put("subtheme",subTheme._1);
                jsonSubTheme.put("page",subTheme._2);
                listSubTheme.put(jsonSubTheme);
            }
            jsonTheme.put("subthemes",listSubTheme);
            list.put(jsonTheme);
            i++;
        }

        jobj.put("themes",list);

        JSONArray listPage = new JSONArray();
        for(int j=0;j<nbPage;j++) {
            JSONObject pageJson = new JSONObject();
            pageJson.put("page",j);

            JSONArray legendeList = new JSONArray();

            for (Tuple3<String, Point2D, Point2D> t : legends.get(j)) {
                JSONObject legendeJson = new JSONObject();
                legendeJson.put("content",t._1());
                JSONObject pos1Json = new JSONObject();
                pos1Json.put("x",t._2().getX());
                pos1Json.put("y",t._2().getY());

                JSONObject pos2Json = new JSONObject();
                pos2Json.put("x",t._3().getX());
                pos2Json.put("y",t._3().getY());

                legendeJson.put("pos1",pos1Json);
                legendeJson.put("pos2",pos2Json);
                legendeList.put(legendeJson);
            }
            pageJson.put("legendes",legendeList);
            listPage.put(pageJson);
        }

        jobj.put("pages",listPage);

        try{
            PrintWriter writer = new PrintWriter(filename.split("\\.")[0]+".json", "UTF-8");
            writer.println(jobj.toString(4));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
