package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import sample.conf.Const;
import sample.Main;

public class SettingsController implements Initializable  {

	/* General */
	
	@FXML
	private Slider stageOpacity;
	
	@FXML
	private CheckBox alwaysOnTop;
	
	@FXML
	private RadioButton winTheme, macTheme;

	//@FXML
	//private JFXTextField showHideActlistHotKey;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
			stageOpacity.setValue(1 * 100);
			alwaysOnTop.setSelected(Main.getConf().isAlwaysOnTop());
			
			String theme = Main.getConf().getTheme();
			switch (theme) {
			case Const.WIN:
				winTheme.setSelected(true);
				break;
			case Const.MAC:
				macTheme.setSelected(true);
				break;
			}
			
			stageOpacity.valueProperty().addListener((observable, oldValue, newValue) -> {
				Main.getStage().setOpacity(newValue.doubleValue() / 100);
			});
			stageOpacity.setOnMouseReleased(mouseEvent -> {
				try {
					Main.getConf().setOpacity(stageOpacity.getValue() / 100);
				} catch (Exception e) {
					
				}
			});
			stageOpacity.setOnKeyReleased(keyEvent -> {
				try {
					Main.getConf().setOpacity(stageOpacity.getValue() / 100);
				} catch (Exception e) {
					
				}
			});

	}
	
	
	
	@FXML
	private void alwaysOnTop() throws Exception {
		Main.getConf().setAlwaysOnTop(alwaysOnTop.selectedProperty().get());
		Main.getStage().setAlwaysOnTop(alwaysOnTop.selectedProperty().get());
	}

	@FXML
	private void theme() throws Exception {
		if (winTheme.selectedProperty().get()) {
			Main.getConf().setTheme(Const.WIN);
		} else if (macTheme.selectedProperty().get()) {
			Main.getConf().setTheme(Const.MAC);
		}
		Main.getStage().fireEvent(new Event(getClass(),Main.getStage(),Const.EVENT_APPLY_THEME));
	}
        
}
