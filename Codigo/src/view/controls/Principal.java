package view.controls;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.controls.login.ControladorTelaLogin;

public class Principal extends Application{
	
	private static Stage currentStage;
	
	
	@Override
	public void start(Stage arg0) {
		
		currentStage = new Stage();
		currentStage.initStyle(StageStyle.UNDECORATED);
		setCurrentStage(new ControladorTelaLogin());	
	}
	
	public static void setCurrentStage(Parent painel){
		
		currentStage.setScene(new Scene(painel));
		currentStage.getScene().getStylesheets().add("/view/style/stylesheet.css");
		currentStage.centerOnScreen();
		currentStage.show();
		
	}
	public static void setCurrentStage(Parent painel,int largura,int altura){
		
		currentStage.setScene(new Scene(painel,largura,altura));
		currentStage.getScene().getStylesheets().add("/view/style/stylesheet.css");
		currentStage.centerOnScreen();
		currentStage.show();
		
	}
	public static void main(String args[]){
		launch();
	}
	


}
