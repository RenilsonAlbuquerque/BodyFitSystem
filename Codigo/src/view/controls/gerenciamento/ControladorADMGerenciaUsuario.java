package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import beans.PerfisEnum;
import beans.Usuario;
import control.Fachada;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controls.cadastro.ControladorTelaCadastroUsuario;
import view.controls.login.ControladorTelaLogin;

public class ControladorADMGerenciaUsuario extends HBox{
	
	private ArrayList usuarios;

	@FXML
	private VBox painelEsquerda;
	
	@FXML
	private VBox painelDireita;
	
	@FXML
	private HBox painelRadaioButtons;
	
	@FXML
	private HBox painelOpcoes;
	
	@FXML
	private TextField txtBuscar;
	
	@FXML
	private ListView listaObjetos;
	
	private ToggleGroup grupo;
	
	@FXML
	private RadioButton rbAluno;
	
	@FXML
	private RadioButton rbProfessor;
		
	@FXML
	private RadioButton rbAdministrador;
	
	
	public ControladorADMGerenciaUsuario(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaGerenciaUsuarios.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			this.inicializarComponentesExtra();
				
			
			this.txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
			    this.atualizaLista(newText);
			    
			});
			//this.getChildren().add(0,new ControladorTelaVisualizacao(aluno));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private void inicializarComponentesExtra(){
		this.grupo =  new ToggleGroup();
		this.grupo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle toggle, Toggle new_toggle) {
		            if (new_toggle == null){
		            	
		            }  
		            else{
		            	if(new_toggle.getToggleGroup().getSelectedToggle() == rbAluno)
		            		povoarlista(PerfisEnum.aluno);
		            	if(new_toggle.getToggleGroup().getSelectedToggle() == rbProfessor)
		            		povoarlista(PerfisEnum.professor);
		            	if(new_toggle.getToggleGroup().getSelectedToggle() == rbAdministrador)
		            		povoarlista(PerfisEnum.administrador);
		            		
		            }
		                
		         }
		});
		
		
		
		this.rbAluno.setToggleGroup(grupo);
		this.rbProfessor.setToggleGroup(grupo);
		this.rbAdministrador.setToggleGroup(grupo);
		
		
	}
	public void povoarlista(PerfisEnum perfil){
		try {
			if(perfil == PerfisEnum.aluno)
				this.usuarios =  Fachada.getInstance().listarAlunos(); 
			if(perfil == PerfisEnum.professor)
				this.usuarios =  Fachada.getInstance().listarProfessores();
			if(perfil == PerfisEnum.administrador)
				this.usuarios =  Fachada.getInstance().listarAdministradores();
			this.listaObjetos.setItems(FXCollections.observableArrayList(usuarios));
			Collections.sort(this.listaObjetos.getItems());
		}
		
		catch (NegocioException e) {
			this.listaObjetos.setItems(FXCollections.observableArrayList(e.getMessage()));
		}
	}
	
	public void atualizaLista(String parametro){
		this.listaObjetos.getItems().clear();
		
		for(Object u: this.usuarios){
			if( ((Usuario) u).getNome().toLowerCase().contains(parametro.toLowerCase()) )
				listaObjetos.getItems().add(u);
		}
		Collections.sort(this.listaObjetos.getItems());
		if(usuarios.isEmpty()){
			listaObjetos.setItems(FXCollections.observableArrayList("Nenhum resutado encontrado"));
		}
		
	}
	@FXML
	private void acaoBtCadastrar(ActionEvent e){
		Stage s = new Stage();
		s.setScene(new Scene(new ControladorTelaCadastroUsuario()));
		s.getScene().getStylesheets().add("/view/style/stylesheet.css");
		s.show();
	}
	
	@FXML
	private void acaoBtAlterar(ActionEvent e){
		
	}
	@FXML
	private void acaoBtExcluir(ActionEvent e){
		
	}
	
}
