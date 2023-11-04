package com.sipl.vehicle.generate.pdf;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sipl.vehicle.model.Vehicle;

@Component
public class PdfGenerator {
	public void generatePdf(List<Vehicle> vehicles,
			HttpServletResponse httpServletResponse,
			LocalDateTime startDate, LocalDateTime endDate)
			throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
		document.open();
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		headerFont.setColor(CMYKColor.WHITE);
		HeaderFooter header = new HeaderFooter(new Phrase(" SIPL ", headerFont), false);
		header.setAlignment(Element.ALIGN_CENTER);
		header.setBorder(10);
		header.setBackgroundColor(CMYKColor.orange);
		document.setHeader(header);

		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(10);
		Paragraph p1 = new Paragraph("List of vehicles from Date : "
		+ startDate + "-" + endDate , fontTitle);
		p1.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p1);
		
		PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(100f);
		table.setWidths(new int[] {3,16,10,10,10,10,10,10,10,10});
		table.setSpacingBefore(10);
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.gray);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);
		font.setSize(7);

		cell.setPhrase(new Phrase("id",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("vehicleRegistrationNumber",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("ownerName",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("brand",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("registrationExpires",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("isActive",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("createdBy",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("modifiedBy",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("creationTime",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("modifiedTime",font));
		table.addCell(cell);

		// Loop through your data and add it to the table with the data font
		Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		dataFont.setSize(7); // Set the font size for data
		for (Vehicle vehicle : vehicles) {
		    table.addCell(new Phrase(String.valueOf(vehicle.getId()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getVehicleRegistrationNumber()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getOwnerName()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getBrand()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getRegistrationExpires()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.isActive()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getCreatedBy()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getModifiedBy()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getCreationTime()), dataFont));
		    table.addCell(new Phrase(String.valueOf(vehicle.getModifiedTime()), dataFont));
		}
		
			document.add(table);
			document.close();
	}
}

