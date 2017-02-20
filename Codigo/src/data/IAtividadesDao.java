package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAtividadesDao<T> {
	
	
	public boolean existe(int codigo)throws SQLException;
	public T buscar(int  codigo)throws SQLException;
	public int cadastrar(T treino) throws SQLException;
	public boolean remover(T treino)throws SQLException;
	public ArrayList<T> listar() throws SQLException;
	public boolean atualizar(T treino) throws SQLException;

}
