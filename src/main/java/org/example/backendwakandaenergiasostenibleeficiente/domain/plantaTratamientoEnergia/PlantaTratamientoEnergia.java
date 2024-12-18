package org.example.backendwakandaenergiasostenibleeficiente.domain.plantaTratamientoEnergia;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PlantasTratamientoEnergia")
@Getter
@Setter
public class PlantaTratamientoEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    @Column(name = "capacidad_maxima_kw", nullable = false)
    private Double capacidadMaximaKW;

    @Column(name = "estado_operativo", nullable = false)
    private String estadoOperativo;

    @Column(name = "tipo_energia", nullable = false)
    private String tipoEnergia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datos_calidad_energia_id") // Indica la columna en la base de datos
    private DatosCalidadEnergia datosCalidadEnergia; // Relación con DatosCalidadEnergia
}
