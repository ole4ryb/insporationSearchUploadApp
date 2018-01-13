/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fileoperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author I318434
 */
public class FileProcessorTest {
    
    public FileProcessorTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
                
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of extractMessageDataFromFileName method, of class FileProcessor.
     */
    @Test
    public void testParseFileName() throws IOException, FileNotFoundException, TikaException {
        FileProcessor fileProc = new FileProcessor("1970-12-12_The Spirit Of Adoption_Bro. Joseph Coleman_NY, USA");
        assertEquals(fileProc.messageDate, "1970-12-12");
        assertEquals(fileProc.messageTitle, "The Spirit Of Adoption");
        assertEquals(fileProc.messagePreacher, "Bro. Joseph Coleman");
        assertEquals(fileProc.messagePlace, "NY, USA");
        //instance.extractMessageDataFromFileName();
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
}
