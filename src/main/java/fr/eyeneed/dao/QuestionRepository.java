package fr.eyeneed.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fr.eyeneed.entities.Module;
import fr.eyeneed.entities.Question;
import fr.eyeneed.entities.Reponse;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	@Query("select p from Question p where p.texte like :x")
	public List<Question> chercher(@Param("x") String mc);
	
	@Query("select p from Question p where p.module = :x order by p.position")
	public List<Question> chercherModule(@Param("x") Module m);
	
	@Query("select p from Question p where p.module = :x AND p.position = :s")
	public Question chercherModulePosition(@Param("x") Module m,@Param("s") int p);
	
	//fonction qui cherche les réponses associées à une question
	@Query("select p from Question p where p.contenant = :x")
	public Question chercherSousQuestion(@Param("x") Reponse q);
	
	@Query("select max(p.position) from Question p where p.module = :x")
	public int LastPosition(@Param("x") Module mod);
	
	@Modifying
	@Transactional
	@Query("UPDATE Question q SET q.position= :position, q.texte= :texte WHERE q.id_question = :id")
	public void updateQuestion(@Param("id") Long id, @Param("position") int position, @Param("texte") String texte);

	@Modifying
	@Transactional
	@Query("UPDATE Question q SET q.contenant = :reponse WHERE q.id_question = :idQuestion")
	public void updateReponseContenant(@Param("idQuestion") Long idQuestion, @Param("reponse") Reponse reponse);

	@Modifying
	@Transactional
	@Query("UPDATE Question q SET q.module = :module WHERE q.id_question = :idQuestion")
	public void updateModule(@Param("idQuestion") Long idQuestion, @Param("module") Module module);
	
	
}
