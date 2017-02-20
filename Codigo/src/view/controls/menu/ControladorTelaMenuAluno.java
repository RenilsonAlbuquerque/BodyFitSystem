package view.controls.menu;

import java.io.IOException;

import beans.Aluno;
import beans.Treino;
import control.Contexto;
import control.Fachada;
import exceptions.NegocioException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorTelaVisualizacaoTreino;

public class ControladorTelaMenuAluno extends HBox{
	
	
	@FXML
	private ListView listaObjetos;
	
	@FXML
	private Button btImprimirTreino;
	
	@FXML
	private Button btTreinar;

	@FXML
	private VBox painelDireita;
	
	public ControladorTelaMenuAluno(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/TelaMenuAluno.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			
			this.listaObjetos.setCellFactory(new Callback<ListView<Treino>, CelulaListaTreino>() {

				@Override
				public CelulaListaTreino call(ListView<Treino> arg0) {
					return new CelulaListaTreino();
				}
			});
			
			this.listaObjetos.setItems(
					FXCollections.observableArrayList(
							((Aluno) Contexto.getInstance().getUsuarioLogado()).getRotinaTreinos()
			));
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		if(!((Aluno) Contexto.getInstance().getUsuarioLogado()).getRotinaTreinos().isEmpty()){
			this.painelDireita.getChildren().add(1, new ControladorTelaVisualizacaoTreino(
						((Aluno) Contexto.getInstance().getUsuarioLogado()).getTreinoDoDia())
					);
		}	
		else{
			this.btImprimirTreino.setVisible(false);
			this.btTreinar.setVisible(false);
		
			//this.painelDireita.getChildren().add(1, new ControladorTelaVisualizacaoTreino());
			
		}
		
	}
	
	@FXML
	private void acaoImprimirTreino(){
		
	}
	
	@FXML
	private void acaobtTreinar(){
		try {
			Fachada.getInstance().salvarHistorico(
					((Aluno)Contexto.getInstance().getUsuarioLogado())
					);
			Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("Feito");
			dialogo.setHeaderText(null);
			dialogo.show();
			
		} catch (NegocioException e) {
			Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText(e.getMessage());
			dialogo.setHeaderText(null);
			dialogo.show();
		}
	}
	
	class CelulaListaTreino extends ListCell<Treino>{
		
		@Override
		protected void updateItem(Treino objeto,boolean empty){
			super.updateItem(objeto,empty);
			
			if(empty){
				setGraphic(null);
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));

	            Label icon = new Label(objeto.getNome());
	            icon.getStyleClass().add("cache-list-icon");
	            painel.add(icon,0,0);          

	            
	            Button visualizar = new Button("V");
	            visualizar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		
	            		if(listaObjetos.getSelectionModel().getSelectedIndex() != CelulaListaTreino.this.getIndex())
	            			listaObjetos.getSelectionModel().select(CelulaListaTreino.this.getIndex());
	            		
	            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
	        			DialogPane d = dialogo.getDialogPane();
	        			d.getStylesheets().add(
	        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
	        					d.getStyleClass().add("dialog-pane");
	        			dialogo.getDialogPane().setContent(
	        					new ControladorTelaVisualizacaoTreino(
	        							(Treino) listaObjetos.getSelectionModel().getSelectedItem()));
	        			dialogo.setHeaderText(null);
	        			dialogo.show();
	            	}
	            });
	            painel.add(visualizar,1,0);
	           
	            
	            setGraphic(painel);
			}
			
		}
	}
}
