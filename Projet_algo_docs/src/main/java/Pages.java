import java.util.List;

/**
 * Created by Ludo on 30/04/2017.
 */
public class Pages{

    private Long page;
    private List<Tags> tags;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}
