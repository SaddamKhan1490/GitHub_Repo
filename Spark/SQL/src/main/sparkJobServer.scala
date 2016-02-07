/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

import org.apache.log4j.{Level, Logger}
import com.typesafe.config.Config
import org.apache.spark.SparkContext, SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark._



// Extending SparkJob trait 
trait UsersSparkJob extends spark.jobserver.SparkJob with spark.jobserver.NamedRddSupport with UsersRDDBuilder {
  val rddName = "users"
println("Start...")

// Implementing validate Function of SparkJob for doing sanity check of submitted jobs
  def validate(sc: SparkContext, config: Config): spark.jobserver.SparkJobValidation = spark.jobserver.SparkJobValid
}

// Implementing runJob Function of SparkJob for running the jobs
object GetOrCreateUsers extends UsersSparkJob {
  override def runJob(sc: SparkContext, config: Config) = {
    val users: RDD[(Reputation, User)] = namedRdds.getOrElseCreate(
      rddName,
      build(sc))
    users.take(5)
  }
  println("End...")
}
