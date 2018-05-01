package application;

import application.model.EmailAccountBean;
import application.model.EmailMessageBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test {
	
	public static void main(String[] args) {
		final EmailAccountBean emailAccountBean = new EmailAccountBean("kaungthulin0@gmail.com", "kaungthulin0#*");
		ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	}

}
