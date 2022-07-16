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
import com.sg.gc.dao.repository.CompteRepository;
import com.sg.gc.dao.repository.OperationRepository;
import com.sg.gc.service.DepositService;


@Service
public class DepositServiceImpl implements DepositService {
	
	@Autowired
	private OperationRepository operationDAO;
	
	@Autowired
	private CompteRepository compteDao;

	@Override
	@Transactional
	public Compte save(Long clientId, BigDecimal amount) {
		Compte cpte = compteDao.getCompteByClientandType(clientId, EnumTypeCompte.COURANT.getId());
		cpte.setSolde(cpte.getSolde().add(amount));
		cpte.setDateModification(new Date());
		cpte = compteDao.save(cpte);
		Operation op = new Operation();
		op.setDateOperation(new Date());
		op.setMontant(amount);
		op.setTypeOperation(EnumTypeOperation.SAVE);
		operationDAO.save(op);
		return cpte;
	}

	
}
