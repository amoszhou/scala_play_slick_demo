package controllers

import play.api.mvc.Controller
import models.Account
import play.api.mvc.Action
import play.api.libs.json._

/**
 * Created by Administrator on 2014/8/11.
 */
object AccountController extends Controller {

  implicit val AccountFormat = Json.format[Account]

  def listAll = Action {
    implicit request =>

      val account = Account(username="scala",password = "bbbb",isAdmin = false,id=1)
      Account.save(account)

      val list = Account.accountListWithPage(1,2)
      val json = Json.toJson(list)
      Ok(json).as("application/json")



  }
}
