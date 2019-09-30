package proyecto1.grafo;


public class Main {

	public static void main(String s[]) {
		 //Grafo.genErdosRenyi(500,500,false,false);
		// +
		Grafo g = new Grafo();
		Grafo y = new Grafo();
		Grafo r = new Grafo();
		g = Grafo.genGilbert(30, 0.5, false, false);
		//r.DFS_r(0);
		y.DFS_i(0);
		 //Grafo.genGeograficoSimple(500, 0.2, false, false);
		//Grafo.genBarabasiAlbert(500, 5, false, false);

	}

}
