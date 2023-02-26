package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.funcoesauxiliares.ComboBoxBuilder;
import gui.funcoesauxiliares.FormatComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class TabReactorController implements Initializable {

	@FXML
	public TextField lenghtTxt = new TextField();

	@FXML
	public TextField diameterTxt = new TextField();

	@FXML
	public TextField porosityTxt = new TextField();
	
	@FXML
	public TextField catalystDensityTxt = new TextField();
	
	@FXML
	public ComboBox<Image> comboBoxComprimentoR = new ComboBox<Image>();

	@FXML
	public ComboBox<Image> comboBoxDiametroR = new ComboBox<Image>();

	FormatComboBox formatComboBox = new FormatComboBox();

	ComboBoxBuilder cbBuilder = new ComboBoxBuilder();



	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		comboBoxConstruct();

	}

	private void comboBoxConstruct() {

		formatComboBox.updateComboBox(comboBoxComprimentoR, cbBuilder.updateCBComprimento(), true);
		formatComboBox.updateComboBox(comboBoxDiametroR, cbBuilder.updateCBComprimento(), true);

		comboBoxComprimentoR.getStyleClass().add("center-aligned");
		comboBoxDiametroR.getStyleClass().add("center-aligned");

		comboBoxComprimentoR.getSelectionModel().select(0);
		comboBoxDiametroR.getSelectionModel().select(0);

	}
	


}
