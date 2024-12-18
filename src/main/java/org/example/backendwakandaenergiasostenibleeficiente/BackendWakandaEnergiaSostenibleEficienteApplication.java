package org.example.backendwakandaenergiasostenibleeficiente;


import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.PlantaTratamientoEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class BackendWakandaEnergiaSostenibleEficienteApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BackendWakandaEnergiaSostenibleEficienteApplication.class, args);


        PlantaTratamientoEnergiaService plantaService = context.getBean(PlantaTratamientoEnergiaService.class);



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

    }
}

