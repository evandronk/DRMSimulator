package newton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.EstimationEntity;

public class NewtonModificado {
	
	FuncaoUtil funcao = new FuncaoUtil();
	
	OperacoesMatrizes matriz = new OperacoesMatrizes();
	
	public List<Double> newtonModificado(List<List<Double>> dadosExperimentais, List<Double> resultadosExperimentais,
			List<String> variaveis, List<String> parametros, String leiVelocidade, List<Double> pontoInicial, EstimationEntity entity) {

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(variaveis);

		todasVariaveis.addAll(parametros);

		String somatorio = "(" + leiVelocidade + "-" + resultadosExperimentais.get(0) + ")^2";
		for (int i = 0; i < dadosExperimentais.size(); i++) {
			somatorio = somatorio.replaceAll(variaveis.get(i), dadosExperimentais.get(i).get(0).toString());
		}

		for (int j = 1; j < dadosExperimentais.get(0).size(); j++) {
			String soma = new String(leiVelocidade);
			for (int i = 0; i < dadosExperimentais.size(); i++) {
				soma = soma.replaceAll(variaveis.get(i), dadosExperimentais.get(i).get(j).toString());
			}
			somatorio += "(" + soma + "-" + resultadosExperimentais.get(j) + ")^2";
		}

		System.out.println("Ponto Inicial" + pontoInicial);

		Integer nIteracoes = 100;

		for (int iteracao = 0; iteracao < nIteracoes; iteracao++) {

			String fObjetivo = somatorio;

			List<List<Double>> matrizHessiana = new ArrayList<List<Double>>();

			for (int i = 0; i < parametros.size(); i++) {
				List<Double> linhaHessiana = new ArrayList<Double>();
				for (int j = 0; j < parametros.size(); j++) {
					linhaHessiana.add(funcao.calculaDerivadaSegunda(fObjetivo, pontoInicial, parametros,
							parametros.get(i), parametros.get(j),entity));
				}
				matrizHessiana.add(linhaHessiana);
			}

			Double det = matriz.determinante(matrizHessiana);

			List<List<Double>> MatrizInversa = new ArrayList<List<Double>>();

			if (det != 0) {

				MatrizInversa = matriz.matrizInversa(matrizHessiana);

			} else {

				System.out.println(matrizHessiana);
				System.out.println("O determinante é igual a zero");
				return pontoInicial;
			}

			List<Double> novoPonto = new ArrayList<Double>();

			List<List<Double>> vetorDerivada = new ArrayList<List<Double>>();

			for (int i = 0; i < pontoInicial.size(); i++) {
				List<Double> novalista = new ArrayList<Double>();
				novalista.add(funcao.calculaDerivadaPrimeira(fObjetivo, pontoInicial, parametros,
						parametros.get(i),entity));
				vetorDerivada.add(novalista);
			}

			for (int i = 0; i < pontoInicial.size(); i++) {
				novoPonto.add(i, pontoInicial.get(i)
						- matriz.multiplicaMatrizes(MatrizInversa, vetorDerivada).get(i).get(0));
			}

			pontoInicial = novoPonto;
		}
		
		pontoInicial.add(funcao.calculaFuncaoNoPonto(pontoInicial, parametros, somatorio,entity));
		
		for(int i = 0; i < dadosExperimentais.get(0).size(); i++) {
			pontoInicial.add(funcao.calculaFuncaoNoPonto(pontoInicial, variaveis, leiVelocidade,entity));
		}

		return pontoInicial;

	}

}
