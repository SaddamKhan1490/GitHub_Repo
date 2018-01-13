4> Detect cascading failures?


•	 Idea would be to identify the flights who have common FlightNum && [source === destination] && [source_departure_time > destination_arrival_time] && [source_departure_time > scheduled_source_departure_time] && [destination_arrival_time > scheduled_destination_arrival_time]

Assumption:

=> Here we are considering only the flights which are in delayed state i.e. no on time flight is considered [source_departure_time === scheduled_source_departure_time] && [destination_arrival_time === scheduled_destination_arrival_time]

        import spark.sql
        import spark.implicits._
        import org.slf4j.LoggerFactory
        import com.typesafe.config._
        import org.apache.spark.sql._
        import org.apache.spark.sql.functions._
        import org.apache.spark.SparkConf
        import org.apache.spark.SparkContext
        import org.apache.spark.sql.Row
        import org.apache.spark.sql.SparkSession
        import org.apache.spark.sql.functions.count

        val warehouseLocation = "spark-warehouse"

            logger.info("Creating spark session")

        val spark = SparkSession.builder().appName("Data modelling").config("spark.sql.shuffle.partitions", "some-value").getOrCreate()
            spark.conf.set("spark.authenticate", "true")
            spark.conf.set("spark.authenticate.secret", "good")
            spark.conf.set("spark.sql.broadcastTimeout",1200)

            logger.info(s"Successfully created spark session. appName=${spark.sparkContext.appName}, master= ${spark.sparkContext.master}")

        // Filtering the data based on above mentioned cascading condition at the time of pulling required columns from the input source

        val df = spark.sql(s"SELECT t1.FlightNum, t1.Origin, t1.Dest, t1.DepTime, t1.ArrTime, t1.CRSDepTime, t1.CRSArrTime  as from prd_fnd_air.summary_stats_2007 t1, prd_fnd_air.summary_stats_2007 t2 WHERE t1.FlightNum = t2.FlightNum AND t1.Dest = t2.Origin AND t1.Origin = t2.Dest AND t1.DepTime > t2.ArrTime AND t1.DepTime > CRSDepTime AND t1.ArrTime > t1.CRSArrTime ")

        // Saving the output as paraquet under the modelled directory

        df.coalesce(1).write.parquet("/user/data/modelled/csv_to_paraquet")

            logger.info("Exiting spark session")

        exit()

# Note:
        Here we also add further fine tune the query by adding extra filter conditions while fetching the data while considering delays: DepTime =!= CRSDepTime AND ArrTime =!= CRSArrTime AND ArrDelay >= (carrierdelay+weatherdelay+nasdelay+securitydelay+lateaircraftdelay). But the idea here is to keep the query very clear & simple
        Here we are taking in consideration only the consolidated delays : ActualElapsedTime & LateAircraftDelay (i.e. excluding other time factors such as : AirTime, ArrDelay, DepDelay, etc... )to identify wether the delay of one flight is affecting the delay for other flight
        Here we can also first pull the data as we did in previous cases and later use Spark API to filter and apply transformations and actions on the data in case this data serve in common for other source queries, but here the requirement is very specific so using HiveSQL
