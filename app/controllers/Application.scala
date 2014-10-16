package controllers

import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def tracker = Action { implicit request =>
    Ok(views.html.employeeTracking())
  }
}