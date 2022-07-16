package com.sg.gc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sg.gc.service.OpenAccountService;
import com.sg.gc.service.RetrieveService;
import com.sg.gc.dao.entities.Client;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.EnumTypeCompte;
import com.sg.gc.dao.EnumTypeOperation;
import com.sg.gc.dao.entities.Operation;
import com.sg.gc.service.DepositService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetrieveTest {
	
	
	@Autowired
	DepositService saveService;
	
	@Autowired
	OpenAccountService oaService;
	
	@Autowired
	RetrieveService retrieveService;
	
	private  Client client;
	
	private  Compte compte;
	private  Operation operation;
	
	@BeforeAll
	public void setUp() {
		//createClient and account
		client = new Client();
		client.setDateNaissance(DateUtil.parse("1993-01-01"));
		client.setEmail("client01@gmail.com");
		client.setNom("Zanaboni");
		client.setPrenom("Adrien");
		client.setPhoneNumber("0678063870");
		client = oaService.createClient(client);
		compte = new Compte();
		compte.setClient(client);
		compte.setDateOuverture(new Date());
		compte.setTypeCompte(EnumTypeCompte.COURANT.getId());
		compte.setSolde(new BigDecimal(100.10));
		compte = oaService.createAccount(compte);
		operation = new Operation();
		operation.setDateOperation(new Date());
		operation.setMontant(new BigDecimal(100.10));
		operation.setTypeOperation(EnumTypeOperation.SAVE);
		operation.setCompte(compte);
		oaService.createOperation(operation);
	}
	
	@Test
	public void retrieveOperationTestOk() {
		assertTrue(retrieveService.retrieve(client.getId(), new BigDecimal(50)));
	}
	
	
	@Test
	public void retrieveOperationTestKO() {
		assertFalse(retrieveService.retrieve(client.getId(), new BigDecimal(100.50)));
	}
	
	@AfterAll
	public void setDown(){
		client = null;
		operation = null;
		compte = null;
	}

}
