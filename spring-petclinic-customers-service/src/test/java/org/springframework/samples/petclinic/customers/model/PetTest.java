package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    private Pet pet;
    private PetType petType;
    private Owner owner;

    @BeforeEach
    void setUp() {
        petType = new PetType();
        petType.setName("Dog");

        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");

        pet = new Pet();
        pet.setName("Buddy");
        pet.setBirthDate(new Date());
        pet.setType(petType);
        pet.setOwner(owner);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Buddy", pet.getName());
        assertNotNull(pet.getBirthDate());
        assertEquals(petType, pet.getType());
        assertEquals(owner, pet.getOwner());
    }

    @Test
    void testEqualsAndHashCode() {
        Pet anotherPet = new Pet();
        anotherPet.setName("Buddy");
        anotherPet.setBirthDate(pet.getBirthDate());
        anotherPet.setType(petType);
        anotherPet.setOwner(owner);

        assertEquals(pet, anotherPet);
        assertEquals(pet.hashCode(), anotherPet.hashCode());
    }

    @Test
    void testToString() {
        assertTrue(pet.toString().contains("Buddy"));
        assertTrue(pet.toString().contains("Dog"));
        assertTrue(pet.toString().contains("John"));
    }
}
