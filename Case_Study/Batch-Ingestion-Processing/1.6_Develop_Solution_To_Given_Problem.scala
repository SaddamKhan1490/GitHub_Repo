================================================================================================================================================================
Solutions for the questionairs:-
================================================================================================================================================================

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

=====================================================================================================================================================================

2> Best time to travel?

•	Idea would be the when ([Arrival_Time - Departure_Time] === [ActualElapsedTime]) for deciding best travel times. Here we are not taking the delays in considering while calculating Best Travel Time


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

        val df = spark.sql(s"select Year, Month, DayofMonth, DayOfWeek, Arrival_Time, Departure_Time, ActualElapsedTime, (Arrival_Time - Departure_Time) as absoluteElapsedTime from prd_fnd_air.summary_stats_2007")

        // Filtering records which qualify to be eligible for our computation of Best Travel Time for DayOfWeek

        val df_on_time = df.select("*").where(df("ActualElapsedTime") === df("absoluteElapsedTime"))

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

=====================================================================================================================================================================

3> Do older planes suffer more delays?

•	Idea here would be to divide the planes into two categories and compare there resulting average

Assumption :

=> As there is no column directly reflecting age of plane therefore taking "FlightNum" as column reflecting "age of the plane"
=> Planes with smaller "FlightNum" are older and planes with higher "FlightNum" are newly added once i.e. age of plane is inversely proportional to absolute value of "FlightNum" column
=> Classifying the planes into "Newer & Older" category based on "FlightNum" i.e. Planes with "FlightNum" lower than 1000 are considered as older once and those with "FlightNum" greater than or equal to 1000 are considered as newer planes


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

        val df = spark.sql(s"select FlightNum, (arrdelay+depdelay+carrierdelay+weatherdelay+nasdelay+securitydelay+lateaircraftdelay) as absolute_delay from prd_fnd_air.summary_stats_2007")

        // Calculating average delay per flight

        val temp_df = df.groupBy("FlightNum").agg(avg($"absolute_delay").as("avg_absolute_delay_per_flight"))

        // Segregating input data into two halfs i.e. older_planes & newer_planes, based on FlightNum

        val old_planes_df = temp_df.where($"FlightNum"<1000)
        val new_planes_df = temp_df.where($"FlightNum">==1000)

        // Computing average delay per plane type

        val old_planes_avg_delay_df = old_planes_df.agg(avg($"absolute_delay").as("avg_absolute_delay_old_flight"))
        val new_planes_avg_delay_df = new_planes_df.agg(avg($"absolute_delay").as("avg_absolute_delay_new_flight"))

        // Storing final average per plane type inside temporary variables

        var old_planes_avg_delay = old_planes_avg_delay_df.select("absolute_delay").head.getString(1)
        var new_planes_avg_delay = new_planes_avg_delay_df.select("absolute_delay").head.getString(1)

        // Comparing averages and printing final result

        if(old_planes_avg_delay > new_planes_avg_delay)
          println("Yes Older Planes Suffer MORE Delay")
        else
          println("Yes Older Planes Suffer LESS Delay")

          logger.info("Exiting spark session")

        exit()

# Note : 
        Here we can consider Median over Average as its relatively more resistant to outliers

================================================================================================================================================================
