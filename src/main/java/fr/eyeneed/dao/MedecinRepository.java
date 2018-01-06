package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Medecin;

public interface MedecinRepository extends JpaRepository<Medecin, Long>{

}
