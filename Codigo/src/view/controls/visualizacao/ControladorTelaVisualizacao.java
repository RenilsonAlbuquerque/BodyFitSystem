package view.controls.visualizacao;

import java.io.IOException;

import beans.Administrador;
import beans.Aluno;
import beans.Professor;
import beans.Usuario;
import control.Fachada;
import data.FTPConnectionFactory;
import exceptions.ConexaoFTPException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import view.controls.login.ControladorTelaLogin;

public class ControladorTelaVisualizacao extends VBox{
	
	
	@FXML
	private Circle fotoPerfil;
	
	@FXML
	private Label labelNome;
	
	@FXML
	private Label labelCPF;
	
	@FXML
	private Label labelNome1;
	
	@FXML
	private Label labelNome2;
	
	@FXML
	private Label labelNome3;
	
	@FXML
	private Label labelValor1;
	
	@FXML
	private Label labelValor2;
	
	@FXML
	private Label labelValor3;
	
	public ControladorTelaVisualizacao(Usuario usuario){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/visualizacao/TelaVisualizacaoUsuario.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			adcionarUsuario(usuario);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void adcionarUsuario(Usuario usuario){
		try {
			this.fotoPerfil.setFill(new ImagePattern(new Image(FTPConnectionFactory.getInstance().retrieveImage(usuario.getCaminhoFoto()))));
			//this.labelFoto.setGraphic(new ImageView(new Image(FTPConnectionFactory.getInstance().retrieveImage(usuario.getCaminhoFoto()))));
			this.labelCPF.setText(usuario.getCpf());
			this.labelNome.setText(usuario.getNome());
			if(usuario instanceof Aluno){
				this.labelNome1.setText("Idade");
				this.labelValor1.setText(String.valueOf(((Aluno)usuario).getIdade()));
				
				this.labelNome2.setText("Altura");
				this.labelValor2.setText(String.valueOf(((Aluno)usuario).getAltura()));
				
				this.labelNome3.setText("Peso");
				this.labelValor3.setText(String.valueOf(((Aluno)usuario).getPeso()));
			}
			if(usuario instanceof Professor){	
				this.labelNome1.setText("CREF");
				this.labelValor1.setText( ((Professor) usuario).getCref() );
				
				this.labelNome2.setText("Turno");
				this.labelValor2.setText( ((Professor) usuario).getTurno() );
				
			}
			if(usuario instanceof Administrador){
				this.labelNome1.setText("Cargo");
				this.labelValor1.setText(((Administrador) usuario).getCargo());
			}
		} catch (ConexaoFTPException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
