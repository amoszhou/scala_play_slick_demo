package controllers

import play.api.mvc.Controller
import models.Account
import play.api.mvc.Action
import play.api.libs.json._
import utils.Page

/**
 * Created by Administrator on 2014/8/11.
 */
object AccountController extends Controller {

  implicit val AccountFormat = Json.format[Account]

  def listAll(currPage: Int, pageSize: Int) = Action {
    implicit request =>
      val page = new Page
      page.currentPage = currPage
      page.pageSize = pageSize
      val list = Account.accountListWithPage(page)
      val json = Json.toJson(list)
      Ok(json).as("application/json")

  }
}
