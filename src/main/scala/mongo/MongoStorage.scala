package mongo

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.gridfs.Imports._

object MongoStorage {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("test")
  val mongoGridFS = GridFS(mongoDB)

  def init() = {
  }

  override def finalize() = {
    mongoConn.close()
    super.finalize()
  }

}
