package proyecto1.grafo;

import java.util.Comparator;

public class Arista implements Comparable<Arista> {
	
	 private Nodo n1;
	 private Nodo n2;
	 private Float peso;

	public Nodo getN1() {
		return n1;
	}
	public void setN1(Nodo n1) {
		this.n1 = n1;
	}
	public Nodo getN2() {
		return n2;
	}
	public void setN2(Nodo n2) {
		this.n2 = n2;
	}
	public Float getPeso() {
		return peso;
	}
	public void setPeso(Float peso) {
		this.peso = peso;
	}
	 
	  @Override
	    public int compareTo(Arista arista) {
	        return (int)(this.peso - arista.getPeso());
	    }
	 

}
