package org.example.backendwakandaenergiasostenibleeficiente.service;

import jakarta.transaction.Transactional;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.DatosCalidadEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.PlantaTratamientoEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.LecturaSensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.SensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.PlantaTratamientoEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.LecturaSensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.SensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.repos.SensorEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorEnergiaService {

    @Autowired
    private SensorEnergiaRepository sensorEnergiaRepository;

    @Autowired
    private PlantaTratamientoEnergiaService plantaTratamientoEnergiaService;

    @Autowired
    private LecturaSensorEnergiaService lecturaSensorEnergiaService;

    private final List<SensorEnergia> sensores = new ArrayList<>();

    // Convertir entidad a DTO
    private SensorEnergiaDTO toDTO(SensorEnergia sensor) {
        SensorEnergiaDTO dto = new SensorEnergiaDTO();
        dto.setId(sensor.getId());
        dto.setTipoSensor(sensor.getTipoSensor());
        dto.setUbicacionExacta(sensor.getUbicacionExacta());
        dto.setUltimaFechaEvento(sensor.getUltimaFechaEvento());
        dto.setEstado(sensor.getEstado());
        return dto;
    }

    // Convertir DTO a entidad
    private SensorEnergia toEntity(SensorEnergiaDTO dto) {
        SensorEnergia sensor = new SensorEnergia();
        sensor.setId(dto.getId());
        sensor.setTipoSensor(dto.getTipoSensor());
        sensor.setUbicacionExacta(dto.getUbicacionExacta());
        sensor.setUltimaFechaEvento(dto.getUltimaFechaEvento());
        sensor.setEstado(dto.getEstado());
        return sensor;
    }

    // CRUD: Obtener todos los sensores
    public List<SensorEnergiaDTO> findAll() {
        return sensorEnergiaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // CRUD: Obtener sensor por ID
    public SensorEnergiaDTO findById(Long id) {
        SensorEnergia sensor = sensorEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + id));
        return toDTO(sensor);
    }

    @Transactional
    public SensorEnergiaDTO create(SensorEnergiaDTO dto) {
        SensorEnergia sensor = toEntity(dto);
        sensor.setUltimaFechaEvento(String.valueOf(LocalDateTime.now()));

        // Obtener todas las plantas
        List<PlantaTratamientoEnergiaDTO> plantasDTO = plantaTratamientoEnergiaService.findAll();

        // Encontrar una planta sin sensores asignados
        PlantaTratamientoEnergia plantaSinSensor = null;
        for (PlantaTratamientoEnergiaDTO plantaDTO : plantasDTO) {
            PlantaTratamientoEnergia planta = plantaTratamientoEnergiaService.toEntity(plantaDTO);

            List<SensorEnergiaDTO> sensoresDeLaPlanta = findSensoresByPlanta(planta.getId());
            if (sensoresDeLaPlanta.isEmpty()) {
                plantaSinSensor = planta;
                break;
            }
        }

        if (plantaSinSensor == null) {
            throw new RuntimeException("No hay ninguna planta sin sensor disponible.");
        }

        sensor.setPlantaTratamiento(plantaSinSensor);
        return toDTO(sensorEnergiaRepository.save(sensor));
    }

    public List<SensorEnergiaDTO> findSensoresByPlanta(Long plantaId) {
        List<SensorEnergia> sensores = sensorEnergiaRepository.findByPlantaTratamientoId(plantaId);
        return sensores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // CRUD: Actualizar un sensor
    @Transactional
    public SensorEnergiaDTO update(Long id, SensorEnergiaDTO dto) {
        SensorEnergia sensor = sensorEnergiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + id));
        sensor.setTipoSensor(dto.getTipoSensor());
        sensor.setUbicacionExacta(dto.getUbicacionExacta());
        sensor.setEstado(dto.getEstado());
        sensor.setUltimaFechaEvento(String.valueOf(LocalDateTime.now()));
        return toDTO(sensorEnergiaRepository.save(sensor));
    }

    // CRUD: Eliminar un sensor
    @Transactional
    public void delete(Long id) {
        if (!sensorEnergiaRepository.existsById(id)) {
            throw new RuntimeException("Sensor no encontrado con ID: " + id);
        }
        sensorEnergiaRepository.deleteById(id);
    }


    // Scraping programado cada 4 segundos y actualización de sensores
    @Scheduled(fixedRate = 4000)
    public void scrapeDatosYActualizarSensores() {
        List<PlantaTratamientoEnergiaDTO> plantasDTO = plantaTratamientoEnergiaService.findAll();
        List<PlantaTratamientoEnergia> plantas = plantasDTO.stream()
                .map(plantaTratamientoEnergiaService::toEntity)
                .toList();

        // Asegurar que haya un sensor disponible para cada planta
        if (sensores.size() < plantas.size()) {
            for (PlantaTratamientoEnergia planta : plantas) {
                if (sensores.stream().noneMatch(sensor -> sensor.getUbicacionExacta().equals(planta.getUbicacion()))) {
                    SensorEnergia nuevoSensor = new SensorEnergia();
                    nuevoSensor.setTipoSensor("Monitor de Energía");
                    nuevoSensor.setUbicacionExacta(planta.getUbicacion());
                    nuevoSensor.setEstado("Activo");
                    nuevoSensor.setUltimaFechaEvento(LocalDateTime.now().toString());
                    nuevoSensor.setPlantaTratamiento(planta);
                    sensores.add(sensorEnergiaRepository.save(nuevoSensor));

                    System.out.println("Nuevo sensor creado para planta: " + planta.getNombre());
                }
            }
        }

        // Scraping de datos y asignación a sensores
        for (int i = 0; i < plantas.size(); i++) {
            PlantaTratamientoEnergia planta = plantas.get(i);
            SensorEnergia sensor = sensores.get(i);

            // Generar nuevos datos de calidad energética
            planta.setDatosCalidadEnergia(DatosCalidadEnergiaService.generarDatosAleatorios());
            DatosCalidadEnergia nuevosDatos = planta.getDatosCalidadEnergia();

            // Mostrar los nuevos datos obtenidos
            System.out.println("Nuevos datos obtenidos:");
            System.out.println("ID: " + nuevosDatos.getId());
            System.out.println("Fecha Medición: " + nuevosDatos.getFechaMedicion());
            System.out.println("Voltaje: " + nuevosDatos.getVoltaje());
            System.out.println("Eficiencia Energética: " + nuevosDatos.getEficienciaEnergetica());
            System.out.println("Pérdidas Energéticas: " + nuevosDatos.getPerdidasEnergeticas());
            System.out.println("Consumo Actual KW/h: " + nuevosDatos.getConsumoActualKWPorHora());
            System.out.println("Temperatura °C: " + nuevosDatos.getTemperaturaDispositivoCelsius());

            // Asignar nuevos datos al sensor
            sensor.setDatosCalidadEnergia(nuevosDatos);

            // Crear una nueva lectura histórica usando el servicio LecturaSensorEnergiaService
            LecturaSensorEnergia nuevaLectura = new LecturaSensorEnergia();
            nuevaLectura.setFechaRegistro(LocalDateTime.now());
            nuevaLectura.setValorMedido(nuevosDatos.getVoltaje());
            nuevaLectura.setUnidadMedida("Voltios");
            nuevaLectura.setTipoParametro("Voltaje");
            nuevaLectura.setSensor(sensor);

            // Crear la lectura
            LecturaSensorEnergiaDTO lecturaCreada = lecturaSensorEnergiaService.create(nuevaLectura);

            // Mostrar la lectura creada
            System.out.println("Lectura creada:");
            System.out.println("ID: " + lecturaCreada.getId());
            System.out.println("Fecha Registro: " + lecturaCreada.getFechaRegistro());
            System.out.println("Valor Medido: " + lecturaCreada.getValorMedido());
            System.out.println("Unidad Medida: " + lecturaCreada.getUnidadMedida());
            System.out.println("Tipo Parametro: " + lecturaCreada.getTipoParametro());
            System.out.println("Sensor ID: " + lecturaCreada.getSensorId());

            // Actualizar el sensor con los nuevos datos
            sensor.setUltimaFechaEvento(String.valueOf(LocalDateTime.now()));
            sensor.setEstado("Datos Actualizados");
            sensorEnergiaRepository.save(sensor);

            System.out.println("Sensor " + sensor.getId() + " actualizado con datos de planta: " + planta.getNombre());
        }
    }
}
