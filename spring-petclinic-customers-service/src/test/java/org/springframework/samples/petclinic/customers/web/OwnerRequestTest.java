package org.springframework.samples.petclinic.customers.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidOwnerRequest() {
        OwnerRequest owner = new OwnerRequest("John", "Doe", "123 Main St", "New York", "1234567890");

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(owner);
        assertTrue(violations.isEmpty(), "Should have no validation errors");
    }

    @Test
    void testBlankFields() {
        OwnerRequest owner = new OwnerRequest("", "", "", "", "");
    
        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(owner);
    
        // Đếm số lượng field khác nhau bị lỗi
        long distinctFieldErrors = violations.stream()
            .map(v -> v.getPropertyPath().toString()) // Lấy tên field
            .distinct()
            .count();
    
        assertEquals(5, distinctFieldErrors, "Should have exactly 5 distinct validation errors");
    }

    @Test
    void testInvalidTelephone() {
        OwnerRequest owner = new OwnerRequest("John", "Doe", "123 Main St", "New York", "123abc");

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(owner);
        assertFalse(violations.isEmpty(), "Should fail due to invalid telephone");
    }

    @Test
    void testLongTelephone() {
        OwnerRequest owner = new OwnerRequest("John", "Doe", "123 Main St", "New York", "1234567890123"); // 13 digits

        Set<ConstraintViolation<OwnerRequest>> violations = validator.validate(owner);
        assertFalse(violations.isEmpty(), "Should fail because telephone exceeds 12 digits");
    }
}
