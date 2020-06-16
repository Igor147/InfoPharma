package visualizacao.model;


public class Produto {
	
	private int id;
	private String nomeMarca;
	private String nomeGenerico;
	private String laboratorio;
	private int quantidade;
	private float preco;
	
	private boolean selecioado;
	
	public Produto() {
		System.out.println("Modelo inicializando");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}

	public String getNomeGenerico() {
		return nomeGenerico;
	}

	public void setNomeGenerico(String nomeGenerico) {
		this.nomeGenerico = nomeGenerico;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public boolean isSelecioado() {
		return selecioado;
	}

	public void setSelecioado(boolean selecioado) {
		this.selecioado = selecioado;
	}
}
