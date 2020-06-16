package visualizacao.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import visualizacao.model.Model;
import visualizacao.model.Produto;
import visualizacao.model.ProdutoDAO;

public class FormView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9186613542474670290L;
	private JPanel painelFundo;
	private JButton btSalvar;

	private JLabel lbId;
	private JLabel lbnomeMarca;
	private JLabel lbnomeGenerico;
	private JLabel lblaboratorio;
	private JLabel lbquantidade;
	private JLabel lbpreco;

	private JTextField txId;
	private JTextField txnomeMarca;
	private JTextField txnomeGenerico;
	private JTextField txlaboratorio;
	private JTextField txquantidade;
	private JTextField txpreco;
	Produto produto;
	private Model model = null;
	
	public FormView(Model model, Produto produto) {
		super("Detalhe do Produto");
		criaJanela();
		ProdutoDAO dao = new ProdutoDAO();
		produto = dao.findById(produto.getId());
		this.model = model;
		txId.setText(Integer.toString(produto.getId()));
		txnomeGenerico.setText(produto.getNomeGenerico());
		txnomeMarca.setText(produto.getNomeMarca());
		txlaboratorio.setText(produto.getLaboratorio());
		txquantidade.setText(String.valueOf(produto.getQuantidade()));
		txpreco.setText(String.valueOf(produto.getPreco()));
	}

	public void criaJanela() {
		btSalvar = new JButton("Salvar");
		
		lbId = new JLabel("         Id:   ");
		lbnomeMarca  = new JLabel("         Marca:   ");
		lbnomeGenerico = new JLabel("         Nome:   ");
		lblaboratorio = new JLabel("         Laboratório:   ");
		lbquantidade = new JLabel("         Quantidade:   ");
		lbpreco = new JLabel("         Preço:   ");
		
		txnomeMarca = new JTextField();
		txnomeMarca.setEditable(false);
		txnomeGenerico = new JTextField();
		txnomeGenerico.setEditable(false);
		txlaboratorio = new JTextField();
		txlaboratorio.setEditable(false);
		txquantidade = new JTextField();
		txpreco = new JTextField();
		
		txId = new JTextField();
		txId.setEditable(false);

		painelFundo = new JPanel();
		painelFundo.setLayout(new GridLayout(7, 2, 2, 4));
		painelFundo.add(lbId);
		painelFundo.add(txId);
		painelFundo.add(lbnomeGenerico);
		painelFundo.add(txnomeGenerico);
		painelFundo.add(lbnomeMarca);
		painelFundo.add(txnomeMarca);
		painelFundo.add(lblaboratorio);
		painelFundo.add(txlaboratorio);
		painelFundo.add(lbquantidade);
		painelFundo.add(txquantidade);
		painelFundo.add(lbpreco);
		painelFundo.add(txpreco);
		painelFundo.add(btSalvar);

		getContentPane().add(painelFundo);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setVisible(true);

		btSalvar.addActionListener(new FormView.BtSalvarListener());
	}

	private class BtSalvarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Produto produto = new Produto();
			produto.setId(Integer.parseInt(txId.getText()));
			produto.setNomeGenerico(txnomeGenerico.getText());
			produto.setNomeMarca(txnomeMarca.getText());
			produto.setLaboratorio(txlaboratorio.getText());
			produto.setQuantidade(Integer.valueOf(txquantidade.getText()));
			produto.setPreco(Float.valueOf(txpreco.getText()));

			ProdutoDAO dao = new ProdutoDAO();
			dao.atualizar(produto);
			model.updateProduto(produto);
		}
	}

}
