package models

import scala.slick.jdbc.GetResult

/**
 * Created by Administrator on 2014/8/19.
 */
case class Auth (id:Int,name:String,path:String)

object Auth extends BaseRepository[Auth]{
  override implicit val resultParser: GetResult[Auth] =  GetResult[Auth](r => Auth(r.<<, r.<<,r.<<))
}
