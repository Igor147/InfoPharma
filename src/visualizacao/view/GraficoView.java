package visualizacao.view;

import java.util.Observable;
import java.util.Set;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import visualizacao.model.Model;
import visualizacao.model.Produto;

public class GraficoView extends ChartPanel implements java.util.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -364982164806420466L;

	private DefaultCategoryDataset dataset = null;

	public GraficoView(JFreeChart chart, DefaultCategoryDataset dataset) {
		super(chart);
		setPreferredSize(new java.awt.Dimension(500, 400));
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof Model) {
			Model model = (Model) o;
			this.dataset = new DefaultCategoryDataset();
			Set<Produto> selectedSet = model.getSelected();
			for (Produto produto : selectedSet) {
				this.dataset.addValue(produto.getQuantidade(), produto.getNomeGenerico(), "Produto");
			}
			this.getChart().getCategoryPlot().setDataset(dataset);
			this.repaint();
		}

	}

}
