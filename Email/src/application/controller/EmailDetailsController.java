package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import application.model.EmailMessageBean;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class EmailDetailsController extends AbstractController implements Initializable{

	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}
	
    @FXML
    private WebView webView;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;
    
    @FXML
    void illegalOperationAction() throws OperationNotSupportedException {
    	ViewFactory view = new ViewFactory();
    	Scene mainScene = view.getMainScene();
    	Stage stage = new Stage();
    	stage.setScene(mainScene);
    	stage.show();
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EmailMessageBean message = getModelAccess().getSelectedMessage();
		
		subjectLabel.setText("Subject : " + message.getSubject());
		senderLabel.setText("Sender : " + message.getSender());
//		webView.getEngine().loadContent(message.getContent());
		
	}

}
