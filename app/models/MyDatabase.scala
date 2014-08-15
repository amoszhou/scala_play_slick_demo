package models

/**
 * Created by Administrator on 2014/8/11.
 */

import scala.slick.jdbc.JdbcBackend.Database
import play.api.Play.current

object MyDatabase {


  /**
   * init configuration from application.conf.
   * <b>Only user in play Application</b>
   */
  private val conf = current.configuration

  private val dbUrl = conf.getString("db.default.url").getOrElse("")

  private val user = conf.getString("db.default.user").getOrElse("root")

  private val pwd = conf.getString("db.default.password").getOrElse("root")

  private val driverClass = conf.getString("db.default.driver").getOrElse("com.mysql.jdbc.Driver")

  /**
   * create the database instance  with configuration
   */
  val database = Database.forURL(dbUrl, user, pwd, driver = driverClass)


}
