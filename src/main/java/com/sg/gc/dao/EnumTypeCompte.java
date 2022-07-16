package com.sg.gc.dao;

import lombok.Data;

public enum EnumTypeCompte {

	LIVRETA(0), COURANT(1);
	
	private int id;
	
	private EnumTypeCompte(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	
}
