package org.springframework.samples.petclinic.vets.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VetsPropertiesTest {

    @Autowired
    private VetsProperties vetsProperties;

    @Test
    void testVetsPropertiesLoaded() {
        assertNotNull(vetsProperties);
        assertNotNull(vetsProperties.cache());
        assertEquals(3600, vetsProperties.cache().ttl(), "TTL should be 3600");
        assertEquals(100, vetsProperties.cache().heapSize(), "Heap size should be 100");
    }

    @TestConfiguration
    @EnableConfigurationProperties(VetsProperties.class)
    static class Config {
    }
}
