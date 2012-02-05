package code.lib

import net.liftweb.http.JsonResponse
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{ InMemoryResponse, StreamingResponse }
import net.liftweb.http.S
import net.liftweb.http.FileParamHolder
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{ Box, Full }
import net.liftweb.http.BadResponse
import net.liftweb.util.StringHelpers

import mongo.MongoStorage

object UploadManager extends RestHelper {
  serve {
    case "uploading" :: Nil Post req => {
         def saveImage(fph: FileParamHolder) = {
         val imageName = StringHelpers.randomString(16)
                MongoStorage.mongoGridFS(fph.fileStream)(fh => 
                { fh.filename = imageName; fh.contentType = fph.mimeType })

                ("name" -> imageName) ~ ("type" -> fph.mimeType) ~ ("size" -> fph.length)
          }

          val ojv: Box[JValue] = req.uploadedFiles.map(fph => saveImage(fph)).headOption
          val ajv = ("name" -> "n/a") ~ ("type" -> "n/a") ~ ("size" -> 0L)
          val ret = ojv openOr ajv
         
          val jr = JsonResponse(ret).toResponse.asInstanceOf[InMemoryResponse]
          InMemoryResponse(jr.data, ("Content-Length", jr.data.length.toString) ::
            ("Content-Type", "text/plain") :: Nil, Nil, 200)
      }

    case "serving" :: imageName :: Nil Get req => {
      MongoStorage.mongoGridFS.findOne(imageName) match {
        case Some(image) =>
          val imageStream = image.inputStream
          StreamingResponse(imageStream, () => imageStream.close(), image.length, ("Content-Type", image.contentType) :: Nil, Nil, 200)
        case _ => new BadResponse
      }
    }
  }
}
