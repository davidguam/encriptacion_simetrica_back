package com.david.encriptacion_cimetrica.repositories;

import com.david.encriptacion_cimetrica.model.TransaccionBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionBancaria, Long> {
}
