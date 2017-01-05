package view.controls;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controls.login.ControladorTelaLogin;

public class Principal extends Application{
	
	private static Stage currentStage;
	private static Dimension dimensions;
	
	public static void main(String args[]){
		launch();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		currentStage = new Stage();
		dimensions = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		//setCurrentStage("/view/fxmls/login/TelaLogin.fxml");
		setCurrentStage(new ControladorTelaLogin());
		
	}
	
	public static void setCurrentStage(String caminho,int altura, int largura){
		try{
		currentStage.setScene(new Scene(FXMLLoader.load(Principal.class.getClass().getResource(caminho)),dimensions.getWidth(),dimensions.getHeight() ));
	
		//currentStage.getScene();
		currentStage.show();
		}
		catch(IOException e){
			
		}
	}
	public static void setCurrentStage(Parent painel){
		currentStage.setScene(new Scene(painel));
	}
	
	


}
