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
        String filename = "resources/dictionary.pdf";

        List<TagProperties> tagProps = new ArrayList<TagProperties>();

        String nameTag = "Legends";
        List<Tuple2<Integer,Point2D>> posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(427,new Point2D.Float(100.005f,192.7897f)));

        Integer startPage=7;
        Integer endPage = 617;
        String regex = null;

      //  tagProps.add(new TagProperties(nameTag, posSample,startPage,endPage,regex));

        nameTag = "Paragraphs";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(9,new Point2D.Float(95.005f,330.7897f)));

        startPage=null;
        endPage = null;
        regex = null;
      //  tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage,regex));

        nameTag = "Sommaire";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(7,new Point2D.Float(300f,330.7897f)));

        startPage=null;
        endPage = null;
        regex = null; // "(\\w(?>[\\w,\'’]+\\s?)*)[\\s.]{2,}(\\d+)[\\s]";  ===> Not working
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage,regex));

        DataExtractor.process(tagProps,filename);

    }
}
