package Testes;

import java.util.ArrayList;
import java.util.HashSet;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("grafo")
public class Grafo {
	int vertices;
	ArrayList<Aresta> arestas;
	public Grafo(int vertices, ArrayList<Aresta> arestas) {
		this.vertices = vertices;
		this.arestas = arestas;
	}
	public int getVertices() {
		return vertices;
	}
	public void setVertices(int vertices) {
		this.vertices = vertices;
	}
	public ArrayList<Aresta> getArestas() {
		return arestas;
	}
	public void setArestas(ArrayList<Aresta> arestas) {
		this.arestas = arestas;
	}
}

