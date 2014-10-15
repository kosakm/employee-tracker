package actors

import akka.actor.Actor
import play.api.Logger

class LocationNotifyActor extends Actor {
  def receive = {
    case msg: String =>
      Logger.debug(msg)
  }
}