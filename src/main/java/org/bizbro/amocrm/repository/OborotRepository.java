package org.bizbro.amocrm.repository;

import org.bizbro.amocrm.model.Oborot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OborotRepository extends JpaRepository<Oborot, Long> {}
