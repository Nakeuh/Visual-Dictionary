import javafx.collections.transformation.SortedList;
import org.apache.jena.rdf.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ludo on 30/04/2017.
 */
public class Main {

    private static final String url = "http://www.visual-dictionnary/page=";

    private static final Model model = ModelFactory.createDefaultModel();

    private static final String chemin = "/Users/Ludo/Documents/SupGalilee/Cours/Annee2/Algo_Docs/Projet/src/main/resources/";

    private static final Property TAGS = model.createProperty("rdf:tags" );
    private static final Property OBJETTAGVALUES = model.createProperty("rdf:tag-values" );
    private static final Property OBJETVALUES = model.createProperty("rdf:values" );
    private static final Property TAG = model.createProperty("rdf:tag" );
    private static final Property POSITION1 = model.createProperty("rdf: pos1" );
    private static final Property POSITION2 = model.createProperty("rdf: pos2" );
    private static final Property X = model.createProperty("rdf:X" );
    private static final Property Y = model.createProperty("rdf:Y" );
    private static final Property CONTENT = model.createProperty("rdf:content" );

    public static void main (String[] args){

        DocumentParser documentParser = new DocumentParser();

        try{

            Document document = documentParser.parseDocumentFromFile(chemin + "dictionary.json");
            List<Pages> pagesList = document.getPages();

            if(pagesList != null && !pagesList.isEmpty()){
                construireRdf(pagesList);
            }
            else{
                System.out.print("Fichier vide");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void construireRdf(List<Pages> pagesList){

        for(Pages pages : pagesList){

            Resource resource = model.createResource(url + pages.getPage().toString());
            resource.addProperty(TAGS, model.createResource());

            for(Tags tags : pages.getTags()) {

                Resource resourceTag = resource.getPropertyResourceValue(TAGS);
                resourceTag.addProperty(OBJETTAGVALUES, model.createResource());

                Resource resourceObjetTagValues = resourceTag.getPropertyResourceValue(OBJETTAGVALUES);

                for(Values values : tags.getValues()){

                    resourceObjetTagValues.addProperty(OBJETVALUES, model.createResource()
                            .addProperty(CONTENT, values.getContent())
                            .addProperty(POSITION1, model.createResource()
                                    .addProperty(X, values.getPos1().getX())
                                    .addProperty(Y, values.getPos1().getY()))
                            .addProperty(POSITION2, model.createResource()
                                    .addProperty(X, values.getPos2().getX())
                                    .addProperty(Y, values.getPos2().getY())));
                }

                resourceObjetTagValues.addProperty(TAG, tags.getTag());
            }
        }

        ecrireDansFichier();

        //model.write(System.out, "RDF/XML-ABBREV");
    }

    private static void ecrireDansFichier(){

        try {
            RDFWriter writer = model.getWriter();
            writer.setProperty("showXmlDeclaration","true");
            writer.setProperty("tab","8");
            writer.setProperty("relativeURIs","same-document,relative");

            FileOutputStream fileOutputStream = new FileOutputStream(chemin + "rdf.xml");

            writer.write(model, fileOutputStream, "test");
        }
        catch(Exception e){
            System.out.print(e);
        }


    }
}
