package visualizacao.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Model extends java.util.Observable {

	private Map<Integer, Produto> produtoMap = new HashMap<Integer, Produto>();
	private Set<Produto> selectedProdutoList = new HashSet<Produto>();
	private Produto lastSelected = null;

	public Model() {
		System.out.println("Model()");
	}

	public void addProduto(Produto produto) {
		if (produto == null) {
			throw new RuntimeException("O produto a ser adicionado não pode ser nullo!");
		}
		this.produtoMap.put(produto.getId(), produto);
		setChanged();
		notifyObservers(produto);
	}

	public void updateProduto(Produto produto) {
		if (produto == null) {
			throw new RuntimeException("O produto a ser adicionado não pode ser nullo!");
		}
		Produto produtoExistente = this.produtoMap.get(produto.getId());
		if (produtoExistente == null) {
			throw new RuntimeException("O produto não existe para ser atualizado!");
		}
		produtoExistente.setPreco(produto.getPreco());
		produtoExistente.setQuantidade(produto.getQuantidade());
		setChanged();
		notifyObservers(produto);
	}

	public Produto getProduto(int id) {
		return produtoMap.get(id);
	}

	public void changeSelect(Produto produto) {
		if (produto.isSelecioado()) {
			selectedProdutoList.add(produto);
			lastSelected = produto;
		}
		else if (selectedProdutoList.contains(produto))
			selectedProdutoList.remove(produto);

		if (selectedProdutoList.isEmpty())
			lastSelected = null;
		
		setChanged();
		notifyObservers(produto);
	}

	public Set<Produto> getSelected() {
		return selectedProdutoList;
	}
	
	public Produto lastSelected() {
		return lastSelected;
	}
	
	public Collection<Produto> getProdutos(){
		return this.produtoMap.values();
	}

}
