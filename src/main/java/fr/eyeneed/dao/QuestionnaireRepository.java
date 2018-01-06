package fr.eyeneed.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Questionnaire;
import fr.eyeneed.entities.Reponse;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>{
	
	@Query(value= "select SEC_TO_TIME(avg(duree)/1000)  from questionnaire;", nativeQuery = true )
	public Date dureeMoyenne();
	
	@Query("select p from Questionnaire p where p.id_questionnaire like :x")
	public Questionnaire findById(@Param("x")Long id);
}
