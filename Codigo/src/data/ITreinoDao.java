package data;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Treino;

public interface ITreinoDao {
	
	
	public boolean existe(int codigo)throws SQLException;
	public Treino buscar(int  codigo)throws SQLException;
	public int cadastrar(Treino treino) throws SQLException;
	public boolean remover(Treino treino)throws SQLException;
	public ArrayList<Treino> listar() throws SQLException;
	public boolean atualizar(Treino treino) throws SQLException;

}
