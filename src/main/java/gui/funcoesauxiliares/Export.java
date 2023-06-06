package gui.funcoesauxiliares;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Export {
	
	public void exportExcel(List<List<Double>> listHorarios, String T0, Double t0) throws IOException {

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		// Create a Sheet
		Sheet sheet1 = workbook.createSheet("Dados Simulados");
	

		List<Sheet> planilhas = new ArrayList<Sheet>();

		planilhas.add(sheet1);


		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setFont(headerFont);

		CellStyle textCellStyle = workbook.createCellStyle();
		textCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		textCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		textCellStyle.setWrapText(true);

		String[] dias = {"z","C_CH4", "C_CO2", "C_H2", "C_CO","T"};

		Row headerRow1 = sheet1.createRow(0);


		List<Row> rows = new ArrayList<Row>();

		rows.add(headerRow1);


		// Cria o cabe�alho das planilhas
		for (Row row : rows) {
			for (int i = 0; i < 6; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(dias[i]);
				cell.setCellStyle(headerCellStyle);
			}
		}

		// Insere os dados dos hor�rios nas linhas
		for (Sheet sheet : planilhas) {
			int rowNum = 1;
			for ( int i =0; i < listHorarios.size(); i++) {

				Row row = sheet.createRow(rowNum++);
				
				for (int j = 0; j < listHorarios.get(i).size(); j++) {
					row.createCell(j).setCellValue(listHorarios.get(i).get(j));
					row.getCell(j).setCellStyle(textCellStyle);
					row.setHeight((short) 900);
				}
				
				
			}
		}

		// Resize all columns to fit the content size
		for (Sheet sheet : planilhas) {
			for (int i = 0; i < 1; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		DecimalFormat formatter = new DecimalFormat("#0.00");
		Integer Tinteger = Integer.parseInt(T0);
		FileOutputStream fileOut = new FileOutputStream("SimulaçãoTemperature"+Tinteger+"Ktime"+formatter.format(t0)+"s.xlsx");
		workbook.write(fileOut);
		fileOut.close();

	}
}
