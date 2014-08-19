package models


import scala.slick.jdbc.GetResult
import utils.Page

/**
 * Created by Administrator on 2014/8/7.
 */

/**
 * 帐号信息
 * @param id
 * @param username
 * @param password
 * @param isAdmin
 */
case class Account(id : Int,username:String,password:String,isAdmin:Boolean)


object Account extends BaseRepository[Account] {

    override val resultParser = GetResult[Account](r => Account(r.<<, r.<<, r.<<, r.<<))


    def allAccount():Seq[Account]={
      queryList(sql= "select * from t_account")
    }

    def accountListWithPage(page:Page):Seq[Account]={
      val sql = "select * from t_account limit ?,?"
      queryList(sql,(page.getStart(),page.pageSize))
    }


}

