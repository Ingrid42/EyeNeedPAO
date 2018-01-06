package fr.eyeneed.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eyeneed.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, String>{


}
