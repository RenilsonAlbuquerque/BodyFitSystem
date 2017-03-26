package view.controls.gerenciamento;

import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beans.Aluno;
import beans.Treino;
import control.Contexto;
import control.Fachada;
import control.GeradorPDF;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
	private Label labelTreinosAluno;
	
	@FXML
	private TextField txtBuscarTreino;
	
	@FXML
	private ListView listaTreinosAluno;
	
	@FXML
	private ListView listaTreinos;
	
	@FXML
	private Button btnImprimirTreino;
	
	@FXML
	private RadioButton btTreinoPadrao;
	
	@FXML
	private RadioButton btMeusTreinos;
	
	private ToggleGroup grupo;
	
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
			
			this.txtBuscarTreino.textProperty().addListener((obs, oldText, newText) -> {
			    this.atualizaLista(newText);
			    
			});
			
			this.grupo =  new ToggleGroup();
			this.grupo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov,
			        Toggle toggle, Toggle new_toggle) {
			            if (new_toggle == null){
			            	
			            }  
			            else{
			            	if(new_toggle.getToggleGroup().getSelectedToggle() == btTreinoPadrao)
			            		atualizaLista(txtBuscarTreino.getText());
			            	if(new_toggle.getToggleGroup().getSelectedToggle() == btMeusTreinos)
			            		atualizaLista(txtBuscarTreino.getText());
			            	
			            		
			            }
			                
			         }
			});
			
			
			
			this.btTreinoPadrao.setToggleGroup(grupo);
			this.btMeusTreinos.setToggleGroup(grupo);
			
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
		this.btMeusTreinos.setSelected(true);

	}
	private void povoaLista(ArrayList lista){
		
		this.listaTreinos.getItems().clear();
		
		this.listaTreinos.refresh();
		this.listaTreinos.setItems(FXCollections.observableArrayList(lista));
	}
	public void atualizaLista(String parametro){
		
		this.listaTreinos.getItems().clear();
		if(this.btMeusTreinos.isSelected()){
			for(Treino t : (ArrayList<Treino>) this.treinosProfessor){
				if( t.getNome().toLowerCase().contains(parametro.toLowerCase()) )
					listaTreinos.getItems().add(t);
			}
		}else{
			for(Treino t : (ArrayList<Treino>) this.treinosPadrao){
				if( t.getNome().toLowerCase().contains(parametro.toLowerCase()) )
					listaTreinos.getItems().add(t);
			}
		}
		
		Collections.sort(this.listaTreinos.getItems());
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
	private void acaoBtImprimir(){
		
		
		if(!this.listaTreinosAluno.getItems().isEmpty()){
			try{
				List<Treino> treinos = this.listaTreinosAluno.getItems();
				Desktop.getDesktop().open(
						GeradorPDF.getInstance().criarPDFPadraoDeTreinos(new ArrayList<Treino>(treinos),aluno.getNome())
						);
			}catch(Exception e){
				Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
				DialogPane d = dialogo.getDialogPane();
				d.getStylesheets().add(
						   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
						d.getStyleClass().add("dialog-pane");
				
				dialogo.setHeaderText(null);
				dialogo.setContentText(e.getMessage());
				dialogo.show();
			}
		}
		else{
			Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			
			dialogo.setHeaderText(null);
			dialogo.setContentText("O aluno não possui treinos cadastrados");
			dialogo.show();
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
	
	class CelulaListaTreinoAdd extends ListCell<Treino>{
		
		@Override
		protected void updateItem(Treino objeto,boolean empty){
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
	            
	       
	            
	            Button adicionar = new Button("A");
	            adicionar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		if(listaTreinos.getSelectionModel().getSelectedIndex() != CelulaListaTreinoAdd.this.getIndex() )
	            			listaTreinos.getSelectionModel().clearAndSelect(CelulaListaTreinoAdd.this.getIndex());
	            		if(listaTreinosAluno.getItems().size() < 6)
	            		listaTreinosAluno.getItems().add(objeto);
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
	        							objeto));
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
			            painel.getColumnConstraints().add(new ColumnConstraints(30));
			            painel.getColumnConstraints().add(new ColumnConstraints(30));

			            Label icon = new Label(objeto.getNome());
			            icon.getStyleClass().add("cache-list-icon");
			            painel.add(icon,0,0);          

			            
			            Button visualizar = new Button("V");
			           // visualizar.setGraphic(new ImageView("/imagens/viewIcon.png"));
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
			        					new ControladorTelaVisualizacaoTreino(objeto));
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
			            		listaTreinosAluno.getItems().remove(objeto);
			            	}
			            });
			            painel.add(remover,2,0);
			            
			            setGraphic(painel);
					}
					
				}
			}
		}

	


