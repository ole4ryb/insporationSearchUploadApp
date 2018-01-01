/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.config;

import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author I318434
 */
public class PropertyHandler{

   private static PropertyHandler instance = null;
   private Properties prop = null;

   private PropertyHandler() {
       try {
           InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\resources\\"+ "windows_config.properties");
           prop = new Properties();
           prop.load(input);
       } catch (FileNotFoundException ex) {
           Logger.getLogger(PropertyHandler.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(PropertyHandler.class.getName()).log(Level.SEVERE, null, ex);
       }         
   }

   public static synchronized PropertyHandler getInstance(){
       if (instance == null)
           instance = new PropertyHandler();
       return instance;
   }

   public String getValue(String propKey){
       return this.prop.getProperty(propKey);
   }
}