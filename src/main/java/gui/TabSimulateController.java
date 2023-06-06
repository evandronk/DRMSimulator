package gui;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import OdeSolver.DiferencasFinitas;
import OdeSolver.RK4SistemasEdos;
import OdeSolver.RKF45Storage;
import entities.SimulationEntity;
import gui.funcoesauxiliares.Export;
import gui.funcoesauxiliares.GetNodeByRowCollumn;
import gui.funcoesauxiliares.PDEBuilder;
import gui.funcoesauxiliares.SetSimulationEntity;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import propriedadesfq.Constantes;
import util.Alerts;

public class TabSimulateController implements PropertyChangeListener, Initializable {

	@FXML
	Button btSimular = new Button();

	@FXML
	GridPane resultadosGridPane = new GridPane();

	@FXML
	ProgressBar progressBar = new ProgressBar();

	@FXML
	TextField textFinalTime = new TextField();

	@FXML
	TextField textMinStep = new TextField();

	@FXML
	TextField textMaxStep = new TextField();
	
	@FXML
	TextField textAxialPartitions = new TextField();

	@FXML
	Label label = new Label();

	@FXML
	TextField textFildSearch = new TextField();
	
	@FXML
	HBox mainHbox = new HBox();
	
	@FXML
	Button buttonR = new Button();
	
	@FXML
	Button buttonExport = new Button();
	
	@FXML
	GridPane valueGridPane = new GridPane();
	
	private ImageView imageViewz = new ImageView();
	private ImageView imageViewCH4 = new ImageView();
	private ImageView imageViewCO2 = new ImageView();
	private ImageView imageViewH2 = new ImageView();
	private ImageView imageViewCO = new ImageView();
	private ImageView imageViewT = new ImageView();
	
	List<ImageView> variaveis = Arrays.asList(imageViewz,imageViewCH4,imageViewCO2,imageViewH2,imageViewCO,imageViewT);
	
	private ImageView imageViewCp = new ImageView();
	private ImageView imageViewvz = new ImageView();
	private ImageView imageViewrhog = new ImageView();
	private ImageView imageViewDL = new ImageView();
	private ImageView imageViewDeltaH = new ImageView();
	private ImageView imageViewLambdaL = new ImageView();
	private ImageView imageViewXCH4 = new ImageView();
	
	List<ImageView> valores = Arrays.asList(imageViewvz, imageViewrhog,imageViewDL,imageViewCp,imageViewDeltaH,imageViewLambdaL,imageViewXCH4);
	
	public TabInletController entradaController;
	public TabEnergyController energiaController;
	public TabReactorController reatorController;
	public TabReactionController reacoesController;
	
	//NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("en", "US"));
	DecimalFormat formatter = new DecimalFormat("#0.00000");

	DiferencasFinitas diferencasFinitas = new DiferencasFinitas();

	RK4SistemasEdos rk4 = new RK4SistemasEdos();

	Alerts alert = new Alerts();
	
	Double L = 0.0;

	List<List<Double>> resultados = new ArrayList<List<Double>>();

	List<Double> tValues = new ArrayList<Double>();

	List<String> equacoes = new ArrayList<String>();
	
	private List<String> listStringsVariaveis = Arrays.asList("\\text{z}", "\\text{CH}_4", "\\text{CO}_{2}",
			"\\text{H}_{2}", "\\text{CO}", "\\text{T}");
	
	private List<ImageView> listImageViewVariaveis = Arrays.asList(imageViewz, imageViewCH4, imageViewCO2,
			imageViewH2, imageViewCO, imageViewT);
	
	private List<String> listStringsValues = Arrays.asList("\\text{Cp}_{mix}", "\\text{vz}", "\\text{\\rho}_{g}",
			"\\text{D}_{L}", "\\Delta\\text{H}", "\\lambda_{\\text{L}}", "\\text{X}_{\\text{CH}_4}");
	
	private List<ImageView> listImageViewValues= Arrays.asList(imageViewCp, imageViewvz, imageViewrhog,
			imageViewDL, imageViewDeltaH, imageViewLambdaL, imageViewXCH4);


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		progressBar.setVisible(false);
		buttonR.setVisible(false);
		textFildSearch.setVisible(false);
		label.setVisible(false);
		defineVariables();
	}

	@FXML
	private void btSimulateAction() {

		resultados.clear();
		SetSimulationEntity setSimulationEntity = new SetSimulationEntity();

		SimulationEntity simulationEntity = setSimulationEntity.generateEntity(entradaController, reatorController,
				reacoesController, energiaController);
 
		if (simulationEntity == null) {
			return;
		}
		L = simulationEntity.getLenght();
		label.setVisible(false);
		buttonR.setVisible(false);
		textFildSearch.setVisible(false);
		resultadosGridPane.getChildren().clear();
		equacoes.clear();
		tValues.clear();
		progressBar.setVisible(true);
		btSimular.setDisable(true);
		
		PDEBuilder pdeBuilder = new PDEBuilder();
		equacoes = pdeBuilder.pdeBuilder(simulationEntity, reacoesController, energiaController);

		List<String> listaVariaveis = new ArrayList<String>();
	
		Integer nParticoes = 1;
		try{
			nParticoes = Integer.parseInt(textAxialPartitions.getText().toString());
		}
		catch(Exception e) {
			String errorParameter = "Axial Partitions";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null, Alert.AlertType.ERROR);
		}
		
		final Integer particoes = nParticoes;

		Double z0 = 0.0;
		Double zf = 1.0;

		List<String> condicoesIniciais = new ArrayList<String>();

		condicoesIniciais.add(simulationEntity.getConcentrationCH4().toString());
	
		condicoesIniciais.add(simulationEntity.getConcentrationCO2().toString());
		condicoesIniciais.add("0.0");
		condicoesIniciais.add("0.0");
		
		Integer numeroEquacoes = equacoes.size();

		if(numeroEquacoes > 4) {
			condicoesIniciais.add(simulationEntity.getInletTemperature().toString());
		}
		
		List<List<String>> resultadoDiferencasFinitas = new ArrayList<List<String>>();

		listaVariaveis.add("t");

		List<String> variaveis = new ArrayList<String>();

		for (int i = 0; i < numeroEquacoes; i++) {
			String equacao = equacoes.get(i).split("=")[1];
			resultadoDiferencasFinitas
					.add(diferencasFinitas.diferencasFinitas(equacao, nParticoes, z0, zf, condicoesIniciais.get(i)));
			String variavel = equacoes.get(i).split("=")[0];
			variaveis.add(variavel);
		}

		for (int i = 0; i <= nParticoes; i++) {
			for (int j = 0; j < variaveis.size(); j++) {
				listaVariaveis.add(variaveis.get(j) + String.valueOf(i));
			}
		}
	
		List<String> funcoes = new ArrayList<String>();

		for (int i = 0; i < resultadoDiferencasFinitas.get(0).size(); i++) {
			for (int j = 0; j < numeroEquacoes; j++) {
				funcoes.add(resultadoDiferencasFinitas.get(j).get(i));
			}

		}

		List<Double> resultadosIniciais = new ArrayList<Double>();

		for (int i = 1; i < listaVariaveis.size(); i++) {
			resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(0)));
			resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(1)));
			resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(2)));
			resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(3)));
			if (equacoes.size() > 4) {
				resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(4)));
			}
		}

		RKF45Storage rkf45 = new RKF45Storage();
		
		rkf45.addObserver(this);

		Thread firstThread = new Thread(() -> {


			List<List<Double>> Resultados = rkf45.Resolve(funcoes, listaVariaveis, resultadosIniciais,
					Double.valueOf(textFinalTime.getText().toString()),
					Double.valueOf(textMinStep.getText().toString()), Double.valueOf(textMaxStep.getText().toString()));
		

			Platform.runLater(() -> {

				resultadosGridPane.getChildren().clear();
				resultadosGridPane.getColumnConstraints().clear();
				resultadosGridPane.getRowConstraints().clear();
				resultadosGridPane.setAlignment(Pos.TOP_LEFT);
				resultadosGridPane.setHgap(10);
				resultadosGridPane.setVgap(10);
				resultados.addAll(Resultados);
		
				for(int j = 0; j < listImageViewVariaveis.size()-1; j++) {
					resultadosGridPane.add(listImageViewVariaveis.get(j), j, 0);
				}
				
				if (equacoes.size() > 4) {
					resultadosGridPane.add(listImageViewVariaveis.get(listImageViewVariaveis.size()-1), 5, 0);
				}

				ColumnConstraints col1 = new ColumnConstraints();
				col1.setFillWidth(false);
				col1.setMinWidth(80);
				col1.setPrefWidth(80);
				col1.setMaxWidth(80);
				col1.setHalignment(HPos.CENTER);
				for (int i = 0; i < 7; i++) {
					resultadosGridPane.getColumnConstraints().add(col1);
				}
				
				
				for (int i = 0; i < resultados.get(resultados.size() - 1).size() / (equacoes.size()); i++) {
					Text node0 = new Text();
					Text node1 = new Text();
					Text node2 = new Text();
					Text node3 = new Text();
					Text node4 = new Text();

					node0.setText(formatter.format(i*simulationEntity.getLenght()/Double.valueOf(particoes)));
					node1.setText(formatter.format(resultados.get(resultados.size() - 1).get(equacoes.size() * i))
							.toString());
					node2.setText(formatter.format(resultados.get(resultados.size() - 1).get(equacoes.size() * i + 1))
							.toString());
					node3.setText(formatter.format(resultados.get(resultados.size() - 1).get(equacoes.size() * i + 2))
							.toString());
					node4.setText(formatter.format(resultados.get(resultados.size() - 1).get(equacoes.size() * i + 3))
							.toString());
					
					resultadosGridPane.add(node0, 0, i + 1);
					resultadosGridPane.add(node1, 1, i + 1);
					resultadosGridPane.add(node2, 2, i + 1);
					resultadosGridPane.add(node3, 3, i + 1);
					resultadosGridPane.add(node4, 4, i + 1);

					node0.setFont(Font.font("", 14.0));
					node1.setFont(Font.font("", 14.0));
					node2.setFont(Font.font("", 14.0));
					node3.setFont(Font.font("", 14.0));
					node4.setFont(Font.font("", 14.0));

					
					
					if (equacoes.size() > 4) {
						Text node5 = new Text();
						Double resultadoT = (Double
								.valueOf(resultados.get(resultados.size() - 1).get(equacoes.size() * i + 4)));
						node5.setText(formatter.format(resultadoT).toString());
						node5.setFont(Font.font("", 14.0));
						resultadosGridPane.add(node5, 5, i + 1);
					}

				}
				
				textFildSearch.setText(tValues.get(tValues.size() - 1).toString());
				label.setText(tValues.get(tValues.size() - 1).toString());

				displayValues(simulationEntity);
				

				
			});
			btSimular.setDisable(false);
			btSimular.setMinHeight(30);
			progressBar.setVisible(false);
			buttonR.setMinHeight(30);
			buttonR.setVisible(true);
			textFildSearch.setMinHeight(30);
			textFildSearch.setVisible(true);
			label.setVisible(true);
			


		});

		try {
			firstThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		firstThread.start();

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Double valor = Double.valueOf(evt.getNewValue().toString());
		progressBar.setProgress(valor);
		tValues.add(valor * Double.valueOf(textFinalTime.getText().toString()));
	}

	private Integer getList(Double value, List<Double> listD) {

		Double c = listD.stream().min(Comparator.comparingDouble(j -> Math.abs(j - value)))
				.orElseThrow(() -> new NoSuchElementException("No value present"));

		Integer pos = listD.indexOf(c);

		return pos;
	}

	@FXML
	private void buttonRAction() {
		resultadosGridPane.getChildren().clear();
		resultadosGridPane.getColumnConstraints().clear();
		resultadosGridPane.getRowConstraints().clear();
		resultadosGridPane.setAlignment(Pos.TOP_LEFT);
		resultadosGridPane.setHgap(10);
		resultadosGridPane.setVgap(10);
		Integer index = getList(Double.valueOf(textFildSearch.getText().toString()), tValues);
		
		if(Double.valueOf(textFildSearch.getText().toString()) >= Double.valueOf(textFinalTime.getText().toString())) {
			index = resultados.size() - 1;
			label.setText("Closiest t value calculated: "+formatter.format(Double.valueOf(textFinalTime.getText().toString())));
		}else {
			label.setText("Closiest t value calculated: "+formatter.format(tValues.get(index)).toString());
		}
		
		for(int j = 0; j < listImageViewVariaveis.size()-1; j++) {
			resultadosGridPane.add(listImageViewVariaveis.get(j), j, 0);
		}
		
		if (equacoes.size() > 4) {
			resultadosGridPane.add(listImageViewVariaveis.get(5), 5, 0);
		}

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(80);
		col1.setPrefWidth(80);
		col1.setMaxWidth(80);
		col1.setHalignment(HPos.CENTER);
		for (int i = 0; i < 7; i++) {
			resultadosGridPane.getColumnConstraints().add(col1);
		}

		for (int i = 0; i < resultados.get(resultados.size() - 1).size() / (equacoes.size()); i++) {
			Text node0 = new Text();
			Text node1 = new Text();
			Text node2 = new Text();
			Text node3 = new Text();
			Text node4 = new Text();
			
			Integer particoes = Integer.parseInt(textAxialPartitions.getText().toString());
			node0.setText(formatter.format(i*L/Double.valueOf(particoes)));
			node1.setText(formatter.format(resultados.get(index).get(equacoes.size() * i))
					.toString());
			node2.setText(formatter.format(resultados.get(index).get(equacoes.size() * i + 1))
					.toString());
			node3.setText(formatter.format(resultados.get(index).get(equacoes.size() * i + 2))
					.toString());
			node4.setText(formatter.format(resultados.get(index).get(equacoes.size() * i + 3))
					.toString());
			
			resultadosGridPane.add(node0, 0, i + 1);
			resultadosGridPane.add(node1, 1, i + 1);
			resultadosGridPane.add(node2, 2, i + 1);
			resultadosGridPane.add(node3, 3, i + 1);
			resultadosGridPane.add(node4, 4, i + 1);

			node0.setFont(Font.font("", 14.0));
			node1.setFont(Font.font("", 14.0));
			node2.setFont(Font.font("", 14.0));
			node3.setFont(Font.font("", 14.0));
			node4.setFont(Font.font("", 14.0));

			if (equacoes.size() > 4) {
				Text node5 = new Text();
				Double resultadoT = (Double
						.valueOf(resultados.get(index).get(equacoes.size() * i + 4)));
				node5.setText(formatter.format(resultadoT).toString());
				node5.setFont(Font.font("", 14.0));
				resultadosGridPane.add(node5, 5, i + 1);
			}

		}
		
		
		

	

	}


	
	private Integer k;
	private Double l;
	private void opSys() {
		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");
		if (newOs[0].contentEquals("Windows")) {
			k = 20;
			l = 1.0;
		} else {
			k = 25;
			l = 0.65;
		}
	}
	
	private void defineVariables() {
		opSys();
		for(int i = 0; i < listImageViewVariaveis.size(); i++) {
			final TeXFormula formula = new TeXFormula(listStringsVariaveis.get(i));
			java.awt.Image imageFormula = formula.createBufferedImage(TeXConstants.STYLE_TEXT, k,java.awt.Color.BLACK, null);
			Image fxFormula = SwingFXUtils.toFXImage((BufferedImage) imageFormula, null);
			listImageViewVariaveis.get(i).setFitWidth(fxFormula.getWidth()*l);
			listImageViewVariaveis.get(i).setImage(fxFormula);
			listImageViewVariaveis.get(i).setPreserveRatio(true);
		}
		
		for(int i = 0; i < valores.size(); i++) {
			final TeXFormula formula = new TeXFormula(listStringsValues.get(i));
			java.awt.Image imageFormula = formula.createBufferedImage(TeXConstants.STYLE_TEXT, k,java.awt.Color.BLACK, null);
			Image fxFormula = SwingFXUtils.toFXImage((BufferedImage) imageFormula, null);
			listImageViewValues.get(i).setFitWidth(fxFormula.getWidth()*l);
			listImageViewValues.get(i).setImage(fxFormula);
			listImageViewValues.get(i).setPreserveRatio(true);
		}
	
	}
	
	GetNodeByRowCollumn getNode = new GetNodeByRowCollumn();
	
	private void displayValues(SimulationEntity entity) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		valueGridPane.getChildren().clear();
		valueGridPane.getColumnConstraints().clear();
		valueGridPane.getRowConstraints().clear();
		valueGridPane.setAlignment(Pos.CENTER);
		valueGridPane.setHgap(20);
		valueGridPane.setVgap(20);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(80);
		col1.setPrefWidth(80);
		col1.setMaxWidth(80);
		col1.setHalignment(HPos.LEFT);
		for (int i = 0; i < 3; i++) {
			valueGridPane.getColumnConstraints().add(col1);
		}
		
		Text parameter = new Text("Parameter");
		parameter.setFont(Font.font("", 16.0));
		valueGridPane.add(parameter, 0, 0);
		
		Text unity = new Text("Unity");
		unity.setFont(Font.font("", 16.0));
		valueGridPane.add(unity, 1, 0);
		
		Text value = new Text("Value");
		value.setFont(Font.font("", 16.0));
		valueGridPane.add(value, 2, 0);
		
		for(int i = 0; i < listImageViewValues.size();i++) {
			valueGridPane.add(listImageViewValues.get(i), 0, i+1);
		}
		
		Text cpUnity = new Text("[kJ/kgK]");
		cpUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(cpUnity, 1, 1);
		
		Text cpValue = new Text(formatter.format(entity.getCpMix()).toString());
		cpValue.setFont(Font.font("", 16.0));
		valueGridPane.add(cpValue, 2, 1);
		
		Text vcUnity = new Text("[m/s]");
		vcUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(vcUnity, 1, 2);
		
		Text vzValue = new Text(formatter.format(entity.getVelocity()).toString());
		vzValue.setFont(Font.font("", 16.0));
		valueGridPane.add(vzValue, 2, 2);
		
		Text rhogUnity = new Text("[kg/m³]");
		rhogUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(rhogUnity, 1, 3);
		
		Text rhogValue = new Text(formatter.format(entity.getGasDensity()).toString());
		rhogValue.setFont(Font.font("", 16.0));
		valueGridPane.add(rhogValue, 2, 3);
		
		Text DLUnity = new Text("[m²/s]");
		DLUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(DLUnity, 1, 4);
		
		Text DLValue = new Text(formatter.format(entity.getDL()).toString());
		DLValue.setFont(Font.font("", 16.0));
		valueGridPane.add(DLValue, 2, 4);
		
		Text HUnity = new Text("[kJ/mol]");
		HUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(HUnity, 1, 5);
		
		Text HValue = new Text(formatter.format(entity.getEnthalpy()/1000.0).toString());
		HValue.setFont(Font.font("", 16.0));
		valueGridPane.add(HValue, 2, 5);
		
		
		Text LambdaLUnity = new Text("[kJ/msK]");
		LambdaLUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(LambdaLUnity, 1, 6);
		
		Constantes constantes  = new Constantes();
		Text LambdaLValue = new Text(formatter.format(constantes.CondTermEff(entity.getCH4Flow(), entity.getCO2Flow(), entity.getInletTemperature(), entity.getPorosity(), entity.getDiameter(), entity.getPressure())).toString());
		LambdaLValue.setFont(Font.font("", 16.0));
		valueGridPane.add(LambdaLValue, 2, 6);
		
		
		Text XUnity = new Text("[%]");
		XUnity.setFont(Font.font("", 16.0));
		valueGridPane.add(XUnity, 1, 7);
		
		
		String finalCHString = sVtoP(((Text) getNode.getNodeByRowColumnIndex(resultadosGridPane.getRowCount()-1, 1, resultadosGridPane)).getText().toString());
		Double finalCH4 = Double.valueOf(finalCHString);
		Double conversion = 100.0*(entity.getCH4Concentration()- finalCH4)/entity.getCH4Concentration();
		Text XValue = new Text(formatter.format(conversion));
		XValue.setFont(Font.font("", 16.0));
		valueGridPane.add(XValue, 2, 7);
		
		
	}

	private String sVtoP(String string) { 
		return string.replace(",", ".");
	}
	

	public void buttonExportAction() {
		Export export = new Export();
		if(resultadosGridPane.getChildren().size() > 1) {
			List<List<Double>> excelTab = new ArrayList<List<Double>>();
			for(int i = 1; i < resultadosGridPane.getChildren().size()/6; i++) {
				List<Double> excelRow = new ArrayList<Double>();
				for(int j = 0; j < 6; j++) {
					excelRow.add(Double.valueOf( ((Text)resultadosGridPane.getChildren().get(6*i+j)).getText().toString().replace(",", ".")));
				}
				excelTab.add(excelRow);
			}
			
			try {
				export.exportExcel(excelTab,energiaController.inletT.getText().toString(), Double.valueOf(textFildSearch.getText().toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}




