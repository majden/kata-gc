package com.sg.gc.service;

import java.math.BigDecimal;

import com.sg.gc.dao.entities.Compte;

public interface DepositService {

	Compte save(Long clientId, BigDecimal amount);
}
