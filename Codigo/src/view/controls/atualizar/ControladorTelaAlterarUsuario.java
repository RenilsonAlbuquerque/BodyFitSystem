package view.controls.atualizar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import beans.Administrador;
import beans.Aluno;
import beans.PerfisEnum;
import beans.Professor;
import beans.TurnoEnum;
import beans.Usuario;
import control.Fachada;
import exceptions.NegocioException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.controls.Principal;
import view.controls.cadastro.ControladorTelaCadastroTreino;
import view.controls.login.ControladorTelaLogin;


public class ControladorTelaAlterarUsuario extends FlowPane{
	
	private Usuario usuario;
	
	private Stage telaAuxiliar;
	
	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCPF;
	
	@FXML
	private TextField txtSenha;
	
	@FXML
	private TextField txtConfirmaSenha;
	
	@FXML
	private Button botaoAluno;
	
	@FXML
	private Button botaoProfessor;
	
	@FXML
	private Button botaoAdministrador;
	
	@FXML
	private ListView listaPerfis;
	
	@FXML
	private Button btnCadastrar;
	
	public ControladorTelaAlterarUsuario(Usuario usuario,ArrayList perfis){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaCadastroTreino.class.getClass().getResource("/view/fxmls/cadastro/TelaCadastroUsuario.fxml"));
			loader.setController(this);
			
			this.getChildren().add(loader.load());
			
			this.listaPerfis.setCellFactory(new Callback<ListView<Usuario>, CelulaListaUsuario>() {

				@Override
				public CelulaListaUsuario call(ListView<Usuario> arg0) {
					return new CelulaListaUsuario();
				}
			});
			
			telaAuxiliar = new Stage();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		this.btnCadastrar.setText("Atualizar");
		this.usuario = usuario;
		this.txtNome.setText(usuario.getNome());
		this.txtCPF.setText(usuario.getCpf());
		this.txtCPF.setEditable(false);
		this.txtSenha.setText(usuario.getSenha());
		this.txtConfirmaSenha.setText(usuario.getSenha());
		
		this.listaPerfis.getItems().addAll(perfis);
		this.listaPerfis.refresh();
	}
	
	@FXML
	private void acaoBtAluno(){
		boolean r = false;
		for (Object o : this.listaPerfis.getItems()) {
			if (o instanceof Aluno) {
				r = true;
			}
		}
		if (r == false) {
		GridPane g = new GridPane();
		g.setHgap(10);
		g.setVgap(10);
		g.setPadding(new Insets(0, 10, 0, 10));
		
		TextField idade = new TextField();
		TextField altura = new TextField();
		TextField peso = new TextField();
		
		ComboBox cb = new ComboBox();
		try {
			cb.getItems().addAll(Fachada.getInstance().listarProfessores());
		} catch (NegocioException e1) {
			cb.getItems().addAll(e1.getMessage());
		}
		finally{
			cb.getSelectionModel().select(0);
		}
		
		Button confirma = new Button("Adcionar");
		confirma.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        listaPerfis.getItems().add(new Aluno("",
		        		((Professor) cb.getSelectionModel().getSelectedItem()).getCpf(),
		        		Integer.parseInt(idade.getText()),
		        		Float.parseFloat(altura.getText()),
		        		Float.parseFloat(peso.getText())));
		        telaAuxiliar.close();
		    }
		});
		
		g.add(new Label("Idade:"),0, 0);
		g.add(idade, 1, 0);
		
		g.add(new Label("Altura:"),0, 1);
		g.add(altura, 1,1);
		
		g.add(new Label("Peso:"),0,2 );
		g.add(peso, 1, 2);
		
		g.add(new Label("Professor:"),0,3 );
		g.add(cb, 1, 3);
		
		g.add(confirma,1, 4);
		
		
		
		telaAuxiliar.setScene(new Scene(g));
		telaAuxiliar.getScene().getStylesheets().add("/view/style/stylesheet.css");
		this.telaAuxiliar.show();
		}
	}
	
	@FXML
	private void acaoBtProfessor(){
		boolean r = false;
		for (Object o : this.listaPerfis.getItems()) {
			if (o instanceof Professor) {
				r = true;
			}
		}
		if (r == false) {
			GridPane g = new GridPane();
			g.setHgap(10);
			g.setVgap(10);
			g.setPadding(new Insets(0, 10, 0, 10));

			TextField cref = new TextField();
			ComboBox turno = new ComboBox();
			turno.getItems().addAll(TurnoEnum.manha, TurnoEnum.tarde, TurnoEnum.noite);
			turno.getSelectionModel().select(0);
			CheckBox coordenador = new CheckBox();

			Button confirma = new Button("Adcionar");
			confirma.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					TurnoEnum t = (TurnoEnum) turno.getSelectionModel().getSelectedItem();
					boolean coor = coordenador.isPressed();
					listaPerfis.getItems().add(new Professor("", cref.getText(), t.toString(), coor));
					telaAuxiliar.close();
				}
			});

			g.add(new Label("CREF:"), 0, 0);
			g.add(cref, 1, 0);

			g.add(new Label("Turno:"), 0, 1);
			g.add(turno, 1, 1);

			g.add(new Label("Coordenador:"), 0, 2);
			g.add(coordenador, 1, 2);

			g.add(confirma, 1, 3);

			telaAuxiliar.setScene(new Scene(g));
			telaAuxiliar.getScene().getStylesheets().add("/view/style/stylesheet.css");
			this.telaAuxiliar.show();
		}
	}
	
	@FXML
	private void acaoBtAdministrador(){
		boolean r = false;
		for (Object o : this.listaPerfis.getItems()) {
			if (o instanceof Administrador) {
				r = true;
			}
		}
		if (r == false) {
		GridPane g = new GridPane();
		g.setHgap(10);
		g.setVgap(10);
		g.setPadding(new Insets(0, 10, 0, 10));
		
		TextField cargo = new TextField();
		
		
		Button confirma = new Button("Adcionar");
		confirma.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		        listaPerfis.getItems().add(new Administrador("",cargo.getText()));
		        telaAuxiliar.close();
		    }
		});
		
		g.add(new Label("Cargo:"),0, 0);
		g.add(cargo, 1, 0);
		
		g.add(confirma,1, 1);
		
		
		
		telaAuxiliar.setScene(new Scene(g));
		telaAuxiliar.getScene().getStylesheets().add("/view/style/stylesheet.css");
		this.telaAuxiliar.show();
		}
	}
	
	
	
	@FXML
	private void acaoBtCadastrar(){
		Alert dialogo = new Alert(Alert.AlertType.NONE);
		DialogPane d = dialogo.getDialogPane();
		d.getStylesheets().add(
				   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
				d.getStyleClass().add("dialog-pane");
		
		dialogo.setHeaderText(null);
		dialogo.show();
		try{
			this.alterar();
			dialogo.setContentText("Alterado!");
		}
		catch(NegocioException e){
			dialogo.setAlertType(Alert.AlertType.ERROR);
			dialogo.setContentText(e.getMessage());
			Optional<ButtonType> result = dialogo.showAndWait();
			if(result.get() == ButtonType.OK){
				dialogo.close();
			}
			else{
				dialogo.close();
			}
		}
		
	}
	private void alterar() throws  NegocioException{
		if (!this.listaPerfis.getItems().isEmpty()) {
			ArrayList<Usuario> perfis = new ArrayList<Usuario>(); 

			for (Object o : this.listaPerfis.getItems()) {
				if (o instanceof Aluno) {
					perfis.add(new Aluno(this.txtCPF.getText(),((Aluno)o).getCpfProfessor(),
								this.txtNome.getText(), this.txtSenha.getText(), "",new ArrayList<PerfisEnum>(),
								((Aluno)o).getIdade(),
								((Aluno)o).getPeso(),
								((Aluno)o).getAltura())
					);
					
					continue;
				}
				
				if (o instanceof Professor) {
				
					perfis.add(new Professor(this.txtCPF.getText(), this.txtNome.getText(), this.txtSenha.getText(), "",new ArrayList<PerfisEnum>(),
								((Professor)o).getCref(),
								((Professor)o).getTurno(),
								((Professor)o).isCoordenador()));
					continue;
				}
				if (o instanceof Administrador) {
					
					perfis.add(new Administrador(this.txtCPF.getText(), this.txtNome.getText(), this.txtSenha.getText(), "",new ArrayList<PerfisEnum>(),
								((Administrador)o).getCargo()));
					continue;
				}
				
			}	
			Fachada.getInstance().alterarUsuario(perfis);
			
		}
		else{
			throw new NegocioException("você precisa adicionar ao menos um perfil de usuário");
		}
	}
	
	class CelulaListaUsuario extends ListCell<Usuario>{
		
		@Override
		protected void updateItem(Usuario objeto,boolean empty){
			super.updateItem(objeto,empty);
			
			if(empty){
				setGraphic(null);
			}else{
	            GridPane painel = new GridPane();
	            painel.getColumnConstraints().add(new ColumnConstraints(200));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));
	            painel.getColumnConstraints().add(new ColumnConstraints(40));

	            Label nomeTipo = new Label();
	            if(objeto instanceof Aluno)
	            	nomeTipo.setText("Aluno");
	            if(objeto instanceof Professor)
	            	nomeTipo.setText("Professor");
	            if(objeto instanceof Administrador)
	            	nomeTipo.setText("Administrador");
	            nomeTipo.getStyleClass().add("cache-list-icon");
	            painel.add(nomeTipo,0,0);          

	            
	            Button visualizar = new Button("V");
	            visualizar.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	            		
	            		if(listaPerfis.getSelectionModel().getSelectedIndex() != CelulaListaUsuario.this.getIndex())
	            			listaPerfis.getSelectionModel().select(CelulaListaUsuario.this.getIndex());
	            		
	            		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
	        			DialogPane d = dialogo.getDialogPane();
	        			d.getStylesheets().add(
	        					   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
	        					d.getStyleClass().add("dialog-pane");
	        			dialogo.setContentText(
	        					"Um usuário está aqui");
	        			dialogo.setHeaderText(null);
	        			dialogo.show();
	            	}
	            });
	            painel.add(visualizar,1,0);
	            
	            Button remover = new Button("X");
	            remover.setOnAction(new EventHandler<ActionEvent>(){
	            	@Override
	            	public void handle(ActionEvent e){
	        
	            		if(listaPerfis.getSelectionModel().getSelectedIndex() != CelulaListaUsuario.this.getIndex())
	            			listaPerfis.getSelectionModel().select(CelulaListaUsuario.this.getIndex());
	            		listaPerfis.getItems().remove(CelulaListaUsuario.this.getIndex());
	            	}
	            });
	            painel.add(remover,2,0);
	            
	            setGraphic(painel);
			}
			
		}
	}

}
