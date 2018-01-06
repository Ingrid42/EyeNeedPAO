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

public interface ModuleRepository extends JpaRepository<Module	, Long>{
	
	@Query("select p from Module p where  p.position = :s")
	public Module chercherModulePosition(@Param("s") int p);
	
	@Query("select p from Module p  order by p.position")
	public List<Module> findAllByPosition();
	
	@Modifying
	@Transactional
	@Query("UPDATE Module m SET m.nom = :nom WHERE m.id_module = :idModule")
	public void updateNom(@Param("idModule") Long idModule, @Param("nom") String nom);

	@Modifying
	@Transactional
	@Query("UPDATE Module m SET m.position = :position WHERE m.id_module = :idModule")
	public void updatePosition(@Param("idModule") Long idModule, @Param("position") int position);

	
}
