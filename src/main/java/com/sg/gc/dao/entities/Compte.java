package com.sg.gc.dao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Majdi
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Compte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="date_ouverture")
	@Temporal(TemporalType.DATE)
	
	private Date dateOuverture;
	@Column(name="date_fermeture")
	@Temporal(TemporalType.DATE)
	private Date dateFermeture;
	
	@Column(name="type_compte")
	private Integer typeCompte;
	
	@Column(length= 34)
	private String iban;
	
	@Column(length = 11 )
	private String bic;
	
	private String rib;
	private String libelleCompte;
	private BigDecimal solde;
	
	@Column(name="date_modification")
	@Temporal(TemporalType.DATE)
	private Date dateModification;
	
	@ManyToOne
	Client client;
	
	@OneToMany(mappedBy="compte", fetch= FetchType.LAZY)
	List<Operation> operations;
}
