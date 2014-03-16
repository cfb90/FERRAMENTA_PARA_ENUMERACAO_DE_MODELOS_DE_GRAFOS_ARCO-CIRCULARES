package Testes;


import java.util.HashSet;


import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("aresta")
public class Aresta{
	int vertice1;
	int vertice2;
	public Aresta(int vertice1, int vertice2) {
		if(vertice1<vertice2){
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		}
		else {
			this.vertice1 = vertice2;
			this.vertice2 = vertice1;
		}
	}
	
	public int getVertice1() {
		return vertice1;
	}

	public void setVertice1(int vertice1) {
		this.vertice1 = vertice1;
	}

	public int getVertice2() {
		return vertice2;
	}

	public void setVertice2(int vertice2) {
		this.vertice2 = vertice2;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vertice1;
		result = prime * result + vertice2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aresta other = (Aresta) obj;
		if (vertice1 != other.vertice1)
			return false;
		if (vertice2 != other.vertice2)
			return false;
		return true;
	}
	
}