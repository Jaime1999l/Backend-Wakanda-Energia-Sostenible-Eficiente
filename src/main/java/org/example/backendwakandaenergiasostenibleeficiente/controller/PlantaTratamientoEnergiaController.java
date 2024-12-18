package org.example.backendwakandaenergiasostenibleeficiente.controller;

import org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia.PlantaTratamientoEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.service.PlantaTratamientoEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plantas-energia")
public class PlantaTratamientoEnergiaController {

    @Autowired
    private PlantaTratamientoEnergiaService plantaTratamientoEnergiaService;

    @GetMapping
    public List<PlantaTratamientoEnergiaDTO> getAllPlantas() {
        return plantaTratamientoEnergiaService.findAll();
    }

    @GetMapping("/{id}")
    public PlantaTratamientoEnergiaDTO getPlantaById(@PathVariable Long id) {
        return plantaTratamientoEnergiaService.findById(id);
    }

    @PostMapping
    public PlantaTratamientoEnergiaDTO createPlanta(@RequestBody PlantaTratamientoEnergiaDTO dto) {
        return plantaTratamientoEnergiaService.create(dto);
    }

    @PutMapping("/{id}")
    public PlantaTratamientoEnergiaDTO updatePlanta(@PathVariable Long id, @RequestBody PlantaTratamientoEnergiaDTO dto) {
        return plantaTratamientoEnergiaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePlanta(@PathVariable Long id) {
        plantaTratamientoEnergiaService.delete(id);
    }
}
