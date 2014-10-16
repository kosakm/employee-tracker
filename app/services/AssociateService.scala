package services

import anorm._
import anorm.SqlParser._
import models.Associate

object AssociateService {

    val associateParser = get[Int]("associate_id") ~ get[String]("first_name") ~ get[String]("last_name") map {
	    case associateId ~ firstName ~ lastName => new Associate(associateId, firstName, lastName)
    }
  
}