	package propriedadesfq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constantes {
	
	
	Double Tstp = 273.15; // K
	Double Pstp = 1.0; //atm
	Double Rcte1 = 8.3144621; //J/molK
	Double Rcte2 = 0.0000821; //atm m3/mol K
	Double MCH4 = 16.04; //g/mol
	Double MCO2 = 44.01; //g/mol
	Double sigma1 = 3.78; // Angstron
	Double sigma2 = 3.996;// Angstron

	Double LambdaW = 15.0; // W/m.K
	Double LambdaAr = 30.0; //W/m.K - 25 ~ 45    
	Double LambdaAgua = 1000.0; //W/m.K 


	//https://www.engineeringtoolbox.com/convective-heat-transfer-d_430.html
	//Free Convection - air, gases and dry vapors : 0.5 - 1000 (W/(m2K))
	//Free Convection - water and liquids: 50 - 3000 (W/(m2K))
	
	
	
	// Parâmetros calculados
	
//	Double vazao = Pstp*vazaostp*temperatura/(Tstp*pressao); // m3/s
//	Double areaTransversal = 3.14159265359*(diametroColuna*diametroColuna)/4.0; //m2
//	Double velocidadeIntersticial = vazao/(areaTransversal*porosidade); // m/s
	
	public Double CH4density(Double P, Double T) {
		return P*16.04/(1000.0*Rcte2*T);
	}
	
	public Double CO2density(Double P, Double T) {
		return P*44.01/(1000.0*Rcte2*T);
	}
	
	public Double densidade(Double Q1, Double Q2, Double P, Double T) {
		Double y1 = Q1/(Q1 + Q2);
		Double y2 = Q2/(Q1 + Q2);
		Double rho = (1.0/1000.0)*(P/(Rcte2*T))*(y1*MCH4+ y2*MCO2);
		return rho;
	}
	
	public Double velocidade(Double Q1, Double Q2, Double diametro) {
		return ((Q1+Q2)/(diametro*diametro*3.14159265/4));		
	}
	
	public List<Double> Cpmix(Double Q1, Double Q2, Double T) { 
		// Results in J/mol.K
		// T < 1200 K
		Double y1 = Q1/(Q1+Q2);
		Double y2 = Q2/(Q1+Q2);
		
		Double t = T/1000;
		
		Double a1 =  -0.703029;
		Double b1 = 108.4773;	
		Double c1 = -42.52157;	
		Double d1 = 5.862788;
		Double e1 = 0.678565;
		
		Double a2 = 24.99735;
		Double b2 = 55.18696;
		Double c2 = -33.6913;
		Double d2 = 7.948387;
		Double e2 = -0.136638;
		
		Double Cp1 =  (a1 + b1*t + c1*Math.pow(t, 2) + d1*Math.pow(t, 3) + e1/(Math.pow(t, 2)))/16.04; // kJ/kgK
		Double Cp2 =  (a2 + b2*t + c2*Math.pow(t, 2) + d2*Math.pow(t, 3) + e2/(Math.pow(t, 2)))/44.01;// kJ/kgK

		Double CpMix = Cp1*y1 + Cp2*y2; // kJ/kgK
		
		List<Double> Cps = Arrays.asList(Cp1,Cp2,CpMix);
	
		return Cps;
	}

	
	public List<List<Double>> viscosidade(Double Q1, Double Q2, Double Temp) {
		Double y1 = Q1/(Q1+Q2);
		Double y2 = Q2/(Q1+Q2);
		List<List<Double>> Resultados = new ArrayList<List<Double>>(); 
		Double ek1 =154.0;
		Double ek2 = 190.0;
		Double A1 = 1.16145;
		Double A2 = 0.52487;
		Double A3 = 2.16178;
		Double B1 = 0.14874;
		Double B2 = 0.077320;
		Double B3 = 2.43787;
		
		Double w1 = Temp/ek1;
		Double w2 = Temp/ek2;
		
		Double Omega1 = A1/Math.pow(w1, B1) + A2/Math.exp(B2*w1) + A3/Math.exp(B3*w1);
		Double Omega2 = A1/Math.pow(w2, B1) + A2/Math.exp(B2*w2) + A3/Math.exp(B3*w2);
		
		List<Double> Omegas = Arrays.asList(Omega1,Omega2); 
		
		Double mu1 = 0.000026693*(Math.sqrt(MCH4*Temp)/(Math.pow(sigma1, 2)*Omega1))/10; // kg/ms
		Double mu2 = 0.000026693*(Math.sqrt(MCO2*Temp)/(Math.pow(sigma2, 2)*Omega2))/10; // kg/ms
		
		List<Double> Mus = new ArrayList<Double>(); 
				Mus.add(mu1);
				Mus.add(mu2);
				//Arrays.asList(mu1,mu2);
		
		Double Phi11 = Math.sqrt(1 + MCH4/MCH4)*(1 + Math.sqrt(mu1/mu1)*Math.pow(MCH4/MCH4,0.25))/Math.sqrt(8);
		Double Phi12 = Math.sqrt(1 + MCH4/MCO2)*(1 + Math.sqrt(mu1/mu2)*Math.pow(MCO2/MCH4,0.25))/Math.sqrt(8);
		Double Phi21 = Math.sqrt(1 + MCO2/MCH4)*(1 + Math.sqrt(mu2/mu1)*Math.pow(MCH4/MCO2,0.25))/Math.sqrt(8);
		Double Phi22 = Math.sqrt(1 + MCO2/MCO2)*(1 + Math.sqrt(mu2/mu2)*Math.pow(MCO2/MCO2,0.25))/Math.sqrt(8);
		
		List<Double> Phis = Arrays.asList(Phi11,Phi12,Phi21,Phi22);

		Double mug = y1*mu1/(y1*Phi11+y2*Phi12) + y2*mu2/(y1*Phi21+y2*Phi22);
		
		Mus.add(mug);
		
		Resultados.add(Mus);
		Resultados.add(Omegas);
		Resultados.add(Phis);

		return Resultados;
		
		
	}
	
	public Double DifMolecular(Double Q1, Double Q2, Double Temp) {
		Double C1 = 1.06036;
		Double C2 = 0.193;
		Double C3 = 1.03587;
		Double C4 = 1.76474;
		Double D1 = 0.15610;
		Double D2 = 0.47635;
		Double D3 = 1.52996;
		Double D4 = 3.89411;
		Double ek12 = Math.sqrt(154*190);
		Double nu = Temp/ek12;
		Double OmegaD12 = C1/(Math.pow(nu,D1))+C2/(Math.exp(nu*D2))+ C3/Math.exp(D3*nu) + C4/Math.exp(D4*nu);
		Double sigma12 = (sigma1+sigma2)/2;
		Double Dmix = 0.000018829*Math.sqrt(Math.pow(Temp, 3)*(1/MCH4 + 1/MCO2))/(101.325*1*Math.pow(sigma12, 2)*OmegaD12);
		return Dmix;
		
	}
	
	public Double Reynolds(Double Q1,Double Q2, Double Temp, Double diametro, Double pressao) {
		
		Double rey = densidade(Q1,Q2,pressao,Temp)*velocidade(Q1,Q2,diametro)*(diametro)/viscosidade(Q1,Q2,Temp).get(0).get(2); 
		
		return rey; 
		// Verificar se diametro da coluna ou diametro da particula
	}
	
	
	
	public Double condTermica(Double Q1, Double Q2, Double Temp) {
		Double y1 = Q1/(Q1+Q2);
		Double y2 = Q2/(Q1+Q2);
		Double kg1 = viscosidade(Q1,Q2,Temp).get(0).get(0)*(Cpmix(Q1,Q2,Temp).get(0)+1.25*Rcte1*1000/MCH4);
		Double kg2 = viscosidade(Q1,Q2,Temp).get(0).get(1)*(Cpmix(Q1,Q2,Temp).get(1)+1.25*Rcte1*1000/MCO2);
		Double kg = y1*kg1/(y1*viscosidade(Q1,Q2,Temp).get(2).get(0)+y2*viscosidade(Q1,Q2,Temp).get(2).get(1)) + y2*kg2/(y1*viscosidade(Q1,Q2,Temp).get(2).get(2)+y2*viscosidade(Q1,Q2,Temp).get(2).get(3));

		return kg/1000.0; //to return in kJ/msK
	}
	
	
	public Double Schmidt(Double Q1, Double Q2, Double Temp, Double P) {
		
		return viscosidade(Q1,Q2,Temp).get(0).get(2)/(densidade(Q1,Q2,P,Temp)*DifMolecular(Q1,Q2,Temp));
		
	}
	
	public Double Prandtl(Double Q1, Double Q2, Double Temp) {
		return Cpmix(Q1,Q2,Temp).get(2)*viscosidade(Q1,Q2,Temp).get(0).get(2)/condTermica(Q1,Q2,Temp);
	}
	
	
	public Double DispAx(Double Q1,Double Q2,Double Temp, Double porosidade, Double diametro, Double pressao) {
		Double lambda1 = 20.0;
		Double lambda2 = 0.5;
		Double DL = (DifMolecular(Q1,Q2,Temp)/porosidade)*(lambda1 + lambda2*Schmidt(Q1, Q2, Temp, pressao)*Reynolds(Q1, Q2, Temp, diametro, pressao));
		return DL;
		
	}
	

	public Double CondTermEff(Double Q1,Double Q2,Double Temp, Double porosidade, Double diametro, Double pressao) {
		Double lambda3 = 20.0;
		Double lambda4 = 0.50;
		
		Double lambdaL = condTermica(Q1,Q2,Temp)*(lambda3 + lambda4*Prandtl(Q1, Q2, Temp)*Reynolds(Q1, Q2, Temp, diametro, pressao));

		return lambdaL;
	} 
	
	
	
	
	//Cálculo constante de equilíbrio
	List<Double> A = Arrays.asList(-0.703029, 24.99735, 33.066178, 25.56759);
	List<Double> B = Arrays.asList(108.4773, 55.18696, -11.363417, 6.09613);
	List<Double> C = Arrays.asList(-42.52157, -33.69137, 11.432816, 4.054656);
	List<Double> D = Arrays.asList(5.862788, 7.948387, -2.772874, -2.6713);
	List<Double> E = Arrays.asList(0.678565, -0.136638, -0.158558, 0.131021);
	List<Double> F = Arrays.asList(-76.84376, -403.6075, -9.980797, -118.008);
	List<Double> G = Arrays.asList(158.7163, 228.2431, 172.707974, 227.3665);
	List<Double> H = Arrays.asList(-74.8731,-393.5224, 0.0, -110.5271);
	List<Double> S298 = Arrays.asList(186.236763, 213.7689511, 130.6656386, 197.6482155);
	Double HReacao298 = 247.4037959;
	
	public Double Kp(Double T) {
		
		Double t = T/1000;
		
		
		List<Double> deltaH = new ArrayList<Double>();
		List<Double> S = new ArrayList<Double>();
		List<Double> deltaS = new ArrayList<Double>();
		 
		for(int i = 0; i < 4; i++) {
			
			deltaH.add(A.get(i)*t+B.get(i)*(t*t)/2 + C.get(i)*(t*t*t)/3+D.get(i)*(t*t*t*t)/4-E.get(i)/t+F.get(i)-H.get(i));
			S.add(A.get(i)*Math.log(t)+B.get(i)*t+C.get(i)*(t*t)/2 + D.get(i)*(t*t*t)/3-E.get(i)/(2*t*t)+G.get(i));
			deltaS.add(S.get(i)-S298.get(i));
		}
		
		Double HReacao = 1000*(2*(deltaH.get(2)+deltaH.get(3))-deltaH.get(0)-deltaH.get(1)+HReacao298);
		Double SReacao = 2*(S.get(2)+S.get(3))-S.get(0)-S.get(1);
		Double deltaG = (HReacao - T*SReacao);
		Double Kp = Math.exp(-deltaG/(Rcte1*T));
		
		return Kp;
		
	}
	
	public Double HReacao(Double T) {
		Double t = T/1000;
		
		
		List<Double> deltaH = new ArrayList<Double>();
		List<Double> S = new ArrayList<Double>();
		List<Double> deltaS = new ArrayList<Double>();
		 
		for(int i = 0; i < 4; i++) {
			
			deltaH.add(A.get(i)*t+B.get(i)*(t*t)/2 + C.get(i)*(t*t*t)/3+D.get(i)*(t*t*t*t)/4-E.get(i)/t+F.get(i)-H.get(i));
			S.add(A.get(i)*Math.log(t)+B.get(i)*t+C.get(i)*(t*t)/2 + D.get(i)*(t*t*t)/3-E.get(i)/(2*t*t)+G.get(i));
			deltaS.add(S.get(i)-S298.get(i));
		}
		
		Double hReacao = 1000*(2*(deltaH.get(2)+deltaH.get(3))-deltaH.get(0)-deltaH.get(1)+HReacao298);
		return hReacao;
	}
	
}
