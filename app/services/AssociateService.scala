package services

import anorm._
import anorm.SqlParser._
import models.Associate
import play.api.db.DB
import play.api.Play.current

object AssociateService {

  val associateParser = get[Int]("associate_id") ~ get[String]("first_name") ~ get[String]("last_name") map {
    case associateId ~ firstName ~ lastName => new Associate(associateId, firstName, lastName)
  }

  def getAllAssociates(): List[Associate] = {
    val associates = DB.withConnection { implicit c =>
      SQL("""SELECT * 
             FROM associate""").as(associateParser *)
    }
    associates
  }

}