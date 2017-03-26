package view.controls.gerenciamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import beans.Exercicio;
import control.Contexto;
import control.Fachada;
import exceptions.NegocioException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controls.login.ControladorTelaLogin;
import view.controls.visualizacao.ControladorVisualizacaoExercicio;

public class ControladorPGerenciaExercicios extends BorderPane{
	
	private ArrayList<Exercicio> exercicios;
	
	@FXML
	private VBox painelEsquerda;
	
	@FXML
	private VBox painelDireita;
	
	@FXML
	private ListView listaObjetos;
	
	@FXML
	private TextField txtBuscar;	
	
	@FXML
	private Button btnCadastrarExercicio;
	
	@FXML
	private Button btnAlterarExercicio;
	
	@FXML
	private Button btnExcluirExercicio;
	
	public ControladorPGerenciaExercicios(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/gerenciamento/TelaProfessorGerenciaExercicios.fxml"));
			loader.setController(this);
			this.setCenter(loader.load());
			
			
			this.povoarlista();
			
			this.painelDireita.getChildren().add(0,new ControladorVisualizacaoExercicio());
			
			this.txtBuscar.textProperty().addListener((obs, oldText, newText) -> {
			    this.atualizaLista(newText);
			    
			});
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public void povoarlista(){
		try {
			
			this.exercicios =  Fachada.getInstance().listarExercicios(Contexto.getInstance().getUsuarioLogado().getCpf());
			this.listaObjetos.setItems(FXCollections.observableArrayList(exercicios));
			Collections.sort(this.listaObjetos.getItems());
			
			
			this.listaObjetos.getItems().addListener(new ListChangeListener<Exercicio>() {
				
				@Override
				public void onChanged(Change<? extends Exercicio> arg0) {
					if(!listaObjetos.getItems().isEmpty() && listaObjetos.getItems().get(0) instanceof Exercicio)
					 ((ControladorVisualizacaoExercicio) painelDireita.getChildren().get(0)).setExercicio((Exercicio)listaObjetos.getItems().get(0));
				}
			});
			
			this.listaObjetos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercicio>() {
				@Override
				public void changed(ObservableValue<? extends Exercicio	> observable, Exercicio oldValue, Exercicio newValue) {
					if( listaObjetos.getSelectionModel().getSelectedItem() != null && !listaObjetos.getItems().isEmpty() && listaObjetos.getItems().get(0) instanceof Exercicio)
						((ControladorVisualizacaoExercicio) painelDireita.getChildren().get(0)).setExercicio((Exercicio)listaObjetos.getSelectionModel().getSelectedItem());	 
					else{
						((ControladorVisualizacaoExercicio) painelDireita.getChildren().get(0)).zerarCampos();
					}
				}
			});		
		}
		catch (NegocioException e) {
			
			this.listaObjetos.setItems(FXCollections.observableArrayList(e.getMessage()));
		}
	}
	public void atualizaLista(String parametro){
		this.listaObjetos.getItems().clear();
		
		for(Exercicio e : this.exercicios){
			if( e.getNome().toLowerCase().contains(parametro.toLowerCase()) )
				listaObjetos.getItems().add(e);
		}
		Collections.sort(this.listaObjetos.getItems());
	}
	@FXML
	private void acaoCadastrarExercicio(ActionEvent e){
		Stage dialogo = new Stage();
		
		GridPane painel = new GridPane();
		painel.setHgap(10);
		painel.setVgap(10);
		painel.setPadding(new Insets(20,150,10,10));
		
		TextField txtNome = new TextField();
		TextField txtCarga = new TextField();
		TextField txtIntervalo = new TextField();
		TextField txtRepeticao = new TextField();
		TextField txtSeries = new TextField();
		TextArea arDescricao = new TextArea();
		
		Label lblNome = new Label("Nome");
		Label lblCarga = new Label("Carga");
		Label lblIntervalo = new Label("Intervalo");
		Label lblRepeticao = new Label("Repetições");
		Label lblSeries = new Label("Séries");
		Label lblDescricao = new Label("Descrição");
		Label lblErro = new Label();
		
		Button botaoCadastrar = new Button("Cadastrar");
		
		painel.add(lblNome,0,0);
		painel.add(txtNome, 1, 0);
		painel.add(lblCarga, 0, 1);
		painel.add(txtCarga,1,1);
		painel.add(lblRepeticao, 0, 2);
		painel.add(txtRepeticao, 1, 2);
		painel.add(lblIntervalo, 0, 3);
		painel.add(txtIntervalo, 1, 3);
		painel.add(lblSeries, 0, 4);
		painel.add(txtSeries, 1, 4);
		painel.add(lblDescricao, 0, 5);
		painel.add(arDescricao, 1, 5);
		painel.add(lblErro, 1, 6);
		painel.add(botaoCadastrar, 2, 7);
		

		botaoCadastrar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		
		    		Fachada.getInstance().cadastrarExercicio(new Exercicio(
		    				Contexto.getInstance().getUsuarioLogado().getCpf(),
		    				txtNome.getText(),txtCarga.getText(),Integer.valueOf(txtRepeticao.getText()),Integer.valueOf(txtIntervalo.getText()),
		    				Integer.valueOf(txtSeries.getText()), arDescricao.getText(),false
		    				));
		    	
		    		dialogo.close();
		    		povoarlista();
				} catch ( NegocioException ex) {
					lblErro.setText(ex.getMessage());
					ex.printStackTrace();
				} 
						
		    }
		});
		
		
		dialogo.setScene(new Scene(painel));
		dialogo.getScene().getStylesheets().add("/view/style/stylesheet.css");
		dialogo.show();
	}
	
	@FXML
	private void acaoAlterarExercicio(ActionEvent e){
		if (listaObjetos.getSelectionModel().getSelectedItem() != null
				&& listaObjetos.getSelectionModel().getSelectedItem() instanceof Exercicio) {
			Exercicio exercicio = ((Exercicio) listaObjetos.getSelectionModel().getSelectedItem());
			Stage dialogo = new Stage();

			GridPane painel = new GridPane();
			painel.setHgap(10);
			painel.setVgap(10);
			painel.setPadding(new Insets(20, 150, 10, 10));

			TextField txtNome = new TextField(exercicio.getNome());
			TextField txtCarga = new TextField(exercicio.getCarga());
			TextField txtIntervalo = new TextField(String.valueOf(exercicio.getIntervalo()));
			TextField txtRepeticao = new TextField(String.valueOf(exercicio.getRepeticao()));
			TextField txtSeries = new TextField(String.valueOf(exercicio.getSeries()));
			TextArea arDescricao = new TextArea(String.valueOf(exercicio.getDescricao()));

			Label lblNome = new Label("Nome");
			Label lblCarga = new Label("Carga");
			Label lblIntervalo = new Label("Intervalo");
			Label lblRepeticao = new Label("Repetições");
			Label lblSeries = new Label("Séries");
			Label lblDescricao = new Label("Descrição");
			Label lblErro = new Label();

			Button botaoCadastrar = new Button("Alterar");

			painel.add(lblNome,0,0);
			painel.add(txtNome, 1, 0);
			painel.add(lblCarga, 0, 1);
			painel.add(txtCarga,1,1);
			painel.add(lblRepeticao, 0, 2);
			painel.add(txtRepeticao, 1, 2);
			painel.add(lblIntervalo, 0, 3);
			painel.add(txtIntervalo, 1, 3);
			painel.add(lblSeries, 0, 4);
			painel.add(txtSeries, 1, 4);
			painel.add(lblDescricao, 0, 5);
			painel.add(arDescricao, 1, 5);
			painel.add(lblErro, 1, 6);
			painel.add(botaoCadastrar, 2, 7);

			botaoCadastrar.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					
					try {
						exercicio.setNome(txtNome.getText());
						exercicio.setCarga(txtCarga.getText());
						exercicio.setRepeticao(Integer.valueOf(txtRepeticao.getText()));
						exercicio.setIntervalo(Integer.valueOf(txtIntervalo.getText()));
						exercicio.setSeries(Integer.valueOf(txtSeries.getText()));
						exercicio.setDescricao(arDescricao.getText());
						Fachada.getInstance().alterarExercicio(exercicio);
						
						dialogo.close();
					
						povoarlista();
						
						listaObjetos.getSelectionModel().select(0);
					}
					catch (NegocioException ex) {
						
					}
					
				}
			});

			dialogo.setScene(new Scene(painel));
			dialogo.getScene().getStylesheets().add("/view/style/stylesheet.css");
			dialogo.show();
		}
	}
	@FXML
	private void acaoExcluirExercicio(ActionEvent e){
		if (listaObjetos.getSelectionModel().getSelectedItem() != null
				&& listaObjetos.getSelectionModel().getSelectedItem() instanceof Exercicio) {
			Exercicio exercicio = (Exercicio) listaObjetos.getSelectionModel().getSelectedItem();
			Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
			DialogPane d = dialogo.getDialogPane();
			d.getStylesheets().add(
					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
					d.getStyleClass().add("dialog-pane");
			dialogo.setContentText("tem certeza que deseja excluir o Exercício " + exercicio.getNome() + "?");
			dialogo.setHeaderText(null);
			Optional<ButtonType> result = dialogo.showAndWait();
			if(result.get() == ButtonType.OK){
				try{
					Fachada.getInstance().removerExercicio((Exercicio) listaObjetos.getSelectionModel().getSelectedItem());
					povoarlista();
					listaObjetos.getSelectionModel().select(0);
				}catch(NegocioException ex){
					
				}
			}
			
		}
	}
}
