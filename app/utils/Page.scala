package utils

/**
 * Created by Administrator on 2014/8/18.
 */
class Page {

  private var currPage = 1
  private var size = 10
  var totalCount = 0


  def currentPage = currPage

  def currentPage_=(page: Int) {
    if (page > 0) this.currPage = page
  }

  def pageSize = size

  def pageSize_=(size: Int){
    if (size > 0) this.size = size
  }


  /**
   * 获取总页数
   * @return
   */
  def getTotalPage(): Int = {
    if (totalCount % pageSize == 0) {
      return totalCount / pageSize
    }
    totalCount / size + 1
  }


  /** 数据库 select oo from xxx limit #start#, #limit# 中需要的 start 参数 */
  def getStart(): Int = {
    return (currPage - 1) * pageSize;
  }

}
