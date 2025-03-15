package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VetTest {

    private Vet vet;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        vet = new Vet();
        vet.setFirstName("John");
        vet.setLastName("Doe");

        specialty = new Specialty();
        specialty.setName("Dentistry");
    }

    @Test
    void testAddSpecialty() {
        assertEquals(0, vet.getNrOfSpecialties());

        vet.addSpecialty(specialty);

        assertEquals(1, vet.getNrOfSpecialties());
        assertTrue(vet.getSpecialties().contains(specialty));
    }

    @Test
    void testVetName() {
        assertEquals("John", vet.getFirstName());
        assertEquals("Doe", vet.getLastName());
    }
}
