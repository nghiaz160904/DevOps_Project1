package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testSaveAndFindOwner() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setAddress("123 Main St");
        owner.setCity("New York");
        owner.setTelephone("1234567890");
        
        Owner savedOwner = ownerRepository.save(owner);
        Optional<Owner> foundOwner = ownerRepository.findById(savedOwner.getId());
        
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getFirstName()).isEqualTo("John");
        assertThat(foundOwner.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void testDeleteOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Alice");
        owner.setLastName("Smith");
        owner.setAddress("456 Oak St");
        owner.setCity("Los Angeles");
        owner.setTelephone("9876543210");
        
        Owner savedOwner = ownerRepository.save(owner);
        ownerRepository.delete(savedOwner);
        
        Optional<Owner> foundOwner = ownerRepository.findById(savedOwner.getId());
        assertThat(foundOwner).isNotPresent();
    }
}
