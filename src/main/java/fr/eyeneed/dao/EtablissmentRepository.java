package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Etablissement;

public interface EtablissmentRepository extends JpaRepository<Etablissement	, Long>{

}
