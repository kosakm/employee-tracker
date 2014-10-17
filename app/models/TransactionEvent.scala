package models

import play.api.libs.json._
import play.api.mvc.WebSocket.FrameFormatter

case class TransactionEvent(deptId: Int, amount: Double)

object TransactionEvent {
  implicit val transactionReads: Reads[TransactionEvent] = Json.reads[TransactionEvent]
  implicit val transactionWrites: Writes[TransactionEvent] = Json.writes[TransactionEvent]
  
  implicit val transactionFrame: Format[TransactionEvent] = Json.format[TransactionEvent]
  implicit val transactionFrameFormatter: FrameFormatter[TransactionEvent] = FrameFormatter.jsonFrame[TransactionEvent]
}