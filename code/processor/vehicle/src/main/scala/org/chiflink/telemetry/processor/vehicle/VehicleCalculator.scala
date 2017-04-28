package org.chiflink.telemetry.processor.vehicle

import org.slf4j.LoggerFactory
import org.apache.flink.api.common.functions.RichFlatMapFunction

class VehicleCalculator extends RichFlatMapFunction[, ]{

  override def flatMap(value: , out: ): Unit = {

    }

  }



}

object VehicleCalculator {
  private val logger = LoggerFactory.getLogger(getClass)
}