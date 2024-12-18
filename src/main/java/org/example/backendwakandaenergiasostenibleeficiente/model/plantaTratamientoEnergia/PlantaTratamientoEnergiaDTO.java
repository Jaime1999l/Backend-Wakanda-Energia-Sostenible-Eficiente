package org.example.backendwakandaenergiasostenibleeficiente.model.plantaTratamientoEnergia;

import lombok.Data;

@Data
public class PlantaTratamientoEnergiaDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private Double capacidadMaximaKW;
    private String estadoOperativo;
    private String tipoEnergia;
    private DatosCalidadEnergiaDTO datosCalidadEnergia;
}