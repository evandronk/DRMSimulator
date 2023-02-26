package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import gui.funcoesauxiliares.LoadView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SimulationController implements Initializable {

	LoadView loadView = new LoadView();

	@FXML
	public Tab tabEntrada = new Tab();

	@FXML
	private Tab tabReator = new Tab();

	@FXML
	private Tab tabReacoes = new Tab();

	@FXML
	private Tab tabEnergia = new Tab();

	//@FXML
	//private Tab tabMomento = new Tab();

	@FXML
	private Tab tabSimular = new Tab();

	@FXML
	public TabPane tabPaneSimulador = new TabPane();

	public synchronized <T> T loadTabs(String absoluteName, Tab tab, VBox icon, Consumer<T> initializingAction) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));

		VBox vbox = new VBox();

		try {
			vbox = loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}

		tab.setContent(vbox);

		tab.setGraphic(icon);

		T controller = loader.getController();

		initializingAction.accept(controller);

		return controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
		List<VBox> listVBoxes = createVBoxes();

		TabInletController entradaController = loadTabs("/gui/TabEntrada.fxml", tabEntrada, listVBoxes.get(0),
				(TabInletController controllerEntrada) -> {

				});

		TabReactorController reatorController = loadTabs("/gui/TabReator.fxml", tabReator, listVBoxes.get(1),
				(TabReactorController controller) -> {

				});

		TabReactionController reacoesController = loadTabs("/gui/TabReacoes.fxml", tabReacoes, listVBoxes.get(2), 
				(TabReactionController controller) -> {
		});

		TabEnergyController energiaController = loadTabs("/gui/TabEnergia.fxml", tabEnergia, listVBoxes.get(3),
				(TabEnergyController controller) -> {

				});

		//TabMomentoController momentoController = loadTabs("/gui/TabMomento.fxml", tabMomento, listVBoxes.get(4), (TabMomentoController controller) -> {
		//});

		loadTabs("/gui/TabSimular.fxml", tabSimular, listVBoxes.get(4), (TabSimulateController controller) -> {
			controller.entradaController = entradaController;
			controller.energiaController = energiaController;
			controller.reatorController = reatorController;
			controller.reacoesController = reacoesController;
			//controller.momentoController = momentoController;
			
		});

	}

	private List<VBox> createVBoxes() {

		List<VBox> listVBoxes = new ArrayList<VBox>();

		Image imagemEntrada = new Image("/images/1.png");
		ImageView imagemEntrada1 = new ImageView();
		ImageView imagemEntrada2 = new ImageView();
		imagemEntrada1.setImage(imagemEntrada);
		imagemEntrada1.setFitHeight(55);
		imagemEntrada1.setPreserveRatio(true);
		imagemEntrada2.setImage(imagemEntrada);
		imagemEntrada2.setFitHeight(55);
		imagemEntrada2.setPreserveRatio(true);
		HBox hBoxEntrada = new HBox();
		hBoxEntrada.setPrefWidth(90);
		hBoxEntrada.setAlignment(Pos.CENTER);
		hBoxEntrada.getChildren().add(imagemEntrada1);
		//hBoxEntrada.getChildren().add(imagemEntrada2);
		Text textoEntrada = new Text();
		textoEntrada.setText("Inlet Flows");
		textoEntrada.setFont(Font.font("System", 13));
		VBox vBoxEntrada = new VBox();
		vBoxEntrada.setPrefHeight(90);
		vBoxEntrada.getChildren().add(hBoxEntrada);
		vBoxEntrada.getChildren().add(textoEntrada);
		vBoxEntrada.setSpacing(10);
		vBoxEntrada.setAlignment(Pos.BOTTOM_CENTER);
		listVBoxes.add(vBoxEntrada);
		

		Image imagemReator = new Image("/images/2.png");
		ImageView imagemReatorView = new ImageView();
		imagemReatorView.setImage(imagemReator);
		imagemReatorView.setFitHeight(60);
		imagemReatorView.setPreserveRatio(true);
		HBox hBoxReator = new HBox();
		hBoxReator.setPrefWidth(80);
		hBoxReator.setAlignment(Pos.CENTER);
		hBoxReator.getChildren().add(imagemReatorView);
		Text textoReator = new Text();
		textoReator.setText("Reactor");
		textoReator.setFont(Font.font("System", 13));
		VBox vBoxReator = new VBox();
		vBoxReator.setPrefHeight(90);
		vBoxReator.getChildren().add(hBoxReator);
		vBoxReator.getChildren().add(textoReator);
		vBoxReator.setSpacing(10);
		vBoxReator.setAlignment(Pos.BOTTOM_CENTER);
		listVBoxes.add(vBoxReator);

		Image imagemReacao = new Image("/images/3.png");
		ImageView imagemReacaoView = new ImageView();
		imagemReacaoView.setImage(imagemReacao);
		imagemReacaoView.setFitHeight(60);
		imagemReacaoView.setPreserveRatio(true);
		HBox hBoxReacao = new HBox();
		hBoxReacao.setAlignment(Pos.CENTER);
		hBoxReacao.getChildren().add(imagemReacaoView);
		Text textoReacao = new Text();
		textoReacao.setText("Kinetics");
		textoReacao.setFont(Font.font("System", 13));
		VBox vBoxReacao = new VBox();
		vBoxReacao.setPrefHeight(90);
		vBoxReacao.getChildren().add(hBoxReacao);
		vBoxReacao.getChildren().add(textoReacao);
		vBoxReacao.setSpacing(10);
		vBoxReacao.setAlignment(Pos.BOTTOM_CENTER);
		listVBoxes.add(vBoxReacao);

		Image imagemEnergia = new Image("/images/calor.png");
		ImageView imagemEnergiaView = new ImageView();
		imagemEnergiaView.setImage(imagemEnergia);
		imagemEnergiaView.setFitHeight(60);
		imagemEnergiaView.setPreserveRatio(true);
		HBox hBoxEnergia = new HBox();
		hBoxEnergia.setAlignment(Pos.CENTER);
		hBoxEnergia.getChildren().add(imagemEnergiaView);
		hBoxEnergia.setPrefWidth(55);
		Text textoEnergia = new Text();
		textoEnergia.setText("Heat");
		textoEnergia.setFont(Font.font("System", 13));
		VBox vBoxEnergia = new VBox();
		vBoxEnergia.setPrefHeight(55);
		vBoxEnergia.setPrefWidth(55);
		vBoxEnergia.setAlignment(Pos.BOTTOM_CENTER);
		vBoxEnergia.getChildren().add(hBoxEnergia);
		vBoxEnergia.getChildren().add(textoEnergia);
		vBoxEnergia.setSpacing(10);
		listVBoxes.add(vBoxEnergia);

		/*
		Image imagemMovimento = new Image("/images/movimento.png");
		ImageView imagemMovimentoView = new ImageView();
		imagemMovimentoView.setImage(imagemMovimento);
		imagemMovimentoView.setFitHeight(50);
		imagemMovimentoView.setPreserveRatio(true);
		HBox hBoxMovimento = new HBox();
		hBoxMovimento.setAlignment(Pos.CENTER);
		hBoxMovimento.getChildren().add(imagemMovimentoView);
		Text textoMovimento = new Text();
		textoMovimento.setText("Momento");
		textoMovimento.setFont(Font.font("System", 13));
		VBox vBoxMovimento = new VBox();
		vBoxMovimento.setPrefHeight(90);
		vBoxMovimento.setAlignment(Pos.BOTTOM_CENTER);;
		vBoxMovimento.getChildren().add(hBoxMovimento);
		vBoxMovimento.getChildren().add(textoMovimento);
		vBoxMovimento.setSpacing(10);
		listVBoxes.add(vBoxMovimento);*/
		
		Image imagemSimular = new Image("/images/5.png");
		ImageView imagemSimularView = new ImageView();
		imagemSimularView.setImage(imagemSimular);
		imagemSimularView.setFitHeight(55);
		imagemSimularView.setPreserveRatio(true);
		HBox hBoxSimular = new HBox();
		hBoxSimular.setAlignment(Pos.CENTER);
		hBoxSimular.getChildren().add(imagemSimularView);
		Text textoSimular = new Text();
		textoSimular.setText("Simulate");
		textoSimular.setFont(Font.font("System", 13));
		VBox vBoxSimular = new VBox();
		vBoxSimular.setPrefHeight(90);
		vBoxSimular.setAlignment(Pos.BOTTOM_CENTER);
		vBoxSimular.getChildren().add(hBoxSimular);
		vBoxSimular.getChildren().add(textoSimular);
		vBoxSimular.setSpacing(10);
		listVBoxes.add(vBoxSimular);

		return listVBoxes;

	}

}
