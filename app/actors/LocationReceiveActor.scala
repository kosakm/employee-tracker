package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import models.Location
import play.api.Logger
import models.LocationReq
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object LocationReceiveActor {
  def props(out: ActorRef) = Props(new LocationReceiveActor(out))
}

class LocationReceiveActor(out: ActorRef) extends Actor {
  val locNotifyActor = context.actorOf(Props[LocationNotifyActor], "LocationNotifyActor")
  val locPersistActor = context.actorOf(Props[LocationPersistActor], "LocationPersistActor")

  def receive = {
    case locationReq: LocationReq => {
      val location = Try(Location.locReqToLocation(locationReq))
      location match {
        case Success(location) => {
          locNotifyActor ! location
          locPersistActor ! location
        }
        case Failure(e) => Logger.error("Couldn't parse event date passed", e)
      }
    }
    case _ => Logger.info("Couldnt handle message in LocationReceiveActor")
  }

}