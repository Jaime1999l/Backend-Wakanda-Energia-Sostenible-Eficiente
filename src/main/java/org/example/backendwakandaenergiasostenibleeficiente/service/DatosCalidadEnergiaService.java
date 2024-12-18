package org.example.backendwakandaenergiasostenibleeficiente.service;

import jakarta.transaction.Transactional;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.DatosCalidadEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.DatosCalidadEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.repos.DatosCalidadEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DatosCalidadEnergiaService {

    @Autowired
    private DatosCalidadEnergiaRepository datosCalidadEnergiaRepository;

    // Convertir entidad a DTO
    public DatosCalidadEnergiaDTO toDTO(DatosCalidadEnergia datos) {
        DatosCalidadEnergiaDTO dto = new DatosCalidadEnergiaDTO();
        dto.setId(datos.getId());
        dto.setFechaMedicion(datos.getFechaMedicion());
        dto.setVoltaje(datos.getVoltaje());
        dto.setEficienciaEnergetica(datos.getEficienciaEnergetica());
        dto.setPerdidasEnergeticas(datos.getPerdidasEnergeticas());
        dto.setConsumoActualKWPorHora(datos.getConsumoActualKWPorHora());
        dto.setTemperaturaDispositivoCelsius(datos.getTemperaturaDispositivoCelsius());
        return dto;
    }

    // Convertir DTO a entidad
    private DatosCalidadEnergia toEntity(DatosCalidadEnergiaDTO dto) {
        DatosCalidadEnergia datos = new DatosCalidadEnergia();
        datos.setId(dto.getId());
        datos.setFechaMedicion(dto.getFechaMedicion());
        datos.setVoltaje(dto.getVoltaje());
        datos.setEficienciaEnergetica(dto.getEficienciaEnergetica());
        datos.setPerdidasEnergeticas(dto.getPerdidasEnergeticas());
        datos.setConsumoActualKWPorHora(dto.getConsumoActualKWPorHora());
        datos.setTemperaturaDispositivoCelsius(dto.getTemperaturaDispositivoCelsius());
        return datos;
    }

    // Obtener todos los datos de calidad
    public List<DatosCalidadEnergiaDTO> findAll() {
        return datosCalidadEnergiaRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    // Obtener datos por ID
    public DatosCalidadEnergiaDTO findById(Long id) {
        DatosCalidadEnergia datos = datosCalidadEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Datos de calidad no encontrados"));
        return toDTO(datos);
    }

    // Crear nuevos datos
    @Transactional
    public void create(DatosCalidadEnergiaDTO dto) {
        DatosCalidadEnergia datos = toEntity(dto);
        datosCalidadEnergiaRepository.save(datos);
    }

    // Actualizar datos de calidad de energía
    @Transactional
    public void update(Long id, DatosCalidadEnergiaDTO dto) {
        DatosCalidadEnergia datos = datosCalidadEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Datos de calidad no encontrados"));
        datos.setVoltaje(dto.getVoltaje());
        datos.setEficienciaEnergetica(dto.getEficienciaEnergetica());
        datos.setPerdidasEnergeticas(dto.getPerdidasEnergeticas());
        datos.setConsumoActualKWPorHora(dto.getConsumoActualKWPorHora());
        datos.setTemperaturaDispositivoCelsius(dto.getTemperaturaDispositivoCelsius());
        datosCalidadEnergiaRepository.save(datos);
    }

    // Eliminar datos por ID
    @Transactional
    public void delete(Long id) {
        datosCalidadEnergiaRepository.deleteById(id);
    }

    // Generar datos aleatorios
    public static DatosCalidadEnergia generarDatosAleatorios() {
        Random random = new Random();
        DatosCalidadEnergia datos = new DatosCalidadEnergia();

        datos.setFechaMedicion(LocalDateTime.now());
        datos.setVoltaje(110.0 + (230.0 - 110.0) * random.nextDouble()); // Valores entre 110V y 230V
        datos.setEficienciaEnergetica(85.0 + (99.0 - 85.0) * random.nextDouble()); // Entre 85% y 99%
        datos.setPerdidasEnergeticas("Pérdidas mínimas"); // Texto fijo
        datos.setConsumoActualKWPorHora(50.0 + (500.0 - 50.0) * random.nextDouble()); // Entre 50 y 500 KW/h
        datos.setTemperaturaDispositivoCelsius(30.0 + (70.0 - 30.0) * random.nextDouble()); // Entre 30 y 70 grados
        return datos;
    }
}
