import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludo on 30/04/2017.
 */
public class Document {

    private String document_name;
    private Long nbPages;
    private List<Pages> pages;

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public Long getNbPages() {
        return nbPages;
    }

    public void setNbPages(Long nbPages) {
        this.nbPages = nbPages;
    }

    public List<Pages> getPages() {
        return pages;
    }

    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }
}
