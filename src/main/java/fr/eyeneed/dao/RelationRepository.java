package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Relation;

public interface RelationRepository extends JpaRepository<Relation, String>{

}
