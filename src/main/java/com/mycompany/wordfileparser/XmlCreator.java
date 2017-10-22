/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordfileparser;
   
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlCreator {
     /**
     * <code>getDocument</code> createa a Document object from the File path.
     * @param filename
     * @return
     */
    public static Document getDocument(File file) {
        try {
            SAXBuilder builder = new SAXBuilder();
            // Create the document
            org.jdom2.Document doc = builder.build(file);
            return doc;
        } catch (Exception e) {            
            throw new RuntimeException("XMLUtil.getDocument() failed");
        }
    }

    /**
     * <code>getDocument</code> createa a Document object from the File path.
     * @param xmlString
     * @return
     */
    public static Document getDocument(String xmlString) {
        try {
            SAXBuilder builder = new SAXBuilder();
            // Create the document
            Reader reader = new StringReader(xmlString);
            org.jdom2.Document doc = builder.build(reader);
            return doc;
        } catch (Exception e) {            
            throw new RuntimeException("XMLUtil.getDocument() failed");
        }
    }

    public static Document getDocument(InputStream inputStream) {
        try {
            SAXBuilder builder = new SAXBuilder();
            return builder.build(inputStream);
        } catch (Exception e) {            
            throw new RuntimeException("XMLUtil.getDocument() failed");
        }
    }

    /**
     * <code>getElementByNameValue</code> is a utility method for getting a child
     * element by name and value of attribute
     * @param document
     * @param name
     * @param value
     * @return
     */
    public static Element getElementByNameValue(List elements, String name, String value) {
        Element childElement = null;
        Attribute attribute = null;
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            childElement = (Element) iterator.next();
            attribute = childElement.getAttribute(name);
            if (attribute != null && value != null && value.equals(attribute.getValue())) {
                return childElement;
            }
        }
        return null;
    }

    public static org.w3c.dom.Document loadXMLFrom(String xml)
            throws SAXException, IOException {
        return loadXMLFrom(new ByteArrayInputStream(xml.getBytes()));
    }

    private static org.w3c.dom.Document loadXMLFrom(InputStream is)
            throws SAXException, IOException {
       DocumentBuilderFactory factory =
                javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
       DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {            
            throw new RuntimeException("XMLUtil.loadXMLFrom() failed");
        }
        org.w3c.dom.Document doc = builder.parse(is);
        is.close();
        return doc;
    }
    
    public static Element getCreateElement(String elementName, String elementValue, String attributeName, String attributeValue){
        Map<String, String> attributeMap = new HashMap<String, String>();
        attributeMap.put(attributeName, attributeValue);
        Element elem = createElement(elementName, elementValue, attributeMap);
        return elem;
    }
    
    public static Element createElement(String elementName, String elementValue, Map<String, String> attributeMap){
        Element elem = new Element(elementName);
        elem.setText(elementValue);
        for(String attributeName: attributeMap.keySet()){
            String attributeValue = attributeMap.get(attributeName);
            if(!attributeName.isEmpty()){
                Attribute attrid = new Attribute(attributeName, attributeValue);
                elem.setAttribute(attrid);
            }
        }
        return elem;
    }
}
