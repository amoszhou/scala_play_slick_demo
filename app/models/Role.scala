package models

import scala.slick.jdbc.GetResult

/**
 * 角色
 */
case class Role(id:Int,name:String)

object Role extends BaseRepository[Role]{

  override implicit val resultParser: GetResult[Role] = GetResult[Role](r => Role(r.<<, r.<<))

}


/**
 * 角色-权限关联
 * @param id
 * @param roleId
 * @param authId
 */
case class RoleAuth(id:Int,roleId:Int,authId:Int)

object RoleAuth extends BaseRepository[RoleAuth]{
  override implicit val resultParser: GetResult[RoleAuth] = GetResult[RoleAuth](r => RoleAuth(r.<<, r.<<,r.<<))
}