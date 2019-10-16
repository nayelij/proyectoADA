package proyecto1.grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

public class Grafo {

	HashMap<Integer, Nodo> hashMapNodo = null;
	HashMap<Integer, Arista> hashMapArista = null;
	HashSet<Nodo> nodos = new HashSet<Nodo>();
	HashMap<Nodo, HashSet<Arista>> incidencia; 
	HashMap<Integer, Arista> hashArbol,hashArbolDijkstra, hashArbolDFS, hashArbolDFSR = null;
	int aristasAux = 0, count, countAux, numnodos;

	public Grafo() {
		this.incidencia = new HashMap<Nodo, HashSet<Arista>>();
		hashMapNodo = new HashMap<Integer, Nodo>();
		hashMapArista = new HashMap<Integer, Arista>();
		hashArbol = new HashMap<Integer, Arista>();
		hashArbolDFS = new HashMap<Integer, Arista>();
		hashArbolDFSR = new HashMap<Integer, Arista>();
	
	}

	public void genErdosRenyi(int numAristas, int numNodos, boolean dirigido, boolean autociclos) {
//		Grafo grafo = new Grafo();
		numnodos = numNodos;
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
					if (revisarConexion(c1, c2, hashMapArista) == 0) {
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
						if (revisarConexion(c1, c2, hashMapArista) == 0) {
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
//		if (dirigido) {
//			exportarArchivo("ErdosRenyi-dirigido", hashMapArista, true, false);
//		} else {
//			exportarArchivo("ErdosRenyi-100", hashMapArista, false, false);
//		}

	}

	public void genGilbert(int numNodos, double probabilidad, boolean dirigido, boolean autociclos) {
//		Grafo grafo = new Grafo();
		numnodos = numNodos;
		Random r = new Random();
		for (int i = 0; i < numNodos; i++) {
			for (int j = 0; j < numNodos; j++) {
				Arista n = new Arista();
				Nodo nodo = new Nodo();
				Nodo nodo1 = new Nodo();
				Nodo nodo2 = new Nodo();
				nodo.setIdNodo(i);
				nodo2.setIdNodo(j);
				n.setN1(nodo);
				n.setN2(nodo2);

				if (autociclos) {
					if (r.nextDouble() <= probabilidad) {
						if (revisarConexion(i, j, hashMapArista) == 0) {
							hashMapArista.put(count, n);
							count += 1;
						}
					}
				} else {
					if (i != j) {
						if (r.nextDouble() <= probabilidad) {
							if (revisarConexion(i, j, hashMapArista) == 0) {
								hashMapArista.put(count, n);
								count += 1;
							}

						}
					}
				}

			}
		}
//		if (dirigido) {
//			exportarArchivo("Gilbert-dirigido", hashMapArista, true,false);
//		} else {
//			exportarArchivo("Gilbert-500", hashMapArista, false,false);
//		}
	}

	public void genGeograficoSimple(int numNodos, double distancia, boolean dirigido, boolean autociclos) {
		Random x = new Random();
		Random y = new Random();
		numnodos = numNodos;
//		Grafo grafo = new Grafo();
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
					if (revisarConexion(i, j, hashMapArista) == 0) {
						hashMapArista.put(count, n);
						count += 1;
					}
				}
			}
		}
		if (dirigido) {
			exportarArchivo("GeograficoSimple-dirigido", hashMapArista, true, false);
		} else {
			exportarArchivo("GeograficoSimple-500", hashMapArista, false, false);
		}

	}

	public void genBarabasiAlbert(int numNodos, int grado, boolean dirigido, boolean autociclos) {
		Random p = new Random();
//		Grafo grafo = new Grafo();

		numnodos = numNodos;
		for (int i = 0; i < numNodos; i++) {
			for (int j = i - 1; j >= 0; j--) {
				double probabilidad = 1 - (obtenerGrado(j) / (double) grado);
				if (p.nextDouble() < probabilidad) {
					if (revisarConexion(i, j, hashMapArista) == 0) {
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
//		if (dirigido) {
//			exportarArchivo("BarabasiAlbert-dirigido", hashMapArista, true, false);
//		} else {
//			exportarArchivo("BarabasiAlbert-500", hashMapArista, false, false);
//		}

	}

	public void conectarVertice(int i, int j, HashMap<Integer, Arista> a) {

		Arista n = new Arista();
		Nodo nodo1 = new Nodo();
		Nodo nodo2 = new Nodo();
		nodo1.setIdNodo(i);
		nodo2.setIdNodo(j);
		n.setN1(nodo1);
		n.setN2(nodo2);
		if(a.equals(hashArbol)) {
			n.setPeso(1.0f);
		}
		a.put(countAux, n);
		countAux += 1;

	}

	public int obtenerGrado(Integer n1) {
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

	public double distanciaCalculada(Integer n1, Integer n2) {
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

	public int revisarConexion(int n, int m, HashMap<Integer, Arista> a) {
		int c = 0;
		for (Entry<Integer, Arista> i : a.entrySet()) {
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

	public void BFS(int s) {
		// Grafo grafo = new Grafo();
		PriorityQueue<Integer> ListaCapas = new PriorityQueue<Integer>();
		Boolean[] discovered = new Boolean[numnodos];
		discovered[s] = true;
		for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
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
					if (revisarConexion(u, n, hashArbol) == 0) {
						conectarVertice(u, n, hashArbol);
						discovered[n] = true;
						ListaCapas.add(n);
					}

				}
			}
			// System.out.println(hashArbol);
		}
		exportarArchivo("ErdosReyni-BFS-100", hashArbol, false, false);
		// return grafo;

	}

	public void DFS_r(int s) {

		Boolean[] discovered = new Boolean[numnodos];
		for (int i = 0; i < numnodos; i++) {
			discovered[i] = false;
		}

		DFSrec(s, discovered);
		exportarArchivo("DFS-r-ErdosReyni-100", hashArbolDFSR, false, false);
	}

	public void DFSrec(int u, Boolean[] discovered) {
		discovered[u] = true;

		HashSet<Integer> nodo = getIncidencia(u);
		for (Integer n : nodo) {
			if (!discovered[n]) {
				conectarVertice(u, n, hashArbolDFSR);
				DFSrec(n, discovered);
			}
		}
	}

	public void DFS_i(int s) {

		Boolean[] discovered = new Boolean[numnodos];
		Stack<Integer> S = new Stack<Integer>();
		Integer[] p = new Integer[numnodos];
		for (int i = 0; i < numnodos; i++) {
			discovered[i] = false;
		}
		S.push(s);
		while (!S.isEmpty()) {

			int u = S.pop();
			if (!discovered[u]) {
				discovered[u] = true;
				if (u != s) {
					conectarVertice(u, p[u], hashArbolDFS);
				}
				HashSet<Integer> nodo = getIncidencia(u);
				for (Integer n : nodo) {
					S.push(n);
					p[n] = u;
				}

			}
		}

		exportarArchivo("DFS-i-ErdosReyni-100", hashArbolDFS, false, false);
		// exportarArchivo("DFS_r", hashArbol, false);
	}

	public void Dijkstra(int s) {

		double inf = Double.POSITIVE_INFINITY;

		Integer[] p = new Integer[numnodos];
		Boolean[] discovered = new Boolean[numnodos];
		discovered[s] = true;
		for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
			Arista value = i.getValue();
			if ((value.getN1().getIdNodo() != s) || (value.getN2().getIdNodo() != 0)) {
				discovered[value.getN1().getIdNodo()] = false;
				discovered[value.getN2().getIdNodo()] = false;
			}

		}
		for (int i = 0; i < numnodos; i++) {
			Nodo n = new Nodo();
			n.setIdNodo(i);
			n.setDistance(inf);
			nodos.add(n);
			p[i] = null;
		}

		for (Nodo node : nodos) {
			if (node.getIdNodo() == s) {
				node.setDistance(0.0);
			}
		}
		p[s] = s;

		PriorityQueue<Nodo> dN = new PriorityQueue<>(vertexDistanceComp);
		for (int i = 0; i < numnodos; i++) {
			for (Nodo node : nodos) {
				if (node.getIdNodo() == i) {
					dN.add(node);
				}
			}

		}
		while (dN.peek() != null) {
			Nodo u = dN.poll();
			HashSet<Arista> aristas = getVertice(u.getIdNodo());
			for (Arista e : aristas) {

				if ((e.getN1().getDistance()) > (u.getDistance() + e.getPeso())){
					for (Nodo nodes : nodos) {
						if (nodes.getIdNodo() == e.getN1().getIdNodo())  {
							nodes.setDistance(u.getDistance() + e.getPeso());
						}
					}
					
					p[e.getN1().getIdNodo()] = u.getIdNodo();
					
				}
	
				}
			
			HashSet<Integer> nodo = getIncidencia(u.getIdNodo());
			for (Integer n : nodo) {
				if (!discovered[n]) {
					if (revisarConexion(u.getIdNodo(), n, hashArbolDFS) == 0) {
						conectarVertice(u.getIdNodo(), n, hashArbolDFS);
						discovered[n] = true;
						
					}

				}
			}
		}
		
			 
		 exportarArchivo("ErdosReyni-Dijkstra", hashArbolDFS, false, true);

	}

	
	  public HashSet<Arista> getNodesPesos(int n) {
		   
		  HashSet<Arista> pesos = new HashSet<Arista>();
			for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
				Arista value = i.getValue();
				if (value.getN1().getIdNodo() == n) {
					pesos.add(value);
				}
				if (value.getN2().getIdNodo() == n) {
					pesos.add(value);
				}
			}

			return pesos;
		  }
	public HashSet<Arista> getVertice(int n) {
		HashSet<Arista> a = new HashSet<Arista>();
		for (Entry<Integer, Arista> i : hashMapArista.entrySet()) {
			Arista value = i.getValue();
			if (value.getN1().getIdNodo() == n || value.getN2().getIdNodo() == n) {
				a.add(value);
			}

		}

		return a;

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

	public void setAristaValues(float min, float max) {
		Random rand = new Random();
		float peso;
		for (int i = 0; i < numnodos; i++) {
			for (int j = 0; j < numnodos; j++) {
				if (revisarConexion(i, j, hashMapArista) == 1) {
					peso = rand.nextFloat() * (max - min) + min;
					setPeso(i, j, peso);
				}
			}
		}
		exportarArchivo("ErdosReyni-100", hashMapArista, false, true);
	}

	public void setPeso(int i, int j, float peso) {
		double inf = Double.POSITIVE_INFINITY;
		Arista a = new Arista();
		Nodo nodo1 = new Nodo();
		Nodo nodo2 = new Nodo();
		for (Entry<Integer, Arista> k : hashMapArista.entrySet()) {
			Arista value = k.getValue();
			Integer key = k.getKey();
			if ((value.getN1().getIdNodo() == i) && (value.getN2().getIdNodo() == j)) {
				nodo1.setIdNodo(i);
				if ((value.getN1().getIdNodo() != 0)) {
					nodo1.setDistance(inf);
				} else if ((value.getN2().getIdNodo() != 0)) {
					nodo2.setDistance(inf);
				}
				nodo2.setIdNodo(j);
				a.setPeso(peso);
				a.setN1(nodo1);
				a.setN2(nodo2);
				hashMapArista.put(key, a);
			}

		}
	}

	public  void exportarArchivo(String nombre, HashMap<Integer, Arista> a, boolean dirigido, boolean peso) {
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
			}
			else if (peso) {
				grafoFinal = "graph {\n";
				
					for (Nodo node : nodos) {
						grafoFinal += "n" +node.getIdNodo()+ "[label=\"n"+node.getIdNodo() + " (" + node.getDistance() + ")\"]" + ";\n";
					}
				
				for (Entry<Integer, Arista> i : a.entrySet()) {
					Arista value = i.getValue();
					grafoFinal += "n" + value.getN1().getIdNodo() + "--" + "n" + value.getN2().getIdNodo() + "[weight="
							+ value.getPeso() + " label=" + value.getPeso() + "]" + ";\n";
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

	Comparator<Nodo> vertexDistanceComp = new Comparator<Nodo>() {
		@Override
		public int compare(Nodo n1, Nodo n2) {
			return Double.compare(n1.getDistance(), n2.getDistance());
		}
	};
}
