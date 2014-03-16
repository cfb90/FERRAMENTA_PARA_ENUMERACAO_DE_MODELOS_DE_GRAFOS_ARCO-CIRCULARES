package programa;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import programa.Grafo;
import textfilter.ImprovedFormattedTextField;
import Extra.Fatorial;

import com.thoughtworks.xstream.XStream;

public class Principal {
	private int numPontos=0;
	private int ok=0;
	private JFrame frmInserirDadosDo;
	ArrayList<Aresta> arestas;
	JButton btnAdicionarAresta;
	JButton btnGerarCirculo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmInserirDadosDo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		arestas= new ArrayList<Aresta>();

		frmInserirDadosDo = new JFrame();
		frmInserirDadosDo.setTitle("Dados do Grafo");
		frmInserirDadosDo.setBounds(100, 100, 450, 358);
		frmInserirDadosDo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInserirDadosDo.getContentPane().setLayout(null);


		textField = new ImprovedFormattedTextField(NumberFormat.getIntegerInstance());
		textField.setBounds(245, 73, 86, 20);
		frmInserirDadosDo.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblDigiteONmero = new JLabel("Digite o n\u00FAmero de v\u00E9rtices");
		lblDigiteONmero.setBounds(218, 48, 161, 14);
		frmInserirDadosDo.getContentPane().add(lblDigiteONmero);

		final JList list = new JList();
		list.setListData(arestas.toArray());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(69, 36, 54, 162);
		frmInserirDadosDo.getContentPane().add(scrollPane);

		scrollPane.setViewportView(list);

		JLabel lblArestas = new JLabel("Arestas");
		lblArestas.setBounds(69, 12, 64, 14);
		frmInserirDadosDo.getContentPane().add(lblArestas);

		textField_1 = new ImprovedFormattedTextField(NumberFormat.getIntegerInstance());
		textField_1.setBounds(248, 132, 47, 20);
		frmInserirDadosDo.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new ImprovedFormattedTextField(NumberFormat.getIntegerInstance());
		textField_2.setColumns(10);
		textField_2.setBounds(311, 132, 47, 20);
		frmInserirDadosDo.getContentPane().add(textField_2);

		btnAdicionarAresta = new JButton("Adicionar Aresta");
		btnAdicionarAresta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnAdicionarAresta);
			}
		});
		btnAdicionarAresta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp;
				int temp1;
				try {
					temp = Integer.parseInt(textField_1.getText());
					temp1 = Integer.parseInt(textField_2.getText());
					if(temp > 0 && temp1>0 && temp!=temp1){
						Aresta a=new Aresta(temp,temp1);
						textField_1.setText("");
						textField_1.requestFocus();
						textField_2.setText("");
						if(!arestas.contains(a)) {
							arestas.add(a);
							list.setListData(arestas.toArray());
						}
						else JOptionPane.showMessageDialog( null, "Aresta ja existe" );
					}
					else JOptionPane.showMessageDialog( null, "Aresta n\u00E3o pode existir" );
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog( null, "Aresta n\u00E3o pode existir" );
				}

			}
		});
		btnAdicionarAresta.setBounds(218, 164, 140, 23);
		frmInserirDadosDo.getContentPane().add(btnAdicionarAresta);

		JLabel lblDigiteOsVrtices = new JLabel("Digite os v\u00E9rtices da aresta");
		lblDigiteOsVrtices.setBounds(218, 107, 161, 14);
		frmInserirDadosDo.getContentPane().add(lblDigiteOsVrtices);

		final JButton btnApagarArestas = new JButton("Apagar Arestas");
		btnApagarArestas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnApagarArestas);
			}
		});
		btnApagarArestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arestas.clear();
				list.setListData(arestas.toArray());
			}
		});
		btnApagarArestas.setBounds(37, 230, 123, 23);
		frmInserirDadosDo.getContentPane().add(btnApagarArestas);

		final ArrayList<ArrayList>  permutacoes= new ArrayList<ArrayList>();

		final JLabel lblTestando = new JLabel("nada");
		lblTestando.setVisible(false);
		lblTestando.setBounds(122, 292, 500, 16);
		frmInserirDadosDo.getContentPane().add(lblTestando);

		final JProgressBar progressBar = new JProgressBar(0,100);
		progressBar.setBounds(12, 293, 101, 14);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		frmInserirDadosDo.getContentPane().add(progressBar);

		final String c[] = {new String("C:/Users/")};
		final JButton btnCarregarGrafo = new JButton("Carregar Grafo");
		btnCarregarGrafo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnCarregarGrafo);
			}
		});
		btnCarregarGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser abrir = new JFileChooser(c[0]);  
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
					c[0]=caminho;
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
					arestas = grafo.getArestas();
					textField.setText(numPontos+"");
					if(arestas==null) arestas= new ArrayList<Aresta>();
					list.setListData(arestas.toArray());
					frmInserirDadosDo.getContentPane().validate();

				}
			}
		});
		btnCarregarGrafo.setBounds(222, 12, 136, 26);
		frmInserirDadosDo.getContentPane().add(btnCarregarGrafo);

		final JButton btnSalvarGrafo = new JButton("Salvar Grafo");
		btnSalvarGrafo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnSalvarGrafo);
			}
		});
		btnSalvarGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser abrir = new JFileChooser(c[0]);  
				XStream xstream = new XStream();

				try {
					numPontos= Integer.parseInt(textField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, "Número de Vértices Inválido" );
					return;
				}
				Grafo g = new Grafo(numPontos, arestas);
				xstream.autodetectAnnotations(true);
				String grafoEmXML = xstream.toXML(g);  
				String[] tem = grafoEmXML.split("\n");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML", "xml");
				abrir.setFileFilter(filter);
				int retorno = abrir.showSaveDialog(null); 
				String caminho;
				if (retorno==JFileChooser.APPROVE_OPTION)  {
					caminho = abrir.getSelectedFile().getAbsolutePath();
					c[0]=caminho;
					if(!caminho.endsWith(".xml")) caminho+=".xml";
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(caminho));
						for (int i = 0; i < tem.length; i++) {
							out.write(tem[i]);
							out.newLine();
						}
						out.close();
					} catch (IOException e1) {
					}
				}
			}
		});
		btnSalvarGrafo.setBounds(37, 255, 113, 26);
		frmInserirDadosDo.getContentPane().add(btnSalvarGrafo);

		final JButton btnApagarAresta = new JButton("Apagar Aresta");
		btnApagarAresta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] remover=list.getSelectedIndices();
				for (int i = 0; i < remover.length; i++) {
					arestas.remove(remover[i]-i);
				}
				list.setListData(arestas.toArray());
			}
		});
		btnApagarAresta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnApagarAresta);
			}
		});
		btnApagarAresta.setBounds(37, 206, 123, 23);
		frmInserirDadosDo.getContentPane().add(btnApagarAresta);

		final JCheckBox chckbxMostrarTodosOs = new JCheckBox("Mostrar todos os modelos");
		chckbxMostrarTodosOs.setSelected(true);
		chckbxMostrarTodosOs.setBounds(218, 195, 196, 24);
		frmInserirDadosDo.getContentPane().add(chckbxMostrarTodosOs);

		btnGerarCirculo= new JButton("Gerar Modelos");
		btnGerarCirculo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				frmInserirDadosDo.getRootPane().setDefaultButton(btnGerarCirculo);
			}
		});
		btnGerarCirculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try {
					numPontos= Integer.parseInt(textField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog( null, "Número de Vértices Inválido" );
					return;
				}

				Fatorial fat = new Fatorial(numPontos*2);
				BigInteger maximo = fat.fatorar((numPontos*2)-1);
				btnGerarCirculo.setEnabled(false);
				btnApagarArestas.setEnabled(false);
				btnAdicionarAresta.setEnabled(false);
				btnCarregarGrafo.setEnabled(false);
				btnApagarAresta.setEnabled(false);
				lblTestando.setText("0 de "+ maximo);
				lblTestando.setVisible(true);
				progressBar.setVisible(true);
				final HashSet temp = new HashSet<Aresta>();
				for (int i = 0; i < arestas.size(); i++) {
					temp.add(arestas.get(i));
				}
				int mostrar=0;
				if(chckbxMostrarTodosOs.isSelected()) mostrar =1;
				final GerarModelos desenho= new GerarModelos(numPontos, temp,lblTestando,progressBar,permutacoes,fat,mostrar);
				desenho.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						if(arg0.getNewValue().toString().equals("DONE") && arg0.getSource().equals(desenho)){
							if(!permutacoes.isEmpty()){
								try {
									Controles f = new Controles(numPontos, temp, permutacoes);
									f.setVisible(true);
									frmInserirDadosDo.dispose();
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								progressBar.setVisible(false);
								lblTestando.setVisible(false);
								btnGerarCirculo.setEnabled(true);
								btnApagarArestas.setEnabled(true);
								btnAdicionarAresta.setEnabled(true);
								btnCarregarGrafo.setEnabled(true);
								btnApagarAresta.setEnabled(true);
								JOptionPane.showMessageDialog( null, "O Grafo não é Arco-Circular" );
							}
						}
					}
				});
				desenho.execute();

			}
		});
		btnGerarCirculo.setBounds(218, 230, 123, 23);
		frmInserirDadosDo.getContentPane().add(btnGerarCirculo);



	}
}
