package org.springframework.samples.petclinic.vets.system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties(VetsProperties.class)
@TestPropertySource(properties = {
    "spring.profiles.active=production",
    "vets.cache.ttl=3600",
    "vets.cache.heapSize=100"
})
class VetsPropertiesTest {

    @Autowired
    private VetsProperties vetsProperties;

    @Test
    void testVetsPropertiesLoaded() {
        assertNotNull(vetsProperties, "VetsProperties should not be null");
        assertNotNull(vetsProperties.cache(), "Cache should not be null");
        assertEquals(3600, vetsProperties.cache().ttl(), "TTL should be 3600");
        assertEquals(100, vetsProperties.cache().heapSize(), "Heap size should be 100");
    }
}
