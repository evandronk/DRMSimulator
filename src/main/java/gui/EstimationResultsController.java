package gui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import entities.EstimationEntity;
import gui.funcoesauxiliares.ConvertToFxImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import propriedadesfq.Estatistica;

public class EstimationResultsController implements Initializable {

	@FXML
	private GridPane gridPaneResultados = new GridPane();

	@FXML
	private GridPane gridPaneParametrosR = new GridPane();
	
	@FXML
	private VBox vboxMain = new VBox();

	private Estatistica estatistica = new Estatistica();

	private NumberAxis xAxis = new NumberAxis();

	private NumberAxis yAxis = new NumberAxis();

    private LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

    private Double[] ab = new Double[2];
    
    private DecimalFormat df = new DecimalFormat("0.0000");
	
    private DecimalFormat cientificNotation = new DecimalFormat("0.####E0");
    
    private ConvertToFxImage convertToFxImage = new ConvertToFxImage();

    private List<String> listaParametros = new ArrayList<String>();
    
    private Integer numeroParametros = 0;
    
    private List<Double> resultadosExperimentaisInput = new ArrayList<Double>();
    
    private List<Double> resultadoModeloInput = new ArrayList<Double>();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void loadElements(EstimationEntity entity) {
		
		entity.getParametrosString().stream().forEach(t -> listaParametros.add(t));
		entity.getrExp().stream().forEach(t -> resultadosExperimentaisInput.add(t));
		entity.getrMod().stream().forEach(t -> resultadoModeloInput.add(t));
		numeroParametros = entity.getParametros().size();
		
		ab = estatistica.Pearson(resultadosExperimentaisInput, resultadoModeloInput, numeroParametros);
	
		
		Text textTituloExp = new Text();
		textTituloExp.setText("Experimental");
		textTituloExp.setFont(Font.font("System", 13));
		gridPaneResultados.add(textTituloExp, 0, 0);
		
		Text textTituloMod = new Text();
		textTituloMod.setText("Modelo");
		textTituloMod.setFont(Font.font("System", 13));
		gridPaneResultados.add(textTituloMod, 1, 0);
		
		Text textTituloErro = new Text();
		textTituloErro.setText("Erro Absoluto");
		textTituloErro.setFont(Font.font("System", 13));
		gridPaneResultados.add(textTituloErro, 2, 0);
		
		
		
		
		for (int j = 0; j < resultadosExperimentaisInput.size(); j++) {
			
			int k = j + numeroParametros + 1;
			
			Text textResultadoExperimental = new Text();
			textResultadoExperimental.setText(df.format(resultadosExperimentaisInput.get(j)));
			gridPaneResultados.add(textResultadoExperimental, 0, j+1);
			
			Text textResultadoModelo = new Text();
			textResultadoModelo.setText(df.format(resultadoModeloInput.get(k)));
			gridPaneResultados.add(textResultadoModelo, 1, j+1);
			
			Text textErroAbs = new Text();
			Double erroAbs = Math.abs(resultadoModeloInput.get(k)-resultadosExperimentaisInput.get(j));
			textErroAbs.setText(df.format(erroAbs));
			gridPaneResultados.add(textErroAbs, 2, j+1);
			
		
			
		}
		
		
		
		Text textTituloParametro = new Text();
		textTituloParametro.setText("Parâmetro");
		textTituloParametro.setFont(Font.font("System", 13));

		gridPaneParametrosR.add(textTituloParametro, 0, 0);
		
		Text textTituloParametroValor = new Text();
		textTituloParametroValor.setText("Valor Estimado");
		textTituloParametroValor.setFont(Font.font("System", 13));
		gridPaneParametrosR.add(textTituloParametroValor, 1, 0);
		
		
		String sysOs = System.getProperty("os.name");
		String[] newOs = sysOs.split(" ");
		Integer k = 0;
		Double fator = 1.0;
		if (newOs[0].contentEquals("Windows")) {
			k = 16;
			fator = 1.0;
		} else {
			k = 25;
			fator = 0.65;
		}

		
		for (int j = 0; j < numeroParametros; j++) {
			
			Text textParametro = new Text();
			textParametro.setText(cientificNotation.format(resultadoModeloInput.get(j)));
			gridPaneParametrosR.add(textParametro,1,j+1);
			
			
			ImageView imageView = new ImageView();
			final String parametro = listaParametros.get(j);
			final TeXFormula parametroTex = new TeXFormula(parametro);
			BufferedImage parametroBImage = (BufferedImage) parametroTex.createBufferedImage(TeXConstants.STYLE_TEXT, k,
					java.awt.Color.BLACK, null);
			Image parametroImage = convertToFxImage.convertToFxImage(parametroBImage);
			imageView.setImage(parametroImage);
			imageView.setFitWidth(parametroImage.getWidth()*fator);
			imageView.setPreserveRatio(true);
			gridPaneParametrosR.add(imageView,0,j+1);
		}

		setConstraints();
		setChart(resultadosExperimentaisInput, resultadoModeloInput, numeroParametros);

		estatistica.Hessiana(entity);
		
	}
	


	private void setConstraints() {

		gridPaneResultados.getColumnConstraints().clear();
		gridPaneResultados.getRowConstraints().clear();
		gridPaneResultados.setAlignment(Pos.TOP_CENTER);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(60);
		col1.setPrefWidth(60);
		col1.setMaxWidth(60);
		col1.setHalignment(HPos.CENTER);
		
		for (int i = 0; i < 3; i++) {
			gridPaneResultados.getColumnConstraints().add(col1);
		}
		
		gridPaneResultados.setHgap(30);
		gridPaneResultados.setVgap(10);
		
		
		
		gridPaneParametrosR.getColumnConstraints().clear();
		gridPaneParametrosR.getRowConstraints().clear();
		gridPaneParametrosR.setAlignment(Pos.TOP_CENTER);
		gridPaneParametrosR.getColumnConstraints().add(col1);
		
		gridPaneParametrosR.setHgap(30);
		gridPaneParametrosR.setVgap(10);
		

	}
	
	public void setChart(List<Double> resultadosExperimentaisInput,	List<Double> resultadoModeloInput,Integer numeroParametros) {
		
		Double minimoExp = resultadosExperimentaisInput.stream().mapToDouble(v->v).min().orElseThrow();
		Double maximoExp = resultadosExperimentaisInput.stream().mapToDouble(v->v).max().orElseThrow();
		
		
		List<Double> onlyResultModelo = resultadoModeloInput.subList(numeroParametros+1, resultadoModeloInput.size());
		
	
		Double minimoMod = onlyResultModelo.stream().mapToDouble(v->v).min().orElseThrow();
		Double maximoMod = onlyResultModelo.stream().mapToDouble(v->v).max().orElseThrow();
		
		List<Double> extremos = Arrays.asList(minimoExp, minimoMod,maximoExp,maximoMod);
		
		Double minimo = extremos.stream().mapToDouble(v->v).min().orElseThrow();
		Double maximo = extremos.stream().mapToDouble(v->v).max().orElseThrow();
		
		XYChart.Series<Number,Number> series = new XYChart.Series<>();
		series.setName("Reta y ="+df.format(ab[0])+ "x+"+df.format(ab[1]));
		
		xAxis.setAutoRanging(false);
		yAxis.setAutoRanging(false);
		Integer extra = 20;
		xAxis.setLowerBound(Math.round(minimo-extra));
		xAxis.setUpperBound(Math.round(maximo+5*extra));
		yAxis.setLowerBound(Math.round(minimo-extra));
		yAxis.setUpperBound(Math.round(maximo+5*extra));
		xAxis.setTickUnit(Math.round((maximo-minimo+10*extra)/5));
		yAxis.setTickUnit(Math.round((maximo-minimo+10*extra)/5));
		
		XYChart.Series<Number,Number> pontos = new XYChart.Series<>();
		pontos.setName("Modelo (eixo-y) vs Experimental (eixo-x)");
		
		for (int j = 0; j < resultadosExperimentaisInput.size(); j++) {
			pontos.getData().add(new XYChart.Data<Number,Number>(resultadosExperimentaisInput.get(j),resultadoModeloInput.get(j+numeroParametros+1)));
		}
		series.getData().add(new XYChart.Data<Number,Number>(Math.round(minimo)-2*extra,Math.round(ab[0]*Math.round(minimo)-2*extra)+ab[1]));
		series.getData().add(new XYChart.Data<Number,Number>(Math.round(maximo)+5*extra,Math.round(ab[0]*Math.round(maximo)+5*extra)+ab[1]));
		
		lineChart.getData().add(pontos);
		lineChart.getData().add(series);
		
		lineChart.getStylesheets().add(getClass().getResource("/gui/css/chart.css").toExternalForm());
		vboxMain.getChildren().add(lineChart);		
		
		lineChart.minWidth(500);
		lineChart.prefWidth(500);
		lineChart.maxWidth(500);
		

	}



}
