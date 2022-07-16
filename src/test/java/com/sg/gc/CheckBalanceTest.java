package com.sg.gc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sg.gc.dao.EnumTypeCompte;
import com.sg.gc.dao.EnumTypeOperation;
import com.sg.gc.dao.entities.Client;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;
import com.sg.gc.service.CheckService;
import com.sg.gc.service.OpenAccountService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckBalanceTest {
	
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	OpenAccountService oaService;
	
	
	private  Client client;
	
	private  Compte compte;
	private  Operation operation;
	private BigDecimal  sumOperations= new BigDecimal("0");
	
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
		compte.setSolde(new BigDecimal("120.10"));
		compte = oaService.createAccount(compte);
		createOperation(new BigDecimal("100.10"), EnumTypeOperation.SAVE);
		createOperation(new BigDecimal("50"), EnumTypeOperation.SAVE);
		createOperation(new BigDecimal("-30"), EnumTypeOperation.RETRIEVE);
		
	}
	
	@Test
	public void chekOperationsOk() {
		Compte compte = checkService.checkBalance(client.getId());
		BigDecimal balance = compte.getSolde();
		List<Operation> operationList = compte.getOperations();
		operationList.stream().map(o -> o.getMontant()).forEach(m ->  {sumOperations = sumOperations.add(m); System.out.println(m); } );
		assertTrue(sumOperations.compareTo(balance)==0);
	}
	
	private void createOperation(BigDecimal amount , EnumTypeOperation type) {
		operation = new Operation();
		operation.setDateOperation(new Date());
		operation.setMontant(amount);
		operation.setTypeOperation(type);
		operation.setCompte(compte);
		oaService.createOperation(operation);
	}
	
	@AfterAll
	public void setDown(){
		client = null;
		operation = null;
		compte = null;
		sumOperations = null;
	}
	
	

}
