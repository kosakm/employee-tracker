package models

import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter

case class DepartmentTotal(deptId: Int, total: Int)

object DepartmentTotal {
  implicit val transactionReads: Reads[DepartmentTotal] = Json.reads[DepartmentTotal]
  implicit val transactionWrites: Writes[DepartmentTotal] = Json.writes[DepartmentTotal]
  
  implicit val transactionFrame: Format[DepartmentTotal] = Json.format[DepartmentTotal]
  implicit val transactionFrameFormatter: FrameFormatter[DepartmentTotal] = FrameFormatter.jsonFrame[DepartmentTotal]

}