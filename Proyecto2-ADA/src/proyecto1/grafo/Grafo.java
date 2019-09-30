package proyecto1.grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

public class Grafo {

	public static HashMap<Integer, Nodo> hashMapNodo = null;
	public static HashMap<Integer, Arista> hashMapArista = null;
	public static HashMap<Integer, Arista> hashArbol = null;
	public static int aristasAux, count, countAux, numnodos;

	public Grafo() {
		hashMapNodo = new HashMap<Integer, Nodo>();
		hashMapArista = new HashMap<Integer, Arista>();
		hashArbol = new HashMap<Integer, Arista>();
	}

	public static Grafo genErdosRenyi(int numAristas, int numNodos, boolean dirigido, boolean autociclos) {
		Grafo grafo = new Grafo();
		Random r1 = new Random();
		Random r2 = new Random();
		while (aristasAux < numNodos) {
			Arista n = new Arista();
			Nodo nodo = new Nodo();
			Nodo nodo2 = new Nodo();
			int c1 = r1.nextInt(numNodos);
			int c2 = r2.nextInt(numNodos);
			nodo.setIdNodo(c1);
			nodo2.setIdNodo(c2);
			n.setN1(nodo);
			n.setN2(nodo2);
			if (autociclos) {
				if (!hashMapArista.isEmpty()) {
					if (revisarConexion(c1, c2) == 0) {
						hashMapArista.put(count, n);
						count += 1;
						aristasAux += 1;
					}

				} else {
					hashMapArista.put(count, n);
					count += 1;
					aristasAux += 1;
				}
			} else {
				if (c1 != c2) {
					if (!hashMapArista.isEmpty()) {
						if (revisarConexion(c1, c2) == 0) {
							hashMapArista.put(count, n);
							count += 1;
							aristasAux += 1;
						}
					} else {
						hashMapArista.put(count, n);
						count += 1;
						aristasAux += 1;
					}
				}
			}
		}
		if (dirigido) {
			exportarArchivo("ErdosRenyi-dirigido", hashMapArista, true);
		} else {
			exportarArchivo("ErdosRenyi-500", hashMapArista, false);
		}
		return grafo;
	}

	public static Grafo genGilbert(int numNodos, double probabilidad, boolean dirigido, boolean autociclos) {
		Grafo grafo = new Grafo();
		numnodos = numNodos;
		Random r = new Random();
		for (int i = 0; i < numNodos; i++) {
			for (int j = 0; j < numNodos; j++) {
				Arista n = new Arista();
				Nodo nodo = new Nodo();
				Nodo nodo2 = new Nodo();
				nodo.setIdNodo(i);
				nodo2.setIdNodo(j);
				n.setN1(nodo);
				n.setN2(nodo2);
				if (autociclos) {
					if (r.nextDouble() <= probabilidad) {
						if (revisarConexion(i, j) == 0) {
							hashMapArista.put(count, n);
							count += 1;
						}
					}
				} else {
					if (i != j) {
						if (r.nextDouble() <= probabilidad) {
							if (revisarConexion(i, j) == 0) {
								hashMapArista.put(count, n);
								count += 1;
							}

						}
					}
				}

			}
		}
		if (dirigido) {
			exportarArchivo("Gilbert-dirigido", hashMapArista, true);
		} else {
			exportarArchivo("Gilbert-500", hashMapArista, false);
		}
		return grafo;
	}

	public static Grafo genGeograficoSimple(int numNodos, double distancia, boolean dirigido, boolean autociclos) {
		Random x = new Random();
		Random y = new Random();
		Grafo grafo = new Grafo();
		for (int i = 0; i < numNodos; i++) {
			Nodo nodo = new Nodo();
			nodo.setIdNodo(i);
			nodo.setX(x.nextDouble());
			nodo.setY(y.nextDouble());
			hashMapNodo.put(i, nodo);
		}

		for (int i = 0; i < numNodos; i++) {
			for (int j = i + 1; j < numNodos; j++) {
				double d = distanciaCalculada(i, j);
				// System.out.printf("\nLa distancia entre los puntos es: %.2f\n", d);
				if (d <= distancia) {
					Arista n = new Arista();
					Nodo nodo1 = new Nodo();
					Nodo nodo2 = new Nodo();
					nodo1.setIdNodo(i);
					nodo2.setIdNodo(j);
					n.setN1(nodo1);
					n.setN2(nodo2);
					if (revisarConexion(i, j) == 0) {
						hashMapArista.put(count, n);
						count += 1;
					}
				}
			}
		}
		if (dirigido) {
			exportarArchivo("GeograficoSimple-dirigido", hashMapArista, true);
		} else {
			exportarArchivo("GeograficoSimple-500", hashMapArista, false);
		}
		return grafo;
	}

	public static Grafo genBarabasiAlbert(int numNodos, int grado, boolean dirigido, boolean autociclos) {
		Random p = new Random();
		Grafo grafo = new Grafo();
		for (int i = 0; i < numNodos; i++) {
			for (int j = i - 1; j >= 0; j--) {
				double probabilidad = 1 - (obtenerGrado(j) / (double) grado);
				if (p.nextDouble() < probabilidad) {
					if (revisarConexion(i, j) == 0) {
						if (obtenerGrado(i) <= (double) grado) {
							Arista n = new Arista();
							Nodo nodo1 = new Nodo();
							Nodo nodo2 = new Nodo();
							nodo1.setIdNodo(i);
							nodo2.setIdNodo(j);
							n.setN1(nodo1);
							n.setN2(nodo2);
							hashMapArista.put(count, n);
							count += 1;
						}
					}
				}
			}
		}
		if (dirigido) {
			exportarArchivo("BarabasiAlbert-dirigido", hashMapArista, true);
		} else {
			exportarArchivo("BarabasiAlbert-500", hashMapArista, false);
		}
		return grafo;
	}

	public static void conectarVertice(int i, int j) {

		Arista n = new Arista();
		Nodo nodo1 = new Nodo();
		Nodo nodo2 = new Nodo();
		nodo1.setIdNodo(i);
		nodo2.setIdNodo(j);
		n.setN1(nodo1);
		n.setN2(nodo2);
		hashArbol.put(countAux, n);
		countAux += 1;
	}

	public static int obtenerGrado(Integer n1) {
		int c = 0;
		for (HashMap.Entry<Integer, Arista> i : hashMapArista.entrySet()) {
			// Integer key = i.getKey();
			Arista value = i.getValue();
			int nod1 = value.getN1().getIdNodo();
			int nod2 = value.getN2().getIdNodo();
			if (nod1 == n1 || nod2 == n1) {
				c = c + 1;
			}
		}

		return c;
	}

	public static double distanciaCalculada(Integer n1, Integer n2) {
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		for (HashMap.Entry<Integer, Nodo> i : hashMapNodo.entrySet()) {
			Integer key = i.getKey();
			Nodo value = i.getValue();
			int k = key;
			if (k == n1) {
				x1 = value.getX();
				y1 = value.getY();
			}
			if (k == n2) {
				x2 = value.getX();
				y2 = value.getY();
			}

		}
		double x = Math.abs(x2 - x1);
		double y = Math.abs(y2 - y1);
		double dis = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return dis;
	}

	public static int revisarConexion(int n, int m) {
		int c = 0;
		for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
			// Integer key = i.getKey();
			Arista value = i.getValue();
			if ((value.getN1().getIdNodo() == n && value.getN2().getIdNodo() == m)
					|| (value.getN1().getIdNodo() == m && value.getN2().getIdNodo() == n)) {
				c = 1;
				break;
			} else {
				c = 0;
			}
		}
		return c;
	}

	public void BFS(int s, Grafo g) {
		// Grafo grafo = new Grafo();
		PriorityQueue<Integer> ListaCapas = new PriorityQueue<Integer>();
		Boolean[] discovered = new Boolean[Grafo.numnodos];
		discovered[s] = true;
		for (Entry<Integer, Arista> i : g.hashMapArista.entrySet()) {
			Arista value = i.getValue();
			if ((value.getN1().getIdNodo() != s) || (value.getN2().getIdNodo() != 0)) {
				discovered[value.getN1().getIdNodo()] = false;
				discovered[value.getN2().getIdNodo()] = false;
			}

		}
		ListaCapas.add(s);
		while (ListaCapas.peek() != null) {
			int u = ListaCapas.poll();
			HashSet<Integer> nodo = getIncidencia(u);
			for (Integer n : nodo) {
				if (!discovered[n]) {
					conectarVertice(u, n);
					discovered[n] = true;
					ListaCapas.add(n);
				}
			}
			// System.out.println(hashArbol);
		}
		exportarArchivo("BFS", hashArbol, false);
		// return grafo;

	}

	public void DFS_r(int s) {

		Boolean[] discovered = new Boolean[Grafo.numnodos];
		for (int i = 0; i < Grafo.numnodos; i++) {
			discovered[i] = false;
		}

 		DFSrec(s, discovered);
		exportarArchivo("DFS_r", hashArbol, false);
	}

	public void DFSrec(int u, Boolean[] discovered) {
		discovered[u] = true;

		HashSet<Integer> nodo = getIncidencia(u);
		for (Integer n : nodo) {
			if (!discovered[n]) {
				conectarVertice(u, n);
				DFSrec(n, discovered);
			}
		}
	}

	public void DFS_i(int s) {

		Boolean[] explored = new Boolean[Grafo.numnodos];
		Stack<Integer> S = new Stack<Integer>();
		Integer[] parent = new Integer[Grafo.numnodos];
		for (int i = 0; i < Grafo.numnodos; i++) {
			explored[i] = false;
		}
		S.push(s);
		while (!S.isEmpty()) {

			int u = S.pop();
			if (!explored[u]) {
				explored[u] = true;
				if (u != s) {
					conectarVertice(u, parent[u]);
				}
				HashSet<Integer> nodo = getIncidencia(u);
				for (Integer n : nodo) {
					S.push(n);
					parent[n] = u;
				}

			}
		}

		exportarArchivo("DFS_i", hashArbol, false);
	}

	public HashSet<Integer> getIncidencia(int n) {
		HashSet<Integer> adyacencia = new HashSet<Integer>();
		for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
			Arista value = i.getValue();
			if (value.getN1().getIdNodo() == n) {
				adyacencia.add(value.getN2().getIdNodo());
			}
			if (value.getN2().getIdNodo() == n) {
				adyacencia.add(value.getN1().getIdNodo());
			}
		}

		return adyacencia;

	}

	public static void exportarArchivo(String nombre, HashMap<Integer, Arista> a, boolean dirigido) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		String grafoFinal;
		try {
			fichero = new FileWriter("C:\\Users\\nayel\\OneDrive\\Documentos\\MCC\\" + nombre + ".gv");
			pw = new PrintWriter(fichero);
			if (dirigido) {
				grafoFinal = "digraph {\n";
				for (Entry<Integer, Arista> i : a.entrySet()) {
					Arista value = i.getValue();
					grafoFinal += "n" + value.getN1().getIdNodo() + "->" + "n" + value.getN2().getIdNodo() + ";\n";
				}
				grafoFinal += "}\n";
				pw.println(grafoFinal);
			} else {
				grafoFinal = "graph {\n";
				for (Entry<Integer, Arista> i : a.entrySet()) {
					Arista value = i.getValue();
					grafoFinal += "n" + value.getN1().getIdNodo() + "--" + "n" + value.getN2().getIdNodo() + ";\n";
				}
				grafoFinal += "}\n";
				pw.println(grafoFinal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
