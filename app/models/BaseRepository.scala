package models

import scala.slick.jdbc._
import scala.slick.jdbc.SetParameter.SetSimpleProduct
import java.lang.reflect.ParameterizedType
import utils.JDBCUtils._
import play.Logger
import java.sql._

/**
 * Created by Administrator on 2014/8/11.
 */
trait BaseRepository[T] {

  /**
   * 得到子类的泛型上的泛型类型，也就是T的具体类型
   */
  val parameterClass = this.getClass.getGenericInterfaces()(0).asInstanceOf[ParameterizedType].getActualTypeArguments()(0).asInstanceOf[Class[T]]
  /**
   * 根据实体类型，根据T_实体名的规则得到表名
   */
  val tableName = DEFAULT_TABLE_PREFIX + parameterClass.getSimpleName

  private val mdb = MyDatabase.database

  /**
   * 结果解析器申明为抽象类,让具体的Repository对象去实现
   */
  implicit val resultParser: GetResult[T]


  /**
   * 基础类型参数设置器
   */
  implicit val simpleSetter = SetSimpleProduct


  /**
   * 查询，不带参数，有SQL注入问题，请勿拼接参数
   * <em>select * from table</em>
   * @param sql
   * @return
   */
  def queryList(sql: String): Seq[T] = {
    mdb.withSession {
      implicit session =>
        StaticQuery.queryNA[T](sql).list()
    }
  }


  /**
   * 查询列表信息，带参数，参数以Tuple的形式传入进来
   * @param sql
   * @param param
   * @tparam R
   * @return
   */
  def queryList[R <: Product](sql: String, param: R): Seq[T] = {
    implicit val paramSetter: SetParameter[R] = new SetTupleParameter(simpleSetter)
    mdb.withSession {
      implicit session =>
        StaticQuery.query[R, T](sql).list(param)
    }
  }

  /**
   * 不分页，查询所有
   * @return
   */
  def queryAll():Seq[T]={
    val sql = "select * from " + tableName
    queryList(sql)
  }

  /**
   * 分页，查询所有
   * @param startIndex
   * @param pageSize
   * @return
   */
  def queryAll(startIndex:Int,pageSize:Int):Seq[T]={
    val sql = "select * from " + tableName + " limit ?,?"
    queryList(sql,(startIndex,pageSize))
  }




  /**
   * 保存一个对象，必须遵循如下几条要求：
   * <ul>
   *    <li>1.表名格式为：T_实体名</li>
   *    <li>2.列表格式为：属性名驼峰转下划线方式</li>
   *    <li>3.都有代理主键，且列名为ID</li>
   * </ul>
   *
   * Example:
   *    Account=>T_account
   *    isAdmin=>is_admin
   * @param t
   */
  def save(t: T) {
    /**
     * 获取泛型上的Class
     */
    val parameterClass = this.getClass.getGenericInterfaces()(0).asInstanceOf[ParameterizedType].getActualTypeArguments()(0).asInstanceOf[Class[T]]
    val tableName = DEFAULT_TABLE_PREFIX + parameterClass.getSimpleName

    //过滤掉id，主键都是自增长，不需要插入
    val fields = parameterClass.getDeclaredFields.filterNot(_.getName.equalsIgnoreCase(DEFAULT_PRIMARY_KEY))

    //拿到所有的列
    val columns = fields.map(f => convertStrToDBFormat(f.getName))

    //拿到所有列的值
    val columnValues = fields.map(f => parameterClass.getDeclaredMethod(f.getName).invoke(t))

    implicit val paramSetter = SetObjectParam

    /**
     * 将所有的Value将为2部分（最后一个，和之前所有）
     */
    val valueWithoutLast = columnValues.slice(0, columnValues.length - 1)
    //    val last = columnValues.last

    val sql = "insert into " + tableName + " ( " + columns.reduceLeft(_ + "," + _) + " ) values  ( "
    val insert = valueWithoutLast.foldLeft(StaticQuery.u + sql)(_ +? _ + ",") +? columnValues.last + ")"

    mdb.withSession {
      implicit session =>
        insert.execute()
    }
  }


  /**
   * 执行更新SQL,不带参数
   * @param sql
   */
  def update(sql:String){
    mdb.withSession {
      implicit session =>
        StaticQuery.updateNA(sql)
    }
  }

  /**
   * 执行更新SQL,带参数，参数以Tuple的形式传入
   * @param sql
   * @param args
   * @tparam R
   */
  def update[R<:Product](sql:String,args :R){
    implicit val paramSetter: SetParameter[R] = new SetTupleParameter(simpleSetter)
    mdb.withSession {
      implicit session =>
        StaticQuery.update[R](sql).execute(args)
    }
  }



  /**
   * 适用于在数据库PreperedStatement在设置参数时，参数类型是Object时使用
   */
  object SetObjectParam extends SetParameter[Object] {
    override def apply(o: Object, pp: PositionedParameters): Unit = o match {
      case v: Object => {
        if (v.isInstanceOf[Boolean]) pp.setBoolean(v.asInstanceOf[Boolean])
        if (v.isInstanceOf[String]) pp.setString(v.asInstanceOf[String])
        if (v.isInstanceOf[Int]) pp.setInt(v.asInstanceOf[Int])
        if (v.isInstanceOf[Byte]) pp.setByte(v.asInstanceOf[Byte])
        if (v.isInstanceOf[Date]) pp.setDate(v.asInstanceOf[Date])
        if (v.isInstanceOf[Double]) pp.setDouble(v.asInstanceOf[Double])
        if (v.isInstanceOf[Float]) pp.setFloat(v.asInstanceOf[Float])
        if (v.isInstanceOf[Long]) pp.setLong(v.asInstanceOf[Long])
        if (v.isInstanceOf[Short]) pp.setShort(v.asInstanceOf[Short])
        if (v.isInstanceOf[Time]) pp.setTime(v.asInstanceOf[Time])
        if (v.isInstanceOf[Timestamp]) pp.setTimestamp(v.asInstanceOf[Timestamp])
      }
      case v: Product => SetSimpleProduct.apply(v, pp)
    }
  }

}
