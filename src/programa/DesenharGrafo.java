package programa;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;

public class DesenharGrafo extends JPanel {
	private Color colors[];
	Integer vertices;
	HashSet<HashSet> arestas;
	ArrayList<Double[]> coordenadas;
	public ArrayList<Double[]> getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(ArrayList<Double[]> coordenadas) {
		this.coordenadas = coordenadas;
	}
	public DesenharGrafo(Integer vertices,HashSet<HashSet> arestas){
		this.vertices=vertices;
		this.arestas=arestas;
		Color inicio = new Color(255, 0, 0);
		Color fim = new Color(255, 255, 0);
		int somaR=(fim.getRed()-inicio.getRed())/(vertices);
		int somaB=(fim.getBlue()-inicio.getBlue())/(vertices);
		int somaG=(fim.getGreen()-inicio.getGreen())/(vertices);
		colors = new Color[vertices];
		for (int i = 0; i < vertices; i++) {
			colors[i]= new Color(inicio.getRed()+somaR*i,inicio.getGreen()+somaG*i,inicio.getBlue()+somaB*i);
		}
	}
	public Color[] getColors(){
		return colors;
	}
	public void setColors(Color inicio,Color fim){
		int somaR=(inicio.getRed()-fim.getRed())/(vertices);
		int somaB=(inicio.getBlue()-fim.getBlue())/(vertices);
		int somaG=(inicio.getGreen()-fim.getGreen())/(vertices);
		for (int i = 0; i < vertices; i++) {
			colors[i]= new Color(inicio.getRed()-somaR*i,inicio.getGreen()-somaG*i,inicio.getBlue()-somaB*i);
		}
	}
	public void setColors(Color cor,int vertice){
		colors[vertice]=cor;
	}
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D graph=(Graphics2D) g;
		//graph.translate(200, 200);
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graph.setColor(Color.BLACK);
		BasicStroke b= new BasicStroke(1);
		ArrayList<Point2D> pontos= new ArrayList<Point2D>(vertices);
		int x=150;
		int y=150;
		int raio = 100;
		double angulo= 2*Math.PI/vertices;
		for(int i=0;i<vertices;i++){
			//essa é para usar um modelo de desenho de grafo diferente junto com o setCoordenadas da classe DesenharModelo
			//Point2D point = new Point2D.Double(coordenadas.get(i)[0]+5,coordenadas.get(i)[1]+5);
			Point2D point = new Point2D.Double(x+raio*Math.sin(i*angulo)+5, y+raio*Math.cos(angulo*i)+5);
			pontos.add(point);
		}
		graph.setStroke(b);
		Iterator<HashSet> it= arestas.iterator();
		while (it.hasNext()) {
			ArrayList<Integer> temp =new ArrayList<Integer>(it.next());
			graph.draw(new Line2D.Double(pontos.get(temp.get(0)-1), pontos.get(temp.get(1)-1)));
		}
		 
		b= new BasicStroke(3);
		graph.setStroke(b);
		for(int i=0;i<vertices;i++){
			graph.setColor(colors[i]);
			graph.fill(new Ellipse2D.Double(pontos.get(i).getX()-5, pontos.get(i).getY()-5, 10, 10));
		}
		
	}
}
