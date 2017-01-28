import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by victor on 1/11/17.
 */
public class DataExtractor {

    public static String DEFAULT_REGEX ="";

    public static void process(List<TagProperties> tagProperties, String filename){
        PDFTextStripperCustom pdfStripper = null;
        PDDocument pdDoc = null;

        File file = new File(filename);

        // <page,<tagName,extractedTextList>
        Map<Integer,Map<String,List<Tag>>> extractedTexts = new HashMap<Integer, Map<String, List<Tag>>>();

        try {

            PDFParser parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            pdfStripper = new PDFTextStripperCustom();
            pdDoc = new PDDocument(parser.getDocument());

            int nbPage = pdDoc.getNumberOfPages();

            List<TagCriterias> tagCriteria = getCriterias(nbPage,tagProperties,pdfStripper,pdDoc);

            // Extract page by page
            for(int page=12; page<13/*nbPage*/ ;page++){

                pdfStripper.setStartPage(page);

                pdfStripper.setEndPage(page);

                // fill the charactersByArticle field of PDFTextStripper
                pdfStripper.getText(pdDoc);

                Map<String,List<Tag>> tagAssociatedText= pdfStripper.extractTags(tagProperties,tagCriteria);

                System.out.println("Page "+page);
                extractedTexts.put(page,tagAssociatedText);

                extractedTextToJSON(filename,nbPage,extractedTexts);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TagCriterias> getCriterias(int nbPage,List<TagProperties> properties,
                                                        PDFTextStripperCustom pdfStripper,
                                                        PDDocument pdDoc)throws IOException{
        List<TagCriterias> criteria = new ArrayList<TagCriterias>();

        for (TagProperties p:properties){
            TagCriterias crit = new TagCriterias();

            crit.setName(p.getName());

            if(p.getStartPage()<0){
                crit.setStartPage(0);
            }else{
                crit.setStartPage(p.getStartPage());
            }

            if(p.getEndPage()<0){
                crit.setEndPage(nbPage);
            }else{
                crit.setEndPage(p.getEndPage());
            }

            if(p.getRegex()==null){
                crit.setRegex(Pattern.compile(DEFAULT_REGEX));
            }else {
                crit.setRegex(p.getRegex());
            }
            List<PDFont> fonts = new ArrayList<PDFont>();

            for (Tuple2<Integer,Point2D>  click: p.getPosSample()) {

                pdfStripper.setStartPage(click._1);
                pdfStripper.setEndPage(click._1);

                // fill the charactersByArticle field of PDFTextStripper
                pdfStripper.getText(pdDoc);
                PDFont font = pdfStripper.generateCriteria(click._2);
                fonts.add(font);
            }
            crit.setFontCriteria(fonts);
            criteria.add(crit);
        }

        return criteria;
    }

    public static void extractedTextToJSON(String filepath,
                                           int nbPage,
                                           Map<Integer,Map<String,List<Tag>>> extractedTexts){

        JSONObject object = new JSONObject();

        String[] tmp = filepath.split("/");
        String filename = tmp[tmp.length-1];

        JSONArray pageArray = new JSONArray();

        for (Map.Entry<Integer, Map<String,List<Tag>>> page : extractedTexts.entrySet()) {

            JSONObject pageObject = new JSONObject();

            JSONArray tagArray = new JSONArray();

            for (Map.Entry<String, List<Tag>> tag : page.getValue().entrySet()) {

                JSONObject tagObject = new JSONObject();

                JSONArray textArray = new JSONArray();

                for(Tag t: tag.getValue()){

                    JSONObject textObject = new JSONObject();

                    textObject.put("content",t.getContent());

                    JSONObject pos1Object = new JSONObject();
                        pos1Object.put("x",t.getDebut().getX());
                        pos1Object.put("y",t.getDebut().getY());

                    JSONObject pos2Object = new JSONObject();
                        pos2Object.put("x",t.getFin().getX());
                        pos2Object.put("y",t.getFin().getY());

                    textObject.put("pos1",pos1Object);
                    textObject.put("pos2",pos2Object);

                    textArray.put(textObject);
                }

                tagObject.put("tag",tag.getKey());
                tagObject.put("values",textArray);

                tagArray.put(tagObject);
            }

            pageObject.put("page",page.getKey());
            pageObject.put("tags",tagArray);

            pageArray.put(pageObject);
        }

        object.put("document_name",filename);
        object.put("nbPages",nbPage);
        object.put("pages",pageArray);

        try{
            PrintWriter writer = new PrintWriter(filename.split("\\.")[0]+".json", "UTF-8");
            writer.println(object.toString(4));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
