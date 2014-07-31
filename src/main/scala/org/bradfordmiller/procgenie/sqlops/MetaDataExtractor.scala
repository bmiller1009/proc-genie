package org.bradfordmiller.procgenie.sqlops

import grizzled.slf4j.Logging
import java.sql.{DriverManager, DatabaseMetaData, ResultSet}
import org.bradfordmiller.procgenie.sqlops.Arm._
import scala.language.reflectiveCalls

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
        while(rs.next()) {
          val procedureCatalog     = rs.getString(1);
          val procedureSchema      = rs.getString(2);
          val procedureName        = rs.getString(3);
          val columnName           = rs.getString(4);
          val columnReturn         = rs.getShort(5);
          val columnDataType       = rs.getInt(6);
          val columnReturnTypeName = rs.getString(7);
          val columnPrecision      = rs.getInt(8);
          val columnByteLength     = rs.getInt(9);
          val columnScale          = rs.getShort(10);
          val columnRadix          = rs.getShort(11);
          val columnNullable       = rs.getShort(12);
          val columnRemarks        = rs.getString(13);
          
          info("****NEXT****")
          info("procedureCatalog= " + procedureCatalog)
          info("procedureSchema= " + procedureSchema)
          info("procedureName= "   + procedureName)
          info("columnName= "      + columnName)
          info("columnReturn= "    + columnReturn)
          info("columnDataType= "  + columnDataType)
          info( "columnReturnTypeName= " + columnReturnTypeName)
          info( "columnPrecision= "      + columnPrecision)
          info( "columnByteLength= "     + columnByteLength)
          info( "columnScale= "          + columnScale)
          info( "columnRadix= "          + columnRadix)
          info( "columnNullable= "       + columnNullable)
          info( "columnRemarks= "        + columnRemarks)
          
        }
      }      
    }
  }
}