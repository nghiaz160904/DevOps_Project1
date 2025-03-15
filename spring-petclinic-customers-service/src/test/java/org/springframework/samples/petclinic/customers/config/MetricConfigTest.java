package org.springframework.samples.petclinic.customers.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterRegistryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MetricConfigTest {
    private MetricConfig metricConfig;
    private MeterRegistry meterRegistry;
    private MeterRegistry.Config config;

    @BeforeEach
    void setUp() {
        metricConfig = new MetricConfig();
        meterRegistry = mock(MeterRegistry.class);
        config = mock(MeterRegistry.Config.class);

        // Khi gọi meterRegistry.config() thì trả về config mock
        when(meterRegistry.config()).thenReturn(config);
    }

    @Test
    void testMetricsCommonTags() {
        assertNotNull(metricConfig.metricsCommonTags());
        
        // Gọi customize để kiểm tra mock hoạt động đúng
        metricConfig.metricsCommonTags().customize(meterRegistry);

        // Xác nhận rằng config.commonTags() đã được gọi với đúng tham số
        verify(config).commonTags("application", "petclinic");
    }

    @Test
    void testTimedAspect() {
        TimedAspect timedAspect = metricConfig.timedAspect(meterRegistry);
        assertNotNull(timedAspect);
    }
}
