package models

import play.api.libs.json._

case class Associate(associateId: Int, firstName: String, lastName: String)

object Associate {
  implicit val associateReads: Reads[Associate] = Json.reads[Associate]
  implicit val associateWrites: Writes[Associate] = Json.writes[Associate]
}