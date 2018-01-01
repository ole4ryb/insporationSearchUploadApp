/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fileoperations;

import com.mycompany.config.PropertyHandler;
import com.mycompany.wordfileparser.QuoteXmlBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import static java.nio.file.Files.lines;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;

/**
 *
 * @author I318434
 */
public class FileProcessor {
    
    String fileName = "";
    int id = 0;
    String messageTitle = "";
    String messagePreacher = "";
    String messagePlace = "";
    String messageDate = null;
    int couunterMessageId = 0;
        
    
    
    public FileProcessor(String fileName) {
        this.fileName = fileName;
        parseFileName(this.fileName);
    }
    
    protected void extractMessageDataFromFileName() throws FileNotFoundException, IOException, TikaException {
        
         //detecting the file type
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
     
      //ParseContext pcontext = new ParseContext();
      String pathToDirToBeListened = System.getProperty("user.dir") + PropertyHandler.getInstance().getValue("messageToProcess"); //"\\messageToProcess\\";
      Path dir = Paths.get(pathToDirToBeListened);
      
      InputStream fileInputStream = new FileInputStream(new File(pathToDirToBeListened + this.fileName));        
      
      Tika tika = new Tika();
      TikaInputStream reader = TikaInputStream.get(fileInputStream); //TikaInputStream.get(new File(this.fileName), metadata);
      String contents = tika.parseToString(reader, metadata);
      
      String[] parts = contents.split("Ω\\d+");
      Stream<String> quoteParts = Arrays.stream(parts);

      couunterMessageId = processMessageId();
      //int[] count = {0};
      //quoteParts.forEach(x -> {System.out.println("Number: # " + count[0]++  + "\n" + x);});
      createQuoteXmlStructure(quoteParts, couunterMessageId, messageTitle, messagePreacher, messagePlace, messageDate);      
        
    }
    
    public int processMessageId() throws IOException  {
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
        Writer wr = new FileWriter(pathToUniqueIdCounterFile);
        wr.write(String.valueOf(++couunterMessageId[0]));
        wr.close();
        return couunterMessageId[0];
    }
    
    private void createQuoteXmlStructure(Stream<String> quoteParts, int id, String messageTitle, String messagePreacher, String messagePlace, String messageDate) {
        QuoteXmlBuilder quoteXmlBuilder = new QuoteXmlBuilder(quoteParts, id, messageTitle, messagePreacher, messagePlace, messageDate);
        quoteXmlBuilder.buildQuoteXml(messageTitle + "_" + messageDate);
    }

    private void parseFileName(String fileName) {
        String messageNameToParse = fileName.trim();
        String[] parts = messageNameToParse.split("_");
        this.messageDate = (parts[0] != null) ? parts[0] : ""; 
        this.messageTitle = parts[1]; 
        this.messagePreacher = parts[2];
        this.messagePlace = parts[3].substring(0, parts[3].indexOf("."));
    }
    
}
