package org.chiflink.telemetry.processor.vehicle


import java.util.Properties

import com.beust.jcommander.JCommander
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.slf4j.LoggerFactory

object VehicleProcessor {

  private val logger = LoggerFactory.getLogger(getClass)
  val config = new VehicleProcessorArgs

  def main(args: Array[String]): Unit = {

    this.logger.info("Starting VehicleProcessor...")

    new JCommander(this.config, args.toArray: _*)

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(this.config.parallelism)

    if (this.config.checkpointInterval > 0) {
      env.enableCheckpointing(this.config.checkpointInterval)
    }

    env.getConfig.setGlobalJobParameters(this.config)

    // configure Kafka consumer
    val kafkaProps = new Properties
    kafkaProps.setProperty("zookeeper.connect", this.config.kafkaZookeeperHost)
    kafkaProps.setProperty("bootstrap.servers", this.config.kafkaBootStrapServer)
    kafkaProps.setProperty("group.id", this.config.consumerGroupId)

    val consumer = new FlinkKafkaConsumer010[](
      this.config.kafkaTopic,
      //new TelemetrySchema,
      kafkaProps)

    val stream = env.addSource(consumer)
      .keyBy(x => ())
      .flatMap(new VehicleCalculator().name("VehicleCalculator"))

    env.execute("VehicleProcessor")
    this.logger.info("Ending VehicleProcessor...")

  }
}
