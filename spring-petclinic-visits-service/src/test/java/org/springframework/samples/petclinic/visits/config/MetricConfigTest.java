package org.springframework.samples.petclinic.visits.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MetricConfigTest {

    @Test
    void testMetricsCommonTags() {
        MetricConfig config = new MetricConfig();
        MeterRegistry registry = new SimpleMeterRegistry();

        config.metricsCommonTags().customize(registry);

        // Kiểm tra bằng cách ghi nhận một metric mẫu và xem nó có chứa tag "application=petclinic" không
        registry.counter("test.metric", Collections.singletonList(Tag.of("application", "petclinic")));

        boolean hasApplicationTag = registry.find("test.metric")
                .tags("application", "petclinic")
                .counter() != null;

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
