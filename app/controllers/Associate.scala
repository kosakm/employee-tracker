package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import models.Location
import play.api.mvc.WebSocket.FrameFormatter
import actors.LocationReceiveActor

object Associate extends Controller {
  def ws = WebSocket.acceptWithActor[Location, JsValue] {request => out =>
		  LocationReceiveActor.props(out)
  }
  
  def test = Action {
    implicit request => Ok(views.html.sendLocation())
  }
}