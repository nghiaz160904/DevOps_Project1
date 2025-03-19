package org.springframework.samples.petclinic.visits.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class VisitTest {

    @Test
    void testVisitGettersAndSetters() {
        Visit visit = new Visit();
        Date date = new Date();

        visit.setId(1);
        visit.setDate(date);
        visit.setDescription("Annual checkup");
        visit.setPetId(101);

        assertEquals(1, visit.getId());
        assertEquals(date, visit.getDate());
        assertEquals("Annual checkup", visit.getDescription());
        assertEquals(101, visit.getPetId());
    }

    @Test
    void testVisitBuilder() {
        Date date = new Date();

        Visit visit = Visit.VisitBuilder.aVisit()
            .id(2)
            .date(date)
            .description("Vaccination")
            .petId(202)
            .build();

        assertEquals(2, visit.getId());
        assertEquals(date, visit.getDate());
        assertEquals("Vaccination", visit.getDescription());
        assertEquals(202, visit.getPetId());
    }
}
