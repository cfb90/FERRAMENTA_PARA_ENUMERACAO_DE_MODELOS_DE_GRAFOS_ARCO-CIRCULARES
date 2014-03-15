package Testes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thoughtworks.xstream.XStream;

public class TesteTirar1Vertice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int resultado=2;
		int numPontos = 10;
		HashSet<HashSet> arestas= new HashSet<HashSet>();
		JFileChooser abrir = new JFileChooser();  
		XStream xstream = new XStream();
		xstream.alias("grafo", Grafo.class);
		xstream.alias("aresta", Aresta.class);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML", "xml");
		abrir.setFileFilter(filter);
		int retorno = abrir.showOpenDialog(null); 
		String caminho;
		if (retorno==JFileChooser.APPROVE_OPTION)  {
			caminho = abrir.getSelectedFile().getAbsolutePath();
			String str = new String();
			try {
				BufferedReader in = new BufferedReader(new FileReader(caminho));
				while (in.ready()) {
					str += in.readLine();
				}
				in.close();
			} catch (IOException e1) {
			}
			Grafo grafo = (Grafo) xstream.fromXML(str);  
			numPontos=grafo.getVertices();
			ArrayList<Aresta> aresta = grafo.getArestas();
			for (Iterator iterator = aresta.iterator(); iterator.hasNext();) {
				Aresta aresta2 = (Aresta) iterator.next();
				arestas.add(aresta2.toHashSet());
			}
			int numPontos1 = numPontos-1;
			for (int i = 1; i <=numPontos; i++) {
				HashSet<HashSet> arestas1= new HashSet<HashSet>();
				for (Iterator iterator = arestas.iterator(); iterator.hasNext();) {
					HashSet<Integer> aresta2 = (HashSet<Integer>) iterator.next();
					if(aresta2.contains(i)) continue;
					else{
						Integer[] t= new Integer[2];
						aresta2.toArray(t);
						HashSet<Integer> aresta1 = new HashSet<Integer>();
						if(t[0]>i) aresta1.add(t[0]-1);
						else aresta1.add(t[0]);
						if(t[1]>i) aresta1.add(t[1]-1);
						else aresta1.add(t[1]);
						arestas1.add(aresta1);
					}
				}
				try {
					resultado = TestarGrafoNormal.testarGrafo(arestas1, numPontos1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(resultado==1){
					System.out.println(numPontos1+"+"+arestas1+"+"+"O grafo e normal");
				}
				else if (resultado==-1){
					System.out.println(numPontos1+"+"+arestas1+"+"+"O grafo nao e normal");
				}
				else if(resultado==0){
					System.out.println(numPontos1+"+"+arestas1+"+"+"o grafo nao e arco-circular");
				}
			}

		}
	}
}

