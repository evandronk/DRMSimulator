package propriedadesfq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.EstimationEntity;
import newton.FuncaoUtil;

public class Estatistica {

	FuncaoUtil funcao =  new FuncaoUtil();
	
	public Double Hessiana(EstimationEntity entity) {

		Double sqRes = 0.0;

		Double sqExp = 0.0;

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(entity.getVariaveisIndepdendentes());

		todasVariaveis.addAll(entity.getParametrosEstimacao());
		
		String fObj = "("+entity.getrExp().get(0)+"-"+entity.getExpressao()+")^2";
		for(int i = 0; i < entity.getVariaveisIndepdendentes().size(); i ++) {	
			fObj = fObj.replace(entity.getVariaveisIndepdendentes().get(i), entity.getDados().get(i).get(0).toString());
		}
		for(int i = 1; i < entity.getrExp().size(); i++) {
			fObj += "+("+entity.getrExp().get(i)+"-"+entity.getExpressao()+")^2";
			for(int j = 0; j < entity.getVariaveisIndepdendentes().size(); j++) {		
				fObj = fObj.replace(entity.getVariaveisIndepdendentes().get(j), entity.getDados().get(j).get(i).toString());
			}
		}

		List<Double> ponto = new ArrayList<Double>();
		for(int i = 0; i < entity.getParametrosEstimacao().size(); i++) {
			ponto.add(entity.getrMod().get(i));
		}
		
		//List<List<Double>> hessiana = new ArrayList<List<Double>>();
				
		/*System.out.println(entity.getParametrosEstimacao());
		for(int i = 0; i < entity.getParametrosEstimacao().size(); i++) {
			List<Double> linha = new ArrayList<Double>();
			for(int j = 0; j < entity.getParametrosEstimacao().size(); j++) {
				linha.add(funcao.calculaDerivadaSegunda(fObj,ponto,entity.getParametrosEstimacao(),entity.getParametrosEstimacao().get(i),entity.getParametrosEstimacao().get(j),entity));
			}	
			hessiana.add(linha);
		}*/
		
		/*for (int i = 0; i < entity.getDados().get(0).size(); i++) {
			Expression expressao = new ExpressionBuilder(entity.getExpressao()).variables(todasVariaveis).build();
			for (int j = 0; j < entity.getDados().size(); j++) {
				expressao.setVariable(entity.getVariaveisIndepdendentes().get(j), entity.getDados().get(j).get(i));
			};
			listaExpressoes.add(expressao);
		};
		
		
		
		
		for(int i = 0; i < listaExpressoes.size(); i++) {
			for(int j = 0; j < entity.getParametrosEstimacao().size(); j++) {
				listaExpressoes.get(i).setVariable(entity.getParametrosEstimacao().get(j), 1);
			}
			System.out.println(listaExpressoes.get(i).evaluate());
		}
		
		for(int i = 0; i < listaExpressoes.size(); i++) {
			for(int j = 0; j < entity.getParametrosEstimacao().size(); j++) {
				listaExpressoes.get(i).setVariable(entity.getParametrosEstimacao().get(j), 2);
			}
			System.out.println(listaExpressoes.get(i).evaluate());
		}*/
	//	System.out.println(hessiana);
		
		//System.out.println(matriz.matrizInversa(hessiana));
		
		return 1 - (sqRes / (sqExp + sqRes));
	}

	public Double Media(List<Double> lista) {

		double media = 0.0;

		for (int i = 0; i < lista.size(); i++) {
			media += lista.get(i);
		}

		return media / lista.size();

	}

	public Double[] Pearson(List<Double> dadosExp, List<Double> dadosMod, Integer nParametros) {

		Double[] r = new Double[2];

		Double sumX = 0.0;
		Double sumY = 0.0;
		Double sumXY = 0.0;
		Double sumX2 = 0.0;
		Double sumY2 = 0.0;

		for (int i = 0; i < dadosExp.size(); i++) {
			sumX += dadosExp.get(i);
			sumY += dadosMod.get(i + 1 + nParametros);
			sumXY += dadosExp.get(i) * dadosMod.get(i + 1 + nParametros);
			sumX2 += dadosExp.get(i) * dadosExp.get(i);
			sumY2 += dadosMod.get(i + 1 + nParametros) * dadosMod.get(i + 1 + nParametros);

		}

		Double numerador = dadosExp.size() * sumXY - (sumX * sumY);
		Double denominador = Math
				.sqrt(((dadosExp.size() * sumX2) - (sumX * sumX)) * ((dadosExp.size() * sumY2) - (sumY * sumY)));

		if (denominador != 0) {
			r[0] = numerador / denominador;
			r[1] = (sumY - r[0] * sumX) / dadosExp.size();
		} else {
			r[0] = 0.0;
			r[1] = 0.0;
		}

		return r;
	}

}
