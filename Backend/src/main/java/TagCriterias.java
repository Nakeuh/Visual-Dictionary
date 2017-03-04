import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.TextPosition;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by victor on 1/25/17.
 */
public class TagCriterias {

    private String name;
    private List<TextPosition> fontCriteria;
    private int startPage;
    private int endPage;
    private Pattern regex;

    public TagCriterias(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TextPosition> getFontCriteria() {
        return fontCriteria;
    }

    public void setFontCriteria(List<TextPosition> fontCriteria) {
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
