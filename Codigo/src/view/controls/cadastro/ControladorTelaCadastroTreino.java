package view.controls.cadastro;

import java.io.IOException;
import java.util.ArrayList;

import beans.Exercicio;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;


public class ControladorTelaCadastroTreino extends GridPane{

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
	
	public ControladorTelaCadastroTreino(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaCadastroTreino.class.getClass().getResource("/view/fxmls/cadastro/TelaCadastroTreino.fxml"));
			loader.setController(this);
			
			this.getChildren().add(loader.load());
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try 
		{
			this.exerciciosPadrao = Fachada.getInstance().listarExerciciosPadrao();
			
		} catch (ConexaoBancoException | CRUDException | NegocioException e) {
			this.exerciciosPadrao = new ArrayList<String>();
			this.exerciciosPadrao.add(e.getMessage());
		}
		try{
			this.exerciciosProfessor = Fachada.getInstance().listarExercicios(Fachada.getInstance().getUsuarioLogado().getCpf());
		} catch (ConexaoBancoException | CRUDException | NegocioException e) {
			this.exerciciosProfessor = new ArrayList<String>();
			this.exerciciosProfessor.add(e.getMessage());
		}

		this.listaExerciciosAdcionar.setVisible(false);
			
		this.listaExerciciosAdcionar.setCellFactory(new Callback<ListView<Exercicio>, CelulaListaExercicio>() {

			@Override
			public CelulaListaExercicio call(ListView<Exercicio> arg0) {
				return new CelulaListaExercicio(new Exercicio("supino", "30kg", 30, 40));
			}
		});
		
		this.listaExerciciosTreino.setCellFactory(new Callback<ListView<Exercicio>, CelulaListaExercicio>() {

			@Override
			public CelulaListaExercicio call(ListView<Exercicio> arg0) {
				return new CelulaListaExercicio(new Exercicio("supino","30kg",30,40));
			}
		});
		
		
		
	}
	
	@FXML
	private void acaoBotaoSalvar(ActionEvent e){
		
	}
	
	@FXML
	private void acaoBotaoMeusExercicios(ActionEvent e){
		this.listaExerciciosAdcionar.setVisible(true);
		listaExerciciosAdcionar.getItems().removeAll();
		
		if(!this.exerciciosProfessor.isEmpty()){
			this.listaExerciciosAdcionar.getItems().addAll(FXCollections.observableArrayList(exerciciosProfessor));
		}else{
			//this.listaExerciciosAdcionar.getItems().add(FXCollections.observableArrayList("O professor não tem exercícios cadastrados"));
		}
	
	}
	
	@FXML
	private void acaoBotaoExerciciosPadrao(ActionEvent e){
		this.listaExerciciosAdcionar.setVisible(true);
		listaExerciciosAdcionar.getItems().removeAll();
		
		if(!this.exerciciosPadrao.isEmpty()){
			this.listaExerciciosAdcionar.getItems().addAll(FXCollections.observableArrayList(exerciciosPadrao));
		}else{
			//this.listaExerciciosAdcionar.getItems().add(FXCollections.observableArrayList("Não existem exercícios padrão cadastrados"));
		}
		
	}
	class CelulaListaExercicio extends ListCell<Exercicio>{
		
		private Exercicio exercicio;
		
		public CelulaListaExercicio(Exercicio exercicio){
			this.exercicio = exercicio;
			this.setVisible(true);
		}
		
		@Override
		protected void updateItem(Exercicio objeto,boolean empty){
			super.updateItem(objeto,empty);
			
			if(empty){
				
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));

	            Label icon = new Label(exercicio.getNome());
	            icon.getStyleClass().add("cache-list-icon");
	            painel.add(icon,0,0);          

	            
	            Button visualizar = new Button("V");
	            painel.add(visualizar,1,0);
	            
	            Button remover = new Button("X");
	            remover.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		listaExerciciosTreino.getItems().remove(CelulaListaExercicio.this);
	            	}
	            });
	            painel.add(remover,2,0);
	            
	            setGraphic(painel);
			}
			
		}
		
	}
}
