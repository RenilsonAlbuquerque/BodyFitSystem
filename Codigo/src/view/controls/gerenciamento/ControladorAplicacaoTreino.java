package view.controls.gerenciamento;

import java.io.IOException;

import beans.Aluno;
import beans.Treino;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorTelaVisualizacao;

public class ControladorAplicacaoTreino extends HBox{
	
		
	
	@FXML
	private ListView listaTreinosAluno;
	
	@FXML
	private Button btnNovoTreino;
	
	@FXML
	private Button btnAdcionarTreino;
	
	public ControladorAplicacaoTreino(Aluno aluno){
		
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaAplicacaoTreinos.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			this.getChildren().add(0,new ControladorTelaVisualizacao(aluno));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try 
		{
			ObservableList<Treino> data = FXCollections.observableArrayList(Fachada.getInstance().treinosAluno(aluno.getCpf()));
			
			this.listaTreinosAluno.setCellFactory(new Callback<ListView<Treino>, CelulaListaTreinos>() {

				@Override
				public CelulaListaTreinos call(ListView<Treino> arg0) {
					return new CelulaListaTreinos(new Treino("De mão"));
				}
			});
			
			this.listaTreinosAluno.getItems().addAll(data);
		} catch (ConexaoBancoException | CRUDException | NegocioException e) {
			ObservableList<String> data = FXCollections.observableArrayList(e.getMessage());
			this.listaTreinosAluno.getItems().addAll(data);
		}
		

	}
	
	class CelulaListaTreinos extends ListCell<Treino>{
		
		private Treino treino;
		
		public CelulaListaTreinos(Treino treino){
			this.treino = treino;
			this.setVisible(true);
		}
		
		@Override
		protected void updateItem(Treino objeto,boolean empty){
			super.updateItem(objeto,empty);
			if(empty){
				
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));

	            Label icon = new Label(treino.getNome());
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
	

}

