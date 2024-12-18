package org.example.backendwakandaenergiasostenibleeficiente.model.sensores;

import lombok.Data;

@Data
public class SensorEnergiaDTO {
    private Long id;
    private String tipoSensor;
    private String ubicacionExacta;
    private String ultimaFechaEvento;
    private String estado;
}
