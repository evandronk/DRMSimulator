package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.EstimationEntity;
import enxame.ParticleSwarm;
import gui.funcoesauxiliares.ComboBoxBuilder;
import gui.funcoesauxiliares.SetEstimationEntity;
import gui.funcoesauxiliares.EquacoesBuilder;
import gui.funcoesauxiliares.FormatComboBox;
import gui.funcoesauxiliares.GetNodeByRowCollumn;
import gui.funcoesauxiliares.ManipulateNode;
import gui.funcoesauxiliares.SetImages;
import gui.funcoesauxiliares.UpdateComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import newton.LevembergMaquartd;
import newton.NewtonModificado;
import util.Alerts;

public class EstimationController implements PropertyChangeListener, Initializable {

	private Double k = 0.0;

	private Integer numeroLinhas = 1;

	private Integer numeroColunas = 2;

	private Integer numeroParametros = 3;

	private Integer numeroParticulas = 10;

	private Integer numeroIteracoes = 10;

	private File selectedFile;

	private Boolean fromEquacoes = false;

	private String expressao;

	private String selecao = "";

	private EstimationEntity entity = new EstimationEntity();

	private ManipulateNode manipulateNode = new ManipulateNode();

	private ComboBoxBuilder cbBuilder = new ComboBoxBuilder();

	private FormatComboBox formatComboBox = new FormatComboBox();

	private UpdateComboBox updateComboBox = new UpdateComboBox();

	private SetImages setImages = new SetImages();

	private GetNodeByRowCollumn getNode = new GetNodeByRowCollumn();

	private SetEstimationEntity defineEntity = new SetEstimationEntity();

	private EquacoesBuilder equacoes = new EquacoesBuilder();

	@FXML
	VBox vBoxMain = new VBox();

	@FXML
	ComboBox<Text> comboBoxVazao = new ComboBox<Text>();

	@FXML
	ProgressBar progressBar = new ProgressBar();

	@FXML
	VBox vBoxParametros = new VBox();

	@FXML
	ComboBox<Text> newComboBoxMetodo = new ComboBox<Text>();

	@FXML
	GridPane gridPaneMedida = new GridPane();

	@FXML
	GridPane gridPaneResultadosMedida = new GridPane();

	@FXML
	Button excelFile = new Button();

	@FXML
	CategoryAxis xAxis = new CategoryAxis();

	@FXML
	NumberAxis yAxis = new NumberAxis();

	@FXML
	VBox vBoxDados1 = new VBox();

	@FXML
	HBox hBoxDados2 = new HBox();

	@FXML
	VBox vBoxDados2 = new VBox();

	@FXML
	VBox vBoxDados3 = new VBox();

	@FXML
	VBox vBoxIniciais = new VBox();

	@FXML
	Text textTituloParametros = new Text();

	@FXML
	ScrollPane scrollPaneDados1 = new ScrollPane();

	@FXML
	ComboBox<Image> expressaoCombo;

	@FXML
	Button btCalcular;

	@FXML
	Button addRow;

	@FXML
	Button removeRow;

	@FXML
	GridPane dadosExperimentaisGridPane;

	@FXML
	GridPane resultadosGridPane;

	@FXML
	GridPane parametrosGridPane;

	private ImageView imageViewT = new ImageView();
	private ImageView imageViewA = new ImageView();
	private ImageView imageViewE = new ImageView();
	private ImageView imageViewCH4 = new ImageView();
	private ImageView imageViewCO2 = new ImageView();
	private ImageView imageViewH2 = new ImageView();
	private ImageView imageViewCO = new ImageView();

	private ImageView imageViewKCH4 = new ImageView();
	private ImageView imageViewKCO2 = new ImageView();
	private ImageView imageViewm = new ImageView();
	private ImageView imageViewn = new ImageView();
	private ImageView imageViewp = new ImageView();
	private ImageView imageViewq = new ImageView();

	private ComboBox<Image> comboBoxT = new ComboBox<Image>();
	private ComboBox<Image> comboBoxV1 = new ComboBox<Image>();
	private ComboBox<Image> comboBoxV2 = new ComboBox<Image>();
	private ComboBox<Image> comboBoxV3 = new ComboBox<Image>();
	private ComboBox<Image> comboBoxV4 = new ComboBox<Image>();
	private ComboBox<Image> comboBoxR = new ComboBox<Image>();

	private TextField fieldNParticulas = new TextField();
	private Text textNIteracoes = new Text();
	private TextField fieldNIteracoes = new TextField();
	private Text textNParticulas = new Text();

	private List<String> variaveisIndependentes = new ArrayList<String>();

	private List<String> parametrosEstimacao = new ArrayList<String>();

	private List<List<Double>> salvarParametros = new ArrayList<List<Double>>();

	private List<String> parametrosResultado = new ArrayList<String>();

	private List<String> listParametros = Arrays.asList("A", "E", "K_CH4", "K_CO2", "m", "n", "p", "q");

	private List<String> listVariaveis = Arrays.asList("T", "CH4_in", "CO2_in", "H2_out", "CO_out");

	private List<ComboBox<Image>> listComboBoxVariaveis = Arrays.asList(comboBoxT, comboBoxV1, comboBoxV2, comboBoxV3,
			comboBoxV4);

	private List<ImageView> listImageViewParametros = Arrays.asList(imageViewA, imageViewE, imageViewKCH4,
			imageViewKCO2, imageViewm, imageViewn, imageViewp, imageViewq);

	private List<Boolean> listImageViewParametrosSize = Arrays.asList(false, false, false, false, false, false, false,
			false);

	private List<ImageView> listImageViewVariaveis = Arrays.asList(imageViewT, imageViewCH4, imageViewCO2, imageViewH2,
			imageViewCO);

	private List<Boolean> listImageViewVariaveisSize = Arrays.asList(false, false, false, false, false, false, false);

	private List<String> listStringsVariaveis = Arrays.asList("\\text{T}", "\\text{CH}_{4}", "\\text{CO}_2",
			"\\text{H}_2", "\\text{CO}");

	private List<String> listStringsParametros = Arrays.asList("\\text{A}", "\\text{E}", "\\text{K}_{CH_4}",
			"\\text{K}_{CO_2}", "\\text{m}", "\\text{n}", "\\text{p}", "\\text{q}");

	private List<Double> resultado = new ArrayList<Double>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void load(String selected) {

		selecao = selected;

		entity.setLeiVelocidade(selecao);
		entity.setSelecao(0);
		entity = defineEntity.defineEntity(entity);

		progressBar.setVisible(false);

		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");

		if (newOs[0].contentEquals("Windows")) {
			k = 1.0;
		} else {
			k = 0.65;
		}

		updateComboBox.updateComboBox(comboBoxVazao, cbBuilder.updateCBVazao(), true);
		comboBoxVazao.getSelectionModel().select(0);
		comboBoxVazao.getStyleClass().add("center-aligned");
		comboBoxVazao.getStylesheets().add("/gui/css/ComboBoxCenter.css");

		setConstraints();

		numeroParametros = 0;

		formatComboBox.updateComboBox(expressaoCombo, equacoes.addEquacoes(selecao), false);

		expressaoCombo.getSelectionModel().select(0);

		entity.getParametros().stream().forEach(t -> parametrosResultado.add(listStringsParametros.get(t)));

		setImages.setImages(listStringsVariaveis, listImageViewVariaveis, listImageViewVariaveisSize);

		setImages.setImages(listStringsParametros, listImageViewParametros, listImageViewParametrosSize);

		UpdateComboBox(newComboBoxMetodo, cbBuilder.updateCBMetodo(), true);

		newComboBoxMetodo.getSelectionModel().select(0);

		updateComboBoxMetodo();
		updateComboBoxMetodo();

		try {
			updateComboBox();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		setVBoxIniciais();

	}

	private void setUpConstraints() {

		for (ComboBox<Image> comboBox : listComboBoxVariaveis) {
			comboBox.getStyleClass().add("center-aligned");
			comboBox.getStylesheets().add("/gui/css/ComboBoxCenter.css");
			comboBox.setPadding(new Insets(0, 0, 0, 10));
		}

		comboBoxR.getStyleClass().add("center-aligned");
		comboBoxR.getStylesheets().add("/gui/css/ComboBoxCenter.css");
		comboBoxR.setPadding(new Insets(0, 0, 0, 10));

		newComboBoxMetodo.getStyleClass().add("center-aligned");

		scrollPaneDados1.getStyleClass().clear();

		int sPMaxW = 930;

		int nColunas = dadosExperimentaisGridPane.getColumnCount();
		Double hGap = dadosExperimentaisGridPane.getHgap();
		Double prefW = (nColunas + 1) * (100 + hGap) + 120;
		if (prefW < 930) {
			dadosExperimentaisGridPane.setPrefWidth(prefW - 100);
			dadosExperimentaisGridPane.setMaxWidth(prefW - 100);
			scrollPaneDados1.setMinWidth(prefW);
			scrollPaneDados1.setMaxWidth(prefW);
			scrollPaneDados1.setPrefWidth(prefW);
			hBoxDados2.setMinWidth(200);
			hBoxDados2.setMaxWidth(prefW - 50);
			hBoxDados2.setPrefWidth(prefW - 50);
			vBoxDados2.setMinWidth(200);
			vBoxDados2.setPrefWidth(prefW);
			vBoxDados2.setMaxWidth(prefW);
			vBoxDados3.setMinWidth(120);
			vBoxDados3.setPrefWidth(120);
			vBoxDados3.setMaxWidth(120);
		} else {
			dadosExperimentaisGridPane.setPrefWidth(sPMaxW);
			dadosExperimentaisGridPane.setMaxWidth(sPMaxW);
			scrollPaneDados1.setMaxWidth(sPMaxW);
			scrollPaneDados1.setPrefWidth(sPMaxW);
			hBoxDados2.setMaxWidth(sPMaxW);
			hBoxDados2.setPrefWidth(sPMaxW);
		}

	}

	@FXML
	private void addColumnAction() {

		for (int i = 0; i < numeroLinhas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(35);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			txt.setFont(Font.font("System", 13));
			dadosExperimentaisGridPane.add(txt, numeroColunas, i);

			if (i == 0) {
				txt.setPromptText("Digite a Variável");
			}
		}

		numeroColunas += 1;

		setConstraints();

	}

	@FXML
	private void addRowAction() {

		for (int i = 0; i < numeroColunas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(35);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			txt.setFont(Font.font("System", 13));
			dadosExperimentaisGridPane.add(txt, i, numeroLinhas);

		}

		TextField txt = new TextField();
		txt.setAlignment(Pos.CENTER);
		txt.setMaxHeight(35);
		txt.setMinWidth(100);
		txt.setPrefWidth(100);
		txt.setMaxWidth(100);
		txt.setFont(Font.font("System", 13));
		resultadosGridPane.add(txt, 0, numeroLinhas - 1);

		numeroLinhas += 1;
		setConstraints();
	}

	@FXML
	private void addParametroAction() {

		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() < 1) {
			TextField txt = new TextField();
			txt.setFont(Font.font("System", 13));
			txt.setAlignment(Pos.CENTER);
			txt.setMaxWidth(60);
			txt.setMinWidth(60);
			txt.setPrefWidth(60);

			TextField textMin = new TextField();
			textMin.setFont(Font.font("System", 13));
			textMin.setAlignment(Pos.CENTER);
			textMin.setMaxWidth(100);
			textMin.setMinWidth(100);
			textMin.setPrefWidth(100);
			textMin.setPromptText("Min");

			TextField textMax = new TextField();
			textMax.setFont(Font.font("System", 13));
			textMax.setAlignment(Pos.CENTER);
			textMax.setMaxWidth(100);
			textMax.setMinWidth(100);
			textMax.setPrefWidth(100);
			textMax.setPromptText("Max");

			// Remover esse trecho das definicoes
			textMin.setText("1");
			textMax.setText("1000");
			fieldNParticulas.setText("50");
			fieldNIteracoes.setText("20");

			parametrosGridPane.add(txt, 0, numeroParametros);
			parametrosGridPane.add(textMin, 1, numeroParametros);
			parametrosGridPane.add(textMax, 2, numeroParametros);
		} else {
			TextField txt = new TextField();
			txt.setAlignment(Pos.CENTER);
			txt.setMaxWidth(60);
			txt.setMinWidth(60);
			txt.setPrefWidth(60);
			txt.setFont(Font.font("System", 13));

			TextField textInicio = new TextField();
			textInicio.setAlignment(Pos.CENTER);
			textInicio.setMaxWidth(100);
			textInicio.setMinWidth(100);
			textInicio.setPrefWidth(100);
			textInicio.setPromptText("Ponto Inicial");
			textInicio.setFont(Font.font("System", 13));

			parametrosGridPane.add(txt, 0, numeroParametros);
			parametrosGridPane.add(textInicio, 1, numeroParametros);
		}

		numeroParametros += 1;
		setConstraints();

	}

	@FXML
	private void removeColumnAction() {

		if (numeroColunas > 1) {
			for (int i = 0; i < numeroLinhas; i++) {
				manipulateNode.removeNodeByRowColumnIndex(i, numeroColunas - 1, dadosExperimentaisGridPane);
			}
			numeroColunas -= 1;
			setConstraints();
		}
	}

	private void removeParametroAction() {

		if (numeroParametros > 0) {

			if (parametrosGridPane.getColumnCount() > 2) {

				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 0, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 1, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 2, parametrosGridPane);
			} else {
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 0, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 1, parametrosGridPane);
			}
			numeroParametros -= 1;
			setConstraints();

		}

		// parametrosGridPane.getChildren().clear();

	}

	@FXML
	private void removeRowAction() {

		if (numeroLinhas > 1) {
			for (int i = 0; i < numeroColunas; i++) {

				manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 1, i, dadosExperimentaisGridPane);
			}
			manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 2, 0, resultadosGridPane);
			numeroLinhas -= 1;
			setConstraints();
		}

	}

	@FXML
	private void btCalcularAction() throws IOException {

		Integer g = tratamentoErros();

		if (g > 0) {
			return;
		}

		progressBar.setProgress(0);
		progressBar.setVisible(true);

		numeroParticulas = Integer.valueOf(fieldNParticulas.getText().toString());
		numeroIteracoes = Integer.valueOf(fieldNIteracoes.getText().toString());

		List<List<Double>> dados = new ArrayList<List<Double>>();

		for (int j = 0; j < numeroColunas; j++) {
			List<Double> listaDados = new ArrayList<Double>();
			for (int i = 1; i < numeroLinhas; i++) {

				Node node = manipulateNode.returnNodeByRowColumn(i, j, dadosExperimentaisGridPane);

				try {
					TextField texto = (TextField) node;
					if (texto.getText() == "") {
						listaDados.add(Double.parseDouble(String.valueOf(0)));
					} else {
						listaDados.add(Double.parseDouble(texto.getText()));
					}
				} catch (Exception e) {

				} finally {

				}

			}
			dados.add(listaDados);
		}

		List<List<Double>> dominio = new ArrayList<List<Double>>();
		List<Double> dominioNewton = new ArrayList<Double>();
		List<Double> dominioVariavel = new ArrayList<Double>();
		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 0) {
			for (int j = 0; j < numeroParametros; j++) {

				dominioVariavel.add(Double.valueOf(
						((TextField) getNode.getNodeByRowColumnIndex(j, 1, parametrosGridPane)).getText().toString()));
				dominioVariavel.add(Double.valueOf(
						((TextField) getNode.getNodeByRowColumnIndex(j, 2, parametrosGridPane)).getText().toString()));
				dominio.add(dominioVariavel);
			}
		} else {
			for (int j = 0; j < numeroParametros; j++) {

				dominioNewton.add(Double.valueOf(
						((TextField) getNode.getNodeByRowColumnIndex(j, 1, parametrosGridPane)).getText().toString()));
				dominio.add(dominioNewton);
			}
		}

		List<Double> resultadosExperimentais = new ArrayList<Double>();

		for (int i = 0; i < numeroLinhas - 1; i++) {
			Node node = manipulateNode.returnNodeByRowColumn(i, 0, resultadosGridPane);
			TextField texto = (TextField) node;
			try {

				if (texto.getText() == "" || texto.getText() == null) {
					texto.setText("0.0");

				}
				resultadosExperimentais.add(Double.parseDouble(texto.getText()));

			} catch (Exception e) {

			} finally {

			}

		}

		

		Thread firstThread = new Thread(() -> {
			btCalcular.setDisable(true);
			if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 0) {
				ParticleSwarm pso = new ParticleSwarm();
				pso.addObserver(this);

				resultado = pso.Calcular(dados, variaveisIndependentes, parametrosEstimacao, resultadosExperimentais,
						dominio, entity.getExpressao(), numeroParticulas, numeroIteracoes);

			} else if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 1) {
				LevembergMaquartd levembergMaquartd = new LevembergMaquartd();

				resultado = levembergMaquartd.newtonModificado(dados, resultadosExperimentais, variaveisIndependentes,
						parametrosEstimacao, entity.getExpressao(), dominioNewton,entity);
			} else if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 2) {
				NewtonModificado newtonModificado = new NewtonModificado();

				resultado = newtonModificado.newtonModificado(dados, resultadosExperimentais, variaveisIndependentes,
						parametrosEstimacao, entity.getExpressao(), dominioNewton,entity);

			}

			entity.setrExp(resultadosExperimentais);
			entity.setrMod(resultado);
			List<String> parametrosString = new ArrayList<String>();
			entity.getParametros().stream().forEach(t -> parametrosString.add(listStringsParametros.get(t)));
			entity.setParametrosString(parametrosString);
			entity.setDados(dados);
			entity.setParametrosEstimacao(parametrosEstimacao);
			entity.setVariaveisIndepdendentes(variaveisIndependentes);
		
		
			Platform.runLater(() -> {
				loadViewResultados("/gui/ResultadosEstimacao.fxml", "Resultados da Estimação de Parâmetros",
						(EstimationResultsController controller) -> {

							controller.loadElements(entity);

						});
				progressBar.setVisible(false);
				btCalcular.setDisable(false);

				// updateScreen(numeroVariaveis, parametros, expressao);
			});

		});

		try {
			firstThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		firstThread.start();

		// MetodoSecante metodoSecante = new MetodoSecante();

		// metodoSecante.metodoSecante(dados, resultadosExperimentais,
		// variaveisIndependentes, parametrosPSO, expressao);

	}

	private synchronized <T> void loadViewResultados(String absoluteName, String title,
			Consumer<T> initializingAction) {

		try {

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			//stage.initOwner(btMapaSala.getScene().getWindow());
			T controller = loader.getController();

			initializingAction.accept(controller);
			stage.show();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	private void setConstraints() {

		dadosExperimentaisGridPane.getColumnConstraints().clear();
		dadosExperimentaisGridPane.getRowConstraints().clear();

	/*	gridPaneMedida.getColumnConstraints().clear();
		gridPaneMedida.getRowConstraints().clear();*/
		gridPaneMedida.setVisible(false);
		
		/*gridPaneResultadosMedida.getColumnConstraints().clear();
		gridPaneResultadosMedida.getRowConstraints().clear();
		gridPaneResultadosMedida.setAlignment(Pos.TOP_CENTER);
		*/
		gridPaneResultadosMedida.setVisible(false);
		
		comboBoxVazao.setVisible(false);
		
		resultadosGridPane.getColumnConstraints().clear();
		resultadosGridPane.getRowConstraints().clear();
		resultadosGridPane.setAlignment(Pos.TOP_CENTER);

		parametrosGridPane.getColumnConstraints().clear();
		parametrosGridPane.getRowConstraints().clear();
		parametrosGridPane.setAlignment(Pos.TOP_LEFT);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(100);
		col1.setMaxWidth(100);
		col1.setHalignment(HPos.CENTER);

		for (int i = 0; i < numeroColunas; i++) {
			dadosExperimentaisGridPane.getColumnConstraints().add(col1);
		}

		ColumnConstraints colParametro = new ColumnConstraints();
		colParametro.setFillWidth(false);
		colParametro.setMinWidth(50);
		colParametro.setMaxWidth(50);
		colParametro.setHalignment(HPos.LEFT);

		ColumnConstraints colMin = new ColumnConstraints();
		colMin.setFillWidth(false);
		colMin.setMinWidth(100);
		colMin.setMaxWidth(100);
		colMin.setHalignment(HPos.LEFT);
		parametrosGridPane.getColumnConstraints().addAll(colParametro, colMin);

		ColumnConstraints colResultados = new ColumnConstraints();
		colResultados.setFillWidth(false);
		colResultados.setMinWidth(100);
		colResultados.setMaxWidth(100);
		colResultados.setHalignment(HPos.CENTER);
		resultadosGridPane.getColumnConstraints().add(colResultados);

		dadosExperimentaisGridPane.setHgap(20);
		//gridPaneMedida.setHgap(20);
		//gridPaneMedida.setVgap(10);
		//gridPaneResultadosMedida.setHgap(20);
		//gridPaneResultadosMedida.setVgap(10);
		dadosExperimentaisGridPane.setVgap(5);
		resultadosGridPane.setVgap(5);
		parametrosGridPane.setHgap(20);
		parametrosGridPane.setVgap(5);

	}

	@FXML
	private void updateComboBox() throws IOException {

		fromEquacoes = true;

		entity.setSelecao(expressaoCombo.getSelectionModel().getSelectedIndex());

		defineEntity.defineEntity(entity);

		entity.getParametros().stream().forEach(t -> parametrosResultado.add(listStringsParametros.get(t)));

		updateScreen(entity.getnVariaveis(), entity.getParametros(), expressao);

		setUpConstraints();

		fromEquacoes = false;

		numeroParametros = entity.getParametros().size();

	}

	public void RemoveAll(Integer variaveisNecessarias, Integer parametrosNecessarios) {

		while (numeroColunas > variaveisNecessarias) {
			removeColumnAction();
		}
		while (numeroParametros > parametrosNecessarios) {
			removeParametroAction();
		}
	}

	public void loadExcelFile() throws IOException {

		List<List<Double>> valoresExcel = new ArrayList<List<Double>>();

		String caminho = "";

		ExtensionFilter xls = new ExtensionFilter("Excel (*.xls, *.xlxs)", "*.*");

		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(xls);

		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			caminho = selectedFile.getPath();
		}
		if (caminho != "") {
			FileInputStream file = new FileInputStream(new File(caminho));

			Workbook workBook = new XSSFWorkbook(file);
			Sheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormater = new DataFormatter();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				List<Double> Linhas = new ArrayList<Double>();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormater.formatCellValue(cell);

					try {
						if (cellValue != null) {
							Linhas.add(Double.parseDouble(cellValue.replace(",", ".")));
						} else {
							Linhas.add(Double.parseDouble("0"));
						}

					} catch (Exception e) {

					}

				}
				if (Linhas != null && Linhas.size() > 0) {
					valoresExcel.add(Linhas);
				}
			}

			if (numeroLinhas > valoresExcel.size() + 1) {
				while (numeroLinhas > valoresExcel.size()) {
					removeRowAction();
				}
			}

			if (numeroLinhas < valoresExcel.size() + 1) {
				while (numeroLinhas < valoresExcel.size() + 1) {
					addRowAction();
				}
			}

			for (int i = 0; i < valoresExcel.size(); i++) {
				for (int j = 0; j < valoresExcel.get(0).size(); j++) {

					TextField node = new TextField();
					node.setText(valoresExcel.get(i).get(j).toString());
					node.setAlignment(Pos.CENTER);
					manipulateNode.replaceNodeByRowColumnIndex(i + 1, j, dadosExperimentaisGridPane, node);
				}
			}

			for (int i = 0; i < valoresExcel.size(); i++) {
				TextField node = new TextField();
				node.setText(valoresExcel.get(i).get(valoresExcel.get(i).size() - 1).toString());
				node.setAlignment(Pos.CENTER);
				manipulateNode.replaceNodeByRowColumnIndex(i, 0, resultadosGridPane, node);
			}

		}

	}

	private void setComboBoxDados(ComboBox<Image> comboBox, Boolean isResult) {
		if (isResult == true) {
			comboBox.setMinWidth(165);
			comboBox.setMaxWidth(165);
			comboBox.setMinHeight(35);
			comboBox.setMaxHeight(35);
		} else {
			comboBox.setMinWidth(100);
			comboBox.setMaxWidth(100);
			comboBox.setMinHeight(35);
			comboBox.setMaxHeight(35);
			comboBox.setPrefHeight(35);
		}
	}

	private void formatComboBox(ComboBox<Image> comboBox, List<Image> comboBoxImage, Boolean center, Boolean isResult) {

		int selected = 0;
		if (comboBox.getSelectionModel().getSelectedIndex() > -1) {
			selected = comboBox.getSelectionModel().getSelectedIndex();
		}
		formatComboBox.updateComboBox(comboBox, comboBoxImage, center); // center
		setComboBoxDados(comboBox, isResult);
		comboBox.getSelectionModel().select(selected);

	}

	private void UpdateComboBox(ComboBox<Text> comboBox, List<Text> comboBoxText, Boolean center) {

		int selected = 0;
		if (comboBox.getSelectionModel().getSelectedIndex() > -1) {
			selected = comboBox.getSelectionModel().getSelectedIndex();
		}
		updateComboBox.updateComboBox(comboBox, comboBoxText, center); // center
		comboBox.getSelectionModel().select(selected);

	}

	@FXML
	private void updateComboBoxMetodo() {

		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() < 1) {
			textTituloParametros.setText("Initial Search Domain");
			entity.setMetodoEstimacao("pso");
		} else {
			textTituloParametros.setText("Valor inicial de busca para cada parâmetro");
			entity.setMetodoEstimacao("levenberg"); // Incluir Newton
		}
		textTituloParametros.setFont(Font.font("System", 13));

		if (fromEquacoes == false) {
			try {
				updateComboBox();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		fromEquacoes = false;

		setVBoxIniciais();

	}

	private void updateScreen(Integer nVariaveis, List<Integer> nParametros, String equacao) {

		salvarParametros.clear();

		if (parametrosGridPane.getChildren().isEmpty() == false) {
			for (int i = 0; i < parametrosGridPane.getRowCount(); i++) {
				List<Double> valores = new ArrayList<Double>();
				for (int j = 1; j < parametrosGridPane.getColumnCount(); j++) {
					if (((TextField) manipulateNode.returnNodeByRowColumn(i, j, parametrosGridPane)).getText()
							.toString() != "") {
						valores.add(Double
								.valueOf(((TextField) manipulateNode.returnNodeByRowColumn(i, j, parametrosGridPane))
										.getText().toString()));
					}
				}
				salvarParametros.add(valores);
			}
		}

		expressaoCombo.setPrefWidth(expressaoCombo.getSelectionModel().getSelectedItem().getWidth() * k + 25);

		formatComboBox(listComboBoxVariaveis.get(0), cbBuilder.updateCBTemperatura(), true, false);

		for (int i = 1; i < nVariaveis; i++) {
			if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 0) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoVol(), true, false);
			} else if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 1) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoMolar(), true, false);
			} else if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 0) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoMassica(), true, false);
			}
		}

		formatComboBox(comboBoxR, cbBuilder.updateCBResultados(), true, true);

	/*	if (gridPaneMedida.getChildren().isEmpty() == false) {

			int size = gridPaneMedida.getChildren().size();

			for (int j = 0; j < size; j++) {
				manipulateNode.removeNodeByRowColumnIndex(0, j, gridPaneMedida);
			}

			for (int i = 0; i < nVariaveis; i++) {
				gridPaneMedida.add(listComboBoxVariaveis.get(i), i, 0);
			}
		} else {
			for (int i = 0; i < nVariaveis; i++) {
				gridPaneMedida.add(listComboBoxVariaveis.get(i), i, 0);
			}
			gridPaneResultadosMedida.add(comboBoxR, 0, 0);
		}
*/
		RemoveAll(nVariaveis, 0);

		while (numeroColunas < nVariaveis) {
			addColumnAction();
		}

		variaveisIndependentes.clear();

		for (int i = 0; i < nVariaveis; i++) {
			variaveisIndependentes.add(listVariaveis.get(i));
			if (getNode.getNodeByRowColumnIndex(0, i, dadosExperimentaisGridPane) == null) {
				dadosExperimentaisGridPane.add(listImageViewVariaveis.get(i), i, 0);
			} else {
				manipulateNode.replaceNodeByRowColumnIndex(0, i, dadosExperimentaisGridPane,
						listImageViewVariaveis.get(i));
			}
		}

		parametrosEstimacao.clear();

		List<Integer> newListImageViewParametros = new ArrayList<Integer>();

		for (Integer parametro : nParametros) {
			addParametroAction();
			newListImageViewParametros.add(parametro);
			parametrosEstimacao.add(listParametros.get(parametro));

		}

		for (int i = 0; i < nParametros.size(); i++) {
			manipulateNode.replaceNodeByRowColumnIndex(i, 0, parametrosGridPane,
					listImageViewParametros.get(nParametros.get(i)));

		}

		Integer linhas = Integer.min(nParametros.size(), salvarParametros.size());

		for (int i = 0; i < linhas; i++) {
			if (salvarParametros.size() > 0) {
				if (salvarParametros.get(i).size() > 0) {
					for (int j = 0; j < salvarParametros.get(i).size(); j++) {
						TextField text = new TextField();
						text.setAlignment(Pos.CENTER);
						text.setMaxWidth(100);
						text.setMinWidth(100);
						text.setPrefWidth(100);
						if (salvarParametros.get(i).get(j).toString() != "") {
							text.setText(salvarParametros.get(i).get(j).toString());
							text.setFont(Font.font("System", 13));
							manipulateNode.replaceNodeByRowColumnIndex(i, j + 1, parametrosGridPane, text);
						}
					}
				}
			}
		}

		expressao = equacao;

	}

	private Integer tratamentoErros() {
		String erro = "";
		Integer numeroErros = 0;
		if (numeroLinhas < 2) {
			numeroErros += 1;
			erro += "- É necessário ao menos 1 linha de dados\n";
		}
		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 0) {

			for (int i = 0; i < numeroParametros; i++) {
				if (!erro.contains("Verifique os parâmetros")) {
					if (getNode.getNodeByRowColumnIndex(i, 1, dadosExperimentaisGridPane) == null
							|| ((TextField) getNode.getNodeByRowColumnIndex(i, 1, parametrosGridPane)).getText() == ""
							|| getNode.getNodeByRowColumnIndex(i, 2, dadosExperimentaisGridPane) == null
							|| ((TextField) getNode.getNodeByRowColumnIndex(i, 2, parametrosGridPane))
									.getText() == "") {
						numeroErros += 1;
						erro += "- Verifique os parâmetros\n";
					}
				}
			}

		} else {
			for (int i = 0; i < numeroParametros; i++) {
				if (!erro.contains("Verifique os parâmetros")) {
					if (getNode.getNodeByRowColumnIndex(i, 1, dadosExperimentaisGridPane) == null
							|| ((TextField) getNode.getNodeByRowColumnIndex(i, 1, parametrosGridPane))
									.getText() == "") {
						numeroErros += 1;
						erro += "- Verifique os parâmetros\n";
					}
				}
			}
		}

		if (fieldNParticulas.getText().toString() == "") {
			numeroErros += 1;
			erro += "- Verifique o número de partículas\n";
		}
		if (fieldNIteracoes.getText().toString() == "") {
			numeroErros += 1;
			erro += "- Verifique o número de iterações\n";
		}

		if (numeroErros > 0) {
			Alerts.showAlert("Erro", erro, null, AlertType.ERROR);
		}
		return numeroErros;
	}

	private void setVBoxIniciais() {

		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() == 0) {

			vBoxIniciais.getChildren().clear();

			GridPane gridPaneIniciais = new GridPane();

			textNIteracoes.setText("Number of Iterations");
			textNIteracoes.setFont(Font.font("System", 13));
			fieldNIteracoes.setFont(Font.font("System", 13));
			fieldNIteracoes.setMinWidth(60);
			fieldNIteracoes.setPrefWidth(60);
			fieldNIteracoes.setMaxWidth(60);
			fieldNIteracoes.setAlignment(Pos.CENTER);
			gridPaneIniciais.add(textNIteracoes, 0, 0);
			gridPaneIniciais.add(fieldNIteracoes, 1, 0);

			textNParticulas.setText("Number of Particles");
			textNParticulas.setFont(Font.font("System", 13));
			fieldNParticulas.setFont(Font.font("System", 13));
			fieldNParticulas.setMinWidth(60);
			fieldNParticulas.setPrefWidth(60);
			fieldNParticulas.setMaxWidth(60);
			fieldNParticulas.setAlignment(Pos.CENTER);
			gridPaneIniciais.add(textNParticulas, 0, 1);
			gridPaneIniciais.add(fieldNParticulas, 1, 1);

			vBoxIniciais.getChildren().add(gridPaneIniciais);

			gridPaneIniciais.setHgap(10);
			gridPaneIniciais.setVgap(5);
		} else {

			vBoxIniciais.getChildren().clear();

			GridPane gridPaneIniciais = new GridPane();

			textNIteracoes.setText("Number of Iterations");
			textNIteracoes.setFont(Font.font("System", 13));
			fieldNIteracoes.setFont(Font.font("System", 13));
			fieldNIteracoes.setMinWidth(60);
			fieldNIteracoes.setPrefWidth(60);
			fieldNIteracoes.setMaxWidth(60);
			fieldNIteracoes.setAlignment(Pos.CENTER);
			gridPaneIniciais.add(textNIteracoes, 0, 0);
			gridPaneIniciais.add(fieldNIteracoes, 1, 0);

			vBoxIniciais.getChildren().add(gridPaneIniciais);

			gridPaneIniciais.setHgap(10);
			gridPaneIniciais.setVgap(5);
		}

	}

	@FXML
	private void updateCBVazao() {
		for (int i = 1; i < numeroColunas; i++) {
			if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 0) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoVol(), true, false);
			} else if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 1) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoMolar(), true, false);
			} else if (comboBoxVazao.getSelectionModel().getSelectedIndex() == 2) {
				formatComboBox(listComboBoxVariaveis.get(i), cbBuilder.updateCBVazaoMassica(), true, false);
			}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		Double valor = Double.valueOf(evt.getNewValue().toString());
		valor += 1;
		valor = valor / numeroIteracoes;
		progressBar.setProgress(valor);

	}

}
