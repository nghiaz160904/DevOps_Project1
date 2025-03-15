package org.springframework.samples.petclinic.customers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager; // Thêm EntityManager

    @Test
    void testSaveAndFindById() {
        // Lưu PetType trước
        PetType petType = new PetType();
        petType.setName("Dog");
        entityManager.persist(petType); // Lưu vào DB
        entityManager.flush(); // Đẩy dữ liệu xuống DB để lấy ID

        // Lưu Owner trước
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setCity("New York"); // Bổ sung
        owner.setAddress("123 Main Street"); // Bổ sung
        owner.setTelephone("1234567890"); // Bổ sung
        owner = ownerRepository.save(owner); // Lưu vào DB

        // Lưu Pet với PetType đã lưu
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setBirthDate(new Date());
        pet.setType(petType);  // Gán PetType đã được lưu
        pet.setOwner(owner);
        pet = petRepository.save(pet);

        // Kiểm tra
        Optional<Pet> foundPet = petRepository.findById(pet.getId());
        assertTrue(foundPet.isPresent());
        assertEquals("Buddy", foundPet.get().getName());
    }
}
