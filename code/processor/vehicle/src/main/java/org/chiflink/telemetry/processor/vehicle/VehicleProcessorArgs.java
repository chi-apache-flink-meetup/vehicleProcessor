package org.chiflink.telemetry.processor.vehicle;


import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.ExecutionConfig;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public final class VehicleProcessorArgs extends ExecutionConfig.GlobalJobParameters
        implements Serializable, Cloneable {


    @Parameter(names = { "--kafka-zookeeper-host" })
    public String kafkaZookeeperHost = "localhost:2181";

    @Parameter(names = { "--kafka-bootstrap-server" })
    public String kafkaBootStrapServer = "localhost:2181";

    @Parameter(names = { "--kafka-topic" })
    public String kafkaTopic = "topic01";

    @Parameter(names = {"--consumer-group-id"})
    public String consumerGroupId = "vehicle-processor";

    @Parameter(names = { "--parallelism" })
    public Integer parallelism = 1;

    @Parameter(names = {"--checkpoint-interval"})
    public Long checkpointInterval = 0L;

    @Override
    protected Object clone() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.valueToTree(this);
        try {
            return mapper.treeToValue(mapper.valueToTree(this), VehicleProcessorArgs.class);
        } catch (JsonProcessingException e) { throw new RuntimeException(); }
    }

    @Override
    public Map<String, String> toMap() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) { throw new RuntimeException(); }
    }
}
