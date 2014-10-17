package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current
import models.Location
import play.api.mvc.WebSocket.FrameFormatter
import actors.LocationReceiveActor
import actors.AnalyticsActor
import models.TransactionEvent

object Analytics extends Controller {
    def ws = WebSocket.acceptWithActor[JsValue, TransactionEvent] {request => out =>
		  AnalyticsActor.props(out)
  }
  
  def test = Action {
    implicit request => Ok(views.html.analytics())
  }
}