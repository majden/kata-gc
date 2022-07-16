package com.sg.gc.dao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sg.gc.dao.EnumTypeOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dateOperation;
	
	private String natureOperation;
	
	private BigDecimal montant;
	
	
	
	private EnumTypeOperation typeOperation;
	
	@JoinColumn(name="compte_id", referencedColumnName="id")
	@ManyToOne
	Compte compte;
	
}
