package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.controller.services.EmailSenderService;
import application.model.EmailConstants;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class ComposeMessageController extends AbstractController implements Initializable {

		@FXML
		List<File> attachments = new ArrayList<File>();
	
	 	@FXML
	    private ChoiceBox<String> senderChoice;

	    @FXML
	    private TextField recipientField;

	    @FXML
	    private TextField subjectField;

	    @FXML
	    private Label attachmentsLabel;

	    @FXML
	    private Label errorLabel;
	    
	    @FXML
	    private HTMLEditor composeArea;

	    @FXML
	    private Button sendBtnAction;

	    @FXML
	    void attachBtnAction() {
	    	FileChooser fileChooser = new FileChooser();
	    	File selectedFile = fileChooser.showOpenDialog(null);
	    	if(selectedFile != null) {
	    		attachments.add(selectedFile);
	    		attachmentsLabel.setText(attachmentsLabel.getText() + selectedFile.getName() + ";");
	    	}
	    }
	    
	    @FXML
	    void sendBtnAction() {
	    	errorLabel.setText("");
	    	EmailSenderService emailSenderService = 
	    			new EmailSenderService(getModelAccess().getEmailAccountsByName(senderChoice.getValue()),//resolved
	    			subjectField.getText(), 
	    			recipientField.getText(), 
	    			composeArea.getHtmlText(), 
	    			attachments);
	    	emailSenderService.restart();
	    	emailSenderService.setOnSucceeded(e -> {
	    		if(emailSenderService.getValue() == EmailConstants.EMAIL_SENT_OK) {
	    			errorLabel.setText("message sent successfully");
	    		} else {
	    			errorLabel.setText("message sending error!!!");
	    		}
	    	});
	    }
	
	public ComposeMessageController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		senderChoice.setItems(getModelAccess().getEmailAccountsNames());
		senderChoice.setValue(getModelAccess().getEmailAccountsNames().get(0));
	}

}
