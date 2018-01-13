1> Which carrier performs better?

•	Idea would be to compute average absolute delay per record then group the data on "uniquecarrier" and get the carrier with minimum delay i.e. min (arrdelay+depdelay+carrierdelay+weatherdelay+nasdelay+securitydelay+lateaircraftdelay), per (uniquecarrier), as minimum delay signifies best performance

Assumption :

=> Here i'm considering -ve sums as early arrivals for flights i.e. arrival or departure before scheduled time

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

        // Pulling uniquecarrier & delay column from the input source

        val df_load = spark.read.option("header","true").csv("/user/data/raw/")
        val df = df_load.withColumn('absolute_delay', df.arrdelay+df.depdelay+df.carrierdelay+df.weatherdelay+df.nasdelay+df.securitydelay+df.lateaircraftdelay)
        or
        val df_load = sc.textFile("/user/data/raw/")
        val df = df_load.withColumn('absolute_delay', df.arrdelay+df.depdelay+df.carrierdelay+df.weatherdelay+df.nasdelay+df.securitydelay+df.lateaircraftdelay)
        or
        val df = spark.sql(s"SELECT uniquecarrier,(arrdelay+depdelay+carrierdelay+weatherdelay+nasdelay+securitydelay+lateaircraftdelay) as absolute_delay FROM prd_fnd_air.summary_stats_2007")

        // Finding minimum delay value

        val temp_df = df.groupBy("uniquecarrier").agg(avg($"absolute_delay").as("avg_absolute_delay_per_carrier"))
        val df_with_min_delay = temp_df.sort($"avg_absolute_delay_per_carrier".desc)

        // Storing inside the temporary variable

        var temp = df_with_min_delay.first

        // Displaying the uniquecarrier for with minimum delay i.e. carrier which perform better

        df_with_min_delay.select("*").where(df("absolute_delay") === temp).show()

            logger.info("Exiting spark session")

        exit()

# Note : 
        Here we can consider Median over Average as its relatively more resistant to outliers
