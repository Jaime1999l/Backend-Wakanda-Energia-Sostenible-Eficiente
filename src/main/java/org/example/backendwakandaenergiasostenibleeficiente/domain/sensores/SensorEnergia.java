package org.example.backendwakandaenergiasostenibleeficiente.domain.sensores;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.PlantaTratamientoEnergia;
import org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia.DatosCalidadEnergia;

import java.util.List;

@Entity
@Table(name = "SensoresEnergia")
@Getter
@Setter
public class SensorEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_sensor", nullable = false)
    private String tipoSensor;

    @Column(name = "ubicacion_exacta", nullable = false)
    private String ubicacionExacta;

    @Column(name = "ultima_fecha_evento", nullable = false)
    private String ultimaFechaEvento;

    @Column(nullable = false)
    private String estado;

    @ManyToOne(optional = true)
    @JoinColumn(name = "planta_id", nullable = true)
    private PlantaTratamientoEnergia plantaTratamiento;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LecturaSensorEnergia> lecturas; // Relación con lecturas históricas

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datos_calidad_energia_id")
    private DatosCalidadEnergia datosCalidadEnergia; // Relación con los datos actuales de calidad de la energía
}
