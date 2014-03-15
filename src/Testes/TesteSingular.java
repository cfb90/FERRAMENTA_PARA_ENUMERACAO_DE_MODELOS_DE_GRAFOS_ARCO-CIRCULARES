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

public class TesteSingular {

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
			try {
				resultado = TestarGrafoNormal.testarGrafo(arestas, numPontos);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(resultado==1){
				System.out.println(numPontos+"+"+arestas+"+"+"O grafo e normal");
			}
			else if (resultado==-1){
				System.out.println(numPontos+"+"+arestas+"+"+"O grafo nao e normal");
			}
			else if(resultado==0){
				System.out.println(numPontos+"+"+arestas+"+"+"o grafo nao e arco-circular");
			}

		}
	}
}

