package newton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import entities.EstimationEntity;

public class MetodoSecante {
	
	FuncaoUtil funcao = new FuncaoUtil();

	public List<Double> metodoSecante(List<List<Double>> dadosExperimentais, List<Double> resultadosExperimentais,
			List<String> variaveis, List<String> parametros, String leiVelocidade, EstimationEntity entity) {

		List<Double> resultado = new ArrayList<Double>();

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(variaveis);

		todasVariaveis.addAll(parametros);

		// Expression r = new
		// ExpressionBuilder(leiVelocidade).variables(todasVariaveis).build();

		// List<Expression> rList = new ArrayList<Expression>();

		String somatorio = "";

		for (int j = 0; j < dadosExperimentais.get(0).size(); j++) {
			String soma = new String(leiVelocidade);
			for (int i = 0; i < dadosExperimentais.size(); i++) {
				soma = soma.replaceAll(variaveis.get(i), dadosExperimentais.get(i).get(j).toString());
			}
			somatorio += "(" + soma + "-" + resultadosExperimentais.get(j) + ")^2+";
		}

		String fObjetivo = somatorio.substring(0, somatorio.length() - 1);

		Random random = new Random();

		List<List<Double>> pontos = new ArrayList<List<Double>>();

		for (int i = 0; i < parametros.size() + 1; i++) {
			List<Double> ponto = new ArrayList<Double>();
			for (int j = 0; j < parametros.size(); j++) {
				ponto.add(random.nextDouble());
			}
			pontos.add(ponto);

		}

		List<List<Double>> derivadanosPontos = new ArrayList<List<Double>>();

		for (int i = 0; i < pontos.size(); i++) {
			List<Double> derivadaPonto = new ArrayList<Double>();
			derivadaPonto.add(1.0);
			for (int j = 0; j < pontos.get(i).size(); j++) {
				derivadaPonto.add(funcao.calculaDerivadaPrimeira(fObjetivo, pontos.get(i), parametros,
						parametros.get(j),entity));
			}
			derivadanosPontos.add(derivadaPonto);
		}

		System.out.println("Pontos " + derivadanosPontos);

		// Transposta transposta = new Transposta();

		// System.out.println(transposta.calculaTransposta(derivadanosPontos));

		return resultado;

	}

}
