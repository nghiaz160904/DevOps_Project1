package org.springframework.samples.petclinic.vets.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("production")  // Kích hoạt profile production
class CacheConfigTest {

    @Test
    void testCacheEnabled(ApplicationContext context) {
        assertTrue(context.containsBean("cacheConfig"),
                "CacheConfig should be enabled in production profile");

        CacheManager cacheManager = context.getBean(CacheManager.class);
        assertNotNull(cacheManager, "CacheManager should be available");
    }
}
