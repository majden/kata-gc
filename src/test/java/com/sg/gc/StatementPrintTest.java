package com.sg.gc;


import java.math.BigDecimal;
import java.util.Date;

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
import com.sg.gc.service.OpenAccountService;
import com.sg.gc.service.StatementPrintService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementPrintTest {
	
	
	@Autowired
	StatementPrintService spService;
	
	@Autowired
	OpenAccountService oaService;
	
	
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
		client.setAdresse("Rue de chateau, Paris 75001");
		client = oaService.createClient(client);
		compte = new Compte();
		compte.setClient(client);
		compte.setIban("FR76300035485658978568574");
		compte.setRib("300035485658978568574");
		compte.setDateOuverture(new Date());
		compte.setTypeCompte(EnumTypeCompte.COURANT.getId());
		compte.setSolde(new BigDecimal("2280.30"));
		compte.setDateModification(new Date());
		compte = oaService.createAccount(compte);
		createOperation(new BigDecimal("3460.20"), EnumTypeOperation.SAVE, "Virement reçu INVIVOO 84574856");
		createOperation(new BigDecimal("100.10"), EnumTypeOperation.SAVE, "Virement reçu 923587458");
		createOperation(new BigDecimal("50"), EnumTypeOperation.SAVE, "Versement en espèces 985742");
		createOperation(new BigDecimal("-30"), EnumTypeOperation.RETRIEVE, "Retrait DAB 9568");
		createOperation(new BigDecimal("-800"), EnumTypeOperation.RETRIEVE, "Prélévement FONCIA LOYER REF 9658742112");
		createOperation(new BigDecimal("-500"), EnumTypeOperation.RETRIEVE, "VIREMENT EMIS REF 5847585");
		
	}
	
	@Test
	public void statementPrintTestOk() {
		spService.exportStatementPdf(client.getId());
	}
	
	private void createOperation(BigDecimal amount , EnumTypeOperation type, String natureOperation) {
		operation = new Operation();
		operation.setDateOperation(new Date());
		operation.setMontant(amount);
		operation.setTypeOperation(type);
		operation.setNatureOperation(natureOperation);
		operation.setCompte(compte);
		oaService.createOperation(operation);
	}
	
	@AfterAll
	public void setDown(){
		client = null;
		operation = null;
		compte = null;
	}
	
	

}
