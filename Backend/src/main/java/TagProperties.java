import scala.Tuple2;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by victor on 1/25/17.
 */
public class TagProperties {

    private String name;
    private List<Tuple2<Integer,Point2D>> posSample;
    private int startPage;              // Optionnal
    private int endPage;                // Optionnal
    private Pattern regex;               // Optionnal

    public TagProperties(String name, List<Tuple2<Integer,Point2D>> posSample, Integer startPage, Integer endPage, String regex){
        if(name==null || "".equals(name)){
            System.out.println("Invalid Name : <<"+name+">>");
            System.exit(0);
        }else{
            this.name = name;
        }

        if(posSample==null || posSample.size()==0){
            System.out.println("Invalid Map<Page,Click>");
            System.exit(0);
        }else{
            this.posSample=posSample;
        }


        if(startPage!=null) {
            this.startPage = startPage;
        }else{
            this.startPage=-1;
        }

        if(endPage!=null) {
            this.endPage = endPage;
        }else{
            this.endPage=-1;
        }

        if(regex != null) {
            try {
                this.regex = Pattern.compile(regex);
            } catch (PatternSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tuple2<Integer,Point2D>> getPosSample() {
        return posSample;
    }

    public void setPosSample(List<Tuple2<Integer,Point2D>> posSample) {
        this.posSample = posSample;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public Pattern getRegex() {
        return regex;
    }

    public void setRegex(Pattern regex) {
        this.regex = regex;
    }
}
