package com.mycompany.wordfileparser;

import com.mycompany.fileoperations.WatchDir;
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
        // register directory and process its events
        String pathToDirToBeListened = System.getProperty("user.dir") + "\\messageToProcess\\";
        Path dir = Paths.get(pathToDirToBeListened);
        new WatchDir(dir, false).processEvents();   
   }

}
