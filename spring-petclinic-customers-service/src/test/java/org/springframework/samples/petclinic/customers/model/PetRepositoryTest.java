package org.springframework.samples.petclinic.customers.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Test
    void testFindPetTypes() {
        PetType petType = new PetType();
        petType.setName("Dog");
        petTypeRepository.save(petType);

        List<PetType> petTypes = petRepository.findPetTypes();
        assertThat(petTypes).isNotEmpty();
        assertThat(petTypes.get(0).getName()).isEqualTo("Dog");
    }

    @Test
    void testFindPetTypeById() {
        PetType petType = new PetType();
        petType.setName("Cat");
        PetType savedPetType = petTypeRepository.save(petType);

        Optional<PetType> foundPetType = petRepository.findPetTypeById(savedPetType.getId());
        assertThat(foundPetType).isPresent();
        assertThat(foundPetType.get().getName()).isEqualTo("Cat");
    }
}
