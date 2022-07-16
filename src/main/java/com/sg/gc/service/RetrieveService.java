package com.sg.gc.service;

import java.math.BigDecimal;

public interface RetrieveService {

	boolean retrieve(Long clientId, BigDecimal amount);
}
