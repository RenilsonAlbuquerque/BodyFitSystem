package view.controls.cadastro;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import beans.Administrador;
import beans.Aluno;
import beans.PerfisEnum;
import beans.Professor;
import beans.TurnoEnum;
import beans.Usuario;
import control.Fachada;
import data.FTPConnectionFactory;
import exceptions.ConexaoFTPException;
import exceptions.NegocioException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControladorTelaCadastroUsuario extends FlowPane{
	
	private File foto;
	
	private Stage telaAuxiliar;
	private GridPane painelAluno;
	private GridPane painelProfessor;
	
	@FXML
	private Circle fotoPerfil;
	
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
	
	public ControladorTelaCadastroUsuario(){
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
			
			this.fotoPerfil.setFill(new ImagePattern(new Image("/imagens/Default User.png")));
			telaAuxiliar = new Stage();
			
		}catch(IOException e){
			e.printStackTrace();
		}
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

			Button confirma = new Button("Adicionar");
			confirma.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					TurnoEnum t = (TurnoEnum) turno.getSelectionModel().getSelectedItem();
					boolean coor = coordenador.isSelected();
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
		
		Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
		DialogPane d = dialogo.getDialogPane();
		d.getStylesheets().add(
				   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
				d.getStyleClass().add("dialog-pane");
		
		dialogo.setHeaderText(null);
		try{
			this.cadastrar();
			dialogo.setContentText("Cadastrado!");
			dialogo.show();
		}
		catch(NegocioException e){
			dialogo.setAlertType(Alert.AlertType.ERROR);
			dialogo.setContentText(e.getMessage());
			dialogo.show();
		}
		
	}
	@FXML
	private void acaoFotoPerfil(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Escolha a foto");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
		this.foto =  fileChooser.showOpenDialog(new Stage());
		if(foto != null){
			 BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(foto);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				this.fotoPerfil.setFill(new ImagePattern(image
					)
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
             
		}
	}

	private void cadastrar() throws NegocioException {
		if (!this.listaPerfis.getItems().isEmpty()) {
			if (this.txtSenha.getText().equals(this.txtConfirmaSenha.getText())) {
				ArrayList<Usuario> perfis = new ArrayList<Usuario>();
				String caminhoFoto;
				if (this.foto == null) {
					caminhoFoto = "Default User.png";
				} else
					caminhoFoto = txtCPF.getText();

				for (Object o : this.listaPerfis.getItems()) {
					if (o instanceof Aluno) {
						perfis.add(
								new Aluno(this.txtCPF.getText(), ((Aluno) o).getCpfProfessor(), this.txtNome.getText(),
										this.txtSenha.getText(), caminhoFoto, new ArrayList<PerfisEnum>(),
										((Aluno) o).getIdade(), ((Aluno) o).getPeso(), ((Aluno) o).getAltura()));

						continue;
					}

					if (o instanceof Professor) {

						perfis.add(new Professor(this.txtCPF.getText(), this.txtNome.getText(), this.txtSenha.getText(),
								caminhoFoto, new ArrayList<PerfisEnum>(), ((Professor) o).getCref(),
								((Professor) o).getTurno(), ((Professor) o).isCoordenador()));
						continue;
					}
					if (o instanceof Administrador) {

						perfis.add(new Administrador(this.txtCPF.getText(), this.txtNome.getText(),
								this.txtSenha.getText(), caminhoFoto, new ArrayList<PerfisEnum>(),
								((Administrador) o).getCargo()));
						continue;
					}

				}

				Fachada.getInstance().cadastrarUsuario(perfis);
				try {
					if (foto != null)
						FTPConnectionFactory.getInstance().saveImage(this.foto, this.txtCPF.getText());
				} catch (ConexaoFTPException e) {

					e.printStackTrace();
				}
			} else
				throw new NegocioException("As senhas não conferem");
		} else
			throw new NegocioException("você precisa adicionar ao menos um perfil de usuário");

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

	            /*
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
	            */
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
