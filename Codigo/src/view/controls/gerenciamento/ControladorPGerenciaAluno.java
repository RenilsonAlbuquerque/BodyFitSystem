package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import beans.Aluno;
import beans.Usuario;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.controls.login.ControladorTelaLogin;
import view.controls.menu.ControladorMenuPrincipal;
import view.controls.visualizacao.ControladorTelaVisualizacao;

public class ControladorPGerenciaAluno extends BorderPane{
	
	private ArrayList<Aluno> alunos;
	
	@FXML
	private VBox painelEsquerda;
	
	@FXML
	private VBox painelDireita;
	
	@FXML
	private ListView listaObjetos;
	
	@FXML
	private TextField txtBuscar;	
	
	@FXML
	private Button btnRotinaTreinos;
	
	public ControladorPGerenciaAluno(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/PGerenciaAluno.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			this.povoarlista();
			
			this.painelDireita.getChildren().add(0,new ControladorTelaVisualizacao((Usuario)this.listaObjetos.getItems().get(0)));
			
			
			this.txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
			    this.atualizaLista(newText);
			    
			});
			
			
			this.listaObjetos.getItems().addListener(new ListChangeListener<Aluno>() {
				
				@Override
				public void onChanged(Change<? extends Aluno> arg0) {
					if(!listaObjetos.getItems().isEmpty())
					((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario((Usuario) listaObjetos.getItems().get(0));
				}
			});
			
			this.listaObjetos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Aluno>() {
				@Override
				public void changed(ObservableValue<? extends Aluno> observable, Aluno oldValue, Aluno newValue) {
					if(!listaObjetos.getItems().isEmpty())
						((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario((Usuario) listaObjetos.getSelectionModel().getSelectedItem());
					
				}
			});
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void povoarlista(){
		try {
			this.alunos =  Fachada.getInstance().listarAlunos(); 
			this.listaObjetos.setItems(FXCollections.observableArrayList(alunos));
			Collections.sort(this.listaObjetos.getItems());
		}
		catch(ConexaoBancoException | CRUDException e){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerta de erro");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}catch (NegocioException e) {
			this.listaObjetos.setItems(FXCollections.observableArrayList(e.getMessage()));
		}
		
	}
	
	public void atualizaLista(String parametro){
		this.listaObjetos.getItems().clear();
		
		for(Aluno a : this.alunos){
			if( a.getNome().toLowerCase().contains(parametro.toLowerCase()) )
				listaObjetos.getItems().add(a);
		}
		Collections.sort(this.listaObjetos.getItems());
	}
	
	@FXML
	public void acaoRotinaTreinos(ActionEvent e){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladoAplicacaoTreino((Aluno) this.listaObjetos.getSelectionModel().getSelectedItem()));
	}
}
