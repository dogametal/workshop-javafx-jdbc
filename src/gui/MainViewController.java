package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	private Button btlOK;
	
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		//loadView("/gui/DepartmentList.fxml");
		//Manual dependencies
		//Insert parameter lambda expression as Consumer 
		loadView ("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
		//Here insert data MOK on table
		controller.setDepartmentService (new DepartmentService());
		//transform List and observableList
		controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		//Lambda expression to run nothing or empty
		loadView("/gui/About.fxml", x ->{});
	}
		
	@FXML
	public void onButtonOKAction() {
		Alerts.showAlert("JavaFX","ButtonOK", null, AlertType.INFORMATION);
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {

		
	}
	
	//Function to open screen
	//Add function Consumer to received lambda expression to run screen currently
	private synchronized <T> void loadView (String absoluteName, Consumer<T> initializingAction) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));		
			VBox newVBox = loader.load();
			//Function to add screnn inside form main
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Run expression received as parameters this is one way to run tableView good
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	

	
}
