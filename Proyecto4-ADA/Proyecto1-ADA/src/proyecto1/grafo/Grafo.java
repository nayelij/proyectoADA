package proyecto1.grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public class Grafo {

	HashMap<Integer, Nodo> hashMapNodo = null;
	HashMap<Integer, Arista> hashMapArista = null;
	HashSet<Nodo> nodos = new HashSet<Nodo>();
	HashMap<Nodo, HashSet<Arista>> incidencia;
	HashMap<Integer, Arista> hashArbol, hashArbolDijkstra, hashArbolDFS, hashArbolDFSR = null;
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

//		if (dirigido) {
//			exportarArchivo("GeograficoSimple-dirigido", hashMapArista, true, false);
//		} else {
//			exportarArchivo("GeograficoSimple-500", hashMapArista, false, false);
//		}

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
		if (a.equals(hashArbol)) {
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

				if ((e.getN1().getDistance()) > (u.getDistance() + e.getPeso())) {
					for (Nodo nodes : nodos) {
						if (nodes.getIdNodo() == e.getN1().getIdNodo()) {
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
		HashMap<Integer, Arista> hashMapArista2 = new HashMap<Integer, Arista>();
		float peso;
		for (int i = 0; i < numnodos; i++) {
			for (int j = 0; j < numnodos; j++) {
				if (revisarConexion(i, j, hashMapArista) == 1) {
					peso = rand.nextFloat() * (max - min) + min;
					setPeso(i, j, peso);
				}
			}
		}
		int index = 0;
		for (Entry<Integer, Arista> k : hashMapArista.entrySet()) {
			Arista value = k.getValue();
			Nodo nodo1 = new Nodo();
			Nodo nodo2 = new Nodo();
			Arista a = new Arista();
			nodo1.setIdNodo(value.getN1().getIdNodo());
			nodo2.setIdNodo(value.getN2().getIdNodo());
			a.setN1(nodo1);
			a.setN2(nodo2);
			a.setPeso(value.getPeso());
			hashMapArista2.put(index, a);
			index += 1;
		}

//		kruskali(kruskal_d(hashMapArista2));

		exportarArchivo("BarabasiAlbert-30", hashMapArista, false, true);
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

	public HashMap<Integer, Arista> kruskal_d() {
		HashMap<Integer, Arista> mst = new HashMap<Integer, Arista>();
		Arista arista = new Arista();

		Collection<Arista> s = hashMapArista.values();
		List<Arista> list = new ArrayList<>(s);
		Collections.sort(list, new MyComparator());

		ArrayList<Integer> nodos = new ArrayList<Integer>();
		ArrayList<Integer> nodos1 = new ArrayList<Integer>();

		ArrayList<ArrayList<Integer>> componentes = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numnodos; i++) {
			nodos.add(i);
			nodos1.add(i);
		}
		componentes.add(0, nodos);
		componentes.add(1, nodos1);
		Iterator<Arista> itr = list.iterator();
		int flag = 0;
		while (itr.hasNext()) {

			arista = itr.next();
			int n1 = arista.getN1().getIdNodo();
			int n2 = arista.getN2().getIdNodo();
			int f1 = find(n1, componentes);
			int f2 = find(n2, componentes);
			float peso = arista.getPeso();
			if (f1 != f2) {
				Nodo nodo1 = new Nodo();
				Nodo nodo2 = new Nodo();
				Arista a = new Arista();
				nodo1.setIdNodo(n1);
				nodo2.setIdNodo(n2);
				a.setN1(nodo1);
				a.setN2(nodo2);
				a.setPeso(peso);
				mst.put(flag, a);
				combinaConjuntos(n1, n2, componentes);
			}
			flag += 1;
		}
		float peso = 0f;
		for (Entry<Integer, Arista> k : mst.entrySet()) {
			Arista value = k.getValue();
			peso += value.getPeso();
		}
		System.out.println("BarabasiAlbert-Kruskal-d-30 " + peso);
		exportarArchivo("BarabasiAlbert-Kruskal-d-30", mst, false, true);
		return mst;
	}

	public void kruskal_i() {
		Arista arista = new Arista();
		HashMap<Integer, Arista> msti = new HashMap<Integer, Arista>();
		ArrayList<ArrayList<Integer>> component = new ArrayList<ArrayList<Integer>>();
		Collection<Arista> s = hashMapArista.values();
		List<Arista> list1 = new ArrayList<>(s);
		Collections.sort(list1, new MyComparatorDescending());
		Iterator<Arista> itr = list1.iterator();
		int i = 0;
		int p = 1;
		int flag = 0;
		int n1, n2, nod1, nod2;
		while (itr.hasNext()) {
			component = limpiaConjuntos();
			arista = itr.next();
			nod1 = arista.getN1().getIdNodo();
			nod2 = arista.getN2().getIdNodo();
			flag = findPosition(nod1, nod2, list1);
			if (p == 1) {
				for (int k = 1; k < list1.size(); k++) {
					n1 = list1.get(k).getN1().getIdNodo();
					n2 = list1.get(k).getN2().getIdNodo();
					combinaConjuntos(n1, n2, component);
				}
				p=0;
			} else {
				if(flag == 0) {
					for (int k = 1; k < list1.size(); k++) {
							n1 = list1.get(k).getN1().getIdNodo();
							n2 = list1.get(k).getN2().getIdNodo();
							combinaConjuntos(n1, n2, component);
					}
				}else {
					for (int k = 0; k < list1.size(); k++) {
						if(k!=flag) {
							n1 = list1.get(k).getN1().getIdNodo();
							n2 = list1.get(k).getN2().getIdNodo();
							combinaConjuntos(n1, n2, component);
						}
						
				}
				}
				
			}
			
			nod1 = arista.getN1().getIdNodo();
			nod2 = arista.getN2().getIdNodo();
			arista.getPeso();
			int f1 = find(nod1, component);
			int f2 = find(nod2, component);
			if (f1 == f2) {
				itr.remove();
			} 

		}

		int index = 0;
		for (int n = 0; n < list1.size(); n++) {
			Arista a = new Arista();
			Nodo nodo1 = new Nodo();
			Nodo nodo2 = new Nodo();
			nodo1.setIdNodo(list1.get(n).getN1().getIdNodo());
			nodo2.setIdNodo(list1.get(n).getN2().getIdNodo());
			a.setN1(nodo1);
			a.setN2(nodo2);
			a.setPeso(list1.get(n).getPeso());
			msti.put(index, a);
			index += 1;
		}
		float peso = 0f;
		for (int k = 0; k < list1.size(); k++) {
			peso += list1.get(k).getPeso();
		}
		System.out.println("BarabasiAlbert-Kruskal-i-30 " + peso);
		exportarArchivo("BarabasiAlbert-Kruskal-i-30", msti, false, true);
	}

	public int findPosition(int n1, int n2, List<Arista> list1 ) {
		int index =0;
		for (int k = 0; k < list1.size(); k++) {
			if(list1.get(k).getN1().getIdNodo() == n1 && list1.get(k).getN2().getIdNodo() == n2 ) {
				index = k;
			}
		}
		return index;
	}
	public ArrayList<ArrayList<Integer>> limpiaConjuntos() {
		ArrayList<Integer> nodos = new ArrayList<Integer>();
		ArrayList<Integer> nodos1 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
		c.add(new ArrayList<Integer>());
		c.add(new ArrayList<Integer>());
		for (int i = 0; i < numnodos; i++) {
			nodos.add(i);
			nodos1.add(i);
		}
		c.set(0, nodos);
		c.set(1, nodos1);
		return c;
	}

	public int find(int nodo, ArrayList<ArrayList<Integer>> componentes) {
		int raiz = 0;
		for (int i = 1; i < componentes.size(); i++) {
			for (int j = 0; j < componentes.get(i).size(); j++) {
				if (j == nodo) {
					raiz = componentes.get(1).get(j);
				}
			}

		}

		return raiz;
	}

	public void combinaConjuntos(int n1, int n2, ArrayList<ArrayList<Integer>> componentes) {
		int raiz1 = componentes.get(1).get(n1);
		int raiz2 = componentes.get(1).get(n2);

		int aux = componentes.get(1).get(n2);
		componentes.get(1).set(n2, raiz1);
		for (int i = 1; i < componentes.size(); i++) {
			for (int j = 0; j < componentes.get(i).size(); j++) {
				int c = componentes.get(1).get(j);
				if (c == aux) {
					componentes.get(1).set(j, raiz1);
				}
			}
		}

//		System.out.println("");
	}

	public void exportarArchivo(String nombre, HashMap<Integer, Arista> a, boolean dirigido, boolean peso) {
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
			} else if (peso) {
				grafoFinal = "graph {\n";

				for (Nodo node : nodos) {
					grafoFinal += "n" + node.getIdNodo() + "[label=\"n" + node.getIdNodo() + " (" + node.getDistance()
							+ ")\"]" + ";\n";
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

class MyComparator implements Comparator<Arista> {

	@Override
	public int compare(Arista s1, Arista s2) {
		return s1.getPeso().compareTo(s2.getPeso());
	}
}

class MyComparatorDescending implements Comparator<Arista> {

	@Override
	public int compare(Arista s1, Arista s2) {
		return s2.getPeso().compareTo(s1.getPeso());
	}
}
