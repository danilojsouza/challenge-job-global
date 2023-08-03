package com.teste.globalweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.globalweb.model.Convidado;

public interface Convidados extends JpaRepository<Convidado, Long> {

}
