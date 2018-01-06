package fr.eyeneed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eyeneed.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
