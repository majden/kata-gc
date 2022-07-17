package com.sg.gc.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.gc.dao.EnumTypeCompte;
import com.sg.gc.dao.EnumTypeOperation;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;
import com.sg.gc.dao.repository.ClientRepository;
import com.sg.gc.dao.repository.CompteRepository;
import com.sg.gc.dao.repository.OperationRepository;
import com.sg.gc.service.RetrieveService;

@Service
public class RetrieveServiceImpl implements RetrieveService{

	@Autowired
	private OperationRepository operationDAO;
	
	@Autowired
	private CompteRepository compteDao;
	
	@Override
	@Transactional
	public synchronized boolean retrieve(Long clientId, BigDecimal amount) {
		
		Compte cpte = compteDao.getCompteByClientandType(clientId, EnumTypeCompte.COURANT.getId());
		if(cpte.getSolde().compareTo(amount) > 0) {
			BigDecimal newSolde = cpte.getSolde().subtract(amount);
			cpte.setSolde(newSolde);
			cpte.setDateModification(new Date());
			cpte = compteDao.save(cpte);
			Operation op = new Operation();
			op.setDateOperation(new Date());
			op.setMontant(amount.negate());
			op.setTypeOperation(EnumTypeOperation.RETRIEVE);
			op.setCompte(cpte);
			operationDAO.save(op);
			return true;
		}
		return false;
	}

}
