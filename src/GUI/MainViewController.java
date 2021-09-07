package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GUI.util.Alerts;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView2("/GUI/DepartmentList.fxml");

	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/GUI/About.fxml");
		
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	//SYNCRONIZED PARA NÃO SER INTERROMPIDA NA HORA DA EXECUÇÃO
	private synchronized void loadView(String absoluteName) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene();
		VBox mainVBox =(VBox)((ScrollPane)mainScene.getRoot()).getContent();
		
		//COLOCAR O ABOUT DENTRO DOS FILHOS(CHILDREN'S DA MAIN SCENE) PRESERVANDO O MENU
		Node mainMenu = mainVBox.getChildren().get(0); //PEGANDO O MENU, PRIMEIRO FILHO DO VBOX(MAIN MENU)
		mainVBox.getChildren().clear();//LIMPANDO TODOS OS FILHOS
		mainVBox.getChildren().add(mainMenu);//PEGANDO O MENU E ADICIONANDO-O NOVAMENTE
		mainVBox.getChildren().addAll(newVBox.getChildren());//ADICIONANDO OS NOVOS ITENS(ABOUT)
		
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exeption", "Erro ao carregar a pagina" , e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene();
		VBox mainVBox =(VBox)((ScrollPane)mainScene.getRoot()).getContent();
		
		//COLOCAR O ABOUT DENTRO DOS FILHOS(CHILDREN'S DA MAIN SCENE) PRESERVANDO O MENU
		Node mainMenu = mainVBox.getChildren().get(0); //PEGANDO O MENU, PRIMEIRO FILHO DO VBOX(MAIN MENU)
		mainVBox.getChildren().clear();//LIMPANDO TODOS OS FILHOS
		mainVBox.getChildren().add(mainMenu);//PEGANDO O MENU E ADICIONANDO-O NOVAMENTE
		mainVBox.getChildren().addAll(newVBox.getChildren());//ADICIONANDO OS NOVOS ITENS(ABOUT)
		
		DepartmentListController controller = loader.getController();
		controller.setDepartmentService(new DepartmentService());
		controller.updateTableView();
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exeption", "Erro ao carregar a pagina" , e.getMessage(), AlertType.ERROR);
		}
	}

}
