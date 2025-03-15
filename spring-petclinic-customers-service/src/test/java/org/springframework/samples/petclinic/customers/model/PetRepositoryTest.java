package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void testSaveAndFindById() {
        // Tạo PetType (không cần PetTypeRepository)
        PetType petType = new PetType();
        petType.setId(1);  // Đặt ID tạm thời
        petType.setName("Dog");

        // Lưu Owner
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setCity("New York"); // Bổ sung
        owner.setAddress("123 Main Street"); // Bổ sung
        owner.setTelephone("1234567890"); // Bổ sung
        ownerRepository.save(owner);

        // Lưu Pet với PetType
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setBirthDate(new Date());
        pet.setType(petType);  // Sử dụng PetType không cần repo riêng
        pet.setOwner(owner);
        petRepository.save(pet);

        // Kiểm tra
        Optional<Pet> foundPet = petRepository.findById(pet.getId());
        assertTrue(foundPet.isPresent());
        assertEquals("Buddy", foundPet.get().getName());
    }
}
