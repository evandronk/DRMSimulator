package gui.funcoesauxiliares;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class EquacoesBuilder {

	List<Image> equacoes = new ArrayList<Image>();

	String sysOs = System.getProperty("os.name");
	String[] newOs = sysOs.split(" ");
	Integer k = 0;

	public List<Image> addEquacoes(String selecao) {

		equacoes.clear();
		
		if (newOs[0].contentEquals("Windows")) {
			k = 20;
		} else {
			k = 30;
		}

		if (selecao == "potencias") {
			addEquacoesPtI();
			addEquacoesPtR();
		}else if (selecao == "potenciasI") {
			addEquacoesPtI();
		}else if (selecao == "potenciasR" ) {
			addEquacoesPtR();
		}else if (selecao == "eley") {
			addEquacoesERI();
			addEquacoesERR();
		}else if (selecao == "eleyI") {
			addEquacoesERI();
		}else if (selecao == "eleyR") {
			addEquacoesERR();
		}else if (selecao == "langmuir") {
			addEquacoesLHI();
			addEquacoesLHR();
		}else if (selecao == "langmuirI") {
			addEquacoesLHI();
		}else if (selecao == "langmuirR") {
			addEquacoesLHR();
		}

		return equacoes;

	}

	
	
	//Power Law Models
	private void addEquacoesPtI() {
		
		final String KCH4 = "\\displaystyle r = Ae^{-\\frac{E}{RT}}[CH_4]^m [CO_2]^n";
		final TeXFormula texKCH4 = new TeXFormula(KCH4);
		java.awt.Image imagemKCH4 = texKCH4.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxKCH4 = SwingFXUtils.toFXImage((BufferedImage) imagemKCH4, null);
		equacoes.add(fxKCH4);

	}
	private void addEquacoesPtR() {

		final String KCH4R = "\\displaystyle r = Ae^{-\\frac{E}{RT}}\\left([CH_4]^m [CO_2]^n-\\frac{[H_2]^p [CO]^q}{K_{p}}\\right)";
		final TeXFormula texKCH4R = new TeXFormula(KCH4R);
		java.awt.Image imagemKCH4R = texKCH4R.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK,
				null);
		Image fxKCH4R = SwingFXUtils.toFXImage((BufferedImage) imagemKCH4R, null);
		equacoes.add(fxKCH4R);

	}
	
	//Langmuir-Hinshelwood Models
	
	
	private void addEquacoesLHI() {
		final String equacao1 = "\\displaystyle r= \\frac{ Ae^{-\\frac{E}{RT}}K_{CH_4}K_{CO_2}[CH_4][CO_2]}{(1+K_{CH_4}[CH_4]+K_{CO_2}[CO_2])^2}";
		final TeXFormula formula1 = new TeXFormula(equacao1);
		java.awt.Image imagem1 = formula1.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxImagem1 = SwingFXUtils.toFXImage((BufferedImage) imagem1, null);
		equacoes.add(fxImagem1);
	}
	
	private void addEquacoesLHR() {
		final String equacao1 = "\\displaystyle r=  \\frac{Ae^{-\\frac{E}{RT}}K_{CH_4}K_{CO_2}\\left([CH_4] [CO_2]-\\frac{[H_2]^2[CO]^2}{K_p}\\right)}{(1+K_{CH_4}[CH_4]+K_{CO_2}[CO_2])^2}";
		final TeXFormula formula1 = new TeXFormula(equacao1);
		java.awt.Image imagem1 = formula1.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxImagem1 = SwingFXUtils.toFXImage((BufferedImage) imagem1, null);
		equacoes.add(fxImagem1);
	}
	
	
	
	//Eley-Rideal Models
	
	private void addEquacoesERI() {

		final String equacao1 = "\\displaystyle r=\\frac{Ae^{-\\frac{E}{RT}}  [CH_4] [CO_2]}{1+K_{CH_4}[CH_4]}";
		final TeXFormula formula1 = new TeXFormula(equacao1);
		java.awt.Image imagem1 = formula1.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxImagem1 = SwingFXUtils.toFXImage((BufferedImage) imagem1, null);
		equacoes.add(fxImagem1);

/*		final String equacao2 = "\\displaystyle r=\\frac{Ae^{-\\frac{E}{RT}} K_{CO_2} [CH_4] [CO_2]}{1+K_{CO_2}[CO_2]}";
		final TeXFormula formula2 = new TeXFormula(equacao2);
		java.awt.Image imagem2 = formula2.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxImagem2 = SwingFXUtils.toFXImage((BufferedImage) imagem2, null);
		equacoes.add(fxImagem2);*/




	}
	
	private void addEquacoesERR() {
		final String equacao3 = "\\displaystyle r=\\frac{Ae^{-\\frac{E}{RT}} K_{CH_4} \\left([CH_4] [CO_2]-\\frac{[H_2]^2[CO]^2}{K_p}\\right)}{1+K_{CH_4}[CH_4]}";
		final TeXFormula formula3 = new TeXFormula(equacao3);
		java.awt.Image imagem3 = formula3.createBufferedImage(TeXConstants.STYLE_TEXT, k, java.awt.Color.BLACK, null);
		Image fxImagem3 = SwingFXUtils.toFXImage((BufferedImage) imagem3, null);
		equacoes.add(fxImagem3);
		
	}



	


}
