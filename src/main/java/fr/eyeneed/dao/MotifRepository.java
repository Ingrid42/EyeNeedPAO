package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Motif;

public interface MotifRepository extends JpaRepository<Motif, String> {

}
