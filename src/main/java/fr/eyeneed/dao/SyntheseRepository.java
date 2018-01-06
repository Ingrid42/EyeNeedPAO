package fr.eyeneed.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Reponse_choisi;
import fr.eyeneed.entities.Synthese;
import fr.eyeneed.entities.Utilisateurs;

public interface SyntheseRepository extends JpaRepository<Synthese, String>{

	@Query("select p from Synthese p")
	List<Synthese> findAll();
	
	@Query("select p from Utilisateurs p where p.login like :x")
	public Synthese findByLogin(@Param("x")String login);
	
}
