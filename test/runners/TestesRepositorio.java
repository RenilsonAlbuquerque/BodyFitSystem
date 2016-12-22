package runners;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestesRepositorio {

	public static void main(String[] args) 
	{
			Result resutaldo = JUnitCore.runClasses(TestesRepositorio.class);
			for(Failure falha : resutaldo.getFailures()){
				System.out.println(falha.toString());
			}
			System.out.println("Passed" + resutaldo.wasSuccessful());
	}
}
