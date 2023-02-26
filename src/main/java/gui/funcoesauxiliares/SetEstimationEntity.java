package gui.funcoesauxiliares;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.EstimationEntity;
import propriedadesfq.Constantes;

public class SetEstimationEntity {

	Constantes constantes = new Constantes();
	
	String expressao = new String();
	List<Integer> parametros = new ArrayList<Integer>();
	Integer nVariaveis = 3;

	public EstimationEntity defineEntity(EstimationEntity entity) {

		if (entity.getLeiVelocidade() == "potencias") {
			potencias(entity);
		} else if (entity.getLeiVelocidade() == "eley") {
			eley(entity);
		} else if (entity.getLeiVelocidade() == "langmuir") {
			langmuir(entity);
		}

		return entity;

	}

	private void potencias(EstimationEntity entity) {

		Integer selecao = entity.getSelecao();

		if (selecao == 0) {
			expressao = "A*exp(-E/(8.3144621*T))*((CH4_in)^m)*((CO2_in)^n)";
			parametros = Arrays.asList(0, 1, 4, 5);
			nVariaveis = 3;
		} else if (selecao == 1) {
			expressao = "A*exp(-E/(8.3144621*T))*(((CH4_in)^m)*((CO2_in)^n)-((CO_out)^p)*((H2_out)^q)/Kp)"; //Trocar esse 7 por Kq
			parametros = Arrays.asList(0, 1, 4, 5, 6, 7);
			nVariaveis = 5;
		}

		define(entity);

	}

	private void eley(EstimationEntity entity) {

		Integer selecao = entity.getSelecao();

		if (selecao == 0) {
			expressao = "A*exp(-E/(8.3144621*T))*(CH4_in*CO2_in)/(1+K_CH4*CH4_in)";
			parametros = Arrays.asList(0, 1, 2);
			nVariaveis = 3;
		} else if (selecao == 1) {
			expressao = "A*exp(-E/(8.3144621*T))*K_CO2*(CH4_in*CO2_in)/(1+K_CO2*CO2_in)";
			parametros = Arrays.asList(0, 1, 3);
			nVariaveis = 3;
		} else if (selecao == 2) {
			expressao = "(A*exp(-E/(8.3144621*T))*K_CH4*(CH4_in*CO2_in)/(1+K_CH4*CH4_in))*(1-CO_out^2*H2_out^2/(K_CH4*K_CO2*CH4_in*CO2_in))";
			parametros = Arrays.asList(0, 1, 2, 3);
			nVariaveis = 5;
		} else if (selecao == 3) {
			expressao = "(A*exp(-E/(8.3144621*T))*(CH4_in - (CO_out^2*H2_out^2/(7*CO2_in^2))))/((1+K_CH4*CO_out^2/CO2_in+K_CO2*(H2_out^(0.5)))^(-5))";
			parametros = Arrays.asList(0, 1, 2, 3);
			nVariaveis = 5;
		}

		define(entity);

	}

	private void langmuir(EstimationEntity entity) {

		Integer selecao = entity.getSelecao();

		if (selecao == 0) {
			expressao = "A*exp(-E/(8.3144621*T))*K_CH4*(CH4_in*CO2_in)/(1+K_CH4*CH4_in)";
			parametros = Arrays.asList(0, 1, 2);
			nVariaveis = 3;
		} else if (selecao == 1) {
			expressao = "A*exp(-E/(8.3144621*T))*K_CO2*(CH4_in*CO2_in)/(1+K_CO2*CO2_in)";
			parametros = Arrays.asList(0, 1, 3);
			nVariaveis = 3;
		} else if (selecao == 2) {
			expressao = "(A*exp(-E/(8.3144621*T))*K_CH4*(CH4_in*CO2_in)/(1+K_CH4*CH4_in))*(1-CO_out^2*H2_out^2/(K_CH4*K_CO2*CH4_in*CO2_in))";
			parametros = Arrays.asList(0, 1, 2, 3);
			nVariaveis = 5;
		} else if (selecao == 3) {
			expressao = "(A*exp(-E/(8.3144621*T))*K_CH4*(CH4_in - (CO2_in^2*H2_out^2/(0.5*CO2_in^2))))/(1+K_CH4*CO_out^2/CO2_in+K_CO2*H2_out^(0.5))^(-5)";
			parametros = Arrays.asList(0, 1, 2, 3);
			nVariaveis = 5;
		}

		define(entity);

	}

	private void define(EstimationEntity entity) {
		entity.setExpressao(expressao);
		entity.setParametros(parametros);
		entity.setnVariaveis(nVariaveis);
	}

}
