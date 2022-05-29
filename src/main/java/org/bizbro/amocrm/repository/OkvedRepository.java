package org.bizbro.amocrm.repository;

import org.bizbro.amocrm.model.Okved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OkvedRepository extends JpaRepository<Okved, String> {}