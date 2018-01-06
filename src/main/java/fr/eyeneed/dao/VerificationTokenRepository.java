package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Utilisateurs;
import fr.eyeneed.entities.VerificationToken;

public interface VerificationTokenRepository 
extends JpaRepository<VerificationToken, Long> {
  @Query("select p from VerificationToken p where p.token like :x")
  VerificationToken findByToken(@Param("x")String token);
  @Query("select p from VerificationToken p where p.user like :x")
  VerificationToken findByUser(Utilisateurs user);
}
