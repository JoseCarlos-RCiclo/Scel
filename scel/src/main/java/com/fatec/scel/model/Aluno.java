package com.fatec.scel.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "Aluno")
public class Aluno {

	@Id
	@Column(nullable = false, length=13)
	@NotEmpty(message = "O RA deve ser preenchido")//exibe mensagem de obrigatoriedade de campo
	private Long ra;
	@Column(nullable = false, length=100)
	@NotEmpty(message = "O Nome deve ser preenchido")
	private String nome;
	@Column(nullable = false, length=100)
	@NotEmpty(message = "O E-mail deve ser preenchido")
	private String email;
	
	
	public Long getRa() {
		return ra;
	}
	public void setRa(Long ra) {
		this.ra = ra;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
