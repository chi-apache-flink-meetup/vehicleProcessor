package org.chiflink.telemetry.processor.vehicle

import org.slf4j.LoggerFactory
import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.util.Collector

class VehicleCalculator extends RichFlatMapFunction[String, String]{

  override def flatMap(value:String, out:Collector[String]): Unit = {
    VehicleCalculator.logger.info(value)
    }

}

object VehicleCalculator {
  private val logger = LoggerFactory.getLogger(getClass)
}