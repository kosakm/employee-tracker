package actors

import akka.actor.Actor
import play.api.Logger
import models.Location
import services.LocationService

class LocationPersistActor extends Actor {
  def receive = {
    case location: Location =>
      Logger.info(s"Creating Location Record. Associate ID: ${location.associateId}  Event Time: ${location.eventTime}  EventType:${location.eventType}")
      LocationService.create(location)
  }
}