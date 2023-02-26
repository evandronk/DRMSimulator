package gui.funcoesauxiliares;

import entities.SimulationEntity;
import gui.TabEnergyController;
import gui.TabReactionController;

public class KineticsBuilder {

	public String kineticsBuilder(SimulationEntity entity, TabReactionController reactionController, TabEnergyController energyController) {
		String equations = "";
		if (entity.getReaction() == 0) {
			if (!reactionController.checkBoxR.isSelected()) {
				equations = PLBuilderIrreversible(entity,energyController);
			} else {
				equations = PLBuilderReversible(entity,energyController);
			}
		} else if (entity.getReaction() == 1) {
			if (!reactionController.checkBoxR.isSelected()) {
				equations = ERBuilderIrreversible(entity,energyController);
			} else {
				equations = ERBuilderReversible(entity,energyController);
			}
		} else if (entity.getReaction() == 2) {
			if (!reactionController.checkBoxR.isSelected()) {
				equations = LHBuilderIrreversible(entity,energyController);
			} else {
				equations = LHBuilderReversible(entity,energyController);
			}
		}

		return equations;

	}

	private String PLBuilderIrreversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double m = entity.getParameters().get(2);
			Double n = entity.getParameters().get(3);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A + "*exp(-" + E / (8.31446 * entity.getInletTemperature()) + ")*[A0](t,z)^" + m + "*[B0](t,z)^"
					+ n;}
			else {
			kinetics = A + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*[A0](t,z)^" + m + "*[B0](t,z)^"+ n;
			}
		} else if (entity.getKinetics() == 1) {
			// not implemented
		}
		return kinetics;
	}

	private String PLBuilderReversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double m = entity.getParameters().get(2);
			Double n = entity.getParameters().get(3);
			Double p = entity.getParameters().get(4);
			Double q = entity.getParameters().get(5);
			Double Kp = entity.getParameters().get(6);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A + "*exp(-" + E / (8.31446 * entity.getInletTemperature()) + ")*([A0](t,z)^" + m + "*[B0](t,z)^"
					+ n + "-([C0](t,0)^" + p + "*[D0](t,z)^" + q + ")/" + Kp + ")";
			}else {
				kinetics = A + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*([A0](t,z)^"+ m +"*[B0](t,z)^"+n +"-([C0](t,z)^" + p + "*[D0](t,z)^" + q + ")/" + Kp + ")";
			}
		} else {

		}
		return kinetics;
	}

	private String ERBuilderIrreversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double KCH4 = entity.getParameters().get(2);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A + "*exp(-" + E / (8.31446 * entity.getInletTemperature()) + ")*([A0](t,z)*[B0](t,z))/(1+"
					+ KCH4 + ")";}
			else {
				kinetics = A + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*([A0](t,z)*[B0](t,z))/(1+"
						+ KCH4 + ")";
			}
		} else if (entity.getKinetics() == 1) {
			// not implemented
		}

		return kinetics;
	}

	private String ERBuilderReversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double KCH4 = entity.getParameters().get(2);
			Double Kp = entity.getParameters().get(3);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A + "*exp(-" + E / (8.31446 * entity.getInletTemperature())
					+ ")*([A0](t,z)*[B0](t,z)-[C0](t,z)^2*[D0](t,z)^2/" + Kp + ")/(1+" + KCH4 + ")";
			}else {
				kinetics = A + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*([A0](t,z)*[B0](t,z)-[C0](t,z)^2*[D0](t,z)^2/" + Kp + ")/(1+" + KCH4 + ")";
			}
		} else {
			// not implemented
		}

		return kinetics;
	}

	private String LHBuilderIrreversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double KCH4 = entity.getParameters().get(2);
			Double KCO2 = entity.getParameters().get(3);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A * KCH4 * KCO2 + "*exp(-" + E / (8.31446 * entity.getInletTemperature())
					+ ")*[A0](t,z)*[B0](t,z)/(1+[A0](t,z)*" + KCH4 + "+[B0](t,z)*" + KCO2 + ")^2";
			}else {
				kinetics = A * KCH4 * KCO2 + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*[A0](t,z)*[B0](t,z)/(1+[A0](t,z)*" + KCH4 + "+[B0](t,z)*" + KCO2 + ")^2";
			}
		} else if (entity.getKinetics() == 1) {
			// not implemented
		}

		return kinetics;
	}

	private String LHBuilderReversible(SimulationEntity entity, TabEnergyController energyController) {

		String kinetics = "";

		if (entity.getKinetics() == 0) {
			Double A = entity.getParameters().get(0);
			Double E = entity.getParameters().get(1);
			Double KCH4 = entity.getParameters().get(2);
			Double KCO2 = entity.getParameters().get(3);
			Double Kp = entity.getParameters().get(4);
			if(energyController.isotermicoCheckBox.isSelected()) {
			kinetics = A * KCH4 * KCO2 + "*exp(-" + E / (8.31446 * entity.getInletTemperature())
					+ ")*([A0](t,z)*[B0](t,z)-([C0](t,z)^2*[D0](t,z)^2/" + Kp + "))/(1+[A0](t,z)*" + KCH4
					+ "+[B0](t,z)*" + KCO2 + ")^2";
			}else {
				kinetics = A * KCH4 * KCO2 + "*exp(-" + E +"/ (8.31446*[T0](t,z)))*([A0](t,z)*[B0](t,z)-([C0](t,z)^2*[D0](t,z)^2/" + Kp + "))/(1+[A0](t,z)*" + KCH4
						+ "+[B0](t,z)*" + KCO2 + ")^2";
			}

		} else if (entity.getKinetics() == 1) {
			// not implemented
		}

		return kinetics;
	}

}
