package actors

import akka.actor.Actor
import play.api.Logger
import akka.event.EventStream
import models.Location

class LocationNotifyActor extends Actor {
  val Enter = 0
  val Exit = 1
  def receive = {
    case location: Location => 
      Logger.info("Publishing location message " + location)
      context.system.eventStream.publish(location)  
    case _ => Logger.error("Couldn't handle message in LocationNotifyActor")
  }
}