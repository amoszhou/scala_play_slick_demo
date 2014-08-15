package utils

/**
 * Created by Administrator on 2014/8/14.
 */
object JDBCUtils {

    val DEFAULT_PRIMARY_KEY = "id"
    val DEFAULT_TABLE_PREFIX = "T_"

    def convertStrToDBFormat(str:String):String={
      str.replaceAll("[a-z](?=[A-Z]+)","$0_").toUpperCase()
    }
}
