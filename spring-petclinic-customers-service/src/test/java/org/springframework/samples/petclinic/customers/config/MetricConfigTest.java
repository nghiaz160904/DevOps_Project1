package org.springframework.samples.petclinic.customers.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MetricConfigTest {
    private MetricConfig metricConfig;
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        metricConfig = new MetricConfig();
        meterRegistry = Mockito.mock(MeterRegistry.class);
    }

    @Test
    void testMetricsCommonTags() {
        assertNotNull(metricConfig.metricsCommonTags());
        metricConfig.metricsCommonTags().customize(meterRegistry);
        Mockito.verify(meterRegistry).config();
    }

    @Test
    void testTimedAspect() {
        TimedAspect timedAspect = metricConfig.timedAspect(meterRegistry);
        assertNotNull(timedAspect);
    }
}
