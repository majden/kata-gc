package com.sg.gc.dao.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Client implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nom;
	private String prenom;
	private String adresse;
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	private String email;
	private String phoneNumber;
	
}
