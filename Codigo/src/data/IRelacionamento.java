package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRelacionamento<T> {
	
	public boolean inserir(T objeto)throws SQLException;
	public boolean remover(T objeto)throws SQLException;
	public ArrayList<T> listar()throws SQLException;
	public ArrayList<T> listar(int codigo) throws SQLException;
}
