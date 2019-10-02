package proyecto1.grafo;


public class Main {

	public static void main(String s[]) {
		
		Grafo g = new Grafo();
		Grafo.genErdosRenyi(500,500,false,false);
		//Grafo.genGilbert(500, 0.07, false, false);
		// Grafo.genGeograficoSimple(500, 0.2, false, false);
		//Grafo.genBarabasiAlbert(500, 5, false, false);
		
		g.BFS(0);
		g.DFS_i(0);
		g.DFS_r(0);
		

	}

}
