package application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Folder;

import application.model.EmailAccountBean;
import application.model.EmailMessageBean;
import application.model.folder.EmailFolderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelAccess {
	
	Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
	ObservableList<String> emailAccountsNames = FXCollections.observableArrayList();
	
	public ObservableList<String> getEmailAccountsNames() {
		return emailAccountsNames;
	}
	
	public EmailAccountBean getEmailAccountsByName(String name) {
		return emailAccounts.get(name);
	}
	
	public void addAccount(EmailAccountBean account) {
		emailAccounts.put(account.getEmailAddress(), account);
		emailAccountsNames.add(account.getEmailAddress());
	}
	
	private EmailMessageBean selectedMessage;
	
	public EmailMessageBean getSelectedMessage() {
		return selectedMessage;
	}
	
	public void setSelectedMessage(EmailMessageBean selectedMessage) {
		this.selectedMessage = selectedMessage;
	}
	
	private EmailFolderBean<String> selectedFolder;
	
	public EmailFolderBean<String> getSelectedFolder() {
		return selectedFolder;
	}
	
	public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}
	
	private List<Folder> foldersList = new ArrayList<Folder>();
	
	public List<Folder> getFoldersList() {
		return foldersList;
	}
	
	public void addFolder(Folder folder) {
		foldersList.add(folder);
	}

}
