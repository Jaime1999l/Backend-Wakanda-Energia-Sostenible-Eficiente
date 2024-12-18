package org.example.backendwakandaenergiasostenibleeficiente.repos;

import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.PlantaTratamientoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantaTratamientoEnergiaRepository extends JpaRepository<PlantaTratamientoEnergia, Long> {
    // Encuentra una planta de tratamiento de energía por su nombre
    Optional<PlantaTratamientoEnergia> findByNombre(String nombre);
}
