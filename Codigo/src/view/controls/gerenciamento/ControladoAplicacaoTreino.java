package view.controls.gerenciamento;

import java.io.IOException;

import beans.Aluno;
import beans.Treino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorTelaVisualizacao;

public class ControladoAplicacaoTreino extends HBox{
	
		
	
	@FXML
	private ListView listaTreinosAluno;
	
	@FXML
	private Button btnNovoTreino;
	
	@FXML
	private Button btnAdcionarTreino;
	
	public ControladoAplicacaoTreino(Aluno aluno){
		/*
		ArrayList<CelulaListaTreinos> treinos = new ArrayList<CelulaListaTreinos>(); 
		treinos.add(new  CelulaListaTreinos(new Treino("treino de mão")));
		*/
		
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaAplicacaoTreinos.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			this.getChildren().add(0,new ControladorTelaVisualizacao(aluno));
		
			 ObservableList<Treino> data = FXCollections.observableArrayList();
		     data.addAll(new Treino("Treino de braço"));
			
		    this.listaTreinosAluno.getItems().addAll(data);
			this.listaTreinosAluno.setCellFactory(new Callback<ListView<Treino>, CelulaListaTreinos>() {
				
	            @Override
	            public CelulaListaTreinos call(ListView<Treino> arg0) {
	                return new CelulaListaTreinos(new Treino("De mão"));
	            }

	        });

			

			
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	class CelulaListaTreinos extends ListCell<Treino>{
		Label nomeTreino;
		Button remover;
		
		public CelulaListaTreinos(Treino treino){
			this.nomeTreino = new Label(treino.getNome());
			this.remover = new Button("X");
			
			this.getChildren().add(nomeTreino);
			this.getChildren().add(remover);
			
			this.setVisible(true);
		}
		
	}
	

}

