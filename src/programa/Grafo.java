package programa;

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

@XStreamAlias("aresta")
class Aresta{
	int vertice1;
	int vertice2;
	public Aresta(int inicio, int fim) {
		if(inicio<fim){
		this.vertice1 = inicio;
		this.vertice2 = fim;
		}
		else {
			this.vertice1 = fim;
			this.vertice2 = inicio;
		}
	}
	public int getInicio() {
		return vertice1;
	}
	public void setInicio(int inicio) {
		this.vertice1 = inicio;
	}
	public int getFim() {
		return vertice2;
	}
	public void setFim(int fim) {
		this.vertice2 = fim;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+vertice1+","+vertice2+")";
	}
	public HashSet toHashSet(){
		HashSet<Integer> t = new HashSet<Integer>();
		t.add(vertice1);
		t.add(vertice2);
		return t;
	}
	@Override
	public boolean equals(Object obj) {
		Aresta t= (Aresta) obj;
		if(t.vertice1==this.vertice1 && t.vertice2==this.vertice2)
			return true;
		else if(t.vertice2==this.vertice1 && t.vertice1==this.vertice2) return true;
		else return false;
	}
}