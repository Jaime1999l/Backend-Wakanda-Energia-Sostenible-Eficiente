package org.example.backendwakandaenergiasostenibleeficiente.repos;

import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.LecturaSensorEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturaSensorEnergiaRepository extends JpaRepository<LecturaSensorEnergia, Long> {
    // Encuentra lecturas de sensores de energía por ID del sensor
    List<LecturaSensorEnergia> findBySensorId(Long sensorId);
}