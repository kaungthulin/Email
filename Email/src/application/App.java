package application;
	
import javax.naming.OperationNotSupportedException;

import application.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) throws OperationNotSupportedException {
		
		ViewFactory viewFactory = ViewFactory.defaultfactory;
		Scene scene = viewFactory.getMainScene();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}