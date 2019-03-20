package io.tofu.commons.symbol;

public class Token implements Terminal {
	private String name;
	private Object value;
	
	public Token(String nome, Object valor) {
		this.name = nome;
		this.value = valor;
	}
	
	@Override
	public String toString() {
		return "<" + name + ", " + value + ">";
	}

	public String getName() {
		return name;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}