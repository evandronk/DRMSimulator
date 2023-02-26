package newton;

import java.util.ArrayList;
import java.util.List;

public class OperacoesMatrizes {

	
	public List<List<Double>> removeLC(List<List<Double>> array, int linha, int coluna) {

		List<List<Double>> resultado = new ArrayList<List<Double>>();

		int size = array.size();

		for (int i = 0; i < size; i++) {
			if (i != linha) {
				List<Double> Linha = new ArrayList<Double>();
				for (int j = 0; j < size; j++) {
					if (j != coluna) {
						Linha.add(array.get(i).get(j));
					}
				}

				resultado.add(Linha);
			}

		}

		return resultado;

	}
	
	public List<List<Double>> calculaTransposta(List<List<Double>> matriz){
		
		List<List<Double>> transposta = new ArrayList<List<Double>>();
		
		for(int j = 0; j < matriz.get(0).size(); j++) {
			List<Double> coluna = new ArrayList<Double>();
			for(int i = 0; i < matriz.size(); i++) {
				coluna.add(matriz.get(i).get(j));
			}
			transposta.add(coluna);
		}
		
		return transposta;
		
	}
	

	public Double determinante(List<List<Double>> array) {

		Double det = 0.0;

		int size = array.size();

	

		if (size == 1) {
			det = array.get(0).get(0);

			return det;
		} else {

			for (int j = 0; j < size; j++) {
				det = det + Math.pow(-1, j + 2) * array.get(0).get(j) * determinante(removeLC(array, 0, j));

			}
			return det;
		}

	}
	
	public List<List<Double>> matrizInversa(List<List<Double>> matriz) {

		List<List<Double>> inversa = new ArrayList<List<Double>>();

		Double det = determinante(matriz);

		for (int i = 0; i < matriz.size(); i++) {
			List<Double> linha = new ArrayList<Double>();
			for (int j = 0; j < matriz.get(i).size(); j++) {
				linha.add(Math.pow(-1.0, i + j + 2) * determinante(removeLC(matriz, j, i)) / det);

			}
			inversa.add(linha);
		}

		return inversa;

	}
	
	public List<List<Double>> multiplicaMatrizes(List<List<Double>> A, List<List<Double>> B){
		
		List<List<Double>> resultado = new ArrayList<List<Double>>();
	
		for(int i = 0; i < A.size(); i++) {
			List<Double> linha = new ArrayList<Double>();
			for(int j = 0; j < B.get(i).size(); j++) {
				Double soma  = 0.0;
				for(int k = 0; k < B.size(); k++) {
					soma = soma + A.get(i).get(k)*B.get(k).get(j);
				}
				linha.add(soma);
			}
			resultado.add(linha);
		}

		return resultado;
	}
	
}
