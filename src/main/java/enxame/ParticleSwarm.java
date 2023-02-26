package enxame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ParticleSwarm  {
	
	public Integer progress = 0 ;
	
	public Integer Iteracoes = 100;
	
	public PropertyChangeSupport pcs = new  PropertyChangeSupport(this);
	
	public List<Double> Calcular(List<List<Double>> dados, List<String> ListaVariaveisDados,
			List<String> ListaVariaveisPSO, List<Double> resultadosExperimentais, List<List<Double>> dominio,
			String funcao, Integer nParticulasInput, Integer nIteracoesInput) {
		
		Iteracoes = nIteracoesInput;
		
		List<List<Double>> Resultados = new ArrayList<List<Double>>();  //***
		
		List<Double> Erros = new ArrayList<Double>();
		
		
		List<Double> resultado = new ArrayList<Double>();

		Set<String> todasVariaveis = new HashSet<String>();

		todasVariaveis.addAll(ListaVariaveisDados);

		todasVariaveis.addAll(ListaVariaveisPSO);
		
		todasVariaveis.add("Kp");

		Expression expressao = new ExpressionBuilder(funcao).variables(todasVariaveis).build();

		// Definindo os valores máximos e mínimos
		Integer nParticulas = nParticulasInput;

		List<List<Double>> particulas = new ArrayList<List<Double>>();

		for (int i = 0; i < nParticulas; i++) {

			List<Double> particula = new ArrayList<Double>();

			for (int j = 0; j < dominio.size(); j++) {
				Random rand = new Random();
				Double nRandom = rand.nextDouble();
				particula.add((dominio.get(j).get(1) - dominio.get(j).get(0)) * nRandom + dominio.get(j).get(0));
			}

			particulas.add(particula);
		}
		
		

		// Define cada particula inicial como a melhor local
		List<List<Double>> melhorLocal = new ArrayList<List<Double>>(particulas);

		// Define o vetor velocidade inicial como 0
		List<List<Double>> vetorVelocidades = new ArrayList<List<Double>>();

		for (int i = 0; i < nParticulas; i++) {
			List<Double> vetorVelocidade = new ArrayList<Double>();
			for (int j = 0; j < ListaVariaveisPSO.size(); j++) {
				vetorVelocidade.add(0.0);
			}
			vetorVelocidades.add(vetorVelocidade);
		}

		List<Double> errosParticulas = new ArrayList<Double>();

		List<List<Double>> valorParticulas = new ArrayList<List<Double>>();

		PSOUtil calcula = new PSOUtil();

		valorParticulas = calcula.calcula(particulas, nParticulas, expressao, ListaVariaveisPSO, ListaVariaveisDados,
				dados);
		

		for (int i = 0; i < nParticulas; i++) {
			errosParticulas.add(calcula.calculaErro(resultadosExperimentais, valorParticulas.get(i)));
		}

		Double melhorGlobal = calcula.getMin(errosParticulas);

		Integer posicaoGlobal = errosParticulas.indexOf(melhorGlobal);

		Integer nIteracoes = nIteracoesInput;

		Double wi = 0.729844;

		Double wf = 0.4;
	
		
		
		for (int i = 0; i < nIteracoes; i++) {
			
			try {
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setProperty(i);

			valorParticulas.clear();

			for (int j = 0; j < nParticulas; j++) {

				Double w = (wf - wi) * (i / nIteracoes) + wi;

				vetorVelocidades.set(j, vetorResultante(particulas.get(j), melhorLocal.get(j), particulas,
						posicaoGlobal, w, vetorVelocidades.get(j)));

				List<Double> novaParticula = new ArrayList<Double>();

				for (int k = 0; k < particulas.get(j).size(); k++) {
					novaParticula.add(particulas.get(j).get(k) + vetorVelocidades.get(j).get(k));
				}

				particulas.set(j, novaParticula);

				valorParticulas = calcula.calcula(particulas, nParticulas, expressao, ListaVariaveisPSO,
						ListaVariaveisDados, dados);

			}

			for (int k = 0; k < nParticulas; k++) {

				Double errodaParticula = calcula.calculaErro(resultadosExperimentais, valorParticulas.get(k));

				if (errodaParticula < errosParticulas.get(k)) {
					errosParticulas.set(k, errodaParticula);
					melhorLocal.set(k, particulas.get(k));
				}

			}

			if (calcula.getMin(errosParticulas) < melhorGlobal) {
				melhorGlobal = calcula.getMin(errosParticulas);
				posicaoGlobal = errosParticulas.indexOf(melhorGlobal);
			}
			
		}

		resultado = particulas.get(posicaoGlobal);

		resultado.add(melhorGlobal);

		resultado.addAll(valorParticulas.get(posicaoGlobal));
		
		Resultados.add(resultado);
		
		Erros.add(melhorGlobal);

		
		
		return Resultados.get(Resultados.size()-1);

	}

	private List<Double> vetorVelocidade(List<Double> inicio, List<Double> fim) {

		List<Double> resultado = new ArrayList<Double>();

		for (int i = 0; i < inicio.size(); i++) {
			resultado.add(fim.get(i) - inicio.get(i));
		}

		return resultado;

	}

	private List<Double> vetorResultante(List<Double> particula, List<Double> melhorLocal,
			List<List<Double>> particulas, Integer melhorGlobalIndex, Double w, List<Double> velocidade) {

		List<Double> resultado = new ArrayList<Double>();

		List<Double> velocidadeLocal = vetorVelocidade(particula, melhorLocal);

		List<Double> velocidadeGlobal = vetorVelocidade(particula, particulas.get(melhorGlobalIndex));

		Double c = 1.49618;
		Random rand = new Random();
		Double r1 = rand.nextDouble();
		Double r2 = rand.nextDouble();

		for (int i = 0; i < particula.size(); i++) {
			Double v = w * velocidade.get(i) + c * r1 * velocidadeLocal.get(i) + c * r2 * velocidadeGlobal.get(i);
			resultado.add(v);
		}

		return resultado;

	}
	
	public void addObserver(PropertyChangeListener l) {
		pcs.addPropertyChangeListener("theProperty", l);
	}
	
	public void setProperty(Integer val) {
		Integer old = progress;
		progress = val;
		pcs.firePropertyChange("theProperty", old, val);
	}

}
