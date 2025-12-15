package br.ifpb.shalomplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifpb.shalomplus.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByLogin(String login);
}
