package org.springframework.samples.petclinic.visits.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricConfigTest {

    @Test
    void testMetricsCommonTags() {
        MetricConfig config = new MetricConfig();
        MeterRegistry registry = new SimpleMeterRegistry();

        config.metricsCommonTags().customize(registry);

        List<Tag> commonTags = registry.config().commonTags();
        boolean hasApplicationTag = false;

        for (Tag tag : commonTags) {
            if (tag.getKey().equals("application") && tag.getValue().equals("petclinic")) {
                hasApplicationTag = true;
                break;
            }
        }

        assertTrue(hasApplicationTag, "Registry should contain the common tag 'application=petclinic'");
    }

    @Test
    void testTimedAspect() {
        MetricConfig config = new MetricConfig();
        MeterRegistry registry = new SimpleMeterRegistry();

        TimedAspect timedAspect = config.timedAspect(registry);

        assertNotNull(timedAspect);
    }
}
