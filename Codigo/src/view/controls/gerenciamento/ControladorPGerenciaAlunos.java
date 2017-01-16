package view.controls.gerenciamento;

import java.io.IOException;
import java.util.Collections;

import beans.Aluno;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import view.controls.login.ControladorTelaLogin;

public class ControladorPGerenciaAlunos extends BorderPane{
	
	@FXML
	private ListView listaAlunos;
	
	@FXML
	private Pane painelDadosAluno;
	
	@FXML
	private TextField txtBuscar;
	
	@FXML
	private Button btnAdcionar;
	
	@FXML
	private Button btnEditar;
	
	@FXML
	private Button btnExcluir;
	
	public ControladorPGerenciaAlunos(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/PGerenciaAluno.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			this.povoarlista();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void povoarlista(){
		ObservableList<Aluno> itens = null;
		try {
			itens = FXCollections.observableArrayList(Fachada.getInstance().listarAlunos());
			Collections.sort(itens);
			this.listaAlunos.setItems(itens);
		} catch (ConexaoBancoException | CRUDException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerta de erro");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
		}catch (NegocioException e) {
			this.listaAlunos.setItems(FXCollections.observableArrayList(e.getMessage()));
		}
	}
}
