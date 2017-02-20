package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import beans.PerfisEnum;
import beans.Treino;
import beans.Usuario;
import control.Fachada;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.controls.atualizar.ControladorTelaAlterarUsuario;
import view.controls.cadastro.ControladorTelaCadastroUsuario;
import view.controls.gerenciamento.ControladorAplicacaoTreino.CelulaListaTreino;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorTelaVisualizacao;

public class ControladorADMGerenciaUsuario extends HBox{
	
	private ArrayList usuarios;

	@FXML
	private HBox painelTopo;
	
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
			this.rbAluno.setSelected(true);
			
			
			this.listaObjetos.setCellFactory(new Callback<ListView<Treino>, CelulaListaUsuario>() {

				@Override
				public CelulaListaUsuario call(ListView<Treino> arg0) {
					return new CelulaListaUsuario();
				}
			});
		
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
		this.listaObjetos.getItems().clear();
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
		if (this.listaObjetos.getSelectionModel().getSelectedItem() instanceof Usuario) {
			Usuario u = (Usuario) this.listaObjetos.getSelectionModel().getSelectedItem();
			Stage s = new Stage();
			try {
				s.setScene(new Scene(new ControladorTelaAlterarUsuario(
						(Usuario) this.listaObjetos.getSelectionModel().getSelectedItem(), 
						Fachada.getInstance().getPerfisObject((Usuario)listaObjetos.getSelectionModel().getSelectedItem()))));
			} catch (NegocioException e1) {
				e1.printStackTrace();
			}
			s.getScene().getStylesheets().add("/view/style/stylesheet.css");
			s.show();
		}
	}
	@FXML
	private void acaoBtExcluir(ActionEvent e){
		
		if (this.listaObjetos.getSelectionModel().getSelectedItem() instanceof Usuario) {
			Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(getClass().getResource("/view/style/stylesheet.css").toExternalForm());
			d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("A operação seguinte apagará todos os perfis do usuário selecionado \n Deseja continuar?");
			dialogo.setHeaderText(null);
			Optional<ButtonType> result = dialogo.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					Fachada.getInstance().remover((Usuario) this.listaObjetos.getSelectionModel().getSelectedItem());
				} catch (NegocioException e1) {

				}
			}
		}
		
	}
	class CelulaListaUsuario extends ListCell<Usuario>{
		
		@Override
		protected void updateItem(Usuario objeto,boolean empty){
			super.updateItem(objeto,empty);
			
			if(empty){
				setGraphic(null);
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(30));
	            painel.getColumnConstraints().add(new ColumnConstraints(30));

	            Label icon = new Label(objeto.getNome());
	            icon.getStyleClass().add("cache-list-icon");
	            painel.add(icon,0,0);     
	            
	       
	              
	            Button visualizar = new Button("V");
	            visualizar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		
	            		if(listaObjetos.getSelectionModel().getSelectedIndex() != CelulaListaUsuario.this.getIndex())
	            			listaObjetos.getSelectionModel().select(CelulaListaUsuario.this.getIndex());
	            		
	            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
	        			DialogPane d = dialogo.getDialogPane();
	        			d.getStylesheets().add(
	        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
	        					d.getStyleClass().add("dialog-pane");
	        			dialogo.getDialogPane().setContent(
	        					new ControladorTelaVisualizacao(objeto));
	        			dialogo.setHeaderText(null);
	        			dialogo.show();
	            	}
	            });
	            painel.add(visualizar,12,0);
	            
	            setGraphic(painel);
			}
			
		}
	}
	
}
