package org.example.backendwakandaenergiasostenibleeficiente.repos;

import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.SensorEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorEnergiaRepository extends JpaRepository<SensorEnergia, Long> {
    // Encuentra sensores de energía por tipo
    List<SensorEnergia> findByTipoSensor(String tipoSensor);

    // Encuentra sensores de energía por estado
    List<SensorEnergia> findByEstado(String estado);

    // Encuentra sensores asociados a una planta de tratamiento específica
    List<SensorEnergia> findByPlantaTratamientoId(Long plantaId);
}
