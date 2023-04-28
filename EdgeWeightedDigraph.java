import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

public class EdgeWeightedDigraph
{
	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V; // number of vertices in this digraph
	private int E; // number of edges in this digraph
	private ArrayList<ArrayList<DirectedEdge>> adj; // adj[v] = adjacency list for vertex v
	private int[] indegree; // indegree[v] = indegree of vertex v

	/**
	 * Initializes an empty edge-weighted digraph with {@code V} vertices and 0
	 * edges.
	 *
	 * @param V
	 *            the number of vertices
	 * @throws IllegalArgumentException
	 *             if {@code V < 0}
	 */
	public EdgeWeightedDigraph(int V)
	{
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
		this.V = V;
		this.E = 0;
		this.indegree = new int[V];
		adj = new ArrayList<ArrayList<DirectedEdge>>();
		for (int v = 0; v < V; v++)
			adj.add(new ArrayList<DirectedEdge>());
	}



	/**
	 * Initializes an edge-weighted digraph from the specified input stream. The
	 * format is the number of vertices <em>V</em>, followed by the number of edges
	 * <em>E</em>, followed by <em>E</em> pairs of vertices and edge weights, with
	 * each entry separated by whitespace.
	 *
	 * @param in
	 *            the input stream
	 * @throws FileNotFoundException 
	 * @throws IllegalArgumentException
	 *             if {@code in} is {@code null}
	 * @throws IllegalArgumentException
	 *             if the endpoints of any edge are not in prescribed range
	 * @throws IllegalArgumentException
	 *             if the number of vertices or edges is negative
	 */
	public EdgeWeightedDigraph(String filename) throws FileNotFoundException
	{
		Scanner in = new Scanner(new File(filename));
		if (in == null)
			throw new IllegalArgumentException("argument is null");
		try
		{
			this.V = in.nextInt();
			if (V < 0)
				throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
			indegree = new int[V];
			adj = new ArrayList<ArrayList<DirectedEdge>>();
			for (int v = 0; v < V; v++)
			{
				adj.add(new ArrayList<DirectedEdge>());
			}

			int E = in.nextInt();
			if (E < 0)
				throw new IllegalArgumentException("Number of edges must be non-negative");
			for (int i = 0; i < E; i++)
			{
				int v = in.nextInt();
				int w = in.nextInt();
				validateVertex(v);
				validateVertex(w);
				double weight = in.nextDouble();
				addEdge(new DirectedEdge(v, w, weight));
			}
		} catch (NoSuchElementException e)
		{
			throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
		}
	}

	
	/**
	 * Returns the number of vertices in this edge-weighted digraph.
	 *
	 * @return the number of vertices in this edge-weighted digraph
	 */
	public int V()
	{
		return V;
	}

	/**
	 * Returns the number of edges in this edge-weighted digraph.
	 *
	 * @return the number of edges in this edge-weighted digraph
	 */
	public int E()
	{
		return E;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v)
	{
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	/**
	 * Adds the directed edge {@code e} to this edge-weighted digraph.
	 *
	 * @param e
	 *            the edge
	 * @throws IllegalArgumentException
	 *             unless endpoints of edge are between {@code 0} and {@code V-1}
	 */
	public void addEdge(DirectedEdge e)
	{
		int v = e.from();
		int w = e.to();
		validateVertex(v);
		validateVertex(w);
		adj.get(v).add(e);
		indegree[w]++;
		E++;
	}

	/**
	 * Returns the directed edges incident from vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the directed edges incident from vertex {@code v} as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<DirectedEdge> adj(int v)
	{
		validateVertex(v);
		return adj.get(v);
	}

	/**
	 * Returns the number of directed edges incident from vertex {@code v}. This is
	 * known as the <em>outdegree</em> of vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the outdegree of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int outdegree(int v)
	{
		validateVertex(v);
		return adj.get(v).size();
	}

	/**
	 * Returns the number of directed edges incident to vertex {@code v}. This is
	 * known as the <em>indegree</em> of vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the indegree of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int indegree(int v)
	{
		validateVertex(v);
		return indegree[v];
	}

	/**
	 * Returns all directed edges in this edge-weighted digraph. To iterate over the
	 * edges in this edge-weighted digraph, use foreach notation:
	 * {@code for (DirectedEdge e : G.edges())}.
	 *
	 * @return all edges in this edge-weighted digraph, as an iterable
	 */
	public Iterable<DirectedEdge> edges()
	{
		TreeSet<DirectedEdge> list = new TreeSet<DirectedEdge>();
		for (int v = 0; v < V; v++)
		{
			for (DirectedEdge e : adj(v))
			{
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * Returns a string representation of this edge-weighted digraph.
	 *
	 * @return the number of vertices <em>V</em>, followed by the number of edges
	 *         <em>E</em>, followed by the <em>V</em> adjacency lists of edges
	 */
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + NEWLINE);
		for (int v = 0; v < V; v++)
		{
			s.append(v + ": ");
			for (DirectedEdge e : adj.get(v))
			{
				s.append(e + "  ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	/**
	 * Unit tests the {@code EdgeWeightedDigraph} data type.
	 *
	 * @param args
	 *            the command-line arguments
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(args[0]);
		System.out.println(G);
	}

}
