package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorTelaVisualizacaoTreino;

public class ControladorAplicacaoTreino extends FlowPane{
	
	private Aluno aluno;
	private ArrayList treinosPadrao;
	private ArrayList treinosProfessor;
	
	@FXML
	private TextField txtBuscarTreino;
	
	@FXML
	private ListView listaTreinosAluno;
	
	@FXML
	private ListView listaTreinos;
	
	@FXML
	private Button btnImprimirTreino;
	
	@FXML
	private Button btnTreinoPadrao;
	
	@FXML
	private Button btnMeusTreinos;
	
	@FXML
	private Button btnSalvar;
	
	public ControladorAplicacaoTreino(Aluno aluno){
		
		this.aluno = aluno;
		
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaAplicacaoTreinos.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			
			this.listaTreinosAluno.setCellFactory(new Callback<ListView<Treino>, CelulaListaTreino>() {

				@Override
				public CelulaListaTreino call(ListView<Treino> arg0) {
					return new CelulaListaTreino();
				}
			});
			
			this.listaTreinos.setCellFactory(new Callback<ListView<Treino>, CelulaListaTreinoAdd>() {

				@Override
				public CelulaListaTreinoAdd call(ListView<Treino> arg0) {
					return new CelulaListaTreinoAdd();
				}
			});
			
			//this.getChildren().add(0,new ControladorTelaVisualizacao(aluno));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try 
		{
			this.treinosPadrao = Fachada.getInstance().listarTreinosPadrao();
			
		} catch ( NegocioException e) {
			this.treinosPadrao = new ArrayList<>();
			/*
			this.treinosPadrao = new ArrayList<String>();
			this.treinosPadrao.add(e.getMessage());
			*/
		}
		try{
			this.treinosProfessor = Fachada.getInstance().listarTreinos(Contexto.getInstance().getUsuarioLogado().getCpf());
			
		} catch ( NegocioException e) {
			this.treinosProfessor = new ArrayList<>();
			/*
			this.treinosProfessor = new ArrayList<String>();
			this.treinosProfessor.add(e.getMessage());
			*/
		}
		
		
		this.listaTreinosAluno.getItems().addAll(this.aluno.getRotinaTreinos());

	}
	private void povoaLista(ArrayList lista){
		
		this.listaTreinos.getItems().clear();
		
		this.listaTreinos.refresh();
		this.listaTreinos.setItems(FXCollections.observableArrayList(lista));
	}
	
	@FXML
	private void acaoBtTreinoPadrao(){
		if(!this.treinosPadrao.isEmpty()){
			this.povoaLista(this.treinosPadrao);
		}
	}
	
	@FXML
	private void acaoBtMeusTreinos(){
		if(!this.treinosProfessor.isEmpty()){
			this.povoaLista(this.treinosProfessor);
		}
	}
	
	@FXML
	private void acaoBtSalvar(){
		try {
			ArrayList<Treino> treinos = new ArrayList<Treino>();
			treinos.addAll(this.listaTreinosAluno.getItems());
			aluno.setRotinaTreinos(treinos);
			Fachada.getInstance().salvarRotinaTreino(aluno);
			
			Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("Rotina de treinos Salva!");
			dialogo.setHeaderText(null);
			dialogo.show();
			
		} catch (NegocioException e) {
			Alert dialogo = new Alert(Alert.AlertType.ERROR);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText(e.getMessage());
			dialogo.setHeaderText(null);
			dialogo.show();
		}
	}
	class CelulaListaTreinos extends ListCell<Treino>{
		
		@Override
		protected void updateItem(Treino objeto,boolean empty){
			super.updateItem(objeto,empty);
			if(empty){
				
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));

	            Label icon = new Label(objeto.getNome());
	            icon.getStyleClass().add("cache-list-icon");
	            painel.add(icon,0,0);          

	            
	            Button visualizar = new Button("V");
	            painel.add(visualizar,1,0);
	            
	            Button remover = new Button("X");
	            remover.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		listaTreinosAluno.getItems().remove(CelulaListaTreinos.this);
	            	}
	            });
	            painel.add(remover,2,0);
	            
	            setGraphic(painel);
			}
		}
	}
	class CelulaListaTreinoAdd extends ListCell<Treino>{
		
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
	            
	       
	            
	            Button adicionar = new Button("A");
	            adicionar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		if(listaTreinos.getSelectionModel().getSelectedIndex() != CelulaListaTreinoAdd.this.getIndex())
	            			listaTreinos.getSelectionModel().clearAndSelect(CelulaListaTreinoAdd.this.getIndex());
	            		listaTreinosAluno.getItems().add((Treino) listaTreinos.getSelectionModel().getSelectedItem());
	            	}
	            });
	            painel.add(adicionar,1,0);
	            
	            Button visualizar = new Button("V");
	            visualizar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		
	            		if(listaTreinos.getSelectionModel().getSelectedIndex() != CelulaListaTreinoAdd.this.getIndex())
	            			listaTreinos.getSelectionModel().select(CelulaListaTreinoAdd.this.getIndex());
	            		
	            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
	        			DialogPane d = dialogo.getDialogPane();
	        			d.getStylesheets().add(
	        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
	        					d.getStyleClass().add("dialog-pane");
	        			dialogo.getDialogPane().setContent(
	        					new ControladorTelaVisualizacaoTreino(
	        							(Treino) listaTreinos.getSelectionModel().getSelectedItem()));
	        			dialogo.setHeaderText(null);
	        			dialogo.show();
	            	}
	            });
	            painel.add(visualizar,2,0);
	            
	            setGraphic(painel);
			}
			
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
		            		
		            		if(listaTreinosAluno.getSelectionModel().getSelectedIndex() != CelulaListaTreino.this.getIndex())
		            			listaTreinosAluno.getSelectionModel().select(CelulaListaTreino.this.getIndex());
		            		
		            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
		        			DialogPane d = dialogo.getDialogPane();
		        			d.getStylesheets().add(
		        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
		        					d.getStyleClass().add("dialog-pane");
		        			dialogo.getDialogPane().setContent(
		        					new ControladorTelaVisualizacaoTreino(
		        							(Treino) listaTreinosAluno.getSelectionModel().getSelectedItem()));
		        			dialogo.setHeaderText(null);
		        			dialogo.show();
		            	}
		            });
		            painel.add(visualizar,1,0);
		            
		            Button remover = new Button("X");
		            remover.setOnAction(new EventHandler<ActionEvent>(){
		            	@Override
		            	public void handle(ActionEvent e){
		        
		            		if(listaTreinosAluno.getSelectionModel().getSelectedIndex() != CelulaListaTreino.this.getIndex())
		            			listaTreinosAluno.getSelectionModel().select(CelulaListaTreino.this.getIndex());
		            		listaTreinosAluno.getItems().remove(CelulaListaTreino.this.getIndex());
		            	}
		            });
		            painel.add(remover,2,0);
		            
		            setGraphic(painel);
				}
				
			}
		}
		

}

