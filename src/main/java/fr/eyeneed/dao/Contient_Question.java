package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Question;

public interface Contient_Question extends JpaRepository<Question, Long>{

}
