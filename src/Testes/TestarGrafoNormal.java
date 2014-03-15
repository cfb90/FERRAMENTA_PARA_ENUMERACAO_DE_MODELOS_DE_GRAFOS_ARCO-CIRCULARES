package Testes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.Timer;

public class TestarGrafoNormal {
	static ArrayList<ArrayList> permutacoes;
	static HashMap<Integer, Integer> cardinalidade;
	static int impr = 0;
	static public int testarGrafo(HashSet<HashSet> arestas,int numPontos){
		permutacoes = new ArrayList<ArrayList>();
		ArrayList<Integer> extremos = new ArrayList<Integer>();
		for(int i=1;i<=numPontos;i++){
			extremos.add(i);
		}
		for(int i=1;i<=numPontos;i++){
			extremos.add(-i);
		}
		final ArrayList<Integer> inicio = new ArrayList<Integer>();
		//System.out.println(numPontos+"+"+arestas);
		inicio.add(menorCardinalidade(numPontos, arestas));
		HashSet<HashSet> teste = new HashSet<HashSet>();
		HashMap<Integer, Integer> pontos= new HashMap<Integer,Integer>();
		for (int i = 1; i <= numPontos; i++) {
			if(i==inicio.get(0)) pontos.put(i, 1);
			else pontos.put(i, 0);
		}
		final Timer timer = new Timer(60000 , new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				impr=1;
			}
		});
		timer.start();
		try {
			if(gerarPermutacoes(numPontos, extremos,inicio ,arestas,teste,pontos)){
				timer.stop();
				return 1;
			}
			else if (!permutacoes.isEmpty()) {
				timer.stop();
				return -1;
			}
			else {
				timer.stop();
				return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}

	}

	static public int menorCardinalidade(int numPontos,HashSet<HashSet> arestas){
		cardinalidade = new HashMap<Integer, Integer>(numPontos);
		for (int i = 1; i <= numPontos; i++) {
			cardinalidade.put(i, 0);
		}
		Iterator<HashSet> temp = arestas.iterator();
		while(temp.hasNext()){
			ArrayList<Integer> t = new ArrayList<Integer>(temp.next());
			cardinalidade.put(t.get(0), cardinalidade.get(t.get(0))+1);
			cardinalidade.put(t.get(1), cardinalidade.get(t.get(1))+1);
		}
		int retorno=1;
		for (int i = 2; i <= numPontos; i++) {
			if(cardinalidade.get(retorno)>=cardinalidade.get(i)){
				retorno=i;
			}
		}
		return retorno;
	}


	public static boolean gerarPermutacoes(int numPonto,ArrayList<Integer> conjunto,ArrayList<Integer> permutacaoParcial,HashSet<HashSet> arestas,HashSet<HashSet> teste,HashMap<Integer, Integer> pontos){

		if(permutacaoParcial.size()==conjunto.size()) {
			if(testarNormalidadePermutacao(permutacaoParcial)) return true;
			else if(permutacoes.isEmpty()){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.addAll(permutacaoParcial);
				permutacoes.add(temp);
			}
			return false;
		}
		ArrayList<Integer> conjuntoT = new ArrayList<>(conjunto);
		for (int j = 0; j < numPonto*2; j++) {
			if(permutacaoParcial.contains(conjuntoT.get(j))){
				continue;
			}
			else{
				if(impr==1) {
					System.out.println(permutacaoParcial);
					impr=0;
				}
				permutacaoParcial.add(conjuntoT.get(j));
				HashSet<HashSet> t = new HashSet<HashSet>();
				t.addAll(teste);
				HashMap<Integer, Integer> p= new HashMap<Integer,Integer>(pontos);
				if(TesteArco(numPonto,permutacaoParcial, arestas,t,p)){
					if(gerarPermutacoes(numPonto, conjunto, permutacaoParcial,arestas,t,p)) return true;
					else permutacaoParcial.remove(permutacaoParcial.size()-1);
				}
				else {
					permutacaoParcial.remove(permutacaoParcial.size()-1);
				}
			}
		}
		return false;
	}


	static public boolean TesteArco(int numPontos, ArrayList<Integer> arcos,HashSet<HashSet> arestas,HashSet<HashSet> teste, HashMap<Integer, Integer> pontos){

		//os arcos come�am dos n�meros positivos e v�o at� os negativos no sentido hor�rio
		int t=0;
		//seta os arcos que come�aram para saber que come�aram
		if (arcos.get(arcos.size()-1)>0){
			t=arcos.get(arcos.size()-1);
			for (int j = 1; j <= numPontos; j++) {
				if(pontos.get(j)==-1){
					HashSet<Integer> temp= new HashSet<Integer>();
					temp.add(t);
					temp.add(j);
					if(arestas.contains(temp) && !teste.contains(temp)){
						return false;
					}
				}
				else if(pontos.get(j)==1){
					HashSet<Integer> temp= new HashSet<Integer>();
					temp.add(t);
					temp.add(j);
					if(!arestas.contains(temp)) return false;
					else {
						teste.add(temp);
					}
				}
				else if(pontos.get(t)==2 && pontos.get(j)==0){
					HashSet<Integer> temp= new HashSet<Integer>();
					temp.add(t);
					temp.add(j);
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
						HashSet<Integer> temp= new HashSet<Integer>();
						temp.add(t);
						temp.add(j);
						if(arestas.contains(temp) && !teste.contains(temp)){
							return false;
						}
					}
					else if(pontos.get(j)==0){
						HashSet<Integer> temp= new HashSet<Integer>();
						temp.add(t);
						temp.add(j);
						if(arestas.contains(temp)){
							for (int i = 1; i <= numPontos; i++) {
								if(pontos.get(i)!=0){
									HashSet<Integer> temp1= new HashSet<Integer>();
									temp1.add(j);
									temp1.add(i);
									if(!arestas.contains(temp1)) return false;
								}
								else if(i!=j){
									HashSet<Integer> temp1= new HashSet<Integer>();
									temp1.add(t);
									temp1.add(i);
									HashSet<Integer> temp2= new HashSet<Integer>();
									temp2.add(j);
									temp2.add(i);
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
					HashSet<Integer> temp= new HashSet<Integer>();
					temp.add(t);
					temp.add(j);
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
	//se um arco estiver aberto quando ler um certo ponto coloca a aresta no grupo de teste

	static public boolean testarNormalidadePermutacao(ArrayList<Integer> permutacao){
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

	static private boolean testarNormalidadePermutacao2(int candidato1, int candidato2,ArrayList<Integer> permutacao) {
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
