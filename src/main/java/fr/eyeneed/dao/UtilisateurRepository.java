package fr.eyeneed.dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.bestVilles;

public interface UtilisateurRepository extends JpaRepository<Utilisateurs, String>{
	@Query("select p from Utilisateurs p where p.nom like :x")
	public List<Utilisateurs> chercher(@Param("x") String mc) ;
	@Query("select p from Utilisateurs p where p.mail like :x")
	public Utilisateurs findByEmail(@Param("x")String email);
	@Query("select p from Utilisateurs p where p.login like :x")
	public Utilisateurs findByLogin(@Param("x")String login);
	//les villes avec plus d'utilisateur
	@Query(value= "SELECT `code_postal`,COUNT(*)/(select count(*) from utilisateurs)*100	FROM  `utilisateurs` GROUP BY `code_postal`ORDER BY COUNT(*) DESC LIMIT 0,5;", nativeQuery = true )
	public List<Object> findBestCodePostal();
	@Query(value= "select SUBSTRING(`code_postal`,1,2), COUNT(*)/(select count(*) from utilisateurs)*100 FROM  `utilisateurs` GROUP BY SUBSTRING(`code_postal`,1,2) ORDER BY COUNT(*) DESC LIMIT 0,2;", nativeQuery = true )
	public List<Object> findBestDepartement();
	@Query(value= "select COUNT(*)/(select count(*) from utilisateurs)*100 FROM utilisateurs WHERE  YEAR(NOW()) -YEAR(`naissance`)   < 31;", nativeQuery = true )
	public int moins30ans();
	@Query(value= "select COUNT(*)/(select count(*) from utilisateurs)*100 FROM utilisateurs WHERE  YEAR(NOW()) -YEAR(`naissance`)   BETWEEN 31 AND 42;", nativeQuery = true )
	public int entre30et42ans();
	@Query(value= "select COUNT(*)/(select count(*) from utilisateurs)*100 FROM utilisateurs WHERE  YEAR(NOW()) -YEAR(`naissance`)   BETWEEN 43 AND 60;", nativeQuery = true )
	public int entre42et60ans();
	@Query(value= "select COUNT(*)/(select count(*) from utilisateurs)*100 FROM utilisateurs WHERE  YEAR(NOW()) -YEAR(`naissance`)  > 61 ;", nativeQuery = true )
	public int plus60ans();
}
