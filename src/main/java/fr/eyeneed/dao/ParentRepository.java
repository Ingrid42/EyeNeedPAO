package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Parent;
import fr.eyeneed.entities.Utilisateurs;

public interface ParentRepository extends JpaRepository<Parent, Long>{

}
