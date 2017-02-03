import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

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

    public Map<String,List<Tag>> extractTags(int nPage,List<TagCriterias> tagCriterias){
        Map<String,List<Tag>> tagAssociatedText = new HashMap<String, List<Tag>>();
        List<List<TextPosition>> list = this.getCharactersByArticle();


        // TODO optimize this
        for(TagCriterias tagCrit: tagCriterias) {
            if (nPage >= tagCrit.getStartPage() && nPage <= tagCrit.getEndPage()){
                String tag = tagCrit.getName();
                TextPosition previousChar = null;

                Tag t = new Tag();

                float maxXInTag=0;
                float minXInTag=0;
                boolean matchedRegex = false;

                ArrayList<Tag> tmpList = new ArrayList<Tag>();

                for (List<TextPosition> l : list) {
                    for (TextPosition currentChar : l) {

                        // First character of the page
                        if (previousChar == null) {
                            maxXInTag = minXInTag = currentChar.getX();
                            t.setDebut(new Point2D.Float(minXInTag, currentChar.getY()));
                        }

                        // For each Font that describe the tag :
                        for (PDFont font : tagCrit.getFontCriteria()) {
                            if (isRightFont(font, currentChar)) {
                                if(tagCrit.hasRegex()) {
                                    Matcher m = tagCrit.getRegex().matcher(t.getContent());

                                    if((matchedRegex && !m.matches()) && previousChar!=null){
                                        t.setDebut(new Point2D.Float(minXInTag,(float)t.getDebut().getY()));
                                        t.setFin(new Point2D.Float(maxXInTag, previousChar.getY() + previousChar.getHeight()));
                                        char tmp  = t.getContent().charAt(t.getContent().length()-1);

                                        t.setContent(t.getContent().substring(0,t.getContent().length()-1));
                                        tmpList.add(t);
                                        t = new Tag();
                                        t.appendContent(tmp+"");

                                        maxXInTag = minXInTag = currentChar.getX();
                                        t.setDebut(new Point2D.Float(minXInTag, currentChar.getY()));
                                    }
                                    matchedRegex = m.matches();
                                }else if (!isInBlock(t.getDebut(), previousChar, currentChar)) {
                                    t.setDebut(new Point2D.Float(minXInTag,(float)t.getDebut().getY()));
                                    t.setFin(new Point2D.Float(maxXInTag, previousChar.getY() + previousChar.getHeight()));
                                    tmpList.add(t);
                                    t = new Tag();

                                    maxXInTag = minXInTag = currentChar.getX();
                                    t.setDebut(new Point2D.Float(minXInTag, currentChar.getY()));

                                }

                                t.appendContent(currentChar.getCharacter());

                                if(currentChar.getX()<minXInTag){
                                    minXInTag = currentChar.getX();
                                }else if(currentChar.getX()>maxXInTag){
                                    maxXInTag = currentChar.getX();
                                }
                                previousChar = currentChar;
                            }
                        }
                    }
                }
                //previouschar== null : pas de texte pour ce tag dans la page
                if (previousChar != null) {
                    if(tagCrit.hasRegex()) {
                        Matcher m = tagCrit.getRegex().matcher(t.getContent());
                        if(m.matches()){
                            t.setDebut(new Point2D.Float(minXInTag,(float)t.getDebut().getY()));
                            t.setFin(new Point2D.Float(maxXInTag, previousChar.getY() + previousChar.getHeight()));
                            tmpList.add(t);
                        }
                    }else {
                        t.setDebut(new Point2D.Float(minXInTag,(float)t.getDebut().getY()));
                        t.setFin(new Point2D.Float(maxXInTag, previousChar.getY() + previousChar.getHeight()));
                        tmpList.add(t);
                    }
                }

                tagAssociatedText.put(tag, tmpList);
            }
        }

        return tagAssociatedText;
    }

    public boolean isRightFont(PDFont font, TextPosition character){
        return font.equals(character.getFont());
    }

    // TODO
    public boolean isInBlock(Point2D startBlock,TextPosition previousChar, TextPosition currentChar){
        boolean inBlock = true;
        if(previousChar!=null) {
          //  System.out.println("Char : "+currentChar.getCharacter());
          //  System.out.println("Current Y : "+currentChar.getY());
          //  System.out.println("Previous Y : "+previousChar.getY());

            if (currentChar.getY()<= previousChar.getY()+previousChar.getHeight()/2 && currentChar.getY()>= previousChar.getY()-previousChar.getHeight()/2) {          // Same line as previous character
                inBlock = (currentChar.getX() <= previousChar.getX() + 3*previousChar.getWidth() &&
                                currentChar.getX() > previousChar.getX());  // Return : ?(current is near to previous char)
            } else if (currentChar.getY() <= previousChar.getY() + 2*previousChar.getHeight() &&
                    currentChar.getY()>previousChar.getY()) { // Next line of previous character
            //    System.out.println("Ligne 2 : ");

                inBlock = currentChar.getX() < previousChar.getX() &&
                        currentChar.getX() >= startBlock.getX() - (previousChar.getX() - currentChar.getX()) / 2;
            } else {
            //    System.out.println("Ligne X : ");
                inBlock = false;
            }
        }

       // System.out.println(inBlock);
        return inBlock;

    }
}