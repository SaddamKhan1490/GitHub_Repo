2> Best time to travel?

•	Idea would be the when ([CRSArrTime - CRSDepTime] === [ActualElapsedTime]) for deciding best travel times. Here we are not taking the delays in considering while calculating Best Travel Time


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

        // Pulling required columns from the input source

        val df = spark.sql(s"select Year, Month, DayofMonth, DayOfWeek, Arrival_Time, Departure_Time, ActualElapsedTime, (CRSArrTime - CRSDepTime) as AbsoluteElapsedTime from prd_fnd_air.summary_stats_2007")

        // Filtering records which qualify to be eligible for our computation of Best Travel Time for DayOfWeek

        val df_on_time = df.select("*").where(df("ActualElapsedTime") === df("AbsoluteElapsedTime"))


        // Calculating Best Travel Time for DayOfWeek

        val df_day_of_week = df_on_time.groupBy("DayOfWeek").agg(count("*").alias("cnt")).where($"cnt">0)
        val df_best_day_of_week = df_day_of_week.agg(max(df_day_of_week(df_day_of_week.columns(1))))

        // Storing Best Travel Time for DayOfWeek inside the temporary variable

        var temp = df_best_day_of_week.first

        // Displaying the Best Travel Time for DayOfWeek

        df_best_day_of_week.select("*").where(df("cnt") === temp).show()

        logger.info("Exiting spark session")

        exit()

# Note :
        Here we can apply the above equation to calculate Best travel time on any time frame. All we need to do is change the duration column i.e. day(DayofMonth)/day of week(DayOfWeek)/time of year(Year & Month), in the groupBy clause while calculating the count
        Here we can consider Median over Average as its relatively more resistant to outliers
