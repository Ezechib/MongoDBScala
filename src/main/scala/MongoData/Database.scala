package MongoData
import MongoData.Helpers._
import org.mongodb.scala._

import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
object Database extends App {

  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("mydb")
  val collection: MongoCollection[Document] = database.getCollection("test")

  val doc: Document = Document( "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  collection.insertOne(doc).results()

  val observable: Observable[Completed] = collection.insertOne(doc)

  observable.subscribe(new Observer[Completed] {

    override def onNext(result: Completed): Unit = println("Inserted")

    override def onError(e: Throwable): Unit = println("Failed")

    override def onComplete(): Unit = println("Completed")
   // collection.deleteOne(equal("i", 5)).printHeadResult("Delete Result: ")

   // collection.deleteMany(gte("name", "MongoDB")).printHeadResult("Delete Result: ")
    collection.find().first().printHeadResult()

    collection.find().printResults()

  collection.updateOne(equal("name", "MongoDB"), set("name", "sql")).printHeadResult("Update Result: ")
//
//    collection.deleteOne(equal("i", 4)).printHeadResult("Delete Result: ")

  })
}
