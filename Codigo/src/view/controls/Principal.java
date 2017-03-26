package view.controls;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.controls.login.ControladorTelaLogin;

public class Principal extends Application{
	
	private static Stage currentStage;
	
	
	@Override
	public void start(Stage arg0) {
		
		currentStage = new Stage();
		//currentStage.initStyle(StageStyle.UNDECORATED);
		currentStage.getIcons().add(new Image("/imagens/Main Icon.png"));
		currentStage.setTitle("BodyFit Systems");
		setCurrentStage(new ControladorTelaLogin());	
	}
	
	public static void setCurrentStage(Parent painel){
		
		currentStage.setScene(new Scene(painel));
		currentStage.getScene().getStylesheets().add("/view/style/stylesheet.css");
		currentStage.centerOnScreen();
		currentStage.getScene().setFill(Color.BLACK);
		currentStage.show();
		
	}
	public static void setCurrentStage(Parent painel,int largura,int altura){
		
		currentStage.setScene(new Scene(painel,largura,altura));
		currentStage.setMaximized(true);
		currentStage.getScene().getStylesheets().add("/view/style/stylesheet.css");
		currentStage.centerOnScreen();
		currentStage.getScene().setFill(Color.BLACK);
		currentStage.show();
		
	}
	public static void main(String args[]){
		launch();
	}
	


}
