package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import models.Location
import play.api.Logger
import models.LocationReq

object LocationReceiveActor {
  def props(out: ActorRef) = Props(new LocationReceiveActor(out))
}

class LocationReceiveActor(out: ActorRef) extends Actor {
  val locNotifyActor = context.actorOf(Props[LocationNotifyActor], "LocationNotifyActor")
  val locPersistActor = context.actorOf(Props[LocationPersistActor], "LocationPersistActor")

  def receive = {
    case locationReq: LocationReq => {
      val location = Location.locReqToLocation(locationReq)
      locNotifyActor ! location
      locPersistActor ! location  
    }
    case _ => Logger.info("Couldnt handle message in LocationReceiveActor")
  }

}