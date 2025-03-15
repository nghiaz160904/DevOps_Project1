package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.Pet;
import org.springframework.samples.petclinic.customers.model.PetType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PetDetailsTest {

    @Test
    void testPetDetailsConstructor() {
        // Tạo dữ liệu mẫu
        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("Dog");

        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Buddy");
        pet.setOwner(owner);
        pet.setBirthDate(new Date());
        pet.setType(petType);

        // Gọi constructor
        PetDetails petDetails = new PetDetails(pet);

        // Kiểm tra giá trị
        assertEquals(1, petDetails.id());
        assertEquals("Buddy", petDetails.name());
        assertEquals("John Doe", petDetails.owner());
        assertEquals(pet.getBirthDate(), petDetails.birthDate());
        assertEquals(petType, petDetails.type());
    }

    @Test
    void testPetDetailsWithNullOwner() {
        PetType petType = new PetType();
        petType.setId(1);
        petType.setName("Cat");

        Pet pet = new Pet();
        pet.setId(2);
        pet.setName("Kitty");
        pet.setOwner(null); // Không có owner
        pet.setBirthDate(new Date());
        pet.setType(petType);

        assertThrows(NullPointerException.class, () -> new PetDetails(pet));
    }
}
