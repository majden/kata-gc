package com.sg.gc.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sg.gc.dao.entities.Compte;



@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

	
	@Query("select c from Compte c where c.client.id = :client and typeCompte = :type")
	public Compte getCompteByClientandType(@Param("client") Long clientId,@Param("type") int typeCompte);
	
	@Query("select c from Compte c   join fetch c.operations o where c.client.id = :client and c.typeCompte = :type")
	public Compte getBalanceByClientandType(@Param("client") Long clientId,@Param("type") int typeCompte);
}
