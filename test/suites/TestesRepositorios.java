package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dataTest.TesteAdministradorDao;
import dataTest.TesteAlunoDao;
import dataTest.TesteProfessorDao;
import dataTest.TesteUsuarioDao;

@RunWith(Suite.class)
	
@Suite.SuiteClasses({
		TesteUsuarioDao.class,
		TesteAlunoDao.class,
		TesteProfessorDao.class,
		TesteAdministradorDao.class
})
public class TestesRepositorios {
	
	

}
