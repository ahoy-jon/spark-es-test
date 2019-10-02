

package io.univalence.quicky

import org.apache.spark.sql.SparkSession


case class Person(name:String, age:Int)

object Es_Step1_Write {

  def main(args: Array[String]): Unit = {

    val ss = SparkSession.builder().appName("test").master("local").getOrCreate()


    import ss.implicits._
    import org.elasticsearch.spark.sql._


    Seq(Person("toto",13)).toDS.saveToEs("spark/person", Map("es.nodes" -> "localhost"))


    ss.close()
  }
}

object Es_Step2_Read {

  def main(args: Array[String]): Unit = {

    val ss = SparkSession.builder().appName("test").master("local").getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")



    import ss.implicits._

    val df = ss.read.format("org.elasticsearch.spark.sql").load("spark/person")


    df.show()


    //Doesn't work, need to cast first bigint to int
    //df.as[Person].filter(_.name == "toto").collect().foreach(println)

    ss.close()

  }
}