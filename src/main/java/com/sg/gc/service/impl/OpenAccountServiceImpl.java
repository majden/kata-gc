package com.sg.gc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.gc.dao.entities.Client;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;
import com.sg.gc.dao.repository.ClientRepository;
import com.sg.gc.dao.repository.CompteRepository;
import com.sg.gc.dao.repository.OperationRepository;
import com.sg.gc.service.OpenAccountService;


@Service
public class OpenAccountServiceImpl implements OpenAccountService {

	@Autowired
	CompteRepository compteDao;
	
	@Autowired
	ClientRepository clientDao;
	
	@Autowired
	OperationRepository operationDao;
	
	@Override
	public Compte createAccount(Compte account) {
		return compteDao.save(account);
	}

	@Override
	public Client createClient(Client client) {
		return clientDao.save(client);
	}

	@Override
	public Operation createOperation(Operation operation) {
		
		return operationDao.save(operation);
	}
	
	

}
