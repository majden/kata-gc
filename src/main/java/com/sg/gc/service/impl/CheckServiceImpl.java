package com.sg.gc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.gc.dao.EnumTypeCompte;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.repository.CompteRepository;
import com.sg.gc.service.CheckService;

@Service
public class CheckServiceImpl implements CheckService {
	
	@Autowired
	CompteRepository compteDao;
	
	@Override
	public Compte checkBalance(Long clientId) {
		return compteDao.getBalanceByClientandType(clientId, EnumTypeCompte.COURANT.getId());
	}

}
