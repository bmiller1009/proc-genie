package org.bradfordmiller.procgenie

import scala.util.{Try, Success, Failure}
import grizzled.slf4j.Logging
import org.bradfordmiller.procgenie.sqlops.MetaDataExtractor

object Driver extends App with Logging { 
  
  val connString = "jdbc:mysql://192.168.1.9/sakila?user=sakila_guy&password=sakila"
  
  val extractor = new MetaDataExtractor(connString, "film_in_stock")
  extractor.scanProcedure()
    
  info("NEW EXTRACT")
  info("NEW EXTRACT")
  
  val extractor1 = new MetaDataExtractor(connString, "inventory_in_stock")
  extractor1.scanProcedure()
  
  info("NEW EXTRACT")
  info("NEW EXTRACT")
  
  val extractor2 = new MetaDataExtractor(connString, "rewards_report")
  extractor2.scanProcedure()
  
  info("Starting driver...")  
  
  info("Hello world!")
  
  info("Ending driver...")
}