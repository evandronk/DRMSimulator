package entities;

import java.util.List;

public class EstimationEntity {
	
	private Integer nVariaveis;

	private Integer selecao;

	private String expressao;

	private String metodoEstimacao;
	
	private String leiVelocidade;

	private List<Integer> parametros;
	
	private List<String> parametrosString;
	
	private List<String> parametrosEstimacao;

	private List<String> variaveisIndepdendentes;

	private List<Double> rExp;

	private List<Double> rMod;
	
	private List<List<Double>> dados;
	
	private List<String> derivadaPrimeira;

	private List<List<String>> derivadaSegunda;

	public void setParametrosEstimacao(List<String> parametrosEstimacao) {
		this.parametrosEstimacao = parametrosEstimacao;
	}

	public List<String> getVariaveisIndepdendentes() {
		return variaveisIndepdendentes;
	}

	public void setVariaveisIndepdendentes(List<String> variaveisIndepdendentes) {
		this.variaveisIndepdendentes = variaveisIndepdendentes;
	}

	public void setDados(List<List<Double>> dados) {
		this.dados = dados;
	}

	public String getLeiVelocidade() {
		return leiVelocidade;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public List<Integer> getParametros() {
		return parametros;
	}

	public void setParametros(List<Integer> parametros) {
		this.parametros = parametros;
	}
	
	public List<String> getParametrosString() {
		return parametrosString;
	}

	public void setParametrosString(List<String> parametrosString) {
		this.parametrosString = parametrosString;
	}

	public Integer getnVariaveis() {
		return nVariaveis;
	}

	public void setnVariaveis(Integer nVariaveis) {
		this.nVariaveis = nVariaveis;
	}

	public String getMetodoEstimacao() {
		return metodoEstimacao;
	}

	public void setMetodoEstimacao(String metodoEstimacao) {
		this.metodoEstimacao = metodoEstimacao;
	}

	public Integer getSelecao() {
		return selecao;
	}

	public void setSelecao(Integer selecao) {
		this.selecao = selecao;
	}

	public void setLeiVelocidade(String leiVelocidade) {
		this.leiVelocidade = leiVelocidade;
	}

	public String getExpressao() {
		return expressao;
	}

	public List<Double> getrExp() {
		return rExp;
	}

	public void setrExp(List<Double> rExp) {
		this.rExp = rExp;
	}

	public List<Double> getrMod() {
		return rMod;
	}

	public void setrMod(List<Double> rMod) {
		this.rMod = rMod;
	}
	
	public List<List<Double>> getDados() {
		return dados;
	}
	
	public List<String> getParametrosEstimacao() {
		return parametrosEstimacao;
	}

	@Override
	public String toString() {
		return "Estimacao [nVariaveis=" + nVariaveis + ", selecao=" + selecao + ", expressao=" + expressao
				+ ", metodoEstimacao=" + metodoEstimacao + ", leiVelocidade=" + leiVelocidade + ", parametros="
				+ parametros + ", parametrosString=" + parametrosString + ", parametrosEstimacao=" + parametrosEstimacao
				+ ", variaveisIndepdendentes=" + variaveisIndepdendentes + ", rExp=" + rExp + ", rMod=" + rMod
				+ ", dados=" + dados + "]";
	}

	public List<String> getDerivadaPrimeira() {
		return derivadaPrimeira;
	}

	public void setDerivadaPrimeira(List<String> derivadaPrimeira) {
		this.derivadaPrimeira = derivadaPrimeira;
	}

	public List<List<String>> getDerivadaSegunda() {
		return derivadaSegunda;
	}

	public void setDerivadaSegunda(List<List<String>> derivadaSegunda) {
		this.derivadaSegunda = derivadaSegunda;
	}

}
