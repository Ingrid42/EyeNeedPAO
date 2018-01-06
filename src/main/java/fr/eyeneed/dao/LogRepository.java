package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
