package org.example.backendwakandaenergiasostenibleeficiente.service;

import jakarta.transaction.Transactional;
import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.LecturaSensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.LecturaSensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.repos.LecturaSensorEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturaSensorEnergiaService {

    @Autowired
    private LecturaSensorEnergiaRepository lecturaSensorEnergiaRepository;

    // Convertir entidad a DTO
    private LecturaSensorEnergiaDTO toDTO(LecturaSensorEnergia lectura) {
        LecturaSensorEnergiaDTO dto = new LecturaSensorEnergiaDTO();
        dto.setId(lectura.getId());
        dto.setFechaRegistro(lectura.getFechaRegistro());
        dto.setValorMedido(lectura.getValorMedido());
        dto.setUnidadMedida(lectura.getUnidadMedida());
        dto.setTipoParametro(lectura.getTipoParametro());
        dto.setSensorId(lectura.getSensor().getId());
        return dto;
    }

    // Convertir DTO a entidad
    public LecturaSensorEnergia toEntity(LecturaSensorEnergiaDTO dto) {
        LecturaSensorEnergia lectura = new LecturaSensorEnergia();
        lectura.setId(dto.getId());
        lectura.setFechaRegistro(dto.getFechaRegistro());
        lectura.setValorMedido(dto.getValorMedido());
        lectura.setUnidadMedida(dto.getUnidadMedida());
        lectura.setTipoParametro(dto.getTipoParametro());
        return lectura;
    }

    // Obtener todas las lecturas
    public List<LecturaSensorEnergiaDTO> findAll() {
        List<LecturaSensorEnergia> lecturas = lecturaSensorEnergiaRepository.findAll();
        return lecturas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Obtener lecturas por ID
    public LecturaSensorEnergiaDTO findById(Long id) {
        LecturaSensorEnergia lectura = lecturaSensorEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura de sensor no encontrada con ID: " + id));
        return toDTO(lectura);
    }

    // Crear una nueva lectura
    @Transactional
    public LecturaSensorEnergiaDTO create(LecturaSensorEnergia lecturaSensor) {
        LecturaSensorEnergia lecturaGuardada = lecturaSensorEnergiaRepository.save(lecturaSensor);
        return toDTO(lecturaGuardada);
    }

    // Actualizar una lectura existente
    @Transactional
    public LecturaSensorEnergiaDTO update(Long id, LecturaSensorEnergiaDTO dto) {
        LecturaSensorEnergia lecturaExistente = lecturaSensorEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura de sensor no encontrada con ID: " + id));
        lecturaExistente.setFechaRegistro(dto.getFechaRegistro());
        lecturaExistente.setValorMedido(dto.getValorMedido());
        lecturaExistente.setUnidadMedida(dto.getUnidadMedida());
        lecturaExistente.setTipoParametro(dto.getTipoParametro());
        lecturaSensorEnergiaRepository.save(lecturaExistente);
        return toDTO(lecturaExistente);
    }

    // Eliminar una lectura por ID
    @Transactional
    public void delete(Long id) {
        if (!lecturaSensorEnergiaRepository.existsById(id)) {
            throw new RuntimeException("Lectura de sensor no encontrada con ID: " + id);
        }
        lecturaSensorEnergiaRepository.deleteById(id);
    }

    // Obtener todas las lecturas asociadas a un sensor
    public List<LecturaSensorEnergiaDTO> findBySensorId(Long sensorId) {
        List<LecturaSensorEnergia> lecturas = lecturaSensorEnergiaRepository.findBySensorId(sensorId);
        return lecturas.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
