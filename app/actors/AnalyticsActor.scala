package actors

import akka.actor._
import models.Location
import play.api.Logger
import play.api.libs.json.Json
import scala.concurrent.Future
import services.LocationService
import scala.concurrent.ExecutionContext.Implicits.global
import models.TransactionEvent
import models.DepartmentTotal

object AnalyticsActor {
    def props(out: ActorRef) = Props(new AnalyticsActor(out))
}

class AnalyticsActor(out: ActorRef) extends Actor {
  val deptActor = context.actorOf(Props(new DepartmentTotalsActor(25)), "DeptActor")
  val salesActor = context.actorOf(Props(new SalesActor(25)), "SalesActor")
  
  override def preStart() {
    Logger.info("Starting up an Analytics and subscribing to transaction events")
    context.system.eventStream.subscribe(context.self, classOf[TransactionEvent])
    context.system.eventStream.subscribe(context.self, classOf[DepartmentTotal])
  }

  override def postStop() {
    Logger.info("Analytics has been stopped")
  }
  
  def receive = {
    case sale: TransactionEvent => {
      Logger.info("Received sales. Sending back to the client")
      out ! Json.obj("response" -> sale)
    }
    case deptTotal: DepartmentTotal => {
      Logger.info("Received dept total. Sending back to the client")
      out ! Json.obj("response" -> deptTotal)
    }
    case _ => Logger.error("Could not handle message in AnalyticsActor")
  }
}