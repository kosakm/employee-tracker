package actors

import akka.actor._
import models.Location
import play.api.Logger
import play.api.libs.json.Json

object LocationRetrieveActor {
    def props(out: ActorRef) = Props(new LocationRetrieveActor(out))
}

class LocationRetrieveActor(out: ActorRef) extends Actor {
  
  override def preStart() {
    Logger.info("Starting up a LocationRetrieveActor and subscribing to location events")
    context.system.eventStream.subscribe(context.self, classOf[Location])
  }

  override def postStop() {
    Logger.info("LocationRetrieveActor has been stopped")
  }
  
  def receive = {
    case location: Location => {
      Logger.info("Received location. Sending back to the client")
      out ! Json.obj("response" -> location)
    }
    case _ => Logger.error("Could not handle message in LocationRetrieveActor")
  }
}