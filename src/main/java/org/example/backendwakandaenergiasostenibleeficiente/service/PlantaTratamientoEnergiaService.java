package org.example.backendwakandaenergiasostenibleeficiente.service;

import jakarta.transaction.Transactional;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.DatosCalidadEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.PlantaTratamientoEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.DatosCalidadEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.PlantaTratamientoEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.repos.PlantaTratamientoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantaTratamientoEnergiaService {

    @Autowired
    private PlantaTratamientoEnergiaRepository plantaTratamientoEnergiaRepository;

    @Autowired
    private DatosCalidadEnergiaService datosCalidadEnergiaService;

    // Convertir entidad a DTO
    public PlantaTratamientoEnergiaDTO toDTO(PlantaTratamientoEnergia planta) {
        PlantaTratamientoEnergiaDTO dto = new PlantaTratamientoEnergiaDTO();
        dto.setId(planta.getId());
        dto.setNombre(planta.getNombre());
        dto.setUbicacion(planta.getUbicacion());
        dto.setCapacidadMaximaKW(planta.getCapacidadMaximaKW());
        dto.setEstadoOperativo(planta.getEstadoOperativo());
        dto.setTipoEnergia(planta.getTipoEnergia());
        if (planta.getDatosCalidadEnergia() != null) {
            dto.setDatosCalidadEnergia(datosCalidadEnergiaService.toDTO(planta.getDatosCalidadEnergia()));
        }
        return dto;
    }

    // Convertir DTO a entidad
    public PlantaTratamientoEnergia toEntity(PlantaTratamientoEnergiaDTO dto) {
        PlantaTratamientoEnergia planta = new PlantaTratamientoEnergia();
        planta.setId(dto.getId());
        planta.setNombre(dto.getNombre());
        planta.setUbicacion(dto.getUbicacion());
        planta.setCapacidadMaximaKW(dto.getCapacidadMaximaKW());
        planta.setEstadoOperativo(dto.getEstadoOperativo());
        planta.setTipoEnergia(dto.getTipoEnergia());
        return planta;
    }

    // Obtener todas las plantas
    public List<PlantaTratamientoEnergiaDTO> findAll() {
        return plantaTratamientoEnergiaRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    // Obtener una planta por ID
    public PlantaTratamientoEnergiaDTO findById(Long id) {
        PlantaTratamientoEnergia planta = plantaTratamientoEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planta de energía no encontrada con ID: " + id));
        return toDTO(planta);
    }

    // Crear nueva planta
    @Transactional
    public PlantaTratamientoEnergiaDTO create(PlantaTratamientoEnergiaDTO dto) {
        PlantaTratamientoEnergia planta = new PlantaTratamientoEnergia();
        planta.setNombre(dto.getNombre());
        planta.setUbicacion(dto.getUbicacion());
        planta.setCapacidadMaximaKW(dto.getCapacidadMaximaKW());
        planta.setEstadoOperativo(dto.getEstadoOperativo());
        planta.setTipoEnergia(dto.getTipoEnergia());

        // Generar datos aleatorios de calidad energética
        DatosCalidadEnergia datos = datosCalidadEnergiaService.generarDatosAleatorios();
        planta.setDatosCalidadEnergia(datos);

        // Guardar la planta y los datos relacionados gracias a CascadeType.ALL
        planta = plantaTratamientoEnergiaRepository.save(planta);
        return toDTO(planta);
    }

    // Actualizar una planta
    @Transactional
    public PlantaTratamientoEnergiaDTO update(Long id, PlantaTratamientoEnergiaDTO dto) {
        PlantaTratamientoEnergia planta = plantaTratamientoEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planta de energía no encontrada con ID: " + id));

        planta.setNombre(dto.getNombre());
        planta.setUbicacion(dto.getUbicacion());
        planta.setCapacidadMaximaKW(dto.getCapacidadMaximaKW());
        planta.setEstadoOperativo(dto.getEstadoOperativo());
        planta.setTipoEnergia(dto.getTipoEnergia());

        // Generar nuevos datos de calidad energética
        DatosCalidadEnergia nuevosDatos = datosCalidadEnergiaService.generarDatosAleatorios();
        planta.setDatosCalidadEnergia(nuevosDatos);

        // Guardar la planta con los nuevos datos
        planta = plantaTratamientoEnergiaRepository.save(planta);
        return toDTO(planta);
    }

    // Eliminar una planta por ID
    @Transactional
    public void delete(Long id) {
        if (!plantaTratamientoEnergiaRepository.existsById(id)) {
            throw new RuntimeException("Planta de energía no encontrada con ID: " + id);
        }
        plantaTratamientoEnergiaRepository.deleteById(id);
    }
}

