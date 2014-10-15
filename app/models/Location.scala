package models

import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter


case class Location(uuid: String, department: String, site: String)

object Location {
  implicit val locationReads: Reads[Location] = Json.reads[Location]
  implicit val locationWrites: Writes[Location] = Json.writes[Location]
  
  implicit val locationFrame: Format[Location] = Json.format[Location]
  implicit val locationFrameFormatter: FrameFormatter[Location] = FrameFormatter.jsonFrame[Location]
}