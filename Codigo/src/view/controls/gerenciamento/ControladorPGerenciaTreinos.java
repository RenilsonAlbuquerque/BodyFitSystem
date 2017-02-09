package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import beans.Exercicio;
import beans.Treino;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.controls.atualizar.ControladorTelaAlterarTreino;
import view.controls.cadastro.ControladorTelaCadastroTreino;
import view.controls.login.ControladorTelaLogin;
import view.controls.menu.ControladorMenuPrincipal;

public class ControladorPGerenciaTreinos extends BorderPane{
	
	private ArrayList<Treino> treinos;
	
	@FXML
	private VBox painelEsquerda;
	
	@FXML
	private VBox painelDireita;
	
	@FXML
	private ListView listaObjetos;
	
	@FXML
	private TextField txtBuscar;	
	
	@FXML
	private Button btnCadastrarTreino;
	
	@FXML
	private Button btnAlterarTreino;
	
	@FXML
	private Button btnExcluirTreino;
	
	public ControladorPGerenciaTreinos(String cpfProf){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaProfessorGerenciaTreinos.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			this.povoarlista(cpfProf);
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void povoarlista(String cpfProf){
		try {
			this.treinos =  Fachada.getInstance().listarTreinos(cpfProf); 
			this.listaObjetos.setItems(FXCollections.observableArrayList(treinos));
			Collections.sort(this.listaObjetos.getItems());
			
			this.listaObjetos.getItems().addListener(new ListChangeListener<Treino>() {
				
				@Override
				public void onChanged(Change<? extends Treino> arg0) {
					//if(!listaObjetos.getItems().isEmpty())
					//((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario(listaObjetos.getItems().get(0));
				}
			});
			
			this.listaObjetos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Treino>() {
				@Override
				public void changed(ObservableValue<? extends Treino> observable, Treino oldValue, Treino newValue) {
					//if(!listaObjetos.getItems().isEmpty())
						//((ControladorTelaVisualizacao) painelDireita.getChildren().get(0)).adcionarUsuario( listaObjetos.getSelectionModel().getSelectedItem());
					
				}
			});
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
	@FXML
	private void acaoCadastrarTreinos(ActionEvent e){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaCadastroTreino());
	}
	@FXML
	private void acaoAlterarTreinos(ActionEvent e){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaAlterarTreino((Treino) this.listaObjetos.getSelectionModel().getSelectedItem()));
	}
	@FXML
	private void acaoExcluirTreino(ActionEvent e){
		if (listaObjetos.getSelectionModel().getSelectedItem() != null
				&& listaObjetos.getSelectionModel().getSelectedItem() instanceof Treino) {
			Treino treino = (Treino) listaObjetos.getSelectionModel().getSelectedItem();
			Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("tem certeza que deseja excluir o treino " + treino.getNome() + "?");
			dialogo.setHeaderText(null);
			Optional<ButtonType> result = dialogo.showAndWait();
			if(result.get() == ButtonType.OK){
				try{
					Fachada.getInstance().removerTreinoPadrao((Treino) listaObjetos.getSelectionModel().getSelectedItem());
					//this.treinos.remove(treino);
					povoarlista(Fachada.getInstance().getUsuarioLogado().getCpf());
					listaObjetos.getSelectionModel().select(0);
				}catch(ConexaoBancoException | CRUDException | NegocioException ex){
					
				}
			}
			
		}
	}
}
