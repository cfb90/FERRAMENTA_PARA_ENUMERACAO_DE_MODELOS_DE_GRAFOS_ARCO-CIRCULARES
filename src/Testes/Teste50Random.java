package Testes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.Timer;

public class Teste50Random {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Digite o número de pontos do grafo");
		int numPontos= Integer.parseInt(in.readLine());
		System.out.println("Digite a chance de uma aresta estar no grafo(número entre 0 e 1)");
		double chance= Double.parseDouble(in.readLine());
		ArrayList<Long> tempos = new ArrayList<Long>();
		int normal=0;
		int naoNormal=0;
		int naoAC=0;
		/*final Timer timer = new Timer(60000 , new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("esta rodando");
			}
		});
		timer.start();*/
		for (int k = 0; k < 50 ; k++) {
			HashSet<HashSet> arestas = new HashSet<HashSet>();
			for (int i = 1; i <= numPontos; i++) {
				for (int j = i+1; j <= numPontos; j++) {
					if(Math.random()<chance){
						HashSet<Integer> temp = new HashSet<Integer>();
						temp.add(i);
						temp.add(j);
						arestas.add(temp);
					}
				}
			}
			long tempoInicio = System.currentTimeMillis(); 
			
			int resultado=2;
			try {
				resultado = TestarGrafoNormal.testarGrafo(arestas, numPontos);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(resultado==1){
				System.out.println(numPontos+"+"+arestas+"+"+"O grafo e normal");
				normal++;
			}
			else if (resultado==-1){
				System.out.println(numPontos+"+"+arestas+"+"+"O grafo nao e normal");
				naoNormal++;
			}
			else if(resultado==0){
				System.out.println(numPontos+"+"+arestas+"+"+"o grafo nao e arco-circular");
				naoAC++;
			}
			//System.out.println("Tempo Total: "+(System.currentTimeMillis()-tempoInicio));
			tempos.add(System.currentTimeMillis()-tempoInicio); 
		}
		double media =0;
		for (int i = 0; i < 50; i++) {
			media+=tempos.get(i);
		}
		media=media/50;
		double desvio=0;
		for (int i = 0; i < 50; i++) {
			desvio+=Math.pow(tempos.get(i)-media,2);
		}
		desvio=Math.sqrt(desvio/49);
		System.out.println("grafos normais= "+normal+" grafos nao-normais= "+naoNormal+" grafos nao AC= "+naoAC);
		System.out.println("A media e "+media+" e o desvio padrao e "+desvio);
	}
}
