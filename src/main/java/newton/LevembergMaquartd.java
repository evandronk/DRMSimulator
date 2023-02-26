package newton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.EstimationEntity;

public class LevembergMaquartd {
	
	FuncaoUtil funcao = new FuncaoUtil();
	
	OperacoesMatrizes matriz = new OperacoesMatrizes();

	public List<Double> newtonModificado(List<List<Double>> dadosExperimentais, List<Double> resultadosExperimentais,
			List<String> variaveis, List<String> parametros, String leiVelocidade, List<Double> pontoInicial, EstimationEntity entity) {

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(variaveis);

		todasVariaveis.addAll(parametros);

		String somatorio = "";
		for (int j = 0; j < dadosExperimentais.get(0).size(); j++) {
			String soma = new String(leiVelocidade);
			for (int i = 0; i < dadosExperimentais.size(); i++) {
				soma = soma.replaceAll(variaveis.get(i), dadosExperimentais.get(i).get(j).toString());
			}
			somatorio += "(" + soma + "-" + resultadosExperimentais.get(j) + ")^2+";
		}

		System.out.println("Ponto Inicial" + pontoInicial);

		Integer nIteracoes = 200;

		for (int iteracao = 0; iteracao < nIteracoes; iteracao++) {

			String fObjetivo = somatorio.substring(0, somatorio.length() - 1);

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

			FuncaoUtil funcaonoPonto = new FuncaoUtil();

			List<List<Double>> MatrizInversa = new ArrayList<List<Double>>();

			for (int i = 0; i > matrizHessiana.size(); i++) {
				// matrizHessiana.get(i).set(i, matrizHessiana.get(i).get(i)+ 2 *
				// funcaonoPonto.calculaFuncaoNoPonto(pontoInicial, parametros, fObjetivo));
				matrizHessiana.get(i).set(i, matrizHessiana.get(i).get(i) + 10);
			}

			// MatrizInversa = inversa.matrizInversa(matrizHessiana);

			if (det != 0) {

				MatrizInversa = matriz.matrizInversa(matrizHessiana);

			} else {
				for (int i = 0; i > matrizHessiana.size(); i++) {
					matrizHessiana.get(i).set(i, matrizHessiana.get(i).get(i)
							+ 2 * funcaonoPonto.calculaFuncaoNoPonto(pontoInicial, parametros, fObjetivo,entity));
				}
				MatrizInversa = matriz.matrizInversa(matrizHessiana);
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

		return pontoInicial;
	}

}
