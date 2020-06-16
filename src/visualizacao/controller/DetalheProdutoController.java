package visualizacao.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import visualizacao.model.Model;
import visualizacao.model.Produto;
import visualizacao.view.FormView;

public class DetalheProdutoController implements ActionListener {

	private Model model = null;

	public DetalheProdutoController(Model model) {
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		Produto produtoSelecionado = model.lastSelected();
		if (produtoSelecionado == null) {
			JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
		}

		FormView ic = new FormView(model, produtoSelecionado);
		ic.setVisible(true);
	}

}
