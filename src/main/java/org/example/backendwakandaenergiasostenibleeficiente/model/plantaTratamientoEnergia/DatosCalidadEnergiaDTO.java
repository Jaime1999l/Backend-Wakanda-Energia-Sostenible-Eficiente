package org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatosCalidadEnergiaDTO {
    private Long id;
    private LocalDateTime fechaMedicion;
    private Double voltaje;
    private Double eficienciaEnergetica;
    private String perdidasEnergeticas;
    private Double consumoActualKWPorHora;
    private Double temperaturaDispositivoCelsius;
}
