package pdf_reader;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import scala.Tuple2;
import scala.Tuple3;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 1/11/17.
 */
public class PDFTextStripperCustom extends PDFTextStripper {

    public List<String> legendTypos;

    public final String regex = "(\\w(?>[\\w,\'’]+\\s?)*)[\\s.]{2,}(\\d+)[\\s]?";
    public PDFTextStripperCustom() throws IOException {
        super();

        legendTypos = new ArrayList<String>();
        legendTypos.add("IPCEHD+WalbaumMTStd-Italic");
        legendTypos.add("IPBPND+WalbaumMT-Italic");

    }

    // TODO : au propre
    public List<Tuple3<String,Point2D,Point2D>> extractLegends(){
        List<Tuple3<String,Point2D,Point2D>> tuples= new ArrayList<Tuple3<String,Point2D,Point2D>>();

        List<List<TextPosition>> list = this.getCharactersByArticle();

        Point2D debut = new Point2D.Float();
        Point2D fin = new Point2D.Float();
        String legende = "";

        for(List<TextPosition>l : list){

            TextPosition tmpChar=null;
            for(TextPosition curentChar : l) {

                if(isLegend(curentChar)){
                    // Premier character de la page
                    if(tmpChar==null){
                        debut.setLocation(curentChar.getTextPos().getXPosition(),curentChar.getTextPos().getYPosition());
                    }
                    else if(isMaj(curentChar.getCharacter())&&                              // Char majuscule
                             !(tmpChar.getCharacter().equals(" ")||                          // Non précédée par espace
                                     tmpChar.getCharacter().equals("(") ||                      // parenthèse
                                     isMaj(tmpChar.getCharacter()) ||                             // majuscule
                                     tmpChar.getCharacter().equals("°")
                             )
                            ){

                        fin.setLocation(tmpChar.getTextPos().getXPosition() ,tmpChar.getTextPos().getYPosition()+tmpChar.getHeight());

                        tuples.add(new Tuple3<String, Point2D, Point2D>(legende.replace('’','\''),debut,fin));

                        legende ="";
                        debut.setLocation(curentChar.getTextPos().getXPosition(),curentChar.getTextPos().getYPosition());

                    }
                    legende+=curentChar.getCharacter();
                    tmpChar = curentChar;
                }


            }

            if(tmpChar!=null) {
                fin.setLocation(tmpChar.getTextPos().getXPosition(), tmpChar.getTextPos().getYPosition());
            }
        }
        return tuples;
    }

    public List<Tuple2<String,Integer>> extractSummary(){
        List<Tuple2<String,Integer>> themeList = new ArrayList<Tuple2<String,Integer>>();
        for(List<TextPosition>l : this.getCharactersByArticle()){

            StringBuilder bloc = new StringBuilder();
            for(TextPosition curentChar : l) {
                if (curentChar.getFont().getBaseFont().equals("IPDJIB+GlyphaLTStd") && curentChar.getHeight()==6.4185004f) {
                  bloc.append(curentChar.getCharacter());
                }
            }

            Pattern pattern = Pattern.compile(regex);
            bloc.toString().replace('’','\'');
            Matcher matcher = pattern.matcher(bloc);
            while(matcher.find()){
                String theme = matcher.group(1).trim();
                int pagetheme = Integer.parseInt(matcher.group(2).trim())+3;

                themeList.add(new Tuple2<String, Integer>(theme,pagetheme));

            }
        }

        return themeList;
    }

    public List<Tuple2<String,Integer>> extractSubThemes(){
        List<Tuple2<String,Integer>> subThemeList = new ArrayList<Tuple2<String,Integer>>();
        for(List<TextPosition>l : this.getCharactersByArticle()){

            StringBuilder bloc=new StringBuilder();

            for(TextPosition curentChar : l) {
                if (curentChar.getFont().getBaseFont().equals("IPDJIA+WalbaumMTStd-Regular") && (
                        curentChar.getHeight()==5.1165004f ||
                                curentChar.getHeight()==3.4110003f ||
                                curentChar.getHeight()==7.3905005f ||
                                curentChar.getHeight()==6.2535005f)) {

                    bloc.append(curentChar.getCharacter());
                }
            }

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(bloc);
            while(matcher.find()){
                String subTheme = matcher.group(1).trim().replace('’','\'');
                int pageSubTheme = Integer.parseInt(matcher.group(2).trim())+2;

                subThemeList.add(new Tuple2<String, Integer>(subTheme,pageSubTheme));

            }
        }

        return subThemeList;
    }

    private boolean isMaj(String character){
        return !character.toLowerCase().equals(character);
    }

    private boolean isNumber(char character){
        return Character.isDigit(character);
    }

    private boolean isLetter(char character){
        return Character.isLetter(character);
    }

    private boolean isLegend(TextPosition t){
        return legendTypos.contains(t.getFont().getBaseFont());
    }
}
