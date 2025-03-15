package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("Springfield");
        owner.setTelephone("1234567890");
    }

    @Test
    void testCreateOwner() {
        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("123 Main St", owner.getAddress());
        assertEquals("Springfield", owner.getCity());
        assertEquals("1234567890", owner.getTelephone());
    }

    @Test
    void testAddPet() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setBirthDate(new Date());
        owner.addPet(pet);

        assertFalse(owner.getPets().isEmpty());
        assertEquals("Buddy", owner.getPets().get(0).getName());
        assertEquals(owner, pet.getOwner());
    }
}
