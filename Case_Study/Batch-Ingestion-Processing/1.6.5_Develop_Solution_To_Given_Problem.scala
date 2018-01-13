5> Create a model to predict flight delays?

•	 Idea here is to append flag column to the existing hive table where the column will have the value either 0(for flights without delay i.e. DepTime === CRSDepTime AND ArrTime === CRSArrTime ) or 1(for flights with delay i.e. DepTime =!= CRSDepTime AND ArrTime =!= CRSArrTime). Later can aggregate the value of flag column per flight and thereby apply filter condition to get the probability of delay for the given flight

Assumption :

=> FlightNum is specific to plane i.e. FlightNum remains constant irrespective of day of flight

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

val df = spark.sql(s"SELECT FlightNum, Origin, Dest, DepTime, ArrTime, CRSDepTime, CRSArrTime  as from prd_fnd_air.summary_stats_2007")

// Categorising flights on basis of delay

val df_temp1 = df.select("*").where(df.DepTime >= df.CRSDepTime AND df.ArrTime >= df.CRSArrTime) 
val df_temp2 = df.select("*").where(df.DepTime =!= df.CRSDepTime AND df.ArrTime =!= df.CRSArrTime)

// Appending extra identification flag i.e. 1 for on - time flights & 0 for delayed

df_temp1.withColumn("Flag", lit(1))
df_temp2.withColumn("Flag", lit(0))

// Aggregating flag_sum & flight_occourrence in order to calculate probability

val df_temp1_flag_sum = df1.groupBy("FlightNum").agg(sum("Flag").alias("Flag_Sum")).agg(count("*").alias("Occurrence"))
val df_temp2_flag_sum = df2.groupBy("FlightNum").agg(sum("Flag").alias("Flag_Sum")).agg(count("*").alias("Occurrence"))

// Computing probability per flight

df_temp1_flag_sum.withColumn("On_Time_Probability", $"Flag_Sum" / $"Occurrence")
df_temp2_flag_sum.withColumn("On_Time_Probability", $"Flag_Sum" / $"Occurrence")

// Combining DataFrame
val df_flights_with_on_time_probability =  df_temp1_flag_sum.union(df_temp2_flag_sum)
or
val df_flights_with_on_time_probability <-  unionAll(df_temp1_flag_sum,df_temp2_flag_sum) # For combining more than two dataFrames

// Sorting dataFrame on On_Time_Probability column

df_flights_with_on_time_probability.sort($"On_Time_Probability".desc)

// Saving the output as paraquet under the modelled directory

df_flights_with_on_time_probability.coalesce(1).write.parquet("/user/data/modelled/csv_to_paraquet")

    logger.info("Exiting spark session")

exit()

Note :

Here we can also fit in Logistic Regression model to compute the probability of flight delay
