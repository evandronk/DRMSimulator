package newton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.EstimationEntity;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FuncaoUtil {

	public Double calculaFuncaoNoPonto(List<Double> pontoInicial, List<String> listaVariaveisPSO, String funcao, EstimationEntity entity) {

		Double funcaoNoPonto = 0.0;

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(listaVariaveisPSO);

		Expression expressao = new ExpressionBuilder(funcao).variables(todasVariaveis).build();

		for (int i = 0; i < listaVariaveisPSO.size(); i++) {
			expressao.setVariable(listaVariaveisPSO.get(i), pontoInicial.get(i));
		}
		
		funcaoNoPonto = expressao.evaluate();

		return funcaoNoPonto;

	}

	public Double calculaDerivadaPrimeira(String funcao, List<Double> ponto, List<String> ListaVariaveisPSO,
			String variavel,  EstimationEntity entity) {

		Double resultado = 0.0;

		Double h = 0.001;
		
		Integer indiceVariavel = ListaVariaveisPSO.indexOf(variavel);

		List<Double> pontoMaisH = new ArrayList<Double>(ponto);
		pontoMaisH.set(indiceVariavel, ponto.get(indiceVariavel) + h);
		Double funcaoMaisH = calculaFuncaoNoPonto(pontoMaisH, ListaVariaveisPSO, funcao,entity);

		List<Double> pontoMenosH = new ArrayList<Double>(ponto);
		pontoMenosH.set(indiceVariavel, ponto.get(indiceVariavel) - h);
		Double funcaoMenosH = calculaFuncaoNoPonto(pontoMenosH, ListaVariaveisPSO, funcao,entity);

		Double centralH = (funcaoMaisH - funcaoMenosH) / (2 * h);

		List<Double> pontoMais2H = new ArrayList<Double>(ponto);
		pontoMais2H.set(indiceVariavel, ponto.get(indiceVariavel) + 2 * h);
		Double funcaoMais2H = calculaFuncaoNoPonto(pontoMais2H, ListaVariaveisPSO, funcao,entity);

		List<Double> pontoMenos2H = new ArrayList<Double>(ponto);
		pontoMenos2H.set(indiceVariavel, ponto.get(indiceVariavel) - 2 * h);
		Double funcaoMenos2H = calculaFuncaoNoPonto(pontoMenos2H, ListaVariaveisPSO, funcao,entity);

		Double central2H = (funcaoMais2H - funcaoMenos2H) / (4 * h);

		resultado = (4 * centralH - central2H) / 3;

		return resultado;

	}

	public Double calculaDerivadaSegunda(String funcao, List<Double> ponto, List<String> ListaVariaveisPSO,
			String variavel1, String variavel2, EstimationEntity entity) {

		Double resultado = 0.0;

		Double h = 0.001;

		Integer indiceVariavel2 = ListaVariaveisPSO.indexOf(variavel2);

		List<Double> pontoMaisH = new ArrayList<Double>(ponto);

		pontoMaisH.set(indiceVariavel2, ponto.get(indiceVariavel2) + h);
		
		Double funcaoMaisH = calculaDerivadaPrimeira(funcao, pontoMaisH, ListaVariaveisPSO,
				variavel1,entity);

		List<Double> pontoMenosH = new ArrayList<Double>(ponto);

		pontoMenosH.set(indiceVariavel2, ponto.get(indiceVariavel2) - h);
		Double funcaoMenosH = calculaDerivadaPrimeira(funcao, pontoMenosH, ListaVariaveisPSO,
				variavel1,entity);

		Double centralH = (funcaoMaisH - funcaoMenosH) / (2 * h);

		List<Double> pontoMais2H = new ArrayList<Double>(ponto);
		// pontoMais2H.set(indiceVariavel1, ponto.get(indiceVariavel1)+2*h);
		pontoMais2H.set(indiceVariavel2, ponto.get(indiceVariavel2) + 2 * h);
		Double funcaoMais2H = calculaDerivadaPrimeira(funcao, pontoMais2H, ListaVariaveisPSO,
				variavel1,entity);

		List<Double> pontoMenos2H = new ArrayList<Double>(ponto);
		// pontoMenos2H.set(indiceVariavel1, ponto.get(indiceVariavel1)-2*h);
		pontoMenos2H.set(indiceVariavel2, ponto.get(indiceVariavel2) - 2 * h);
		Double funcaoMenos2H = calculaDerivadaPrimeira(funcao, pontoMenos2H, ListaVariaveisPSO,
				variavel1,entity);

		Double central2H = (funcaoMais2H - funcaoMenos2H) / (4 * h);

		resultado = ((4 * centralH - central2H) / 3);

		return resultado;

	}

}
