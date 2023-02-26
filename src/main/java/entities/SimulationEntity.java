package entities;

import java.util.List;

public class SimulationEntity {

	private Double CH4Concentration;

	private Double CO2Concentration;

	private Double lenght;

	private Double diameter;

	private Double porosity;

	private Integer reaction;

	private Double inletTemperature;

	private Double wallTemperature;

	private Double pressure;

	private Integer kinetics;
	
	private Double velocity;
	
	private Double DL;
	
	private Double enthalpy;

	public Double getEnthalpy() {
		return enthalpy;
	}

	public void setEnthalpy(Double enthalpy) {
		this.enthalpy = enthalpy;
	}

	public Double getDL() {
		return DL;
	}

	public void setDL(Double dL) {
		DL = dL;
	}

	public Double getVelocity() {
		return velocity;
	}

	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}

	public Double getCH4Concentration() {
		return CH4Concentration;
	}

	public void setCH4Concentration(Double cH4Concentration) {
		CH4Concentration = cH4Concentration;
	}

	public Double getCO2Concentration() {
		return CO2Concentration;
	}

	public void setCO2Concentration(Double cO2Concentration) {
		CO2Concentration = cO2Concentration;
	}

	public Integer getKinetics() {
		return kinetics;
	}

	public void setKinetics(Integer kinetics) {
		this.kinetics = kinetics;
	}

	public Double getCpMix() {
		return cpMix;
	}

	public void setCpMix(Double cpMix) {
		this.cpMix = cpMix;
	}

	public Double getGasDensity() {
		return gasDensity;
	}

	public void setGasDensity(Double gasDensity) {
		this.gasDensity = gasDensity;
	}

	public Double getCatalystDensity() {
		return catalystDensity;
	}

	public void setCatalystDensity(Double catalystDensity) {
		this.catalystDensity = catalystDensity;
	}

	public Double getCH4Flow() {
		return CH4Flow;
	}

	public void setCH4Flow(Double cH4Flow) {
		CH4Flow = cH4Flow;
	}

	public Double getCO2Flow() {
		return CO2Flow;
	}

	public void setCO2Flow(Double cO2Flow) {
		CO2Flow = cO2Flow;
	}

	public List<Double> getKineticsParameters() {
		return kineticsParameters;
	}

	public void setKineticsParameters(List<Double> kineticsParameters) {
		this.kineticsParameters = kineticsParameters;
	}

	private Double cpMix;

	private Double gasDensity;

	private Double catalystDensity;

	private Double CH4Flow;

	private Double CO2Flow;

	private List<Double> kineticsParameters;

	private Double Utc;

	public Double getUtc() {
		return Utc;
	}

	public void setUtc(Double utc) {
		Utc = utc;
	}

	public Double getConcentrationCH4() {
		return CH4Concentration;
	}

	public void setConcentrationCH4(Double CH4Concentration) {
		this.CH4Concentration = CH4Concentration;
	}

	public Double getConcentrationCO2() {
		return CO2Concentration;
	}

	public void setConcentrationCO2(Double CO2Concentration) {
		this.CO2Concentration = CO2Concentration;
	}

	public Double getLenght() {
		return lenght;
	}

	public void setLenght(Double lenght) {
		this.lenght = lenght;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Double getPorosity() {
		return porosity;
	}

	public void setPorosity(Double porosity) {
		this.porosity = porosity;
	}

	public Integer getReaction() {
		return reaction;
	}

	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}

	public Double getInletTemperature() {
		return inletTemperature;
	}

	public void setInletTemperature(Double inletTemperature) {
		this.inletTemperature = inletTemperature;
	}

	public Double getWallTemperature() {
		return wallTemperature;
	}

	public void setWallTemperature(Double wallTemperature) {
		this.wallTemperature = wallTemperature;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Integer getKinectisType() {
		return kinetics;
	}

	public void setKineticsType(Integer kinetics) {
		this.kinetics = kinetics;
	}

	public List<Double> getParameters() {
		return kineticsParameters;
	}

	public void setParameters(List<Double> parameters) {
		this.kineticsParameters = parameters;
	}

	@Override
	public String toString() {
		return "SimulationEntity [CH4Concentration=" + CH4Concentration + ", CO2Concentration=" + CO2Concentration
				+ ", lenght=" + lenght + ", diameter=" + diameter + ", porosity=" + porosity + ", reaction=" + reaction
				+ ", inletTemperature=" + inletTemperature + ", wallTemperature=" + wallTemperature + ", pressure="
				+ pressure + ", kinetics=" + kinetics + ", cpMix=" + cpMix + ", gasDensity=" + gasDensity
				+ ", catalystDensity=" + catalystDensity + ", CH4Flow=" + CH4Flow + ", CO2Flow=" + CO2Flow
				+ ", kineticsParameters=" + kineticsParameters + "]";
	}

}
