package data;

import java.sql.SQLException;

public interface IRepositorioAtividades<T,K> extends InterfaceCRUD<T,K> {
	
	public boolean cadastrar(T objeto,String cpfProf)throws SQLException;
	
	
}
