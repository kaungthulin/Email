package application.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.controller.services.CreateAndRegisterEmailAccountService;
import application.controller.services.FolderUpdaterService;
import application.controller.services.MessageRendererService;
import application.controller.services.SaveAttachmentsService;
import application.model.EmailMessageBean;
import application.model.folder.EmailFolderBean;
import application.model.table.BoldableRowFactory;
import application.model.table.FormatableInteger;
import application.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController extends AbstractController implements Initializable {
	
	public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}
	
	@FXML
	TreeView<String> emailFoldersTreeView;
	
	@FXML
	MenuItem showDetails = new MenuItem("show details");
	
    @FXML
    private Label downAttachLabel;
    
    @FXML
    private ProgressBar downAttachProgress;
    
    private SaveAttachmentsService saveAttachmentsService;
	
	@FXML
	private TableView<EmailMessageBean> emailTableView;
	
	@FXML
	private TableColumn<EmailMessageBean, String> senderCol;
	
	@FXML
	private TableColumn<EmailMessageBean, String> subjectCol;
	
	@FXML
	private TableColumn<EmailMessageBean, FormatableInteger> sizeCol;
	
	@FXML
	private TableColumn<EmailMessageBean, Date> dateCol;
	
	@FXML
	private WebView messageRenderer;
	
	@FXML
    private Button Button1, downAttachBtn;
	private MessageRendererService messageRendererService;

    @FXML
    void Button1Action(ActionEvent event) {
    	Scene scene = ViewFactory.defaultfactory.getComposeMessageScene();
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void downAttachBtnAction(ActionEvent event) {
    	EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
    	if(message != null && message.hasAttachments()) {
    		saveAttachmentsService.setMessageToDownload(message);
    		saveAttachmentsService.restart();
    	}
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		downAttachProgress.setVisible(false);
		downAttachLabel.setVisible(false);
		
		saveAttachmentsService = new SaveAttachmentsService(downAttachProgress, downAttachLabel);
		
		downAttachProgress.progressProperty().bind(saveAttachmentsService.progressProperty());
		
		messageRendererService = new MessageRendererService(messageRenderer);
		
		FolderUpdaterService folderUpdaterService = new FolderUpdaterService(getModelAccess().getFoldersList());
		folderUpdaterService.start();
		
		emailTableView.setRowFactory(e -> new BoldableRowFactory<>());
		
		ViewFactory viewFactory = ViewFactory.defaultfactory;
		
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, FormatableInteger>("size"));
		dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, Date>("date"));
		
//		BUG: size doesn't get it's default comparator overriden, so we do it by hand!!!
		sizeCol.setComparator(new FormatableInteger(0));
		
		EmailFolderBean<String> root = new EmailFolderBean<>("");
		emailFoldersTreeView.setRoot(root);
		emailFoldersTreeView.setShowRoot(false);
		
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService1 = new CreateAndRegisterEmailAccountService("kaungthulin0@gmail.com", 
				"kaungthulin0#*", 
				root, 
				getModelAccess());
		createAndRegisterEmailAccountService1.start();
		
		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService2 = new CreateAndRegisterEmailAccountService("hackerboy2211996@gmail.com", 
				"Security#code", 
				root, 
				getModelAccess());
		createAndRegisterEmailAccountService2.start();
		
		emailTableView.setContextMenu(new ContextMenu(showDetails));
		
		emailFoldersTreeView.setOnMouseClicked(e -> {
			EmailFolderBean<String> item = (EmailFolderBean<String>)emailFoldersTreeView.getSelectionModel().getSelectedItem();
			if((item != null) && (!item.isTopElement())) {
				emailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				//clear the message:
				getModelAccess().setSelectedMessage(null);
			}
		});
		
		emailTableView.setOnMouseClicked(e -> {
			EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
			if(message != null) {
				getModelAccess().setSelectedMessage(message);
				messageRendererService.setMessageToRender(message);
				messageRendererService.restart();
//				Platform.runLater(messageRendererService);
			}
		});
		
		showDetails.setOnAction(e -> {
			Scene scene = viewFactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		
	}

}
