package org.springframework.samples.petclinic.customers.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPetRequest() {
        PetRequest petRequest = new PetRequest(1, new Date(), "Buddy", 2);
        Set<ConstraintViolation<PetRequest>> violations = validator.validate(petRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName_Empty() {
        PetRequest petRequest = new PetRequest(1, new Date(), "", 2);
        Set<ConstraintViolation<PetRequest>> violations = validator.validate(petRequest);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 2147483647", violations.iterator().next().getMessage());
    }

    @Test
    void testNullBirthDate() {
        PetRequest petRequest = new PetRequest(1, null, "Buddy", 2);
        Set<ConstraintViolation<PetRequest>> violations = validator.validate(petRequest);

        assertTrue(violations.isEmpty()); // Không có ràng buộc nên vẫn hợp lệ
    }
}
