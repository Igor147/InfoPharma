package visualizacao.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

	private final String INSERT = "INSERT INTO produtos (id, nome_marca, nome_generico, laboratorio, quantidade, preco) VALUES (?,?, ?, ?, ?, ?)";
	private final String UPDATE = "UPDATE produtos SET quantidade = ?, preco = ? WHERE id = ?";
	private final String DELETE = "DELETE FROM produtos WHERE ID =?";
	private final String LIST = "SELECT * FROM produtos";
	private final String LISTBYID = "SELECT * FROM produtos WHERE ID=?";

	public void inserir(Produto produto) {
		if (produto != null) {
			Connection conn = null;
			try {
				conn = ConexaoDB.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(INSERT);
				pstm.setInt(1, produto.getId());
				pstm.setString(2, produto.getNomeMarca());
				pstm.setString(3, produto.getNomeGenerico());
				pstm.setString(4, produto.getLaboratorio());
				pstm.setInt(5, produto.getQuantidade());
				pstm.setFloat(6, produto.getPreco());

				pstm.execute();
				ConexaoDB.fechaConexao(conn, pstm);

			} catch (Exception e) {
				throw new RuntimeException("Erro ao inserir produto no banco de" + "dados " + e.getMessage());
			}
		} else {
			System.out.println("O produto enviado por parâmetro está vazio");
		}
	}

	public void atualizar(Produto produto) {
		if (produto != null) {
			Connection conn = null;
			try {
				conn = ConexaoDB.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(UPDATE);

				pstm.setInt(1, produto.getQuantidade());
				pstm.setFloat(2, produto.getPreco());
				pstm.setInt(3, produto.getId());

				pstm.execute();
				ConexaoDB.fechaConexao(conn);

			} catch (Exception e) {
				throw new RuntimeException("Erro ao atualizar produto no banco de" + "dados " + e.getMessage());
			}
		} else {
			throw new RuntimeException("O produto enviado por parâmetro está vazio");
		}

	}

	public void remover(int id) {
		Connection conn = null;
		try {
			conn = ConexaoDB.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(DELETE);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoDB.fechaConexao(conn, pstm);

		} catch (Exception e) {
			throw new RuntimeException("Erro ao excluir produto do banco de" + "dados " + e.getMessage());
		}
	}

	public List<Produto> getAll() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Produto> produtoList = new ArrayList<Produto>();
		try {
			conn = ConexaoDB.getConexao();
			pstm = conn.prepareStatement(LIST);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Produto produto = new Produto();

				produto.setId(rs.getInt("id"));
				produto.setNomeMarca(rs.getString("nome_marca"));
				produto.setNomeGenerico(rs.getString("nome_generico"));
				produto.setLaboratorio(rs.getString("laboratorio"));
				produto.setPreco(rs.getFloat("preco"));
				produto.setQuantidade(rs.getInt("quantidade"));

				produtoList.add(produto);
			}
			ConexaoDB.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar produtos" + e.getMessage());
		}
		return produtoList;
	}

	public Produto findById(int id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Produto produto = new Produto();
		try {
			conn = ConexaoDB.getConexao();
			pstm = conn.prepareStatement(LISTBYID);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				produto.setId(rs.getInt("id"));
				produto.setNomeMarca(rs.getString("nome_marca"));
				produto.setNomeGenerico(rs.getString("nome_generico"));
				produto.setLaboratorio(rs.getString("laboratorio"));
				produto.setPreco(rs.getFloat("preco"));
				produto.setQuantidade(rs.getInt("quantidade"));
			}
			ConexaoDB.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar produtos" + e.getMessage());
		}
		return produto;
	}
}