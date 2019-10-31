package proyecto1.grafo;


public class Main {

	public static void main(String s[]) {
		
		Grafo g = new Grafo();
		
//		g.genGilbert(500,0.05,false,false);
		g.genBarabasiAlbert(30, 3, false, false);
//		g.genErdosRenyi(500, 500, false, false);
//		g.genGeograficoSimple(500, 0.5, false, false);
		
		g.setAristaValues(1.0f, 15.0f);
		g.kruskal_d();
		g.kruskal_i();
		//g.Dijkstra(0);
		//Grafo.genGilbert(500, 0.07, false, false);
		// Grafo.genGeograficoSimple(500, 0.2, false, false);
		//Grafo.genBarabasiAlbert(500, 5, false, false);
		
		//g.BFS(0);
//		g.DFS_i(0);
//		g.DFS_r(0);
		

	}

}
