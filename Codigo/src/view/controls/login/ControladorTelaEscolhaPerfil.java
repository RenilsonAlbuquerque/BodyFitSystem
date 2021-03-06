package view.controls.login;

import java.io.IOException;
import java.util.ArrayList;

import beans.PerfisEnum;
import control.Contexto;
import control.Fachada;
import exceptions.NegocioException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import view.controls.Principal;
import view.controls.gerenciamento.ControladorADMGerenciaUsuario;
import view.controls.menu.ControladorMenuPrincipal;
import view.controls.menu.ControladorOpcoesProfessor;
import view.controls.menu.ControladorTelaMenuAluno;

public class ControladorTelaEscolhaPerfil extends VBox{
	
	@FXML
	private VBox painel;
	@FXML
	private Button botaoProfessor;
	@FXML
	private Button botaoAluno;
	@FXML
	private Button botaoAdministrador;
	@FXML
	private Button botaoCoordenador;
	@FXML
	private Button botaoVoltar;
	@FXML
	private HBox painelInferior;
	
	private String cpf;
		
	public ControladorTelaEscolhaPerfil(String cpf){
		 try {
			 	FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/login/TelaEscolhaPerfil.fxml"));
			 	loader.setController(this);
			 	this.getChildren().add(loader.load());
			 	
			 	painel.getChildren().clear();
			 	this.setAlignment(Pos.CENTER);
			 	
			 	this.cpf = cpf;
			 	
			 	ArrayList<PerfisEnum> perfis = Fachada.getInstance().getPerfisUsuario(cpf);
			 	
			 	if(perfis.indexOf(PerfisEnum.aluno) > -1)
			 		painel.getChildren().add(botaoAluno);
			 	
			 	if(perfis.indexOf(PerfisEnum.professor) > -1)
			 		painel.getChildren().add(botaoProfessor);
			 	
			 	if(perfis.indexOf(PerfisEnum.coordenador) > -1)
			 		painel.getChildren().add(botaoCoordenador);
			 	
			 	if(perfis.indexOf(PerfisEnum.administrador) > -1)
			 		painel.getChildren().add(botaoAdministrador);
			 	
			 	
			 	
			 	
			} catch (IOException | NegocioException e) {
				e.printStackTrace();
			}
		 
	}
	
	
	@FXML
	private void acaoBotaoAluno(ActionEvent evt){
		try {
			Contexto.getInstance().setUsuarioLogado(cpf, PerfisEnum.aluno);
			Principal.setCurrentStage(ControladorMenuPrincipal.getInstance(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
			ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaMenuAluno());	
		} catch (NegocioException e) {
			
			e.printStackTrace();
		}
		
	}
	@FXML
	private void acaoBotaoProfessor(ActionEvent evt){
			
		try {
			Contexto.getInstance().setUsuarioLogado(cpf, PerfisEnum.professor);
			Principal.setCurrentStage(ControladorMenuPrincipal.getInstance(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
			ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorOpcoesProfessor());
		} catch (NegocioException e) {
			
			e.printStackTrace();
		}
		
	
	}
	@FXML
	private void acaoBotaoCoordenador(ActionEvent evt){
		try {
			Contexto.getInstance().setUsuarioLogado(cpf, PerfisEnum.coordenador);
			Principal.setCurrentStage(ControladorMenuPrincipal.getInstance(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
			ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorOpcoesProfessor());
		} catch (NegocioException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	private void acaoBotaoAdministrador(ActionEvent evt){
		try {
			
			Contexto.getInstance().setUsuarioLogado(cpf, PerfisEnum.administrador);
			
			Principal.setCurrentStage(ControladorMenuPrincipal.getInstance(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
			ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorADMGerenciaUsuario());
		} catch (NegocioException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	private void acaoBotaoSair(ActionEvent evt){
		Contexto.getInstance().setUsuarioLogado(null);
		Principal.setCurrentStage(new ControladorTelaLogin());
	} 
	
	

}
