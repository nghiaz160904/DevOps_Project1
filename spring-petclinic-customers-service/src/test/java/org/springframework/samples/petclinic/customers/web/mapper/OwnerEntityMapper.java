package org.springframework.samples.petclinic.customers.web.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.web.OwnerRequest;

import static org.junit.jupiter.api.Assertions.*;

class OwnerEntityMapperTest {
    private OwnerEntityMapper ownerEntityMapper;

    @BeforeEach
    void setUp() {
        ownerEntityMapper = new OwnerEntityMapper();
    }

    @Test
    void testMap() {
        Owner owner = new Owner();
        OwnerRequest request = new OwnerRequest("John", "Doe", "123 Street", "New York", "123456789");

        Owner updatedOwner = ownerEntityMapper.map(owner, request);

        assertEquals("John", updatedOwner.getFirstName());
        assertEquals("Doe", updatedOwner.getLastName());
        assertEquals("123 Street", updatedOwner.getAddress());
        assertEquals("New York", updatedOwner.getCity());
        assertEquals("123456789", updatedOwner.getTelephone());
    }
}
