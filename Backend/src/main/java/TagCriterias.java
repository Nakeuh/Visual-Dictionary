import org.apache.pdfbox.pdmodel.font.PDFont;
import scala.Tuple2;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by victor on 1/25/17.
 */
public class TagCriterias {

    private String name;
    private Pattern regex;
    private List<PDFont> fontCriteria;
    private int startPage;
    private int endPage;

    public TagCriterias(String name, String regex, List<PDFont> fontCriteria, Integer startPage, Integer endPage){

        this.name = name;

        try {
            this.regex = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid Regex : <<" + regex + ">>");
            System.exit(0);
        }

        this.fontCriteria = fontCriteria;

        this.startPage = startPage;

        this.endPage = endPage;
    }

    public TagCriterias(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pattern getRegex() {
        return regex;
    }

    public void setRegex(Pattern regex) {
        this.regex = regex;
    }

    public List<PDFont> getFontCriteria() {
        return fontCriteria;
    }

    public void setFontCriteria(List<PDFont> fontCriteria) {
        this.fontCriteria = fontCriteria;
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
}
