package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import play.api.Logger

object LocationReceiveActor {
  def props(out: ActorRef) = Props(new LocationReceiveActor(out))
}

class LocationReceiveActor(out: ActorRef) extends Actor {
  val locNotifyActor = context.actorOf(Props[LocationNotifyActor], "LocationNotifyActor")
  val locPersistActor = context.actorOf(Props[LocationNotifyActor], "LocationPersistActor")

  def receive = {
    case _ =>
      Logger.debug("Received a location update!")
      locNotifyActor ! "Your notified of an associates location event"
      locPersistActor ! "Please persist this location event"
  }

}