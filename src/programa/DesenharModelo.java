package programa;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;

class DesenharModelo extends JPanel{
	private Color colors[];
	ArrayList<Integer> permutacao;
	HashSet<Aresta> arestas;
	ArrayList<Integer> raios;
	int maiorRaio=0;
	boolean pie=false;
	ArrayList<Double[]> coordenadas;
	public ArrayList<Double[]> getCoordenadas() {
		return coordenadas;
	}
	int numPontos;
	public DesenharModelo(ArrayList<Integer> permutacao,HashSet<Aresta> arestas){
		this.permutacao=permutacao;
		this.arestas=arestas;
		coordenadas = new ArrayList<Double[]>();
		numPontos=permutacao.size()/2;
		raios= new ArrayList<Integer>(numPontos);
		for (int i = 0; i < numPontos; i++) {
			raios.add(i, 1);
		}
		for (int i = 1; i <= raios.size(); i++) {
			for (Iterator<Aresta> iterator = arestas.iterator(); iterator.hasNext();) {
				Aresta temp= iterator.next();
				if(temp.vertice1==i && raios.get(temp.vertice2-1)<=raios.get(temp.vertice1-1)){
					raios.remove(temp.vertice2-1);
					raios.add(temp.vertice2-1, raios.get(temp.vertice1-1)+1);
					if(raios.get(temp.vertice2-1)>maiorRaio) maiorRaio=raios.get(temp.vertice2-1);
				}
			}
		}

		Color inicio = new Color(255, 0, 0);
		Color fim = new Color(255, 255, 0);
		int somaR=(fim.getRed()-inicio.getRed())/numPontos;
		int somaB=(fim.getBlue()-inicio.getBlue())/numPontos;
		int somaG=(fim.getGreen()-inicio.getGreen())/numPontos;
		colors = new Color[numPontos];
		for (int i = 0; i < numPontos; i++) {
			colors[i]= new Color(inicio.getRed()+somaR*i,inicio.getGreen()+somaG*i,inicio.getBlue()+somaB*i);
		}
		
		//setCoordenadas();

	}

	public void setCoordenadas(){
		coordenadas.clear();
		int inicio=0;
		int raio=100;
		int aumentoRaio=10;
		int grau = 0;
		if(permutacao.size()!=0) {
			grau=360/permutacao.size();
		}

		HashMap<Integer, Integer> grauInicio= new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> grauFim= new HashMap<Integer,Integer>();

		for (int i = 0; i < permutacao.size(); i++) {
			if(permutacao.get(i)<0){
				grauFim.put(-permutacao.get(i), inicio);
				inicio+=grau;
			}
			else{
				grauInicio.put(permutacao.get(i), inicio);
				inicio+=grau;
			}
		}

		for(int i=1;i<=grauInicio.size();i++){
			int r = raios.get(i-1);
			int extent = grauFim.get(i)-grauInicio.get(i);
			int comeco= grauInicio.get(i);
			if(extent<0)extent=extent+360;
			double angulo = ((extent/2)+comeco)*Math.PI/180;
			Double[] temp = new Double[2];
			temp[0]=Math.cos(angulo)*(raio+r*aumentoRaio);
			temp[1]=Math.sin(angulo)*(raio+r*aumentoRaio);
			coordenadas.add(temp);
		}
	}
	public void setPermutacoes(ArrayList<Integer> permutacoes){
		this.permutacao=permutacoes;
	}
	public void setPie(boolean pie){
		this.pie=pie;
	}
	public Color[] getColors(){
		return colors;
	}
	public void setColors(Color inicio,Color fim){
		int somaR=(inicio.getRed()-fim.getRed())/numPontos;
		int somaB=(inicio.getBlue()-fim.getBlue())/numPontos;
		int somaG=(inicio.getGreen()-fim.getGreen())/numPontos;
		for (int i = 0; i < numPontos; i++) {
			colors[i]= new Color(inicio.getRed()-somaR*i,inicio.getGreen()-somaG*i,inicio.getBlue()-somaB*i);
		}
	}
	public void setColors(Color cor,int vertice){
		colors[vertice]=cor;
	}
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		int x=100;
		int y=100;
		int inicio=0;
		int raio=100;
		int aumentoRaio=10;
		int grau = 0;
		if(permutacao.size()!=0) {
			grau=360/permutacao.size();
		}
		if(maiorRaio>=8){
			x=x+2*maiorRaio;
			y=y+2*maiorRaio;
		}
		HashMap<Integer, Integer> grauInicio= new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> grauFim= new HashMap<Integer,Integer>();

		for (int i = 0; i < permutacao.size(); i++) {
			if(permutacao.get(i)<0){
				grauFim.put(-permutacao.get(i), inicio);
				inicio+=grau;
			}
			else{
				grauInicio.put(permutacao.get(i), inicio);
				inicio+=grau;
			}
		}
		Graphics2D graph=(Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if (pie) {
			float[] pattern = new float[] { 3, 3, 3, 3 }; // pattern of lines and spaces
			BasicStroke pen = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, pattern, 0);
			graph.setStroke(pen);
			for (int i = 1; i <= grauInicio.size(); i++) {
				graph.setColor(colors[i - 1]);
				int r = raios.get(i-1);
				int extent = grauFim.get(i) - grauInicio.get(i);
				int comeco = grauInicio.get(i);
				if (extent < 0)
					extent = extent + 360;
				graph.draw(new Arc2D.Double(x - r * aumentoRaio, y - r * aumentoRaio, (raio+r*aumentoRaio)*2, (raio+r*aumentoRaio)*2, comeco,
						extent, Arc2D.PIE));
			}
		}
		graph.setColor(Color.gray);
		BasicStroke b= new BasicStroke(1);
		graph.setStroke(b);
		graph.draw(new Ellipse2D.Double(x, y, raio*2, raio*2));//(200,200)
		b= new BasicStroke(3);
		graph.setStroke(b);
		for(int i=1;i<=grauInicio.size();i++){
			graph.setColor(colors[i-1]);
			int r = raios.get(i-1);
			int extent = grauFim.get(i)-grauInicio.get(i);
			int comeco= grauInicio.get(i);
			if(extent<0)extent=extent+360;
			graph.draw(new Arc2D.Double(x-r*aumentoRaio,y-r*aumentoRaio,(raio+r*aumentoRaio)*2,(raio+r*aumentoRaio)*2,
					comeco,extent,Arc2D.OPEN));
		}

	}
}