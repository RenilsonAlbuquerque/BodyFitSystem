package view.controls.visualizacao;

import java.io.IOException;

import beans.Exercicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import view.controls.login.ControladorTelaLogin;

public class ControladorVisualizacaoExercicio extends VBox {
	
	@FXML
	private Label lblNome;
	
	@FXML
	private Label lblCarga;
	
	@FXML
	private Label lblIntervalo;
	
	@FXML
	private Label lblRepeticoes;
	
	public ControladorVisualizacaoExercicio(Exercicio exercicio){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/visualizacao/TelaVisualizacaoExercicio.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			this.setExercicio(exercicio);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public ControladorVisualizacaoExercicio(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/visualizacao/TelaVisualizacaoExercicio.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setExercicio(Exercicio exercicio){
		this.lblNome.setText(exercicio.getNome());
		this.lblCarga.setText(exercicio.getCarga());
		this.lblIntervalo.setText(String.valueOf(exercicio.getIntervalo()));
		this.lblRepeticoes.setText(String.valueOf(exercicio.getRepeticao()));
		
	}
	
}
