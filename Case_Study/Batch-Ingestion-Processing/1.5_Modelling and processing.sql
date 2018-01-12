================================================================================================================================================================
# Modelling and processing :-
================================================================================================================================================================

•	Using Spark API:-
-------------------

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

// Create dataframe out of given input raw file
val df = spark.read.option("header","true").csv("/user/data/raw/")
or
val df = sc.textFile("/user/data/raw/")

// Removing Nulls & Whitespaces

var notNullDf = df
var newDf = df

  for(col <- df.columns){
    notNullDf = DF.where(DF("col").isNotNull)
    newDf = trimedDF.withColumnRenamed(col,col.replaceAll("\\s", "_"))
  }

// Trimming column values

var trimedDF = newDf
for(col <- df.columns){
    trimedDF = newDf.select(col).rdd.map(x => x.trim)).toDF
}

// Removing duplicate values

val distinctDf = trimedDF.distinct

// Saving the output as paraquet under the modelled directory

distinctDf.coalesce(1).write.parquet("/user/data/modelled/csv_to_paraquet")

    logger.info("Exiting spark session")

exit()

================================================================================================================================================================
•	 Using HiveSQL:-
------------------

# Definition of the Hive Table

      CREATE TABLE prd_fnd_air.summary_stats_2007(
            Year	 int COMMENT '1987-2008',
            Month	 int COMMENT '1-12',
            DayofMonth  int COMMENT	'1-31',
            DayOfWeek	 int COMMENT '1 (Monday) - 7 (Sunday)',
            DepTime	 string COMMENT 'actual departure time (local, hhmm)',
            CRSDepTime	 string COMMENT 'scheduled departure time (local, hhmm)',
            ArrTime	 string COMMENT 'actual arrival time (local, hhmm)',
            CRSArrTime	 string COMMENT 'scheduled arrival time (local, hhmm)',
            UniqueCarrier	 string COMMENT 'unique carrier code',
            FlightNum	 int COMMENT 'flight number',
            TailNum	 string COMMENT 'plane tail number',
            ActualElapsedTime	 int COMMENT 'in minutes',
            CRSElapsedTime	 int COMMENT 'in minutes',
            AirTime	 int COMMENT 'in minutes',
            ArrDelay	 int COMMENT 'arrival delay, in minutes',
            DepDelay	 int COMMENT 'departure delay, in minutes',
            Origin	 string COMMENT 'origin IATA airport code',
            Dest	 string COMMENT 'destination IATA airport code',
            Distance	 int COMMENT 'in miles',
            TaxiIn	 int COMMENT 'taxi in time, in minutes',
            TaxiOut	 int COMMENT 'taxi out time in minutes',
            Cancelled	 string COMMENT 'was the flight cancelled?',
            CancellationCode	 string COMMENT 'reason for cancellation (A = carrier, B = weather, C = NAS, D = security)',
            Diverted	 int COMMENT '1 = yes, 0 = no',
            CarrierDelay	 int COMMENT 'in minutes',
            WeatherDelay	 int COMMENT 'in minutes',
            NASDelay	 int COMMENT 'in minutes',
            SecurityDelay	 int COMMENT 'in minutes',
            LateAircraftDelay	 int COMMENT 'in minutes')
      COMMENT 'This table has the summary data for 2007'
      ROW FORMAT SERDE
      'org.apache.hadoop.hive.serde2.OpenCSVSerde'
      WITH serdeproperties(
        'escapeChar'='\\',
        'quoteChar'='\"',
        'separatorChar'='|')
      LOCATION '/data/schema/';

# Loading data inside Hive Table:-

  => set hive.execution.engine=tez;LOAD DATA INPATH '/data/raw/*' INTO TABLE prd_fnd_air.summary_stats_2007;

# Assumption : Here have built Hive table where removing NULL value at the time of data load itself

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

// Removing duplicate values
val Df = spark.sql(s"SELECT DISTINCT * FROM prd_fnd_air.summary_stats_2007")

// Trimming column values
var trimedDF = Df
for(col <- Df.columns){
    trimedDF = Df.select(col).rdd.map(x => x.trim)).toDF
}

// Saving the output as paraquet under the modelled directory
distinctDf.coalesce(1).write.parquet("/user/data/modelled/csv_to_paraquet")

    logger.info("Exiting spark session")

exit()
