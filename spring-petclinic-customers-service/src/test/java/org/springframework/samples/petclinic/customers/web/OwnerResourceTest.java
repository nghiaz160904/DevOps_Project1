package org.springframework.samples.petclinic.customers.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.samples.petclinic.customers.web.mapper.OwnerEntityMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerResource.class)
class OwnerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private OwnerEntityMapper ownerEntityMapper;

    private Owner testOwner;

    @BeforeEach
    void setUp() {
        testOwner = new Owner(); // Dùng constructor mặc định
        ReflectionTestUtils.setField(testOwner, "id", 1); // Gán giá trị id bằng Reflection
        ReflectionTestUtils.setField(testOwner, "firstName", "John");
        ReflectionTestUtils.setField(testOwner, "lastName", "Doe");
        ReflectionTestUtils.setField(testOwner, "address", "123 Main St");
        ReflectionTestUtils.setField(testOwner, "city", "New York");
        ReflectionTestUtils.setField(testOwner, "telephone", "1234567890");
    }

    @Test
    void testFindOwnerById() throws Exception {
        when(ownerRepository.findById(1)).thenReturn(Optional.of(testOwner));

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void testFindAllOwners() throws Exception {
        when(ownerRepository.findAll()).thenReturn(List.of(testOwner));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void testCreateOwner() throws Exception {
        OwnerRequest ownerRequest = new OwnerRequest("Jane", "Doe", "456 Elm St", "Los Angeles", "9876543210");
        Owner newOwner = new Owner();

        ReflectionTestUtils.setField(newOwner, "id", 2);
        ReflectionTestUtils.setField(newOwner, "firstName", "Jane");
        ReflectionTestUtils.setField(newOwner, "lastName", "Doe");
        ReflectionTestUtils.setField(newOwner, "address", "456 Elm St");
        ReflectionTestUtils.setField(newOwner, "city", "Los Angeles");
        ReflectionTestUtils.setField(newOwner, "telephone", "9876543210");

        when(ownerEntityMapper.map(any(), any())).thenReturn(newOwner);
        when(ownerRepository.save(any())).thenReturn(newOwner);

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Jane",
                                  "lastName": "Doe",
                                  "address": "456 Elm St",
                                  "city": "Los Angeles",
                                  "telephone": "9876543210"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Jane")));
    }

    @Test
    void testUpdateOwner() throws Exception {
        when(ownerRepository.findById(1)).thenReturn(Optional.of(testOwner));
        when(ownerRepository.save(any())).thenReturn(testOwner);

        mockMvc.perform(put("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Updated",
                                  "lastName": "Doe",
                                  "address": "123 Main St",
                                  "city": "New York",
                                  "telephone": "1234567890"
                                }
                                """))
                .andExpect(status().isNoContent());

        Mockito.verify(ownerRepository).save(any());
    }
}
