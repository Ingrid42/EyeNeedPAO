package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>{

}
