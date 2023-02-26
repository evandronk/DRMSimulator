package enxame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import propriedadesfq.Constantes;

public class PSOUtil {
	
	Constantes constantes = new Constantes();

	public List<List<Double>> calcula(List<List<Double>> particulas, Integer nParticulas, Expression expressao,
			List<String> variaveisPSO, List<String> variaveisIndependentes, List<List<Double>> dados) {

		List<List<Double>> resultado = new ArrayList<List<Double>>();
		
		Integer nDados = dados.get(0).size();

		for (int i = 0; i < nParticulas; i++) {

			for (int j = 0; j < variaveisPSO.size(); j++) {
				expressao.setVariable(variaveisPSO.get(j), particulas.get(i).get(j));
			}
			

			List<Double> valorParticula = new ArrayList<Double>();

			for (int k = 0; k < nDados; k++) {
				expressao.setVariable("Kp", constantes.Kp(dados.get(0).get(k)));
				
				for (int j = 0; j < variaveisIndependentes.size(); j++) {

					expressao.setVariable(variaveisIndependentes.get(j), dados.get(j).get(k));

				}

				valorParticula.add(expressao.evaluate());

			}

			resultado.add(valorParticula);
		}

		return resultado;

	}

	public Double calculaErro(List<Double> dadosExperimentais, List<Double> dadosModelo) {

		Double resultado = 0.0;

		for (int i = 0; i < dadosExperimentais.size(); i++) {
			resultado += Math.pow(dadosExperimentais.get(i) - dadosModelo.get(i), 2);
		}

		return resultado;

	}

	public Double getMin(List<Double> list) {
		if (list == null || list.size() == 0) {
			return Double.MAX_VALUE;
		}

		return Collections.min(list);
	}

	public List<Double> returnMinMax(List<Double> lista1, List<Double> lista2) {

		List<Double> minimos = new ArrayList<Double>();
		minimos.add(Collections.min(lista1));
		minimos.add(Collections.min(lista2));

		List<Double> maximos = new ArrayList<Double>();
		maximos.add(Collections.max(lista1));
		maximos.add(Collections.max(lista2));

		List<Double> minMax = new ArrayList<Double>();

		minMax.add(Collections.min(minimos));
		minMax.add(Collections.max(maximos));

		return minMax;

	}

}
