package org.example.backendwakandaenergiasostenibleeficiente.repos;

import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.DatosCalidadEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DatosCalidadEnergiaRepository extends JpaRepository<DatosCalidadEnergia, Long> {
    // Encuentra datos de calidad de la energía por fecha de medición
    List<DatosCalidadEnergia> findByFechaMedicionBetween(LocalDateTime startDate, LocalDateTime endDate);
}
