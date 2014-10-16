package models

import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter
import org.joda.time.DateTime


case class Location(associateId: Int, departmentId: Int, site: String, eventTime: DateTime, eventType: Int)

object Location {
  
  implicit val locationReads: Reads[Location] = Json.reads[Location]
  implicit val locationWrites: Writes[Location] = Json.writes[Location]
  
  implicit val locationFrame: Format[Location] = Json.format[Location]
  implicit val locationFrameFormatter: FrameFormatter[Location] = FrameFormatter.jsonFrame[Location]
}