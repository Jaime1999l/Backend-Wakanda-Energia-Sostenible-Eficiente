package org.example.backendwakandaenergiasostenibleeficiente.controller;

import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.SensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.service.SensorEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensores-energia")
public class SensorEnergiaController {

    @Autowired
    private SensorEnergiaService sensorEnergiaService;

    @GetMapping
    public List<SensorEnergiaDTO> getAllSensores() {
        return sensorEnergiaService.findAll();
    }

    @GetMapping("/{id}")
    public SensorEnergiaDTO getSensorById(@PathVariable Long id) {
        return sensorEnergiaService.findById(id);
    }

    @PostMapping
    public SensorEnergiaDTO createSensor(@RequestBody SensorEnergiaDTO dto) {
        return sensorEnergiaService.create(dto);
    }

    @PutMapping("/{id}")
    public SensorEnergiaDTO updateSensor(@PathVariable Long id, @RequestBody SensorEnergiaDTO dto) {
        return sensorEnergiaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorEnergiaService.delete(id);
    }
}
