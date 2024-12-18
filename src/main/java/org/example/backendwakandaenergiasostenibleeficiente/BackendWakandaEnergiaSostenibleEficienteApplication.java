package org.example.backendwakandaenergiasostenibleeficiente;

import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.LecturaSensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.SensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.usuario.UsuarioDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.PlantaTratamientoEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.SensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.LecturaSensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.repos.SensorEnergiaRepository;
import org.example.backendwakandaenergiasostenibleeficiente.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class BackendWakandaEnergiaSostenibleEficienteApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BackendWakandaEnergiaSostenibleEficienteApplication.class, args);

        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        PlantaTratamientoEnergiaService plantaService = context.getBean(PlantaTratamientoEnergiaService.class);
        SensorEnergiaService sensorService = context.getBean(SensorEnergiaService.class);
        LecturaSensorEnergiaService lecturaSensorService = context.getBean(LecturaSensorEnergiaService.class);
        SensorEnergiaRepository sensorRepository = context.getBean(SensorEnergiaRepository.class);

        // ========================
        // TEST CRUD USUARIO
        // ========================
        System.out.println("=== TEST CRUD USUARIO ===");
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre("Ana");
        nuevoUsuario.setApellido("Gómez");
        nuevoUsuario.setEmail("ana.gomez@example.com");
        nuevoUsuario.setRoles(List.of("Usuario"));

        // CREATE usuario
        Long usuarioId = usuarioService.create(nuevoUsuario);
        System.out.println("Usuario creado con ID: " + usuarioId);

        // GET usuario
        UsuarioDTO usuarioObtenido = usuarioService.get(usuarioId);
        System.out.println("Usuario obtenido: " + usuarioObtenido);

        // UPDATE usuario
        usuarioObtenido.setNombre("Ana Actualizada");
        usuarioObtenido.setApellido("Gómez Actualizada");
        usuarioService.update(usuarioId, usuarioObtenido);
        UsuarioDTO usuarioActualizado = usuarioService.get(usuarioId);
        System.out.println("Usuario luego de update: " + usuarioActualizado);

        // FIND ALL usuarios
        List<UsuarioDTO> listaUsuarios = usuarioService.findAll();
        System.out.println("Lista de usuarios: " + listaUsuarios);

        System.out.println("Omitiendo la eliminación del usuario con ID " + usuarioId + " (no se borra nada).");

        // ========================
        // TEST CRUD PLANTA TRATAMIENTO ENERGÍA
        // ========================
        System.out.println("\n=== TEST CRUD PLANTA TRATAMIENTO ENERGÍA ===");
        PlantaTratamientoEnergiaDTO plantaDTO = new PlantaTratamientoEnergiaDTO();
        plantaDTO.setNombre("Planta Solar A");
        plantaDTO.setUbicacion("Ubicación Central");
        plantaDTO.setCapacidadMaximaKW(500000.0);
        plantaDTO.setEstadoOperativo("Operativo");
        plantaDTO.setTipoEnergia("Solar");

        // CREATE planta
        PlantaTratamientoEnergiaDTO plantaCreada = plantaService.create(plantaDTO);
        System.out.println("Planta creada: " + plantaCreada);

        // GET planta
        PlantaTratamientoEnergiaDTO plantaObtenida = plantaService.findById(plantaCreada.getId());
        System.out.println("Planta obtenida: " + plantaObtenida);

        // UPDATE planta
        plantaObtenida.setNombre("Planta Solar A Actualizada");
        PlantaTratamientoEnergiaDTO plantaActualizada = plantaService.update(plantaObtenida.getId(), plantaObtenida);
        System.out.println("Planta actualizada: " + plantaActualizada);

        // FIND ALL plantas
        List<PlantaTratamientoEnergiaDTO> listaPlantas = plantaService.findAll();
        System.out.println("Lista de plantas: " + listaPlantas);

        System.out.println("Omitiendo la eliminación de la planta con ID " + plantaActualizada.getId() + " (no se borra nada).");

        // ========================
        // TEST CRUD SENSOR ENERGÍA
        // ========================
        System.out.println("\n=== TEST CRUD SENSOR ENERGÍA ===");
        SensorEnergiaDTO sensorDTO = new SensorEnergiaDTO();
        sensorDTO.setTipoSensor("Voltaje");
        sensorDTO.setUbicacionExacta("Subestación Norte");
        sensorDTO.setEstado("Activo");
        sensorDTO.setUltimaFechaEvento(LocalDateTime.now().toString());

        // CREATE sensor
        SensorEnergiaDTO sensorCreado = sensorService.create(sensorDTO);
        System.out.println("Sensor creado: " + sensorCreado);

        // GET sensor
        SensorEnergiaDTO sensorObtenido = sensorService.findById(sensorCreado.getId());
        System.out.println("Sensor obtenido: " + sensorObtenido);

        // UPDATE sensor
        sensorObtenido.setTipoSensor("Consumo KW");
        sensorObtenido.setUbicacionExacta("Subestación Sur");
        SensorEnergiaDTO sensorActualizado = sensorService.update(sensorObtenido.getId(), sensorObtenido);
        System.out.println("Sensor actualizado: " + sensorActualizado);

        // FIND ALL sensors
        List<SensorEnergiaDTO> listaSensores = sensorService.findAll();
        System.out.println("Lista de sensores: " + listaSensores);

        System.out.println("Omitiendo la eliminación del sensor con ID " + sensorActualizado.getId() + " (no se borra nada).");

        // ========================
        // TEST CRUD LECTURA SENSOR ENERGÍA
        // ========================
        System.out.println("\n=== TEST CRUD LECTURA SENSOR ENERGÍA ===");

        // Obtener el sensor entidad
        SensorEnergia sensorEntidad = sensorRepository.findById(sensorActualizado.getId())
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado para LecturaSensor"));

        LecturaSensorEnergia nuevaLectura = new LecturaSensorEnergia();
        nuevaLectura.setFechaRegistro(LocalDateTime.now());
        nuevaLectura.setValorMedido(220.0);
        nuevaLectura.setUnidadMedida("Voltios");
        nuevaLectura.setTipoParametro("Voltaje");
        nuevaLectura.setSensor(sensorEntidad);

        // CREATE lectura
        LecturaSensorEnergiaDTO lecturaCreada = lecturaSensorService.create(nuevaLectura);
        System.out.println("Lectura creada: " + lecturaCreada);

        // GET lectura
        LecturaSensorEnergiaDTO lecturaObtenida = lecturaSensorService.findById(lecturaCreada.getId());
        System.out.println("Lectura obtenida: " + lecturaObtenida);

        // UPDATE lectura
        lecturaObtenida.setValorMedido(230.0);
        LecturaSensorEnergiaDTO lecturaActualizada = lecturaSensorService.update(lecturaObtenida.getId(), lecturaObtenida);
        System.out.println("Lectura actualizada: " + lecturaActualizada);

        // FIND ALL lecturas
        List<LecturaSensorEnergiaDTO> listaLecturas = lecturaSensorService.findAll();
        System.out.println("Lista de lecturas: " + listaLecturas);

        System.out.println("Omitiendo la eliminación de la lectura con ID " + lecturaActualizada.getId() + " (no se borra nada).");

        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
}

