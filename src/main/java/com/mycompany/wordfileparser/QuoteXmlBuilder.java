/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordfileparser;

import com.mycompany.config.PropertyHandler;
import org.jdom2.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.stream.Stream;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
/**
 *
 * @author I318434
 */
public class QuoteXmlBuilder {
    
    Stream<String> quoteContent = Stream.empty();
    int idMessage = 0;
    String messageTitle = "";
    String messagePreacher = "";
    String messagePlace = "";
    String messageDate = null;
    
    public QuoteXmlBuilder(Stream<String> quoteContent) {
           this.quoteContent = quoteContent;
    }
    
    public QuoteXmlBuilder(Stream<String> quoteContent, int id, String messageTitle, String messagePreacher, String messagePlace, String messageDate) {
           this.quoteContent = quoteContent;
           this.idMessage = id;
           this.messageTitle = messageTitle;
           this.messagePreacher = messagePreacher;
           this.messagePlace = messagePlace;
           this.messageDate = messageDate;
    }
    
    public void buildQuoteXml(String nameToProcessedMessage) {
        String pathToProcessedMessages = System.getProperty("user.dir") + PropertyHandler.getInstance().getValue("pathToProcessedMessages");        
        Element root = getRootElement("add");        
         
        int[] counter = {1};
        this.quoteContent.forEach(cont -> {            
            Element docParagraph = buildDocElement(cont, counter[0]++);
            root.addContent(docParagraph);        
        });
        
        
        try{ 
            XMLOutputter outter = new XMLOutputter();
            outter.setFormat(Format.getPrettyFormat());
            Writer processedXmlFile = null;
            try {
                    processedXmlFile = new OutputStreamWriter(new FileOutputStream(new File(pathToProcessedMessages + nameToProcessedMessage + ".xml")), StandardCharsets.UTF_8);
                    outter.output(root, processedXmlFile);
                  } catch (IOException e1) {
                    e1.printStackTrace();
                }
    
            //outter.output(root, new FileWriter(new File(pathToProcessedMessages + nameToProcessedMessage + ".xml")));
//            PrintWriter writer = new PrintWriter("structureXml.txt", "UTF-8");
//            writer.println(root.toString());            
//            writer.close();
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
    }
        
    public Element buildDocElement(String messageParagraph, int counter) {
        Element doc = XmlCreator.getCreateElement("doc", "", "", "");
                        
        Element idMessageField = XmlCreator.getCreateElement("field", Integer.toString(idMessage), "name", "message_id");
        doc.addContent(idMessageField);
                
        Element quoteIdField = XmlCreator.getCreateElement("field", Integer.toString(counter), "name", "quote_id");
        doc.addContent(quoteIdField);
        
        Element messageTitleField = XmlCreator.getCreateElement("field", this.messageTitle, "name", "message_title");
        doc.addContent(messageTitleField);
        
        Element messagePreacherField = XmlCreator.getCreateElement("field", this.messagePreacher, "name", "message_preacher");
        doc.addContent(messagePreacherField);
        
        Element messagePlaceField = XmlCreator.getCreateElement("field", this.messagePlace, "name", "message_place");
        doc.addContent(messagePlaceField);
        
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Element messageDateField = XmlCreator.getCreateElement("field", this.messageDate , "name", "message_date");
        doc.addContent(messageDateField);
        
        Element messageParagraphField = XmlCreator.getCreateElement("field", messageParagraph, "name", "message_quote");
        doc.addContent(messageParagraphField);
        
        return doc;
    }
    
    public Element getRootElement(String rootElement) {
        Element root = new Element(rootElement);
        return root;
    }
    
}
