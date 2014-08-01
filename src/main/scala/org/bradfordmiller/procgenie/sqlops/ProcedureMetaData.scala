package org.bradfordmiller.procgenie.sqlops

case class ProcedureMetaData (
  databaseName: String,
  schemaName: Option[String],
  procedureName: String,
  parameterName: String,
  parameterDirection: Short,
  parameterDataType: Int,
  parameterSQLDataType: String,
  parameterPrecision: Int,
  parameterByteLength: Int,
  parameterScale: Short,
  parameterNullable: Boolean
)