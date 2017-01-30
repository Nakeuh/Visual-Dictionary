import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 1/11/17.
 */
public class PDFTextStripperCustom extends PDFTextStripper {

    public PDFTextStripperCustom() throws IOException {
        super();

    }

    public PDFont generateCriteria(Point2D pos){
        List<List<TextPosition>> list = this.getCharactersByArticle();

        double distance = pos.distance(list.get(0).get(0).getX(), list.get(0).get(0).getY());
        TextPosition bestChar = list.get(0).get(0);

        for(List<TextPosition> l:list) {
            for (TextPosition t : l) {
                double tmp = pos.distance(t.getX(), t.getY());
                if(tmp<distance){
                    distance = tmp;
                    bestChar = t;
                }
            }
        }

        return bestChar.getFont();
    }

    // TODO : depend on start/end page
    public Map<String,List<Tag>> extractTags(List<TagCriterias> tagCriterias){
        Map<String,List<Tag>> tagAssociatedText = new HashMap<String, List<Tag>>();
        List<List<TextPosition>> list = this.getCharactersByArticle();


        // TODO optimize this
        for(TagCriterias tagCrit: tagCriterias){
            String tag = tagCrit.getName();
            TextPosition previousChar = null;

            Tag t = new Tag();

            ArrayList<Tag> tmpList = new ArrayList<Tag>();

            for(List<TextPosition>l : list){
                for(TextPosition currentChar : l) {
                    if(previousChar==null){
                        t.setDebut(new Point2D.Float(currentChar.getX(),currentChar.getY()));
                    }
                    for(PDFont font: tagCrit.getFontCriteria()){
                        if(isRightFont(font,currentChar)){
                            if(!isInBlock(t.getDebut(),previousChar,currentChar)) {
                                t.setFin(new Point2D.Float(previousChar.getX(),previousChar.getY()+previousChar.getHeight()));
                                tmpList.add(t);
                                t = new Tag();

                                t.setDebut(new Point2D.Float(currentChar.getX(),currentChar.getY()));
                            }

                            t.appendContent(currentChar.getCharacter());
                            previousChar = currentChar;
                        }
                    }
                }
            }
                //previouschar== null : pas de texte pour ce tag dans la page
            if(previousChar!=null){
                t.setFin(new Point2D.Float(previousChar.getX(),previousChar.getY()+previousChar.getHeight()));
                tmpList.add(t);
            }

            tagAssociatedText.put(tag,tmpList);
        }

        return tagAssociatedText;
    }

    public boolean isRightFont(PDFont font, TextPosition character){
        return font.equals(character.getFont());
    }

    // TODO
    public boolean isInBlock(Point2D startBlock,TextPosition previousChar, TextPosition currentChar){

        return true;

    }
}