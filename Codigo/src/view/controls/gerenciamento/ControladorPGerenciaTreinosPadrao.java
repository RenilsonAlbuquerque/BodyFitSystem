package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import beans.Treino;
import control.Fachada;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.controls.atualizar.ControladorTelaAlterarTreinoPadrao;
import view.controls.cadastro.ControladorTelaCadastroTreinoPadrao;
import view.controls.login.ControladorTelaLogin;
import view.controls.menu.ControladorMenuPrincipal;
import view.controls.visualizacao.ControladorTelaVisualizacaoTreino;

public class ControladorPGerenciaTreinosPadrao extends BorderPane {
	
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
	
	public ControladorPGerenciaTreinosPadrao(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaProfessorGerenciaTreinos.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			this.povoarlista();
			
			this.painelDireita.getChildren().add(0,new ControladorTelaVisualizacaoTreino());
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void povoarlista(){
		try {
			this.treinos =  Fachada.getInstance().listarTreinosPadrao(); 
			this.listaObjetos.setItems(FXCollections.observableArrayList(treinos));
			Collections.sort(this.listaObjetos.getItems());
			
			this.listaObjetos.getItems().addListener(new ListChangeListener<Treino>() {
				
				@Override
				public void onChanged(Change<? extends Treino> arg0) {
					if(!listaObjetos.getItems().isEmpty())
						((ControladorTelaVisualizacaoTreino) painelDireita.getChildren().get(0))
						.adicionarTreino((Treino) listaObjetos.getItems().get(0));
				}
			});
			
			this.listaObjetos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Treino>() {
				@Override
				public void changed(ObservableValue<? extends Treino> observable, Treino oldValue, Treino newValue) {
					if(!listaObjetos.getItems().isEmpty())
						((ControladorTelaVisualizacaoTreino) painelDireita.getChildren().get(0))
						.adicionarTreino((Treino) listaObjetos.getSelectionModel().getSelectedItem());
					
				}
			});
		}
		catch (NegocioException e) {
			this.listaObjetos.setItems(FXCollections.observableArrayList(e.getMessage()));
		}
		
	}
	@FXML
	private void acaoCadastrarTreinos(ActionEvent e){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaCadastroTreinoPadrao());
	}
	@FXML
	private void acaoAlterarTreinos(ActionEvent e){
		if(this.listaObjetos.getSelectionModel().getSelectedItem() != null)
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaAlterarTreinoPadrao((Treino) this.listaObjetos.getSelectionModel().getSelectedItem()));
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
					Fachada.getInstance().removerTreino((Treino) listaObjetos.getSelectionModel().getSelectedItem());
					this.treinos.remove(treino);
					povoarlista();
					//listaObjetos.getSelectionModel().select(0);
				}catch(NegocioException ex){
					System.out.println(ex.getMessage());
				}
			}
			
		}
	}

}
