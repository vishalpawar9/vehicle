package com.sipl.vehicle.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sipl.vehicle.model.Vehicle;

public class ExcelUtil {
	public static String HEADER[] = { "id", "vehicleRegistrationNumber", "ownerName", "brand", "registrationExpires","isActive", "createdBy","modifiedBy", "creationTime", "modifiedTime" };
	public static String SHEET_NAME = "Vehicle_Details";

	public static ByteArrayInputStream dataToExcel(List<Vehicle> vehicleList) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
		Sheet sheet = workbook.createSheet(SHEET_NAME);
		Row row = sheet.createRow(0);
		for (int i = 0; i < HEADER.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(HEADER[i]);
		}
		int rowIndex=1;
		for(Vehicle v:vehicleList) {
			Row row1=sheet.createRow(rowIndex);
			rowIndex++;
			row1.createCell(0).setCellValue(v.getId());
			row1.createCell(1).setCellValue(v.getVehicleRegistrationNumber());
			row1.createCell(2).setCellValue(v.getOwnerName());
			row1.createCell(3).setCellValue(v.getBrand());
			row1.createCell(4).setCellValue(v.getRegistrationExpires());
			row1.createCell(5).setCellValue(v.isActive());
			row1.createCell(6).setCellValue(v.getCreatedBy());
			row1.createCell(7).setCellValue(v.getModifiedBy());
			row1.createCell(8).setCellValue(v.getCreationTime());
			row1.createCell(9).setCellValue(v.getModifiedTime());
		}
	
			workbook.write(byteArrayOutputStream);
			return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} catch (IOException e) {
		 
			throw new RuntimeException(e);
		}
		finally {
			workbook.close();
			byteArrayOutputStream.close();
		}
	}
}
