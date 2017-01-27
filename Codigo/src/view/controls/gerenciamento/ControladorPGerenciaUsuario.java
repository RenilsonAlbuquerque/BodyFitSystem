package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import beans.Administrador;
import beans.Aluno;
import beans.Exercicio;
import beans.Professor;
import beans.Treino;
import beans.Usuario;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import view.controls.visualizacao.ControladorTelaVisualizacao;

public class ControladorPGerenciaUsuario<T> extends BorderPane{
	
	private Class<T> tipo;
	
	private ArrayList objetos;
	
	@FXML
	private VBox painelEsquerda;
	
	@FXML
	private VBox painelDireita;
	
	@FXML
	private ListView listaObjetos;
	
	@FXML
	private TextField txtBuscar;	
	
	@FXML
	private Button btnAdcionar;
	
	@FXML
	private Button btnEditar;
	
	@FXML
	private Button btnExcluir;
	
	public ControladorPGerenciaUsuario(Class<T> tipo){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/PGerenciaAluno.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			this.tipo = tipo;
			
			this.povoarlista();
			if(objetos.get(0) instanceof Usuario)
				this.painelDireita.getChildren().add(0,new ControladorTelaVisualizacao((Usuario)this.listaObjetos.getItems().get(0)));
			else{
				if(objetos.get(0) instanceof Treino){
					
				}
				else{
					
					this.getClass().getName();
				}
			}
			
			
			this.txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
			    this.atualizaLista(newText);
			    
			});
			
			
			this.listaObjetos.getItems().addListener(new ListChangeListener<T>() {
				
				@Override
				public void onChanged(Change<? extends T> arg0) {
					if(!listaObjetos.getItems().isEmpty())
					((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario((Usuario) listaObjetos.getItems().get(0));
				}
			});
			
			this.listaObjetos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
				@Override
				public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
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
			if(tipo.getSuperclass().isAssignableFrom(Usuario.class)){
				if(this.tipo.isAssignableFrom(Aluno.class))
					this.objetos =  Fachada.getInstance().listarAlunos(); 
				if(tipo.isAssignableFrom(Professor.class))
					this.objetos =  Fachada.getInstance().listarProfessores();
				if(tipo.isAssignableFrom(Administrador.class))
					this.objetos =  Fachada.getInstance().listarAdministradores();
			}else{
				
			}
			this.listaObjetos.setItems(FXCollections.observableArrayList(objetos));
			Collections.sort(this.listaObjetos.getItems());
			
		} catch (ConexaoBancoException | CRUDException e) {
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
		if(tipo.getSuperclass().isAssignableFrom(Usuario.class)){
			for(Object o : this.objetos){
				if( ((Usuario)o).getNome().toLowerCase().contains(parametro.toLowerCase()))
					listaObjetos.getItems().add(o);
			}
			
		}
		Collections.sort(this.listaObjetos.getItems());
		
	}
	public void updatePanel(T item){
		if(item instanceof Usuario)
		 ((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario((Usuario)item);
		if(item instanceof Treino){
			
		}
		if(item instanceof Exercicio){
			
		}
	}

}
