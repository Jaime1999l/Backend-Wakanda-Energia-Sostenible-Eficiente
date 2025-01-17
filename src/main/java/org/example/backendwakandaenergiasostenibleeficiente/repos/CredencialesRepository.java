package org.example.backendwakandaenergiasostenibleeficiente.repos;

import org.example.backendwakandaenergiasostenibleeficiente.domain.usuario.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {
    // Encuentra credenciales por correo electrónico
    Optional<Credenciales> findByCorreo(String correoElectronico);
    Optional<Credenciales> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioId(Long usuarioId);
}
