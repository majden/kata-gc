package com.sg.gc.service;

import com.sg.gc.dao.entities.Client;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;

public interface OpenAccountService {

	Compte createAccount(Compte account);
	
	Client createClient(Client client);
	
	Operation createOperation(Operation operation);
}
