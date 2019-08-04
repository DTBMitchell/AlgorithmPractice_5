/*
 * Complete topologicalSort() method - 3pts
 * Complete topologicalSortStack() method - 2pts
 * Modify addEdge(String v1, String v2, int weight) method - 1pt
 */
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map;

public class A8Graph {
	private Map<String, A8Vertex> map;
	
	/*
	 * Grading:
	 * Correctly implement Pseudo code - 3pts
	 */
	public String topologicalSort()
	{
		//perform a topological sort on the vertices in the graph
		//add the name and a comma to an output string in the order they should be visited
		//Below is the pseudo code from the textbook
		/*
		 * PSEUDO CODE
		 * Queue<Vertex> q = new Queue<Vertex>();
		 * int counter = 0;
		 * for each Vertex v
		 * {
		 * 		if(v.indegree == 0)
		 * 		{
		 * 			q.enqueue(v);
		 * 		}
		 * }
		 * while(!q.isEmpty())
		 * {
		 * 		Vertex v = q.dequeue();
		 * 		v.topNum = ++counter;//NOTE FROM JET - this is where you add vertex name to output string
		 * 		for each Vertex w adjacent to v
		 * 		{
		 * 			if(--w.indegree == 0)
		 * 			{
		 * 				q.enqueue(w);
		 * 			}
		 * 		}
		 * }
		 */

		PriorityQueue<A8Vertex> q = new PriorityQueue<>();
		String rtn = "";
		for (Map.Entry<String, A8Vertex> e : map.entrySet()) {
			if(e.getValue().indegree == 0) {
				q.offer(e.getValue());
			}
		}

		while(!q.isEmpty()){
			A8Vertex v = q.poll();
			rtn += v.getName()+", ";

			for(Map.Entry<String, Integer> e : v.getAdjacencyList().entrySet())//for each Vertex
			{
				A8Vertex w = map.get(e.getKey());
				if(--w.indegree == 0){
					q.offer(w);
				}
			}
		}

		return rtn;
	}
	/*
	 * Grading:
	 * Correctly implement method using a stack instead of a queue - 2pts
	 */
	public String topologicalSortStack()
	{
		/*
		 * Use the same process as the previous method, using a stack instead of queue
		 */

		Stack<A8Vertex> s = new Stack<>();
		String rtn = "";
		for (Map.Entry<String, A8Vertex> e : map.entrySet()) {
			if(e.getValue().indegree == 0)
		 	{
				s.push(e.getValue());
			}
		}

		while(!s.isEmpty()){
			A8Vertex v = s.pop();
			rtn += v.getName()+", ";
			for(Map.Entry<String, Integer> e : v.getAdjacencyList().entrySet())//for each Vertex
			{
				A8Vertex w = map.get(e.getKey());
				if(--w.indegree == 0){
					s.push(w);
				}
			}
		}

		return rtn;
	}
	/*
	 * Grading:
	 * Indegree incremented for correct vertex - 1pt
	 */
	public void addEdge(String v1, String v2, int weight)
	{
		//MODIFY - increment indegree for correct vertex

		//ensure each vertex exists in map
		addVertex(v1);
		addVertex(v2);
		//connect vertex1 to vertex2 with given weight
		map.get(v1).addEdge(v2, weight);
		map.get(v1).indegree++;
	}
	
	/*DO NOT MODIFY*/
	public A8Graph()
	{
		map = new TreeMap<>();
	}

	
	public void addEdge(String v1, String v2)
	{
		addEdge(v1, v2, 1);
	}
	public void addUndirectedEdge(String v1, String v2, int weight)
	{
		addEdge(v1, v2, weight);
		addEdge(v2, v1, weight);
	}
	public void addUndirectedEdge(String v1, String v2)
	{
		addUndirectedEdge(v1, v2, 1);
	}

	private void addVertex(String v)
	{
		if(!map.containsKey(v))//if vertex isn't already in the map
		{
			map.put(v, new A8Vertex(v));//create vertex and add to map
		}
	}
	public String toString()
	{
		String output = "Graph:\n";

		if(map.size() > 0)
		{
			for(Map.Entry<String,A8Vertex> e : map.entrySet())
			{
				output += "("+e.getKey()+", "+e.getValue()+")\n";
			}
		}

		return output;
	}

	public void printPath(String vs, String ve, String type)
	{
		if(map.containsKey(vs) && map.containsKey(ve))
		{
			System.out.println(type.toUpperCase());
			A8Vertex s = map.get(vs);
			if(type.toLowerCase().equals("unweighted"))
			{
				unweighted(s);
			}
			else if(type.toLowerCase().equals("weighted"))
			{
				weighted(s);
			}
			else if(type.toLowerCase().equals("negative"))
			{
				negative(s);
			}
			A8Vertex e = map.get(ve);
			/*
			 * Pseudocode
			if(e.dist != INFINITY){
				String path = "";
				Vertex curr = e;
				while(curr.path != null){
					path += curr;
					curr = curr.path;
				}
				path = s + path;
				print(path)
				print(dist)
			}else{
				print("can not reach end");
			}
			 */
			if(e.dist != Integer.MAX_VALUE)
			{
				String path = "";
				A8Vertex curr = e;
				while(curr.path != null)
				{
					if(path != "")
					{
						path = curr.getName() + "," + path;
					}
					else
					{
						path = curr.getName();
					}
					curr = curr.path;
				}
				path = s.getName() + "," + path;
				System.out.println(path);
				System.out.println(e.dist);
			}
			else
			{
				System.out.println("End is not reachable from Start");
			}
		}
	}
	public void unweighted(A8Vertex s)
	{
		/*
		 * Pseudocode from textbook PG 372
		Queue<Vertex> q = new Queue<Vertex>();
		for each Vertex v{
			v.dist = INFINITY;
			v.path = null;//added to make sure we clear the path between runs of pathing methods
		}
		s.dist = 0;
		q.enqueue(s);
		while(!q.isEmpty()){
			Vertex v = q.dequeue();
			for each Vertex w adjacent to v{
				if(w.dist == INFINITY){
					w.dist = v.dist + 1;
					w.path = v;
					q.enqueue(w);
				}
			}
		}
		 */
		LinkedList<A8Vertex> q = new LinkedList<>();//can be used like a queue
		for(Map.Entry<String, A8Vertex> vertexEntry : map.entrySet())
		{
			A8Vertex v = vertexEntry.getValue();
			v.dist = Integer.MAX_VALUE;//~2+ billion ~INFINITY
			v.path = null;//added to make sure we clear the path between runs of pathing methods
		}
		s.dist = 0;
		q.addLast(s);//queue.enqueue is FIFO, so add at end and take off beginning
		while(!q.isEmpty())
		{
			A8Vertex v = q.removeFirst();//q.dequeue();, remove from beginning because adding to end
			for(Map.Entry<String, Integer> adjacent : v.getAdjacencyList().entrySet())
			{
				A8Vertex w = map.get(adjacent.getKey());//adjacent.getKey is name of adjacent vertex
				if(w.dist == Integer.MAX_VALUE)//INFINITY
				{
					w.dist = v.dist + 1;
					w.path = v;//how we got to this vertex
					q.addLast(w);//q.enqueue(w);
				}
			}
		}
	}
	public void weighted(A8Vertex s)
	{
		/*
		 * Pseudocode
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
		//implement Comparable<Vertex> based on distance for PriorityQueue
		for each Vertex v{
			v.dist = INFINITY;
			v.path = null;//added to make sure we clear the path between runs of pathing methods
			v.known = false;
		}
		s.dist = 0;
		q.enqueue(s);
		while(!q.isEmpty()){
			Vertex v = q.dequeue();//smallest distance in queue
			v.known = true;
			for each Vertex w adjacent to v{
				if(w.dist > v.dist + w.weight){
					w.dist = v.dist + w.weight;
					w.path = v;
				}
				if(!w.known){
					q.enqueue(w);
				}
			}
		}
		 */
		PriorityQueue<A8Vertex> q = new PriorityQueue<>();
		for(Map.Entry<String, A8Vertex> vertexEntry : map.entrySet())
		{
			A8Vertex v = vertexEntry.getValue();
			v.dist = Integer.MAX_VALUE;//INFINITY
			v.path = null;//added to make sure we clear the path between runs of pathing methods
			v.known = false;
		}
		s.dist = 0;
		q.offer(s);//q.enqueue(s);
		while(!q.isEmpty())
		{
			A8Vertex v = q.poll();//q.dequeue();//smallest distance in queue
			//System.out.println(v.getName()+":"+q.size());
			v.known = true;
			for(Map.Entry<String, Integer> adjacent : v.getAdjacencyList().entrySet())
			{
				A8Vertex w = map.get(adjacent.getKey());//adjacent.getKey is name of adjacent vertex
				Integer weight = adjacent.getValue();
				if(w.dist > v.dist + weight){
					w.dist = v.dist + weight;
					w.path = v;
				}
				if(!w.known){
					q.offer(w);//q.enqueue(w);
				}
			}
		}

	}
	public void negative(A8Vertex s)
	{
		/*
		 * Pseudocode
		Queue<Vertex> q = new Queue<Vertex>();
		for each Vertex v{
			v.dist = INFINITY;
			v.path = null;//added to make sure we clear the path between runs of pathing methods
		}
		s.dist = 0;
		q.enqueue(s);
		while(!q.isEmpty()){
			Vertex v = q.dequeue();
			for each Vertex w adjacent to v{
				if(w.dist > v.dist + w.weight){
					w.dist = v.dist + w.weight;
					w.path = v;
					if(!q.contains(w)){
						q.enqueue(w);
					}
				}
			}
		}
		 */
		LinkedList<A8Vertex> q = new LinkedList<>();//can be used like a queue
		for(Map.Entry<String, A8Vertex> vertexEntry : map.entrySet())
		{
			A8Vertex v = vertexEntry.getValue();
			v.dist = Integer.MAX_VALUE;//~2+ billion ~INFINITY
			v.path = null;//added to make sure we clear the path between runs of pathing methods
		}
		s.dist = 0;
		q.addLast(s);//queue.enqueue is FIFO, so add at end and take off beginning
		while(!q.isEmpty())
		{
			A8Vertex v = q.removeFirst();//q.dequeue();, remove from beginning because adding to end
			for(Map.Entry<String, Integer> adjacent : v.getAdjacencyList().entrySet())
			{
				A8Vertex w = map.get(adjacent.getKey());//adjacent.getKey is name of adjacent vertex
				Integer weight = adjacent.getValue();
				if(w.dist > v.dist + weight){
					w.dist = v.dist + weight;
					if(w.dist < 0)//stop negative cycle infinite recursion
						w.dist = 0;
					w.path = v;
					if(!q.contains(w)){
						q.addLast(w);//q.enqueue(w);
					}
				}
			}
		}
	}


	public void printMaxDistance()
	{
		int maxDist = 0;
		String vert = "";
		for(Map.Entry<String, A8Vertex> vertex : map.entrySet())
			if(vertex.getValue().dist != Integer.MAX_VALUE && vertex.getValue().dist > maxDist)
			{
				maxDist = vertex.getValue().dist;
				vert = vertex.getKey();
			}
		System.out.println("MAX:"+vert+":"+maxDist);
	}
}
