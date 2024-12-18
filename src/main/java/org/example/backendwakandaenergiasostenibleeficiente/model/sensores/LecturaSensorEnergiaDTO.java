package org.example.backendwakandaenergiasostenibleeficiente.model.sensores;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LecturaSensorEnergiaDTO {
    private Long id;
    private LocalDateTime fechaRegistro;
    private Double valorMedido;
    private String unidadMedida;
    private String tipoParametro;
    private Long sensorId;
}
