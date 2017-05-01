import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ludo on 30/04/2017.
 */

public class DocumentParser {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Parse a JSON HAR
     *
     * @param f a JSON file
     * @return the HarLog representation
     * @throws IOException if file not found
     */
    public Document parseDocumentFromFile(String f) throws IOException {
        File input = new File(f);
        return parseDocument(input);
    }

    /**
     * Parse a JSON HAR
     *
     * @param file a JSON String
     * @return the HarLog representation
     * @throws IOException if file not found
     */
    public Document parseDocument(File file) throws IOException {
        Document document = mapper.readValue(file, Document.class);
        return document;
    }
}