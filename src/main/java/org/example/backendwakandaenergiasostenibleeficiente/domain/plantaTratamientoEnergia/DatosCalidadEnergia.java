package org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backendwakandaenergiasostenibleeficiente.domain.sensores.SensorEnergia;

import java.time.LocalDateTime;

@Entity
@Table(name = "DatosCalidadEnergia")
@Getter
@Setter
public class DatosCalidadEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_medicion", nullable = false)
    private LocalDateTime fechaMedicion;

    @Column(name = "voltaje", nullable = false)
    private Double voltaje;

    @Column(name = "eficiencia_energetica", nullable = false)
    private Double eficienciaEnergetica;

    @Column(name = "perdidas_energeticas", nullable = false)
    private String perdidasEnergeticas;

    @Column(name = "consumo_actual_kw_por_hora", nullable = false)
    private Double consumoActualKWPorHora;

    @Column(name = "temperatura_dispositivo_celsius", nullable = false)
    private Double temperaturaDispositivoCelsius;

    @OneToOne(mappedBy = "datosCalidadEnergia", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlantaTratamientoEnergia plantaTratamiento; // Relación con PlantaTratamientoEnergia
}
