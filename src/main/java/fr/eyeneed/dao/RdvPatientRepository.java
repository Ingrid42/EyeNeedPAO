package fr.eyeneed.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.rdvPatient;

public interface RdvPatientRepository extends JpaRepository<rdvPatient, Long>{

	@Query("select p from rdvPatient p")
	List<rdvPatient> findAll();
	@Query(value= "SELECT `code_postal`,COUNT(*)/(select count(*) from `rdv_patient` WHERE `type_rdv`  = ?1 )*100	FROM  `rdv_patient` WHERE `type_rdv`  = ?1 GROUP BY `code_postal`ORDER BY COUNT(*) DESC LIMIT 0,5;", nativeQuery = true )
	public List<Object> findBestCodePostal(String typeRdv);
	@Query(value= "select SUBSTRING(`code_postal`,1,2), COUNT(*)/(select count(*) from `rdv_patient` WHERE `type_rdv`  = ?1 )*100 FROM  `rdv_patient`  WHERE `type_rdv`  = ?1 GROUP BY SUBSTRING(`code_postal`,1,2) ORDER BY COUNT(*) DESC LIMIT 0,2;", nativeQuery = true )
	public List<Object> findBestDepartement(String typeRdv);
}
