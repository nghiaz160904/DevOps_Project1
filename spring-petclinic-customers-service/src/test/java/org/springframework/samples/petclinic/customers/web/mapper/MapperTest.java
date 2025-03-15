package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    // Dummy implementation for testing
    static class DummyMapper implements Mapper<String, StringBuilder> {
        @Override
        public StringBuilder map(StringBuilder response, String request) {
            response.append(request);
            return response;
        }
    }

    @Test
    void testMap() {
        DummyMapper mapper = new DummyMapper();
        StringBuilder response = new StringBuilder("Hello, ");
        String request = "World!";

        StringBuilder result = mapper.map(response, request);

        assertEquals("Hello, World!", result.toString());
    }
}
