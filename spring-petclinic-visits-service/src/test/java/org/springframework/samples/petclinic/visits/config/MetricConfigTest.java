package org.springframework.samples.petclinic.visits.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricConfigTest {

    @Test
    void testMetricsCommonTags() {
        MetricConfig config = new MetricConfig();
        MeterRegistry registry = new SimpleMeterRegistry();

        config.metricsCommonTags().customize(registry);

        assertTrue(registry.config().commonTags().stream()
            .anyMatch(tag -> tag.getKey().equals("application") && tag.getValue().equals("petclinic")));
    }

    @Test
    void testTimedAspect() {
        MetricConfig config = new MetricConfig();
        MeterRegistry registry = new SimpleMeterRegistry();

        TimedAspect timedAspect = config.timedAspect(registry);

        assertNotNull(timedAspect);
    }
}
