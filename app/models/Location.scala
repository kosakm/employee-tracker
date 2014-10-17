package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.mvc.WebSocket.FrameFormatter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import scala.util.Try
import scala.util.Failure
import play.api.Logger
import scala.util.Success


case class Location(associateId: Int, departmentId: Int, site: String, eventTime: DateTime, eventType: Int)

object Location {
  
  def locReqToLocation(locationReq: LocationReq): Location = {
      val date = stringToDateTime(locationReq.eventTime)
      Location(locationReq.associateId, locationReq.departmentId, locationReq.site, date, locationReq.eventType)
  }
  
  private def stringToDateTime(str: String): DateTime = {
      val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
      formatter.parseDateTime(str);
  }
  
  implicit val locationReads: Reads[Location] = Json.reads[Location]
  implicit val locationWrites: Writes[Location] = Json.writes[Location]
  
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

  implicit val locationFrame: Format[Location] = Json.format[Location]
  implicit val locationFrameFormatter: FrameFormatter[Location] = FrameFormatter.jsonFrame[Location]
}