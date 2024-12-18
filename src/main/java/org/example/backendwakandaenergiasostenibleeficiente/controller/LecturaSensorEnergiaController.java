package org.example.backendwakandaenergiasostenibleeficiente.controller;

import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.LecturaSensorEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.model.sensores.LecturaSensorEnergiaDTO;
import org.example.backendwakandaenergiasostenibleeficiente.service.LecturaSensorEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturas-energia")
public class LecturaSensorEnergiaController {

    @Autowired
    private LecturaSensorEnergiaService lecturaSensorEnergiaService;

    @GetMapping
    public List<LecturaSensorEnergiaDTO> getAllLecturas() {
        return lecturaSensorEnergiaService.findAll();
    }

    @GetMapping("/{id}")
    public LecturaSensorEnergiaDTO getLecturaById(@PathVariable Long id) {
        return lecturaSensorEnergiaService.findById(id);
    }

    @PostMapping
    public LecturaSensorEnergiaDTO createLectura(@RequestBody LecturaSensorEnergiaDTO dto) {
        // Convertir DTO a entidad usando un método del servicio
        LecturaSensorEnergia lectura = lecturaSensorEnergiaService.toEntity(dto);

        // Ahora crear la lectura en la base de datos
        return lecturaSensorEnergiaService.create(lectura);
    }

    @PutMapping("/{id}")
    public LecturaSensorEnergiaDTO updateLectura(@PathVariable Long id, @RequestBody LecturaSensorEnergiaDTO dto) {
        return lecturaSensorEnergiaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteLectura(@PathVariable Long id) {
        lecturaSensorEnergiaService.delete(id);
    }
}
