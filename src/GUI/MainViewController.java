package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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

public class MainViewController implements Initializable {

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
		loadView("/GUI/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});

	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/GUI/About.fxml", x -> {
		});

	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	// SYNCRONIZED PARA NÃO SER INTERROMPIDA NA HORA DA EXECUÇÃO
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			// COLOCAR O ABOUT DENTRO DOS FILHOS(CHILDREN'S DA MAIN SCENE) PRESERVANDO O
			// MENU
			Node mainMenu = mainVBox.getChildren().get(0); // PEGANDO O MENU, PRIMEIRO FILHO DO VBOX(MAIN MENU)
			mainVBox.getChildren().clear();// LIMPANDO TODOS OS FILHOS
			mainVBox.getChildren().add(mainMenu);// PEGANDO O MENU E ADICIONANDO-O NOVAMENTE
			mainVBox.getChildren().addAll(newVBox.getChildren());// ADICIONANDO OS NOVOS ITENS(ABOUT)
			
			T controller = loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			Alerts.showAlert("IO Exeption", "Erro ao carregar a pagina", e.getMessage(), AlertType.ERROR);
		}
	}

}
