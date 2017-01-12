package view.controls.login;

import java.io.IOException;
import java.util.ArrayList;

import beans.PerfisEnum;
import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import view.controls.Principal;
import view.controls.menu.ControladorMenuPrincipalProfessor;

public class ControladorTelaEscolhaPerfil extends AnchorPane{
	
	@FXML
	private GridPane painel;
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
	
	private String cpf;
		
	public ControladorTelaEscolhaPerfil(String cpf){
		 try {
			 	FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/login/TelaEscolhaPerfil.fxml"));
			 	loader.setController(this);
			 	this.getChildren().add(loader.load());
			 	
			 	painel.getChildren().clear();
			 	
			 	this.cpf = cpf;
			 	
			 	ArrayList<PerfisEnum> perfis = Fachada.getInstance().getPerfis(cpf);
			 	if(perfis.indexOf(PerfisEnum.aluno) > -1)
			 		painel.getChildren().add(botaoAluno);
			 	
			 	if(perfis.indexOf(PerfisEnum.professor) > -1)
			 		painel.getChildren().add(botaoProfessor);
			 	
			 	if(perfis.indexOf(PerfisEnum.coordenador) > -1)
			 		painel.getChildren().add(botaoCoordenador);
			 	
			 	if(perfis.indexOf(PerfisEnum.administrador) > -1)
			 		painel.getChildren().add(botaoAdministrador);
			 	
			 	painel.add(botaoVoltar, 0, 4);
			 	
			} catch (IOException | ConexaoBancoException e) {
				e.printStackTrace();
			}
		 
	}
	
	
	@FXML
	private void acaoBotaoAluno(ActionEvent evt){
		try {
			Fachada.getInstance().setUsuarioLogado(cpf, PerfisEnum.aluno);
			Principal.setCurrentStage(new ControladorMenuPrincipalProfessor(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
		} catch (ConexaoBancoException | CRUDException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	private void acaoBotaoProfessor(ActionEvent evt){
			
		try {
			Fachada.getInstance().setUsuarioLogado(cpf, PerfisEnum.professor);
			Principal.setCurrentStage(new ControladorMenuPrincipalProfessor(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
		} catch (ConexaoBancoException | CRUDException e) {
			
			e.printStackTrace();
		}
		
	
	}
	@FXML
	private void acaoBotaoCoordenador(ActionEvent evt){
		try {
			Fachada.getInstance().setUsuarioLogado(cpf, PerfisEnum.coordenador);
			Principal.setCurrentStage(new ControladorMenuPrincipalProfessor(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
		} catch (ConexaoBancoException | CRUDException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	private void acaoBotaoAdministrador(ActionEvent evt){
		try {
			Fachada.getInstance().setUsuarioLogado(cpf, PerfisEnum.administrador);
			Principal.setCurrentStage(new ControladorMenuPrincipalProfessor(),
					(int)Screen.getPrimary().getVisualBounds().getWidth(),
					(int)Screen.getPrimary().getVisualBounds().getHeight());
		} catch (ConexaoBancoException | CRUDException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	private void acaoBotaoSair(ActionEvent evt){
		
	} 
	
	

}
