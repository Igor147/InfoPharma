package visualizacao.view;
//View.java

import java.awt.BorderLayout;

//(C) Joseph Mack 2011, jmack (at) wm7d (dot) net, released under GPL v3 (or any later version)

//inspired by Joseph Bergin's MVC gui at http://csis.pace.edu/~bergin/mvc/mvcgui.html

//View is an Observer

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionListener; //for addController()
import java.awt.event.WindowAdapter; //for CloseListener()
import java.awt.event.WindowEvent; //for CloseListener()
import java.util.Collection;
import java.util.Observable; //for update();

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import visualizacao.model.Model;
import visualizacao.model.Produto;

public class PrincipalView implements java.util.Observer {

	private Button buttonDetalhes;
	private Button buttonExport;
	private Button buttonImport;
	private String title = "Loja";
	private DefaultTableModel defaultTableModel = null;
	JFrame frame = null;

	JPanel painelFundo;
	JTable tabela = null;
	JScrollPane barraRolagem;
	Model myModel = null;
	private GraficoView grafico = null;

	public PrincipalView(DefaultTableModel defaultTableModel, Model myModel, GraficoView grafico) {
		System.out.println("View()");
		
		this.defaultTableModel = defaultTableModel;
		tabela = new JTable(defaultTableModel);
		this.myModel = myModel;
		this.grafico = grafico;

		criarJanela();
		criarTable();
		criarGrafico();
		frame.addWindowListener(new CloseListener());
		frame.setSize(1200, 700);
		frame.setLocationRelativeTo(null);
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}
	
	public JTable getTabela() {
		return this.tabela;
	}

	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public GraficoView getGrafico() {
		return grafico;
	}

	public void setGrafico(GraficoView grafico) {
		this.grafico = grafico;
		frame.add(grafico,BorderLayout.EAST);
	}

	private void criarJanela() {
		frame = new JFrame(title);

		Panel panel = new Panel();
		buttonDetalhes = new Button("Detalhes");
		buttonImport = new Button("Importar");
		buttonExport = new Button("Exportar");
		panel.add(buttonDetalhes);
		panel.add(buttonImport);
		panel.add(buttonExport);
		frame.add("South", panel);
	}
	
	void criarGrafico() {
		frame.add(grafico,BorderLayout.EAST);
	}
	
	private void criarTable() {
		painelFundo = new JPanel();
		painelFundo.setLayout(new GridLayout(1, 1));

		defaultTableModel.addColumn("Id");
		defaultTableModel.addColumn("Nome (Marca)");
		defaultTableModel.addColumn("Nome");
		defaultTableModel.addColumn("Laboratório");
		defaultTableModel.addColumn("Quantidade");
		defaultTableModel.addColumn("Preço");
		tabela.getColumnModel().getColumn(0).setWidth(0);
		// tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
		// tabela = new JTable(dados, colunas);
		barraRolagem = new JScrollPane(tabela);
		painelFundo.add(barraRolagem);
		frame.add(painelFundo);
		defaultTableModel.setNumRows(0);
		
		// TODO: corrigir
		
		ListSelectionModel model = tabela.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if(!model.isSelectionEmpty()&&!e.getValueIsAdjusting()) {	
					int index = tabela.getSelectedRow();
					int idProduto = (int) defaultTableModel.getValueAt(index, 0);
					Produto produto = myModel.getProduto(idProduto);
					produto.setSelecioado(!produto.isSelecioado());
					myModel.changeSelect(produto);
				}
			}
		});
		
	}
	
	// Called from the Model
	public void update(Observable obs, Object obj) {
		
		Produto produto = (Produto) obj;
		boolean encontrou = false;
		for (int row = 0; row < defaultTableModel.getRowCount(); row++) {
			int idProduto = (int) defaultTableModel.getValueAt(row, 0);
			// se existe , atualiza
			if (produto.getId() == idProduto) {
				defaultTableModel.setValueAt(produto.getQuantidade(), row, 2);
				encontrou = true;
				break;
			}
		}

		// se não existe insete
		if (!encontrou) {
			defaultTableModel.addRow(new Object[] { produto.getId(),produto.getNomeMarca(), produto.getNomeGenerico(), produto.getLaboratorio(), produto.getQuantidade(), produto.getPreco() });
		}

	} 
	
	public void addControllerDetalhes(ActionListener controller) {
		System.out.println("View   buttonDetalhes   : adding controller");
		buttonDetalhes.addActionListener(controller); 
	} 
	
	public void addControllerImportar(ActionListener controller) {
		System.out.println("View    buttonImport  : adding controller");
		buttonImport.addActionListener(controller); 
	} 
	
	public void addControllerExportar(ActionListener controller) {
		System.out.println("View     buttonExport : adding controller");
		buttonExport.addActionListener(controller); 
	} 

	public void addController(ActionListener controller) {
		System.out.println("View      : adding controller");
		buttonDetalhes.addActionListener(controller);
	} 

	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
