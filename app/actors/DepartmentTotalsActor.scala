package actors

import akka.actor.Actor
import akka.actor.ActorRef
import play.libs.Akka
import play.api.Logger
import akka.event.EventStream
import scala.concurrent.duration._
import akka.actor.Props
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import models.Location
import models.DepartmentTotal
import services.LocationService


class DepartmentTotalsActor(dept: Int) extends Actor {
    var currTotal: Int = 0;

    override def preStart() {
    	currTotal = LocationService.getTotalEmployeesForDept(dept)
    	Logger.info(s"Starting DepartmentTotals with a total of $currTotal");
    }
  
  // Fetch the latest stock value every 750ms
  val totalsTick = context.system.scheduler.schedule(Duration.Zero, 750.millis, self, FetchLatest)
  
  def receive = {
    case FetchLatest =>
      Logger.info(s"new total: $dept, $currTotal");
      context.system.eventStream.publish(DepartmentTotal(dept, currTotal))
    case location: Location => {
      if(location.departmentId == dept) {
        //Employee enters
        if(location.eventType == 0)
          currTotal = currTotal + 1;
        else
          currTotal = currTotal - 1;
      }
        context.system.eventStream.publish(DepartmentTotal(dept, currTotal))
    }
    case _ => Logger.error("Couldn't handle message in SalesNotifyActor")
  }
}

case object FetchLatest