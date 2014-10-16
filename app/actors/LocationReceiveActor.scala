package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.Logger
import models.Location
import play.Logger

object LocationReceiveActor {
  def props(out: ActorRef) = Props(new LocationReceiveActor(out))
}

class LocationReceiveActor(out: ActorRef) extends Actor {
  val locNotifyActor = context.actorOf(Props[LocationNotifyActor], "LocationNotifyActor")
  val locPersistActor = context.actorOf(Props[LocationPersistActor], "LocationPersistActor")

  def receive = {
    case location: Location => {
      locNotifyActor ! location
      locPersistActor ! location     
    case _ => {
    }
  }

}