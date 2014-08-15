package models


import scala.slick.jdbc.GetResult

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

    def accountListWithPage(currPage:Int,pageSize:Int):Seq[Account]={
      val sql = "select * from t_account where username = ? limit ?"
      queryList(sql,Tuple2("bb",2))
    }



}

