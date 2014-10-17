package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.mvc.WebSocket.FrameFormatter

case class LocationReq(associateId: Int, departmentId: Int, site: Int, eventTime: String, eventType: Int)

object LocationReq {
  
  implicit val locationReqReads: Reads[LocationReq] = Json.reads[LocationReq]
  implicit val locationReqWrites: Writes[LocationReq] = Json.writes[LocationReq]
  
/*    // Json Read/Writes
  implicit val locationWrites: Writes[Location] = (
    (JsPath \ "associateId").write[Int] and
    (JsPath \ "departmentId").write[Int] and
    (JsPath \ "site").write[String] and
    (JsPath \ "eventTime").write[DateTime] and
    (JsPath \ "eventType").write[Int])(unlift(Location.unapply _)

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "associateId").read[Int] and
    (JsPath \ "departmentId").read[Int] and
    (JsPath \ "site").read[String] and
    (JsPath \ "eventTime").read[DateTime] and
    (JsPath \ "eventType").read[Int])(Location.apply _)*/

  implicit val locationReqFrame: Format[LocationReq] = Json.format[LocationReq]
  implicit val locationReqFrameFormatter: FrameFormatter[LocationReq] = FrameFormatter.jsonFrame[LocationReq]
}