package view.controls.visualizacao;

import java.io.IOException;

import beans.Exercicio;
import beans.Treino;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import view.controls.cadastro.ControladorTelaCadastroTreino;

public class ControladorTelaVisualizacaoTreino extends VBox{
	
	@FXML
	private Label nomeTreino;

	@FXML
	private ListView listaExercicios;
	
	public ControladorTelaVisualizacaoTreino(Treino treino){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaCadastroTreino.class.getClass().getResource("/view/fxmls/visualizacao/TelaVisualizacaoTreino.fxml"));
			loader.setController(this);
			
			this.getChildren().add(loader.load());
			
			
			this.listaExercicios.setCellFactory(new Callback<ListView<Exercicio>, CelulaListaExercicio>() {

				@Override
				public CelulaListaExercicio call(ListView<Exercicio> arg0) {
					return new CelulaListaExercicio();
				}
			});
			
			this.listaExercicios.getItems().addAll(treino.getExerciciosArray());
			this.nomeTreino.setText(treino.getNome());
			
		}catch(IOException e){
			e.printStackTrace();
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
				
				
				painel.add(new Label("Nome"), 0, 0);
				painel.add(new Label(objeto.getNome()), 0, 1);
				
				painel.add(new Label("Carga"), 1, 0);
				painel.add(new Label(objeto.getCarga()), 1, 1);
				
				painel.add(new Label("Intervalo"), 2, 0);
				painel.add(new Label(String.valueOf(objeto.getIntervalo())), 2, 1);
				
				painel.add(new Label("Repetições"), 3, 0);
				painel.add(new Label(String.valueOf(objeto.getRepeticao())), 3, 1);
				
				painel.getColumnConstraints().add(new ColumnConstraints(200)); // column 1 is 100 wide
				painel.getColumnConstraints().add(new ColumnConstraints(60)); // column 2 is 200 wide
				painel.getColumnConstraints().add(new ColumnConstraints(80)); // co
				painel.getColumnConstraints().add(new ColumnConstraints(80)); // co
				
	            setGraphic(painel);
			}
			
		}
	}

}
