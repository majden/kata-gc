package com.sg.gc.service;

import java.util.List;

import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;

public interface CheckService {

	Compte checkBalance(Long clientId);
}
