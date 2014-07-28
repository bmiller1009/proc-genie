package org.bradfordmiller.procgenie.sqlops

trait SqlClassLoader extends Enumeration {

  type SqlClassLoader = Value
  val MySql, Oracle, SqlServer, PostGres, Netezza, MariaDB = Value
  
  val classMap = Map(
      MySql -> "com.mysql.jdbc.Driver"
  )
  
  def loadClass(driver: Value): Unit = {
    val className = classMap(driver)
    Class.forName(className)
  }
}