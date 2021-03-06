package services

import models.Location
import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import java.util.Date
import models.AssociateLocation
import models.Associate
import org.joda.time.DateTime
import java.sql.Timestamp
import play.api.Logger


object LocationService {
  val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
  
  val employeeLocationParser = get[Int]("associate_id") ~ get[Int]("department_id") ~ get[Int]("site_id") ~ get[Date]("event_time") ~ get[Int]("event_type") ~ AssociateService.associateParser map {
	  case associateId ~ departmentId ~ site ~ eventTime ~ eventType ~ associate =>  AssociateLocation(Location(associateId, departmentId, site, new DateTime(eventTime), eventType), Associate(associate.associateId, associate.firstName, associate.lastName))
  }
  
  def create(location: Location) = {
    val id: Option[Long] = DB.withConnection{ implicit c => 
      SQL("""INSERT INTO location_event(associate_id, department_id, site_id, event_time, event_type) values ({associate_id}, {department_id},{site},{event_time},{event_type})""")
      .on("associate_id" -> location.associateId, "department_id" -> location.departmentId, "site" -> location.site, "event_time" -> new Timestamp(location.eventTime.toDate().getTime()), "event_type" -> location.eventType)
      .executeInsert()
    }
  }
  
  def getAssociateLocationEvents(associateId: Int) = {
    val employeeLocations = DB.withConnection{ implicit c => 
      SQL("""SELECT loc.*, ass.first_name, ass.last_name 
             FROM location_event loc,
	                associate ass
             WHERE loc.associate_id = ass.associate_id AND
	                 ass.associate_id = {associate_id}
             ORDER BY loc.event_time desc""")
      .on("associate_id" -> associateId).as(employeeLocationParser *)
    }
    employeeLocations
  }
  
  def getCurrentLocations(): List[AssociateLocation] = {
    Logger.info("Getting current Locations")
    val associates = AssociateService.getAllAssociates()
    Logger.info(s"Found ${associates.length} associates")
    val associateLocations = getCurrentLocations(associates)
    Logger.info(s"Returning current Locations $associateLocations")
    associateLocations
  }
  
  def getCurrentLocations(associates: List[Associate]) = {
    val x = associates.map { associate =>
      val associateLocEvents = getAssociateLocationEvents(associate.associateId)
      associateLocEvents.headOption
    }
    x.flatten
  }
  
  def getTotalEmployeesForDept(deptId: Int) = {
    val currentLocations = getCurrentLocations();
    currentLocations.count(x => x.location.departmentId == deptId);
  }
}