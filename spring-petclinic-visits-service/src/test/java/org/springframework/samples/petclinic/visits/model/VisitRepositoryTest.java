package org.springframework.samples.petclinic.visits.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VisitRepositoryTest {

    @Autowired
    VisitRepository visitRepository;

    @Test
    void testFindByPetId() {
        Visit visit1 = Visit.VisitBuilder.aVisit()
            .petId(100)
            .date(new Date())
            .description("Checkup")
            .build();
        Visit visit2 = Visit.VisitBuilder.aVisit()
            .petId(100)
            .date(new Date())
            .description("Vaccination")
            .build();
        visitRepository.saveAll(Arrays.asList(visit1, visit2));

        List<Visit> visits = visitRepository.findByPetId(100);
        assertEquals(2, visits.size());
        assertEquals("Checkup", visits.get(0).getDescription());
        assertEquals("Vaccination", visits.get(1).getDescription());
    }

    @Test
    void testFindByPetIdIn() {
        Visit visit1 = Visit.VisitBuilder.aVisit()
            .petId(101)
            .date(new Date())
            .description("Checkup")
            .build();
        Visit visit2 = Visit.VisitBuilder.aVisit()
            .petId(102)
            .date(new Date())
            .description("Surgery")
            .build();
        visitRepository.saveAll(Arrays.asList(visit1, visit2));

        List<Visit> visits = visitRepository.findByPetIdIn(Arrays.asList(101, 102));
        assertEquals(2, visits.size());
    }
}
