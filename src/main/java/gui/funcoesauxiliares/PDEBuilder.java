package gui.funcoesauxiliares;

import java.util.ArrayList;
import java.util.List;

import entities.SimulationEntity;
import gui.TabEnergyController;
import gui.TabReactionController;
import propriedadesfq.Constantes;

public class PDEBuilder {

	public List<String> pdeBuilder(SimulationEntity entity, TabReactionController reactionController,
			TabEnergyController energyController) {

		List<String> equations = new ArrayList<String>();

		KineticsBuilder kineticsBuilder = new KineticsBuilder();
		Constantes constantes = new Constantes();

		Double Q1 = entity.getCH4Flow();
		Double Q2 = entity.getCO2Flow();
		Double Dt = entity.getDiameter();
		Double L = entity.getLenght();
		Double Tin = entity.getInletTemperature();
		Double Tw = entity.getWallTemperature();
		Double porosity = entity.getPorosity();
		Double rhoc = entity.getCatalystDensity();
		Double rhog = entity.getGasDensity();
		Double Utc = entity.getUtc();
		Double DeltaH = constantes.HReacao(Tin);
		Double P = entity.getPressure();

		Double vz = constantes.velocidade(Q1, Q2, Dt);
		Double DL = constantes.DispAx(Q1, Q2, Tin, porosity, Dt, P);
		Double CpMix = constantes.Cpmix(Q1, Q2, Tin).get(2);
		String reaction = kineticsBuilder.kineticsBuilder(entity, reactionController, energyController);

		Double tConv = -vz / L;
		Double tDiff = DL / (L * L);
		Double tReac = rhoc * 1000.0 / (3600 * 16.04 * porosity);

		String a = "A= " + tConv + "*d[A0](t,z)/dz +" + tDiff + "*d2[A0](t,z)/dz2+" + -tReac + "*" + reaction;
		String b = "B= " + tConv + "*d[B0](t,z)/dz +" + tDiff + "*d2[B0](t,z)/dz2+" + -tReac + "*" + reaction;
		String c = "C= " + tConv + "*d[C0](t,z)/dz +" + tDiff + "*d2[C0](t,z)/dz2+" + 2 * tReac + "*" + reaction;
		String d = "D= " + tConv + "*d[D0](t,z)/dz +" + tDiff + "*d2[D0](t,z)/dz2+" + 2 * tReac + "*" + reaction;

		equations.add(a);
		equations.add(b);
		equations.add(c);
		equations.add(d);

		if (!energyController.isotermicoCheckBox.isSelected()) {

			Double condeff = constantes.CondTermEff(Q1, Q2, Tin, porosity, Dt, P);
			Double tTimeT = rhog * CpMix;
			Double tReacT = (DeltaH * rhoc / (16.04 * 3600.0)) / tTimeT;
			Double tDiffT = (condeff / (L * L)) / tTimeT;
			Double tConvT = (rhog * CpMix * vz / (L)) / tTimeT;
			Double tUTC = (4.0 * Utc / (3600.0 * Dt)) / tTimeT;

			String e = "T= -(" + tReacT + "*" + reaction + "-" + tDiffT + "*d2[T0](t,z)/dz2" + "+" + tConvT
					+ "*d[T0](t,z)/dz+" + tUTC + "*([T0](t,z)-" + Tw + "))";

			equations.add(e);

		}

		return equations;

	}

}
