package application.view;

import javax.naming.OperationNotSupportedException;

import application.controller.AbstractController;
import application.controller.ComposeMessageController;
import application.controller.EmailDetailsController;
import application.controller.MainController;
import application.controller.ModelAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewFactory {
	
	public static ViewFactory defaultfactory = new ViewFactory();
	public static boolean mainViewInitialized = false;
	
	private final String DEFAULT_CSS = "style.css";
	private final String MAIN_SCREEN_FXML = "MainLayout.fxml";
	private final String COMPOSE_SCREEN_FXML = "ComposeMessageLayout.fxml";
	private final String EMAIL_DETAILS_FXML = "EmailDetailsLayout.fxml";
	
	private ModelAccess modelAccess = new ModelAccess();
	
	private MainController mainController;
	private EmailDetailsController emaillDetailsController;
	
	public Scene getMainScene() throws OperationNotSupportedException {
		
		if(!mainViewInitialized) {
			mainController = new MainController(modelAccess);
			mainViewInitialized = true;
			return initializeScene(MAIN_SCREEN_FXML, mainController);
		} else {
			throw new OperationNotSupportedException("Main Scene already initialized");
		}
		
	}
	
	public Scene getEmailDetailsScene() {
		
		emaillDetailsController = new EmailDetailsController(modelAccess);
		return initializeScene(EMAIL_DETAILS_FXML, emaillDetailsController);
		
	}
	
	public Scene getComposeMessageScene() {
		AbstractController composeMessageController = new ComposeMessageController(modelAccess);
		return initializeScene(COMPOSE_SCREEN_FXML, composeMessageController);
	}
	
	public Node resolveIcon(String treeItemValue) {
		
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
		try {
			if(lowerCaseTreeItemValue.contains("inbox")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/inbox.png")));
			} else if(lowerCaseTreeItemValue.contains("sent")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/sent2.png")));
			} else if(lowerCaseTreeItemValue.contains("spam")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/spam.png")));
			} else if(lowerCaseTreeItemValue.contains("@")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/email.png")));
			}
			else {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("images/folder.png")));
			}
		} catch (NullPointerException e) {
			System.out.println("Invalid image location!!!");
			e.printStackTrace();
			returnIcon = new ImageView();
		}
		
		returnIcon.setFitHeight(16);
		returnIcon.setFitWidth(16);
		
		return returnIcon;
		
	}
	
	private Scene initializeScene(String fxmlPath, AbstractController controller) {
		
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(controller);
			parent = loader.load();	
		}catch (Exception e) {
			return null;
		}
		
		scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
		return scene;
	}

}
