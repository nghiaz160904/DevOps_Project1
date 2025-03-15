package org.springframework.samples.petclinic.vets.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VetRepositoryTest {

    @Autowired
    private VetRepository vetRepository;

    @Test
    void testSaveAndFindVet() {
        Vet vet = new Vet();
        vet.setFirstName("Alice");
        vet.setLastName("Smith");

        vet = vetRepository.save(vet);

        assertNotNull(vet.getId());

        Vet foundVet = vetRepository.findById(vet.getId()).orElse(null);
        assertNotNull(foundVet);
        assertEquals("Alice", foundVet.getFirstName());
        assertEquals("Smith", foundVet.getLastName());
    }
}
