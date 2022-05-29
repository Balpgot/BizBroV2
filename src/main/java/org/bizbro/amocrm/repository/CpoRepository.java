package org.bizbro.amocrm.repository;

import org.bizbro.amocrm.model.Cpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpoRepository extends JpaRepository<Cpo, Long> {}
