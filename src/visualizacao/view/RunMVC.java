package visualizacao.view;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import visualizacao.controller.CSVExportController;
import visualizacao.controller.CSVImportController;
import visualizacao.controller.DetalheProdutoController;
import visualizacao.model.Model;
import visualizacao.model.Produto;
import visualizacao.model.ProdutoDAO;

public class RunMVC {

	public RunMVC() {

		// observavel, possui o bind com as interfaces
		DefaultTableModel modelo = new DefaultTableModel();

		// cria o Modelo e a Visao
		Model myModel = new Model();
		// builderGrafico
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JFreeChart chart = ChartFactory.createBarChart(
														"Relatório",
														"Produto", 
														"Quantidade", 
														dataset,
														PlotOrientation.VERTICAL, 
														true, 
														true, 
														false);
		GraficoView grafico = new GraficoView(chart, dataset);
		// tela principal
		PrincipalView myView = new PrincipalView(modelo, myModel, grafico);

		// avisa o modelo que a visao existe
		myModel.addObserver(myView);
		myModel.addObserver(grafico);

		// controllers
		CSVImportController csvImportController = new CSVImportController(myModel);
		myView.addControllerImportar(csvImportController);

		CSVExportController csvExportController = new CSVExportController(myView);
		myView.addControllerExportar(csvExportController);

		DetalheProdutoController detalheProdutoController = new DetalheProdutoController(myModel);
		myView.addControllerDetalhes(detalheProdutoController);

		// carregando os dados da aplicação
		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> produtoList = dao.getAll();
		for (Produto produto : produtoList) {
			myModel.addProduto(produto);
		}

	}

}
