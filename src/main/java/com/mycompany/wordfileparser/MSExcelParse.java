package com.mycompany.wordfileparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.tika.Tika;
import org.apache.tika.detect.EncodingDetector;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.parser.txt.UniversalEncodingDetector;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

/**
 *
 * @author I318434
 */
public class MSExcelParse {
    
   public static void main(final String[] args) throws IOException, TikaException, SAXException, SAXException, ParseException {
       
       int id = 0;
       String messageTitle = "";
       String messagePreacher = "";
       String messagePlace = "";
       LocalDate messageDate = null;
       
              
       if (args.length == 0) {
            System.out.println("no arguments were given.");
            System.exit(0);
        } else {
            id = Integer.parseInt(args[0]);
            messageTitle = args[1];
            messagePreacher = args[2];
            
            String cmdDate = args[3];
            LocalDate localDate = LocalDate.parse(cmdDate);
            messageDate = localDate;
        }
      
       int couunterMessageId = processMessageId();
      //detecting the file type
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
     
      InputStream fileInputStream = new FileInputStream(new File("1965-04-25_Spirit_Of_Truth.doc"));            
      ParseContext pcontext = new ParseContext();
      
      Tika tika = new Tika();
      TikaInputStream reader = TikaInputStream.get(new File("1965-04-25_Spirit_Of_Truth.doc"), metadata);
      String contents = tika.parseToString(reader, metadata);
      
      String[] parts = contents.split("Ω\\d+");
      Stream<String> quoteParts = Arrays.stream(parts);

      int[] count = {0};
      //quoteParts.forEach(x -> {System.out.println("Number: # " + count[0]++  + "\n" + x);});
      createQuoteXmlStructure(quoteParts, couunterMessageId, messageTitle, messagePreacher, messagePlace, messageDate);
   }

    private static void createQuoteXmlStructure(Stream<String> quoteParts, int id, String messageTitle, String messagePreacher, String messagePlace, LocalDate messageDate) {
        QuoteXmlBuilder quoteXmlBuilder = new QuoteXmlBuilder(quoteParts, id, messageTitle, messagePreacher, messagePlace, messageDate);
        quoteXmlBuilder.buildQuoteXml();
    }
        
    public static int processMessageId() throws IOException  {
        String pathToUniqueIdCounterFile = System.getProperty("user.dir") + "\\resources\\" + "uniqueIdCounterFile.txt";
        File f = new File(pathToUniqueIdCounterFile);
        Integer[] couunterMessageId = new Integer[1];
                        
        if(f.exists() && !f.isDirectory()) { 
            Stream<String> lines = Files.lines(Paths.get(pathToUniqueIdCounterFile));
            lines.forEach((String line) -> {
               couunterMessageId[0] =  Integer.parseInt(line);
            });
        } else {
            int uniqueIdCounter = 1;            
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(pathToUniqueIdCounterFile), "utf-8"))) {
                writer.write(new Integer(1).toString());
                couunterMessageId[0] = 1;
             }            
        }
        
        return couunterMessageId[0];
    }
}
