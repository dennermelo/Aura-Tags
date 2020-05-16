package com.github.dennermelo.aura.tags.objects;


public class Tag {

	private String nome;
	private String permissao;
	private String formato;
	private String item_nome;
	private int valor;
	private String tipo;

	public Tag(String nome, String item_nome, String permissao, String formato, int valor, String tipo) {
		this.nome = nome;
		this.permissao = permissao;
		this.formato = formato;
		this.item_nome = item_nome;
		this.valor = valor;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public String getItemNome() {
		return item_nome;
	}

	public void setItemNome(String item_nome) {
		this.item_nome = item_nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
