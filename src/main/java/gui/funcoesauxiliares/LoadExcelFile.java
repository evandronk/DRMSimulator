package gui.funcoesauxiliares;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class LoadExcelFile {
	

	private int numeroColunas;
	private int numeroLinhas;
	private GridPane dadosExperimentaisGridPane = new GridPane();
	private GridPane resultadosGridPane = new GridPane();
	private ManipulateNode Return = new ManipulateNode();
	
	public List<GridPane> loadExcelFile(
			File selectedFile, 
			int numeroLinhasE, 
			int numeroColunasE,
			ManipulateNode ReturnE,
			GridPane dadosExperimentaisGridPaneE,
			GridPane resultadosGridPaneE
		
			) throws Exception {
		
		Return = ReturnE;
		numeroLinhas = numeroLinhasE;
		numeroColunas = numeroColunasE;
		dadosExperimentaisGridPane = dadosExperimentaisGridPaneE;
		resultadosGridPane = resultadosGridPaneE;
		
		List<List<Double>> valoresExcel = new ArrayList<List<Double>>();

		String caminho = "";

		ExtensionFilter xls = new ExtensionFilter("Excel (*.xls, *.xlxs)", "*.*");

		FileChooser fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(xls);

		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			caminho = selectedFile.getPath();
		}
		if (caminho != "") {
			FileInputStream file = new FileInputStream(new File(caminho));

			Workbook workBook = new XSSFWorkbook(file);
			Sheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormater = new DataFormatter();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				List<Double> Linhas = new ArrayList<Double>();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormater.formatCellValue(cell);

					try {
						if (cellValue != null) {
							Linhas.add(Double.parseDouble(cellValue.replace(",", ".")));
						} else {
							Linhas.add(Double.parseDouble("0"));
						}

					} catch (Exception e) {

					}

				}
				if (Linhas != null && Linhas.size() > 0) {
					valoresExcel.add(Linhas);
				}
			}

			if (numeroLinhas > valoresExcel.size() + 1) {
				while (numeroLinhas > valoresExcel.size()) {
					removeRowAction();
				}
			}

			if (numeroLinhas < valoresExcel.size() + 1) {
				while (numeroLinhas < valoresExcel.size() + 1) {
					addRowAction();
				}
			}

			for (int i = 0; i < valoresExcel.size(); i++) {
				for (int j = 0; j < valoresExcel.get(0).size(); j++) {

					TextField node = new TextField();
					node.setText(valoresExcel.get(i).get(j).toString());
					node.setAlignment(Pos.CENTER);
					Return.replaceNodeByRowColumnIndex(i + 1, j, dadosExperimentaisGridPane, node);
				}
			}

			for (int i = 0; i < valoresExcel.size(); i++) {
				TextField node = new TextField();
				node.setText(valoresExcel.get(i).get(valoresExcel.get(i).size() - 1).toString());
				node.setAlignment(Pos.CENTER);
				Return.replaceNodeByRowColumnIndex(i, 0, resultadosGridPane, node);
			}

		}
		
		List<GridPane> gridPaneList = new ArrayList<GridPane>();
		gridPaneList.add(dadosExperimentaisGridPane);
		gridPaneList.add(resultadosGridPane);
		
		return gridPaneList;

	}

	private void addRowAction() {

		for (int i = 0; i < numeroColunas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(40);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			dadosExperimentaisGridPane.add(txt, i, numeroLinhas);

		}

		TextField txt = new TextField();
		txt.setAlignment(Pos.CENTER);
		txt.setMaxHeight(40);
		txt.setMinWidth(140);
		txt.setPrefWidth(140);
		txt.setMaxWidth(140);
		resultadosGridPane.add(txt, 0, numeroLinhas - 1);

		numeroLinhas += 1;
		
	}
	
	
	private void removeRowAction() {

		if (numeroLinhas > 1) {
			for (int i = 0; i < numeroColunas; i++) {

				Return.removeNodeByRowColumnIndex(numeroLinhas - 1, i, dadosExperimentaisGridPane);
			}
			Return.removeNodeByRowColumnIndex(numeroLinhas - 2, 0, resultadosGridPane);
			numeroLinhas -= 1;
		
		}

	}
	


	


}
