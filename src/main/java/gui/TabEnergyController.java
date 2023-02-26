package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.funcoesauxiliares.ComboBoxBuilder;
import gui.funcoesauxiliares.FormatComboBox;
import gui.funcoesauxiliares.ManipulateNode;
import gui.funcoesauxiliares.UpdateComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TabEnergyController implements Initializable {

	VBox energiaVBox = new VBox();

	@FXML
	public TextField inletT = new TextField();

	@FXML
	public TextField extT = new TextField();

	@FXML
	public TextField UTCtxt = new TextField();
	
	@FXML
	VBox UTCVBox = new VBox();
	
	
	@FXML
	VBox espessuraVBox = new VBox();

	@FXML
	VBox materialExternoVBox = new VBox();
	
	

	@FXML
	public CheckBox isotermicoCheckBox = new CheckBox();

	@FXML
	VBox temperaturaExternaVBox = new VBox();

	@FXML
	ComboBox<Image> espessuraComboBox = new ComboBox<Image>();
	
	@FXML
	ComboBox<Image> UTCComboBox = new ComboBox<Image>();

	@FXML
	public ComboBox<Image> temperaturaEntradaComboBox = new ComboBox<Image>();

	@FXML
	public ComboBox<Image> temperaturaExternaComboBox = new ComboBox<Image>();

	FormatComboBox formatComboBox = new FormatComboBox();

	UpdateComboBox updateComboBox = new UpdateComboBox();

	ComboBoxBuilder cbBuilder = new ComboBoxBuilder();
	
	ManipulateNode manipulateNode = new ManipulateNode();

	ImageView balancoEnergiaImageView = new ImageView();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		comboBoxConstruct();
	
	}

	@FXML
	private void checkBoxIsotermicoAction() {

		if (isotermicoCheckBox.isSelected()) {
			temperaturaExternaVBox.setVisible(false);
			materialExternoVBox.setVisible(false);
			espessuraVBox.setVisible(false);
			UTCVBox.setVisible(false);
		} else {
			temperaturaExternaVBox.setVisible(true);
			materialExternoVBox.setVisible(true);
			espessuraVBox.setVisible(true);
			UTCVBox.setVisible(true);
		}

	}

	private void comboBoxConstruct() {

	

		formatComboBox.updateComboBox(temperaturaEntradaComboBox, cbBuilder.updateCBTemperatura(), true);

		formatComboBox.updateComboBox(temperaturaExternaComboBox, cbBuilder.updateCBTemperatura(), true);

		formatComboBox.updateComboBox(UTCComboBox, cbBuilder.updateCBUTC(), true);

		formatComboBox.updateComboBox(espessuraComboBox, cbBuilder.updateCBComprimento(), true);

		temperaturaEntradaComboBox.getSelectionModel().select(0);
		temperaturaExternaComboBox.getSelectionModel().select(0);
		UTCComboBox.getSelectionModel().select(0);

		espessuraComboBox.getSelectionModel().select(0);


		temperaturaEntradaComboBox.getStyleClass().add("center-aligned");
		temperaturaExternaComboBox.getStyleClass().add("center-aligned");
		UTCComboBox.getStyleClass().add("center-aligned");
		espessuraComboBox.getStyleClass().add("center-aligned");
	}

}
