package application.controller.services;

import java.util.List;

import javax.mail.Folder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdaterService extends Service<Void> {
	
	private List<Folder> foldersList;
	
	public FolderUpdaterService(List<Folder> foldersList) {
		this.foldersList = foldersList;
	}
	
	@Override
	public Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			public Void call() throws Exception {
				for(;;) {
						try {
							Thread.sleep(10000);
							if (FetchFoldersService.noServicesActive()) {
								System.out.println("CHECKING FOR EMAILS!!!");
								for (Folder folder : foldersList) {
									if (folder.getType() != folder.HOLDS_FOLDERS && folder.isOpen()) {
										folder.getMessageCount();
									}
								} 
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
				}
				
				
				
			}
		};
	}

}
