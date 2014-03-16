package programa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import Extra.Fatorial;

public class GerarModelos extends SwingWorker<Void , BigInteger>{
	ArrayList<ArrayList> permutacoes;
	int numPontos; 
	JLabel progresso;
	JProgressBar progre;
	Fatorial fat;
	HashSet<Aresta> arestas;
	BigInteger computados = BigInteger.ZERO;
	BigInteger max;
	int guardarTudo;
	int normal=0;
	int naoNormal=0;
	public GerarModelos(int numPontos,HashSet<Aresta> arestas,JLabel progresso,JProgressBar progre,ArrayList<ArrayList> permutacoes,Fatorial fat,int guardarTudo) {
		this.arestas=arestas;
		this.numPontos=numPontos;
		this.progresso=progresso;
		this.permutacoes=permutacoes;
		this.fat=fat;
		this.guardarTudo=guardarTudo;
		this.progre=progre;
		max=fat.fatorar((numPontos*2)-1);
	}
	public Void doInBackground(){
		ArrayList<Integer> conjunto = new ArrayList<Integer>();
		for(int i=1;i<=numPontos;i++){
			conjunto.add(i);
		}
		for(int i=1;i<=numPontos;i++){
			conjunto.add(-i);
		}
		ArrayList<Integer> inicio = new ArrayList<Integer>();
		inicio.add(menorCardinalidade(numPontos, arestas));
		HashSet<Aresta> teste = new HashSet<Aresta>();
		HashMap<Integer, Integer> pontos= new HashMap<Integer,Integer>();
		//os arcos come�am dos n�meros positivos e v�o at� os negativos no sentido hor�rio
		int t=0;
		for (int i = 1; i <= numPontos; i++) {
			if(i==inicio.get(0)) pontos.put(i, 1);
			else pontos.put(i, 0);
		}
		gerarPermutacoes(numPontos, conjunto,inicio ,arestas,teste,pontos);
		/*for (int i = 0; i < permutacoes.size(); i++) {
			System.out.println(permutacoes.get(i)+"\n");
		}*/
		return null;
		//return permutacoes;
	}

	public int menorCardinalidade(int numPontos,HashSet<Aresta> arestas){
		HashMap<Integer, Integer> cardinalidade = new HashMap<Integer, Integer>(numPontos);
		for (int i = 1; i <= numPontos; i++) {
			cardinalidade.put(i, 0);
		}
		Iterator<Aresta> temp = arestas.iterator();
		while(temp.hasNext()){
			Aresta t = (Aresta) temp.next();
			cardinalidade.put(t.vertice1, cardinalidade.get(t.vertice1)+1);
			cardinalidade.put(t.vertice2, cardinalidade.get(t.vertice2)+1);
		}
		int retorno=1;
		for (int i = 2; i <= numPontos; i++) {
			if(cardinalidade.get(retorno)>=cardinalidade.get(i)){
				retorno=i;
			}
		}
		return retorno;
	}

	public void gerarPermutacoes(int numPonto,ArrayList<Integer> conjunto,ArrayList<Integer> permutacaoParcial,HashSet<Aresta> arestas,HashSet<Aresta> teste,HashMap<Integer, Integer> pontos){
		if(permutacaoParcial.size()==conjunto.size()) {
			if(guardarTudo==1){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.addAll(permutacaoParcial);
				permutacoes.add(temp);
			}
			else if(normal==0 || naoNormal==0){
				if(testarNormalidadePermutacao(permutacaoParcial) && normal==0){
					normal++;
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.addAll(permutacaoParcial);
					permutacoes.add(temp);
				}
				else if(!testarNormalidadePermutacao(permutacaoParcial) && naoNormal==0){
					naoNormal++;
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.addAll(permutacaoParcial);
					permutacoes.add(temp);
				}
			}
			computados=computados.add(BigInteger.ONE);
			//System.out.println(inicio);
			publish(computados);
			return;
		}
		for (int j = 0; j < numPonto*2; j++) {
			if(permutacaoParcial.contains(conjunto.get(j))){
				continue;
			}
			else{
				if(normal==1 && naoNormal==1) return;
				permutacaoParcial.add(conjunto.get(j));
				HashSet<Aresta> t = new HashSet<Aresta>();
				t.addAll(teste);
				HashMap<Integer, Integer> p= new HashMap<Integer,Integer>(pontos);
				if(TesteArco(numPonto,permutacaoParcial, arestas,t,p)){
					gerarPermutacoes(numPonto, conjunto, permutacaoParcial,arestas,t,p);
					permutacaoParcial.remove(permutacaoParcial.size()-1);
				}
				else {
					computados=computados.add(fat.fatorar((numPonto*2)-permutacaoParcial.size()));
					permutacaoParcial.remove(permutacaoParcial.size()-1);
					publish(computados);
				}
			}
		}
	}

	@Override
	protected void process(List<BigInteger> chunks) {
		progresso.setText(chunks.get(chunks.size()-1)+ " de "+max);
		String t =chunks.get(chunks.size()-1).multiply(BigInteger.valueOf(100)).divide(max).toString();
		progre.setValue(Integer.valueOf(t));
	}

	public boolean TesteArco(int numPontos,ArrayList<Integer> arcos,HashSet<Aresta> arestas,HashSet<Aresta> teste,HashMap<Integer, Integer> pontos){
		//os arcos come�am dos n�meros positivos e v�o at� os negativos no sentido hor�rio
		int t=0;
		//seta os arcos que come�aram para saber que come�aram
		if (arcos.get(arcos.size()-1)>0){
			t=arcos.get(arcos.size()-1);
			for (int j = 1; j <= numPontos; j++) {
				if(pontos.get(j)==-1){
					Aresta temp= new Aresta(t, j);
					if(arestas.contains(temp) && !teste.contains(temp)){
						return false;
					}
				}
				else if(pontos.get(j)==1){
					Aresta temp= new Aresta(t, j);
					if(!arestas.contains(temp)) return false;
					else {
						teste.add(temp);
					}
				}
				else if(pontos.get(t)==2 && pontos.get(j)==0){
					Aresta temp= new Aresta(t, j);
					if(!arestas.contains(temp)) return false;
				}
			}
			pontos.put(t, 1);
			return true;

		}
		//seta para saber que os arcos fecharam
		else if(pontos.get(-arcos.get(arcos.size()-1))==1){
			t=-arcos.get(arcos.size()-1);
				for (int j = 1; j <= numPontos; j++) {
					if(pontos.get(j)==2){
						Aresta temp= new Aresta(t, j);
						if(arestas.contains(temp) && !teste.contains(temp)){
							return false;
						}
					}
					else if(pontos.get(j)==0){
						Aresta temp= new Aresta(t, j);
						if(arestas.contains(temp)){
							for (int i = 1; i <= numPontos; i++) {
								if(pontos.get(i)!=0){
									Aresta temp1= new Aresta(j, i);
									if(!arestas.contains(temp1)) return false;
								}
								else if(i!=j){
									Aresta temp1= new Aresta(t, i);
									Aresta temp2= new Aresta(j, i);
									if(arestas.contains(temp1) && !arestas.contains(temp2)) return false;
								}
							}
						}
					}
				}
			pontos.put(t, -1);
			return true;
		}
		else{
			t=-arcos.get(arcos.size()-1);
			for (int j = 1; j <= numPontos; j++) {
				if(pontos.get(j)!=0){
					Aresta temp= new Aresta(t, j);
					if(!arestas.contains(temp)) return false;
					else {
						teste.add(temp);
					}
				}

			}
			pontos.put(t, 2);
			return true;

		}
	}


	public boolean testarNormalidadePermutacao(ArrayList<Integer> permutacao){
		boolean normalidade=true;
		for (int j = 1; j <= permutacao.size()/2; j++) {
			for (int k = j+1; k <= permutacao.size()/2 ; k++) {
				normalidade=testarNormalidadePermutacao2(j,k,permutacao);
				if(!normalidade) {
					return normalidade;
				}
			}
		}
		return normalidade;
	}


	private boolean testarNormalidadePermutacao2(int candidato1, int candidato2,ArrayList<Integer> permutacao) {
		for (int i = 0; i < permutacao.size(); i++) {
			if(permutacao.get(i)==candidato1){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == -candidato2) {
						for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == candidato2){
								return false;
							}
							else if(permutacao.get(j2)== -candidato1)
								return true;
						}

					}
					else if(permutacao.get(j)== -candidato1 || permutacao.get(j)== candidato2) 
						return true;
				}
			}
			else if(permutacao.get(i)==-candidato1){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == candidato1) {
						for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == -candidato2){
								return false;
							}
							else if(permutacao.get(j2)== candidato2)
								return true;
						}

					}
					else if(permutacao.get(j)== -candidato2 || permutacao.get(j)== candidato2)
						return true;
				}
			}
			else if(permutacao.get(i)==candidato2){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == -candidato1) {
						for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == candidato1){
								return false;
							}
							else if(permutacao.get(j2)== -candidato2)
								return true;
						}

					}
					else if(permutacao.get(j)== -candidato2 || permutacao.get(j)== candidato1)
						return true;
				}
			}
			else if(permutacao.get(i)==-candidato2){
				for (int j = i+1; j < permutacao.size(); j++) {
					if (permutacao.get(j) == candidato2) {
						for (int j2 = j+1; j2 < permutacao.size(); j2++) {
							if (permutacao.get(j2) == -candidato1){
								return false;
							}
							else if(permutacao.get(j2)== candidato1)
								return true;
						}

					}
					else if(permutacao.get(j)== -candidato1 || permutacao.get(j)== candidato1)
						return true;
				}
			}
		}
		return true;
	}
}
