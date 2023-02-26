package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.funcoesauxiliares.ComboBoxBuilder;
import gui.funcoesauxiliares.FormatComboBox;
import gui.funcoesauxiliares.UpdateComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public class TabInletController implements Initializable {

	@FXML
	public TextField CH4Flow = new TextField();

	@FXML
	public TextField CO2Flow = new TextField();
	
	@FXML
	public TextField pressure = new TextField();

	@FXML
	public ComboBox<Image> comboBoxVazaoCH4 = new ComboBox<Image>();

	@FXML
	public ComboBox<Image> comboBoxVazaoCO2 = new ComboBox<Image>();
	
	@FXML
	public ComboBox<Text> comboBoxPressure = new ComboBox<Text>();

	FormatComboBox formatComboBox = new FormatComboBox();

	ComboBoxBuilder cbBuilder = new ComboBoxBuilder();
	
	UpdateComboBox updateComboBox = new UpdateComboBox();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		comboBoxConstruct();

		
		
	}

	private void comboBoxConstruct() {
		
		
		formatComboBox.updateComboBox(comboBoxVazaoCH4, cbBuilder.updateCBVazaoVol(), true);
		formatComboBox.updateComboBox(comboBoxVazaoCO2, cbBuilder.updateCBVazaoVol(), true);
		updateComboBox.updateComboBox(comboBoxPressure, cbBuilder.updateCBPressure(), true);
		
		comboBoxVazaoCH4.getSelectionModel().select(0);
		comboBoxVazaoCO2.getSelectionModel().select(0);
		comboBoxPressure.getSelectionModel().select(0);

		comboBoxVazaoCH4.getStyleClass().add("center-aligned");
		comboBoxVazaoCO2.getStyleClass().add("center-aligned");
		comboBoxPressure.getStyleClass().add("center-aligned");
	}

}
