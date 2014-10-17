package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.WebSocket.FrameFormatter
import models._

case class AssociateLocation(location: Location, associate: Associate)

object AssociateLocation {
  implicit val locationReads: Reads[AssociateLocation] = Json.reads[AssociateLocation]
  implicit val locationWrites: Writes[AssociateLocation] = Json.writes[AssociateLocation]

  implicit val locationFrame: Format[AssociateLocation] = Json.format[AssociateLocation]
  implicit val locationFrameFormatter: FrameFormatter[AssociateLocation] = FrameFormatter.jsonFrame[AssociateLocation]
}