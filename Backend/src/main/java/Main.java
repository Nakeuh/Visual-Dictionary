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

        Integer startPage=null;
        Integer endPage = 617;
        tagProps.add(new TagProperties(nameTag, posSample,startPage,endPage));

        nameTag = "Paragraphs";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(9,new Point2D.Float(95.005f,330.7897f)));

        startPage=null;
        endPage = null;
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage));
/*
        nameTag = "Legends & Paragraphs";
        posSample = new ArrayList<Tuple2<Integer,Point2D>>();
        posSample.add(new Tuple2<Integer, Point2D>(300,new Point2D.Float(95.005f,330.7897f)));
        posSample.add(new Tuple2<Integer, Point2D>(9,new Point2D.Float(95.005f,330.7897f)));

        startPage=null;
        endPage = null;
        tagProps.add(new TagProperties(nameTag,posSample,startPage,endPage));
*/
        DataExtractor.process(tagProps,filename);

    }
}
