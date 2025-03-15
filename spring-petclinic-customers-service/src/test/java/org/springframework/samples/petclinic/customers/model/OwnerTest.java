package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class OwnerTest {

    @Test
    void shouldCreateOwnerWithValidData() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Street");
        owner.setCity("New York");
        owner.setTelephone("1234567890");

        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("123 Street", owner.getAddress());
        assertEquals("New York", owner.getCity());
        assertEquals("1234567890", owner.getTelephone());
    }

    @Test
    void shouldAddPetToOwner() {
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Buddy");
        
        owner.addPet(pet);
        List<Pet> pets = owner.getPets();

        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
        assertEquals(owner, pets.get(0).getOwner());
    }
}
