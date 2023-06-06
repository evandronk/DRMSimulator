package gui.funcoesauxiliares;

import java.util.ArrayList;
import java.util.List;

import entities.SimulationEntity;
import gui.TabEnergyController;
import gui.TabInletController;
import gui.TabReactionController;
import gui.TabReactorController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import propriedadesfq.Constantes;
import util.Alerts;

public class SetSimulationEntity {

	public SimulationEntity generateEntity(TabInletController inletController, TabReactorController reactorController,
			TabReactionController reactionController, TabEnergyController energyController) {

		Alerts alert = new Alerts();
		SimulationEntity simulationEntity = new SimulationEntity();
		ConversionModule conversion = new ConversionModule();
		Constantes constantes = new Constantes();
		GetNodeByRowCollumn getnode = new GetNodeByRowCollumn();

		Double vCH4 = 0.0;
		try {
			vCH4 = conversion.flowConverter(Double.valueOf(inletController.CH4Flow.getText().toString()),
					inletController.comboBoxVazaoCH4.getSelectionModel().getSelectedIndex());
			simulationEntity.setCH4Flow(vCH4);
		} catch (Exception e) {
			String errorParameter = "Inlet Methane Flow";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double vCO2 = 0.0;
		try {
			vCO2 = conversion.flowConverter(Double.valueOf(inletController.CO2Flow.getText().toString()),
					inletController.comboBoxVazaoCO2.getSelectionModel().getSelectedIndex());
			simulationEntity.setCO2Flow(vCO2);
		} catch (Exception e) {
			String errorParameter = "Inlet Carbon Dioxide Flow";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double pressure = 0.0;
		try {
			pressure = Double.valueOf(inletController.pressure.getText().toString());
			simulationEntity.setPressure(pressure);
		} catch (Exception e) {
			String errorParameter = "Pressure";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double lenght = 0.0;
		try {
			lenght = conversion.lenghtConversion(Double.valueOf(reactorController.lenghtTxt.getText().toString()),
					reactorController.comboBoxComprimentoR.getSelectionModel().getSelectedIndex());
			simulationEntity.setLenght(lenght);
		} catch (Exception e) {
			String errorParameter = "Lenght";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double diameter = 0.0;
		try {
			diameter = conversion.lenghtConversion(Double.valueOf(reactorController.diameterTxt.getText().toString()),
					reactorController.comboBoxDiametroR.getSelectionModel().getSelectedIndex());
			simulationEntity.setDiameter(diameter);
		} catch (Exception e) {
			String errorParameter = "Diameter";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double porosity = 1.0;
		try {
			porosity = Double.valueOf(reactorController.porosityTxt.getText().toString());
			simulationEntity.setPorosity(porosity);
		} catch (Exception e) {
			String errorParameter = "Porosity";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double catalystDensity = 0.0;
		try {
			catalystDensity = Double.valueOf(reactorController.catalystDensityTxt.getText().toString());
			simulationEntity.setCatalystDensity(catalystDensity);
		} catch (Exception e) {
			String errorParameter = "Catalyst Density";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double inletT = 0.0;
		try {
			inletT = conversion.temperatureConverter(Double.valueOf(energyController.inletT.getText().toString()),
					energyController.temperaturaEntradaComboBox.getSelectionModel().getSelectedIndex());
			simulationEntity.setInletTemperature(inletT);
		} catch (Exception e) {
			String errorParameter = "Inlet Temperature";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double wallT = 0.0;
		try {
			wallT = conversion.temperatureConverter(Double.valueOf(energyController.extT.getText().toString()),
					energyController.temperaturaExternaComboBox.getSelectionModel().getSelectedIndex());
			simulationEntity.setWallTemperature(wallT);
		} catch (Exception e) {
			String errorParameter = "Wall Temperature";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double Utc = 0.0;
		try {
			Utc = Double.valueOf(energyController.UTCtxt.getText().toString());
			simulationEntity.setUtc(Utc);
		} catch (Exception e) {
			String errorParameter = "Utc";
			alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
					Alert.AlertType.ERROR);
			return null;
		}

		Double velocity = constantes.velocidade(vCH4, vCO2, diameter,inletT);
		simulationEntity.setVelocity(velocity);

		Double DL = constantes.DispAx(vCH4, vCO2, inletT, porosity, diameter, pressure);
		simulationEntity.setDL(DL);

		Double enthalpy = constantes.HReacao(inletT);
		simulationEntity.setEnthalpy(enthalpy);

		Double CH4Concentration = constantes.CH4density(pressure, inletT) * (vCH4 / (vCH4 + vCO2)) * (1.0 / (0.01604));
		simulationEntity.setConcentrationCH4(CH4Concentration);

		Double CO2Concentration = constantes.CO2density(pressure, inletT) * (vCO2 / (vCH4 + vCO2)) * (1.0 / (0.04401));
		simulationEntity.setConcentrationCO2(CO2Concentration);

		Double cpMix = constantes.Cpmix(vCH4, vCO2, inletT).get(2);
		simulationEntity.setCpMix(cpMix);

		Double gasDensity = constantes.densidade(vCH4, vCO2, pressure, inletT);
		simulationEntity.setGasDensity(gasDensity);

		Integer law = reactionController.comboBoxLeis.getSelectionModel().getSelectedIndex();
		simulationEntity.setReaction(law);

		Integer rate = reactionController.comboBoxLeiVelocidade.getSelectionModel().getSelectedIndex();
		simulationEntity.setKinetics(rate);

		List<Double> parameters = new ArrayList<Double>();
		Integer nParameters = reactionController.getGridPaneParametros().getChildren().size() / 2;

		for (int i = 0; i < nParameters; i++) {
			try {
				parameters.add(Double.valueOf(
						((TextField) getnode.getNodeByRowColumnIndex(1, i, reactionController.getGridPaneParametros()))
								.getText().toString()));
			} catch (Exception e) {
				String errorParameter = "Kinetic Parameter";
				alert.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null,
						Alert.AlertType.ERROR);
				return null;
			}
		}

		simulationEntity.setKineticsParameters(parameters);

		return simulationEntity;

	}

}
