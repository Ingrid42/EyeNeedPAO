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
import fr.eyeneed.entities.Utilisateurs;

public interface ReponseRepository extends JpaRepository<Reponse, Long>{
	//fonction qui cherche les réponses associées à une question
	@Query("select p from Reponse p where p.question = :x order by p.position")
	public List<Reponse> chercherQuestion(@Param("x") Question q);
	
	@Query("select p from Reponse p where p.texte like :x")
	public Reponse findByTexte(@Param("x")String texte);
	
	@Query("select p from Reponse p where p.id_reponse like :x")
	public Reponse findById(@Param("x")Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Reponse r SET r.position= :position, r.texte= :texte, r.isCommentaire= :isCommentaire, r.commentaire = :commentaire WHERE r.id_reponse = :id")
	public void updateReponse(@Param("id") Long id, @Param("position") int position, @Param("texte") String texte, @Param("isCommentaire") Boolean isCommentaire,@Param("commentaire") String commentaire);	

	@Modifying
	@Transactional
	@Query("UPDATE Reponse r SET r.question= :question WHERE r.id_reponse = :idReponse")
	public void updateQuestion(@Param("idReponse") long idReponse, @Param("question") Question question);	
	
	@Query(value = "SELECT MAX(CHAR_LENGTH(`texte`)) FROM `reponse` WHERE `question_id_question`  = ?1", nativeQuery = true)
	public int maxWidth(int mc);
	
}
