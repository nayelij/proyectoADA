package proyecto1.grafo;

import java.util.HashMap;

public class Nodo {

	private Integer idNodo;
	private Double x;
	private Double y;
	HashMap<Integer, Integer> mapNodo;

	public Integer getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(Integer idNodo) {
		this.idNodo = idNodo;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

}
