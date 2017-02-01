import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by victor on 1/25/17.
 */
public class TagCriterias {

    private String name;
    private List<PDFont> fontCriteria;
    private int startPage;
    private int endPage;
    private Pattern regex;

    public TagCriterias(String name, List<PDFont> fontCriteria, Integer startPage, Integer endPage, Pattern regex){

        this.name = name;

        this.fontCriteria = fontCriteria;

        this.startPage = startPage;

        this.endPage = endPage;

        this.regex = regex;
    }

    public TagCriterias(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Pattern getRegex() {
        return regex;
    }

    public boolean hasRegex(){
        return regex!=null;
    }

    public void setRegex(Pattern regex) {
        this.regex = regex;
    }
}
