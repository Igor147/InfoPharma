package visualizacao.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.opencsv.CSVReader;

import visualizacao.model.Model;
import visualizacao.model.Produto;
import visualizacao.model.ProdutoDAO;
import visualizacao.view.PrincipalView;

public class CSVImportController implements ActionListener {
	
	

	private JFrame parent = null;
	private ProdutoDAO produtoDAO = null;
	private final int INDICE_ID = 0;
	private final int INDICE_MARCA = 1;
	private final int INDICE_NOME_GENERICO = 2;
	private final int INDICE_LABORATORIO = 3;
	private final int INDICE_QUANTIDADE = 4;
	private final int INDICE_PRECO = 5;

	private Model model = null;

	public CSVImportController(PrincipalView principal, Model model) {
		this.parent = principal.getFrame();
		this.model = model;
	}
	
	public CSVImportController(Model model) {
		this.model = model;
	}
	
	public float tratarPreco(String preco) {
		return preco != null ? Float.valueOf(preco.replace("$","")): 0f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecione, apenas, um arquivo .CSV");
		// verifica se o arquivo é um csv e só permitir extensões CSV
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV files", "csv"));
		// somente diretórios serão selecionados
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showOpenDialog(this.parent);

		// se o usuario clicar em cancelar, encerra programa
		if (result != JFileChooser.CANCEL_OPTION) {
			produtoDAO = new ProdutoDAO();
			CSVReader reader = null;
			try {
				reader = new CSVReader(new FileReader(fileChooser.getSelectedFile()));
				// para ignorar o cabeçalho
				String[] lineParts = reader.readNext();
				while ((lineParts = reader.readNext()) != null) {

					Integer id = Integer.valueOf(lineParts[INDICE_ID]);
					Produto produto = produtoDAO.findById(id);
					// adicionar quantidade ao produto
					if (produto != null && produto.getId() > 0) {
						int novaQuantidade = Integer.valueOf(lineParts[INDICE_QUANTIDADE]);
						int bancoQuantidade = produto.getQuantidade();
						novaQuantidade += bancoQuantidade;
						produto.setQuantidade(novaQuantidade);
						produto.setPreco(tratarPreco(String.valueOf(lineParts[INDICE_PRECO])));
						produtoDAO.atualizar(produto);
						model.updateProduto(produto);
					} else {
						// incluir produto
						produto = new Produto();
						produto.setId(id);
						produto.setNomeMarca(lineParts[INDICE_MARCA]);
						produto.setNomeGenerico(lineParts[INDICE_NOME_GENERICO]);
						produto.setLaboratorio(lineParts[INDICE_LABORATORIO]);
						produto.setQuantidade(Integer.valueOf(lineParts[INDICE_QUANTIDADE]));
						produto.setPreco(tratarPreco(lineParts[INDICE_PRECO]));
						produtoDAO.inserir(produto);
						model.addProduto(produto);
					}

				}
				JOptionPane.showMessageDialog(null, "Arquivo importado com sucesso!");

			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao exportar o arquivo.\n " + e1.getMessage());
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

}
