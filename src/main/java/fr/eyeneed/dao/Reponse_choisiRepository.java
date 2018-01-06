package fr.eyeneed.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Relation;
import fr.eyeneed.entities.Reponse;
import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Utilisateurs;


public interface Reponse_choisiRepository extends JpaRepository<Reponse_choisi, Long>{
	@Query("select p from Reponse_choisi p where p.utilisateur like :x")
	public List<Reponse_choisi> findByUser(@Param("x")Utilisateurs utilisateur);
	@Query("select p from Reponse_choisi p where p.utilisateur like :x")
	public List<Reponse_choisi> findRepByUser(@Param("x")Utilisateurs utilisateur);
	
	@Query("select p from Reponse_choisi p where p.utilisateur like :x AND p.reponse like :y")
	public Reponse_choisi findRepOrdo(@Param("x")Utilisateurs utilisateur,@Param("y") Reponse rep);
}
