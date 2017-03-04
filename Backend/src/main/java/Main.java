import scala.Tuple2;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 1/11/17.
 */
public class Main {
    public static void main(String[] args){
        Tuple2<String,List<TagProperties>> inputs = getInputs();
        DataExtractor.process(inputs._2,inputs._1);
    }

    public static Tuple2<String, List<TagProperties>> getInputs(){
        String filename = "resources/dictionary.pdf";

        List<TagProperties> tagProps = new ArrayList<TagProperties>();

        String nameTag = "Legends";
        List<Tuple2<Integer,Point2D>> posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(427,new Point2D.Float(100.005f,192.7897f)));

        Integer startPage=7;
        Integer endPage = 617;
        String regex = null;

        tagProps.add(new TagProperties(nameTag, posSample,startPage,endPage,regex));

        nameTag = "Paragraphs";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(12,new Point2D.Float(150f,200f)));

        startPage=null;
        endPage = null;
        regex = null;
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage,regex));

        nameTag = "Sommaire";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(7,new Point2D.Float(300f,330.7897f)));

        startPage=null;
        endPage = null;
        regex =  "(\\w(?>[\\w,\'’]+\\s?)*)[\\s.]{2,}(\\d+)[\\s]?";
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage,regex));

        nameTag = "NumPage";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(7,new Point2D.Float(527.6f,652.0f)));

        startPage=null;
        endPage = null;
        regex = null;
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage,regex));


        return new Tuple2<String, List<TagProperties>>(filename,tagProps);
    }
}
