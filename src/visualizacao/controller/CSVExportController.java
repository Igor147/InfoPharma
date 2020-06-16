package visualizacao.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import visualizacao.model.Produto;
import visualizacao.model.ProdutoDAO;
import visualizacao.view.PrincipalView;

public class CSVExportController implements ActionListener{
	
	private JFrame parent = null;
	private ProdutoDAO produtoDAO = null;
	public static final String FIRST_LINE = "id,nome (marca),nome generico,laboratorio,quantidade,preco";
	public static final String FORMAT_LINE = "%d,%s,%s,%s,%d,$%s\n";
	
	public CSVExportController(PrincipalView principal) {
		this.parent = principal.getFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// configura dialogo para selecionar arquivo ou diretorio
		JFileChooser escolhedorDeCaminho = new JFileChooser();
		// somente diretórios serão selecionados
		escolhedorDeCaminho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int resultado = escolhedorDeCaminho.showOpenDialog(this.parent);
		
		// se o usuario clicar em cancelar, encerra programa
		if (resultado != JFileChooser.CANCEL_OPTION) {
			// caminho da pasta onde será gravado o arquivo csv
			Path filePath = escolhedorDeCaminho.getSelectedFile().toPath();
			produtoDAO = new ProdutoDAO();
			List<Produto> produtoList = produtoDAO.getAll();
			File file = new File(filePath + File.separator + "export.csv");
			FileWriter writer = null;
			try {
				writer = new FileWriter(file);
				writer.write(FIRST_LINE + "\n");
				for (Produto produto : produtoList) {
					writer.write(
							String.format(FORMAT_LINE, produto.getId(), formataStringComVirgula(produto.getNomeMarca()),
									formataStringComVirgula(produto.getNomeGenerico()),
									formataStringComVirgula(produto.getLaboratorio()), produto.getQuantidade(),
									produto.getPreco()));
				}
				JOptionPane.showMessageDialog(null, "Arquivo exportado com sucesso!");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao exportar o arquivo.\n " + e1.getMessage());
			} finally {
				if (writer != null)
					try {
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
	}
	
	/**
	 * Coloca as aspas quando a string possui ','
	 * @param valor
	 * @return
	 */
	public String formataStringComVirgula(String valor) {
		String padraoString = "\"%s\"";
		if (valor == null) {
			return "null";
		} else {
			return valor.contains(",") ? String.format(padraoString, valor): valor;  
		}
	}

}
