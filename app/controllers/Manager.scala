package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import play.api.mvc.WebSocket.FrameFormatter
import actors.LocationRetrieveActor

object Manager extends Controller {
  def ws = WebSocket.acceptWithActor[String, JsValue] {request => out =>
		  LocationRetrieveActor.props(out)
  }

  def test = Action {
    implicit request => Ok(views.html.receiveLocation())
  }
}