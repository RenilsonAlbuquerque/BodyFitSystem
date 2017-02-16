package view.controls.atualizar;

import java.io.IOException;
import java.util.ArrayList;

import beans.Exercicio;
import beans.Treino;
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
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import view.controls.cadastro.ControladorTelaCadastroTreino;
import view.controls.visualizacao.ControladorVisualizacaoExercicio;

public class ControladorTelaAlterarTreino extends GridPane{

	private Treino treino;
	
	private ArrayList exerciciosPadrao;
	private ArrayList exerciciosProfessor;
	
	
	@FXML
	private ListView listaExerciciosTreino;
	@FXML
	private ListView listaExerciciosAdcionar;
	
	@FXML
	private TextField txtNomeTreino;
	
	@FXML
	private Button btnMeusExercicios;
	
	@FXML
	private Button btnExerciciosPadrao;
	
	@FXML
	private Button btnSalvar;
	
	
	
	public ControladorTelaAlterarTreino(Treino treino){
		try{
			
			FXMLLoader loader = new FXMLLoader(ControladorTelaCadastroTreino.class.getClass().getResource("/view/fxmls/cadastro/TelaCadastroTreino.fxml"));
			loader.setController(this);
			
			this.getChildren().add(loader.load());
			
			this.listaExerciciosTreino.setCellFactory(new Callback<ListView<Exercicio>, CelulaListaTreino>() {

				@Override
				public CelulaListaTreino call(ListView<Exercicio> arg0) {
					return new CelulaListaTreino();
				}
			});
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		this.treino = treino;
		try 
		{
			exerciciosPadrao= Fachada.getInstance().listarExerciciosPadrao();
			
		} catch ( NegocioException e) {
			this.exerciciosPadrao = new ArrayList<String>();
			this.exerciciosPadrao.add(e.getMessage());
		}
		try{
			exerciciosProfessor = Fachada.getInstance().listarExercicios(Fachada.getInstance().getUsuarioLogado().getCpf());
			
		} catch ( NegocioException e) {
			this.exerciciosProfessor = new ArrayList<String>();
			this.exerciciosProfessor.add(e.getMessage());
		}

		this.listaExerciciosAdcionar.setVisible(false);
		
		this.listaExerciciosTreino.setItems(FXCollections.observableArrayList(this.treino.getExerciciosArray()));
		this.txtNomeTreino.setText(this.treino.getNome());
		this.btnSalvar.setText("Alterar");
	}
	
	
	
	private void povoaLista(ArrayList lista){
		
		this.listaExerciciosAdcionar.getItems().clear();

		if(lista.get(0) instanceof Exercicio){
			this.listaExerciciosAdcionar.setCellFactory(new Callback<ListView<Exercicio>, CelulaListaExercicio>() {
				@Override
				public CelulaListaExercicio call(ListView<Exercicio> arg0) {
					return new CelulaListaExercicio();
				}
			});
			
		}
		else{
			
			this.listaExerciciosAdcionar.setCellFactory(list-> {
				ListCell<String> cell = new ListCell<String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : item);
					}
				};
				return cell;
			});
			
		}
		this.listaExerciciosAdcionar.refresh();
		this.listaExerciciosAdcionar.setItems(FXCollections.observableArrayList(lista));
	}
	
	@FXML
	private void acaoBotaoSalvar(ActionEvent e){
		
		if(!this.listaExerciciosTreino.getItems().isEmpty()){
			this.treino.setNome(this.txtNomeTreino.getText());
			this.treino.getExerciciosArray().clear();
			this.treino.getExerciciosArray().addAll(this.listaExerciciosTreino.getItems());
			try {
				Fachada.getInstance().alterarTreino(this.treino);
				Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
				DialogPane d = dialogo.getDialogPane();
				d.getStylesheets().add(
						   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
						d.getStyleClass().add("dialog-pane");
				dialogo.setContentText("Treino alterado");
				dialogo.setHeaderText(null);
				dialogo.show();
			} catch (NegocioException e1) {
				Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
    			DialogPane d = dialogo.getDialogPane();
    			d.getStylesheets().add(
    					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
    					d.getStyleClass().add("dialog-pane");
    			dialogo.setContentText(e1.getMessage());
    			dialogo.setHeaderText(null);
    			dialogo.show();
			}
		}else{
			Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("Insira exercícios no treino");
			dialogo.setHeaderText(null);
			dialogo.show();
		}
	}
	
	@FXML
	private void acaoBotaoMeusExercicios(ActionEvent e){
		this.listaExerciciosAdcionar.setVisible(true);
		this.listaExerciciosAdcionar.getItems().clear();
		
		if(!this.exerciciosProfessor.isEmpty()){
			this.povoaLista(exerciciosProfessor);
		}
	
	}
	
	@FXML
	private void acaoBotaoExerciciosPadrao(ActionEvent e){
		this.listaExerciciosAdcionar.setVisible(true);
		this.listaExerciciosAdcionar.getItems().clear();
		
		if(!this.exerciciosPadrao.isEmpty()){
			this.povoaLista(exerciciosPadrao);
		}
		
	}
	class CelulaListaExercicio extends ListCell<Exercicio>{
		
		@Override
		protected void updateItem(Exercicio objeto,boolean empty){
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
	            		if(listaExerciciosAdcionar.getSelectionModel().getSelectedIndex() != CelulaListaExercicio.this.getIndex())
	            			listaExerciciosAdcionar.getSelectionModel().clearAndSelect(CelulaListaExercicio.this.getIndex());
	            		listaExerciciosTreino.getItems().add((Exercicio)listaExerciciosAdcionar.getSelectionModel().getSelectedItem());
	            	}
	            });
	            painel.add(adicionar,1,0);
	            
	            Button visualizar = new Button("V");
	            visualizar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		
	            		if(listaExerciciosAdcionar.getSelectionModel().getSelectedIndex() != CelulaListaExercicio.this.getIndex())
	            			listaExerciciosAdcionar.getSelectionModel().select(CelulaListaExercicio.this.getIndex());
	            		
	            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
	        			DialogPane d = dialogo.getDialogPane();
	        			d.getStylesheets().add(
	        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
	        					d.getStyleClass().add("dialog-pane");
	        			dialogo.getDialogPane().setContent(
	        					new ControladorVisualizacaoExercicio(
	        							(Exercicio) listaExerciciosAdcionar.getSelectionModel().getSelectedItem()));
	        			dialogo.setHeaderText(null);
	        			dialogo.show();
	            	}
	            });
	            painel.add(visualizar,2,0);
	            
	            setGraphic(painel);
			}
			
		}
	}
	class CelulaListaTreino extends ListCell<Exercicio>{
			
			@Override
			protected void updateItem(Exercicio objeto,boolean empty){
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
		            		
		            		if(listaExerciciosTreino.getSelectionModel().getSelectedIndex() != CelulaListaTreino.this.getIndex())
		            			listaExerciciosTreino.getSelectionModel().select(CelulaListaTreino.this.getIndex());
		            		
		            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
		        			DialogPane d = dialogo.getDialogPane();
		        			d.getStylesheets().add(
		        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
		        					d.getStyleClass().add("dialog-pane");
		        			dialogo.getDialogPane().setContent(
		        					new ControladorVisualizacaoExercicio(
		        							(Exercicio) listaExerciciosTreino.getSelectionModel().getSelectedItem()));
		        			dialogo.setHeaderText(null);
		        			dialogo.show();
		            	}
		            });
		            painel.add(visualizar,1,0);
		            
		            Button remover = new Button("X");
		            remover.setOnAction(new EventHandler<ActionEvent>(){
		            	@Override
		            	public void handle(ActionEvent e){
		        
		            		if(listaExerciciosTreino.getSelectionModel().getSelectedIndex() != CelulaListaTreino.this.getIndex())
		            			listaExerciciosTreino.getSelectionModel().select(CelulaListaTreino.this.getIndex());
		            		listaExerciciosTreino.getItems().remove(CelulaListaTreino.this.getIndex());
		            	}
		            });
		            painel.add(remover,2,0);
		            
		            setGraphic(painel);
				}
				
			}
		}
		
	
}
