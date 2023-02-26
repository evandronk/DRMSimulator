package gui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import gui.funcoesauxiliares.ComboBoxBuilder;
import gui.funcoesauxiliares.ConvertToFxImage;
import gui.funcoesauxiliares.EquacoesBuilder;
import gui.funcoesauxiliares.FormatComboBox;
import gui.funcoesauxiliares.SetImages;
import gui.funcoesauxiliares.UpdateComboBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class TabReactionController implements Initializable {

	SetImages setImages = new SetImages();

	@FXML
	public CheckBox checkBoxR = new CheckBox();

	@FXML
	ImageView reacao1ImageView = new ImageView();

	@FXML
	public ComboBox<Image> comboBoxLeiVelocidade = new ComboBox<Image>();

	@FXML
	public ComboBox<Image> comboBoxUnity = new ComboBox<Image>();
	
	@FXML
	public ComboBox<Text> comboBoxLeis = new ComboBox<Text>();

	@FXML
	private GridPane gridPaneParametros = new GridPane();
	
	@FXML
	public ColumnConstraints columnC = new ColumnConstraints();

	public GridPane getGridPaneParametros() {
		return gridPaneParametros;
	}

	public void setGridPaneParametros(GridPane gridPaneParametros) {
		this.gridPaneParametros = gridPaneParametros;
	}

	UpdateComboBox updateComboBox = new UpdateComboBox();

	FormatComboBox formatComboBox = new FormatComboBox();

	EquacoesBuilder eqBuilder = new EquacoesBuilder();

	ComboBoxBuilder cbBuilder = new ComboBoxBuilder();

	ConvertToFxImage convertToFxImage = new ConvertToFxImage();

	@FXML
	public ImageView unityImageView = new ImageView();



	double l = 1.0;

	Integer nParameters = 0;

	private ImageView imageViewA = new ImageView();
	private ImageView imageViewE = new ImageView();
	private ImageView imageViewKCH4 = new ImageView();
	private ImageView imageViewKCO2 = new ImageView();
	private ImageView imageViewm = new ImageView();
	private ImageView imageViewn = new ImageView();
	private ImageView imageViewp = new ImageView();
	private ImageView imageViewq = new ImageView();
	private ImageView imageViewLp = new ImageView();

	private TextField parameter1 = new TextField();
	private TextField parameter2 = new TextField();
	private TextField parameter3 = new TextField();
	private TextField parameter4 = new TextField();
	private TextField parameter5 = new TextField();
	private TextField parameter6 = new TextField();
	private TextField parameter7 = new TextField();

	private List<String> listStringsParametros = Arrays.asList("\\text{A}", "\\text{E}", "\\text{K}_{CH_4}",
			"\\text{K}_{CO_2}", "\\text{m}", "\\text{n}", "\\text{p}", "\\text{q}", "\\text{K}_p");

	private List<ImageView> listImageViewParametros = Arrays.asList(imageViewA, imageViewE, imageViewKCH4,
			imageViewKCO2, imageViewm, imageViewn, imageViewp, imageViewq, imageViewLp);

	private List<Boolean> listImageViewParametrosSize = Arrays.asList(false, false, false, false, false, false, false,
			false, false);

	@FXML
	private void updateComboBoxCinetica() {

		comboBoxLeiVelocidade.getItems().clear();

		if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 0) {
			if (checkBoxR.isSelected()) {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("potenciasR"), false);
			} else {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("potenciasI"), false);
			}

		} else if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 1) {

			if (checkBoxR.isSelected()) {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("eleyR"), false);
			} else {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("eleyI"), false);
			}
		} else if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 2) {

			if (checkBoxR.isSelected()) {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("langmuirR"), false);
			} else {
				formatComboBox.updateComboBox(comboBoxLeiVelocidade, eqBuilder.addEquacoes("langmuirI"), false);
			}
		}
		comboBoxLeiVelocidade.getSelectionModel().select(0);

		comboBoxLeiVelocidade.setVisibleRowCount(comboBoxLeiVelocidade.getItems().size());
		// updateComboBoxSize();
		setKinetics();
	}

	@FXML
	private void updateComboBoxSize() {
		if (comboBoxLeiVelocidade.getSelectionModel().getSelectedItem() != null) {
			comboBoxLeiVelocidade
					.setMinWidth(comboBoxLeiVelocidade.getSelectionModel().getSelectedItem().getWidth() * l + 25);
			comboBoxLeiVelocidade
					.setPrefWidth(comboBoxLeiVelocidade.getSelectionModel().getSelectedItem().getWidth() * l + 25);
			comboBoxLeiVelocidade
					.setMinHeight(comboBoxLeiVelocidade.getSelectionModel().getSelectedItem().getHeight() * l + 5);
			comboBoxLeiVelocidade
					.setPrefHeight(comboBoxLeiVelocidade.getSelectionModel().getSelectedItem().getHeight() * l + 5);
			columnC.setPrefWidth(comboBoxLeiVelocidade.getSelectionModel().getSelectedItem().getWidth() * l + 150);
		}
		comboBoxLeiVelocidade.setVisibleRowCount(comboBoxLeiVelocidade.getItems().size() - 1);
		
		setKinetics();
	}

	private void setUp() {

		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");
		Integer k = 0;
		if (newOs[0].contentEquals("Windows")) {
			k = 20;
		} else {
			k = 31;
		}

		String reacao1 = "\\displaystyle \\mbox{CH}_{4}+\\mbox{CO}_{2}  \\rightleftharpoons 2\\mbox{H}_2+2\\mbox{CO}";

		final TeXFormula reacao1Tex = new TeXFormula(reacao1);
		java.awt.Image imageReacao1 = reacao1Tex.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK,
				null);
		Image fxReacao1 = SwingFXUtils.toFXImage((BufferedImage) imageReacao1, null);
		reacao1ImageView.setFitWidth(fxReacao1.getWidth() * l);
		reacao1ImageView.setImage(fxReacao1);
		reacao1ImageView.setPreserveRatio(true);

		updateComboBox.updateComboBox(comboBoxLeis, cbBuilder.updateCBCinetica(), true);
		comboBoxLeis.getStyleClass().add("center-aligned");
		comboBoxLeis.getSelectionModel().select(0);
		updateComboBoxCinetica();

		comboBoxLeiVelocidade.getSelectionModel().select(0);
		updateComboBoxSize();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");
		if (newOs[0].contentEquals("Windows")) {
			l = 1.0;
		} else {
			l = 0.65;
		}
		setImages.setImages(listStringsParametros, listImageViewParametros, listImageViewParametrosSize);
		setUp();
		updateComboBoxCinetica();
		formatComboBox.updateComboBox(comboBoxUnity, cbBuilder.updateCBResultados(),false);
		comboBoxUnity.getSelectionModel().select(0);
	}

	private void setKinetics() {
		gridPaneParametros.getChildren().clear();
		if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 0) {
			if (!checkBoxR.isSelected()) {
				if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 0) {
					// Irreversible Power Law

					gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
					gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
					gridPaneParametros.add(listImageViewParametros.get(4), 2, 0);
					gridPaneParametros.add(listImageViewParametros.get(5), 3, 0);

					gridPaneParametros.add(parameter1, 0, 1);
					gridPaneParametros.add(parameter2, 1, 1);
					gridPaneParametros.add(parameter3, 2, 1);
					gridPaneParametros.add(parameter4, 3, 1);

					nParameters = 4;
					setConstraints();
				} else if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 1) {
					//
				}
			} else {
				gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
				gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
				gridPaneParametros.add(listImageViewParametros.get(4), 2, 0);
				gridPaneParametros.add(listImageViewParametros.get(5), 3, 0);
				gridPaneParametros.add(listImageViewParametros.get(6), 4, 0);
				gridPaneParametros.add(listImageViewParametros.get(7), 5, 0);
				gridPaneParametros.add(listImageViewParametros.get(8), 6, 0);

				gridPaneParametros.add(parameter1, 0, 1);
				gridPaneParametros.add(parameter2, 1, 1);
				gridPaneParametros.add(parameter3, 2, 1);
				gridPaneParametros.add(parameter4, 3, 1);
				gridPaneParametros.add(parameter5, 4, 1);
				gridPaneParametros.add(parameter6, 5, 1);
				gridPaneParametros.add(parameter7, 6, 1);

				nParameters = 7;
				setConstraints();
			}
		} else if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 1) {
			if (!checkBoxR.isSelected()) {
				if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 0) {
					// Irreversible Eley-Rideal

					gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
					gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
					gridPaneParametros.add(listImageViewParametros.get(2), 2, 0);

					gridPaneParametros.add(parameter1, 0, 1);
					gridPaneParametros.add(parameter2, 1, 1);
					gridPaneParametros.add(parameter3, 2, 1);

					nParameters = 3;
					setConstraints();
				} else if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 1) {
					//
				}
			} else {
				gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
				gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
				gridPaneParametros.add(listImageViewParametros.get(2), 2, 0);
				gridPaneParametros.add(listImageViewParametros.get(8), 3, 0);

				gridPaneParametros.add(parameter1, 0, 1);
				gridPaneParametros.add(parameter2, 1, 1);
				gridPaneParametros.add(parameter3, 2, 1);
				gridPaneParametros.add(parameter4, 3, 1);

				nParameters = 4;
				setConstraints();
			}
		} else if (comboBoxLeis.getSelectionModel().getSelectedIndex() == 2) {
			if (!checkBoxR.isSelected()) {
				if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 0) {
					// Irreversible Power Law

					gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
					gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
					gridPaneParametros.add(listImageViewParametros.get(2), 2, 0);
					gridPaneParametros.add(listImageViewParametros.get(3), 3, 0);

					gridPaneParametros.add(parameter1, 0, 1);
					gridPaneParametros.add(parameter2, 1, 1);
					gridPaneParametros.add(parameter3, 2, 1);
					gridPaneParametros.add(parameter4, 3, 1);

					nParameters = 4;
					setConstraints();
				} else if (comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex() == 1) {
					//
				}
			} else {
				gridPaneParametros.add(listImageViewParametros.get(0), 0, 0);
				gridPaneParametros.add(listImageViewParametros.get(1), 1, 0);
				gridPaneParametros.add(listImageViewParametros.get(2), 2, 0);
				gridPaneParametros.add(listImageViewParametros.get(3), 3, 0);
				gridPaneParametros.add(listImageViewParametros.get(8), 4, 0);

				gridPaneParametros.add(parameter1, 0, 1);
				gridPaneParametros.add(parameter2, 1, 1);
				gridPaneParametros.add(parameter3, 2, 1);
				gridPaneParametros.add(parameter4, 3, 1);
				gridPaneParametros.add(parameter5, 4, 1);

				nParameters = 5;
				setConstraints();
			}
		}

	}

	private void setConstraints() {

		gridPaneParametros.getColumnConstraints().clear();
		gridPaneParametros.getRowConstraints().clear();
		gridPaneParametros.setAlignment(Pos.TOP_LEFT);
		gridPaneParametros.setHgap(10);
		gridPaneParametros.setVgap(10);
		gridPaneParametros.setMargin(checkBoxR, new Insets(0, 1, 1, 1));

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(true);
		col1.setMinWidth(100);
		col1.setMaxWidth(100);
		col1.setHalignment(HPos.CENTER);

		for (int i = 0; i < nParameters; i++) {
			gridPaneParametros.getColumnConstraints().add(col1);
		}

		RowConstraints row1 = new RowConstraints();
		row1.setFillHeight(false);
		row1.setMinHeight(25);
		row1.setMaxHeight(25);
		row1.setValignment(VPos.BOTTOM);

		RowConstraints row2 = new RowConstraints();
		row2.setFillHeight(true);
		row2.setMinHeight(35);
		row2.setMaxHeight(35);
		row2.setValignment(VPos.BOTTOM);
		row2.setPrefHeight(35);

		gridPaneParametros.getRowConstraints().add(row1);
		gridPaneParametros.getRowConstraints().add(row2);
		gridPaneParametros.getRowConstraints().add(row2);

		parameter1.setPrefHeight(30);
		parameter2.setPrefHeight(30);
		parameter3.setPrefHeight(30);
		parameter4.setPrefHeight(30);
		parameter5.setPrefHeight(30);
		parameter6.setPrefHeight(30);
		parameter7.setPrefHeight(30);

		parameter1.setAlignment(Pos.CENTER);
		parameter2.setAlignment(Pos.CENTER);
		parameter3.setAlignment(Pos.CENTER);
		parameter4.setAlignment(Pos.CENTER);
		parameter5.setAlignment(Pos.CENTER);
		parameter6.setAlignment(Pos.CENTER);
		parameter7.setAlignment(Pos.CENTER);

	}

}
