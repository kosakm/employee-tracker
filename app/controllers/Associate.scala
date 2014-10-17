package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import play.api.mvc.WebSocket.FrameFormatter
import actors.LocationReceiveActor
import models.LocationReq

/**
 * End point for Associate devices to receive location updates
 */
object Associate extends Controller {
  def ws = WebSocket.acceptWithActor[LocationReq, JsValue] {request => out =>
		  LocationReceiveActor.props(out)
  }
  
  def test = Action {
    implicit request => Ok(views.html.sendLocation())
  }
}