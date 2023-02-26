package gui;

import gui.funcoesauxiliares.LoadView;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MainViewController {

	LoadView loadView = new LoadView();

	@FXML
	MenuItem pseudoHomogeneous = new Menu();

	@FXML
	MenuItem menuPotencias = new MenuItem();

	@FXML
	MenuItem menuEleyRideal = new MenuItem();

	@FXML
	MenuItem menuLagmuir = new MenuItem();
	
	@FXML
	MenuItem menuLicense = new MenuItem();
	

	@FXML
	private void menuAboutAction() {
		loadView.loadView("/gui/About.fxml", (AboutController controller) -> {
			
		});
	}
	



	@FXML
	private void pseudoHomogeneousAction() {
		loadView.loadView("/gui/Simulacao.fxml", (SimulationController controller) -> {
			
		});
	}

	@FXML
	private void menuLangmuirAction() {
		loadView.loadView("/gui/Estimacao.fxml", (EstimationController controller) -> {
			String selecao = "langmuir";
			controller.load(selecao);
		});
	}

	@FXML
	private void onMenuEleyRidealAction() {

		loadView.loadView("/gui/Estimacao.fxml", (EstimationController controller) -> {
			String selecao = "eley";
			controller.load(selecao);
		});


	}

	@FXML
	private void onMenuPotenciasAction() {

		loadView.loadView("/gui/Estimacao.fxml", (EstimationController controller) -> {
			String selecao = "potencias";
			controller.load(selecao);
			
		});

	}



/*	private synchronized <T> void loadView(String absoluteName, String title) {

		try {

			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.show();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}*/

}
