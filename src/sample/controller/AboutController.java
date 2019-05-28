package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

	@FXML
	private Label version, runtime;
	
	@FXML
	private HBox newVersionBox;
	
	@FXML
	private Hyperlink sourceCodeLink, licenseLink, courseLink;
	
	@FXML
	private void browseSourceCode() {
		browse(sourceCodeLink, "https://github.com/johnMinelli/Algareedy");
	}
	
	@FXML
	private void browseLicense() {
		browse(licenseLink, "https://github.com/johnMinelli/Algareedy/README.md");
	}
	
	@FXML
	private void browseCourse() {
		browse(courseLink, "http://www.cs.unibo.it/~bertossi/didattica.html");
	}
	
	private void browse(Hyperlink hyperlink, String uri) {
		hyperlink.setVisited(false);
		
		try {
			Desktop.getDesktop().browse(URI.create(uri));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {   
    }



}
