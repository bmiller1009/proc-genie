package org.bradfordmiller.procgenie.sqlops

import grizzled.slf4j.Logging
import java.sql.{DriverManager, DatabaseMetaData, ResultSet => RS}
import org.bradfordmiller.procgenie.sqlops.Arm._
import scala.language.reflectiveCalls
import scala.annotation.tailrec

object Arm {
	
  def using[Closeable <: {def close(): Unit}, B](closeable: Closeable)
  	(getB: Closeable => B): B = 
  	try {
      getB(closeable)
    } finally {
      closeable.close()
  }  
}

class MetaDataExtractor(connString: String, procName: String) extends SqlClassLoader with Logging {
  
  loadClass(MySql)  
  
  trace(s"In getMySQLConnectionString: conn=$connString")
  lazy val connection = DriverManager.getConnection(connString)
  
  def scanProcedure() = {
    
    using(connection) {conn =>
      using(conn.getMetaData().getProcedureColumns(conn.getCatalog(), null, procName, "%")) {rs =>
        
        @tailrec
        def processRS(paramSet: Set[ProcedureMetaData], resultSet: RS): Set[ProcedureMetaData] = {
          
          if(!rs.next())
            paramSet
          else {
            
            val procMetaData = ProcedureMetaData(
              rs.getString(1),
              if(rs.getString(2) != null) Some(rs.getString(2)) else None,
              rs.getString(3),
              rs.getString(4),
              rs.getShort(5),
              rs.getInt(6),
              rs.getString(7),
              rs.getInt(8),
              rs.getInt(9),
              rs.getShort(10),
              rs.getBoolean(11)
            )    
            
            processRS(paramSet + procMetaData, resultSet)
          }
        }        
        processRS(Set(), rs)        
      }      
    }
  }
}