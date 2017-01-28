import java.awt.geom.Point2D;

/**
 * Created by victor on 1/25/17.
 */
public class Tag {
    private StringBuilder content = new StringBuilder();
    private Point2D debut = new Point2D.Float();
    private Point2D fin = new Point2D.Float();

    public Tag(){
        content = new StringBuilder();
        debut = new Point2D.Float();
        fin = new Point2D.Float();
    }

    public String getContent() {
        return content.toString();
    }

    public void setContent(String content) {
        this.content = new StringBuilder(content);
    }

    public void appendContent(String str){
        this.content.append(str);
    }

    public Point2D getDebut() {
        return debut;
    }

    public void setDebut(Point2D debut) {
        this.debut = debut;
    }

    public Point2D getFin() {
        return fin;
    }

    public void setFin(Point2D fin) {
        this.fin = fin;
    }
}
