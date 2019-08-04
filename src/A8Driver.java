
public class A8Driver {

	public static void main(String[] args) {
		A8Graph graph = new A8Graph();
		graph.addEdge("D", "S");
		graph.addEdge("S", "V");
		graph.addEdge("W", "D");
		graph.addEdge("W", "V");
		graph.addEdge("W", "H");
		graph.addEdge("R", "W");
		graph.addEdge("G", "R");
		graph.addEdge("G", "L");
		graph.addEdge("L", "Y");
		graph.addEdge("J", "L");
		graph.addEdge("J", "O");
		graph.addEdge("Q", "J");
		graph.addEdge("Q", "H");
		graph.addEdge("N", "V");
		graph.addEdge("N", "H");
		graph.addEdge("N", "O");
		System.out.println(graph.topologicalSort());
		graph = new A8Graph();
		graph.addEdge("D", "S");
		graph.addEdge("S", "V");
		graph.addEdge("W", "D");
		graph.addEdge("W", "V");
		graph.addEdge("W", "H");
		graph.addEdge("R", "W");
		graph.addEdge("G", "R");
		graph.addEdge("G", "L");
		graph.addEdge("L", "Y");
		graph.addEdge("J", "L");
		graph.addEdge("J", "O");
		graph.addEdge("Q", "J");
		graph.addEdge("Q", "H");
		graph.addEdge("N", "V");
		graph.addEdge("N", "H");
		graph.addEdge("N", "O");
		System.out.println(graph.topologicalSortStack());
	}

}
