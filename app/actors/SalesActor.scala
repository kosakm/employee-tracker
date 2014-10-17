package actors

import akka.actor.Actor
import play.api.Logger
import akka.event.EventStream
import models.TransactionEvent
import scala.concurrent.duration._
import akka.actor.Props
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.immutable.{HashSet, Queue}
import akka.actor.ActorRef
import play.libs.Akka

class SalesActor(dept: Int) extends Actor {
    
    override def preStart() {
    	Logger.info("Starting SalesActor");
    }
  
  // Fetch the latest stock value every 750ms
  val salesTick = context.system.scheduler.schedule(Duration.Zero, 750.millis, self, FetchLatest)

  def receive = {
    case FetchLatest =>
      // add a new stock price to the history and drop the oldest
      val newSale = Math.random() * 100;
      Logger.info(s"new sale: $dept, $newSale");
      context.system.eventStream.publish(TransactionEvent(dept, newSale))
    case _ => Logger.error("Couldn't handle message in SalesNotifyActor")
  }
}

case object FetchLatest