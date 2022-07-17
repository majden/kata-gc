package com.sg.gc.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sg.gc.dao.EnumTypeCompte;
import com.sg.gc.dao.EnumTypeOperation;
import com.sg.gc.dao.entities.Compte;
import com.sg.gc.dao.entities.Operation;
import com.sg.gc.dao.repository.CompteRepository;
import com.sg.gc.service.StatementPrintService;

@Service
public class StatementPrintServiceImpl implements StatementPrintService {

	@Autowired
	CompteRepository compteDao;
	
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
	 private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 9,
	            Font.NORMAL);
	 
	
	@Override
	public synchronized void exportStatementPdf(Long clientId) {
		
		try {
			Compte compte = compteDao.getBalanceByClientandType(clientId, EnumTypeCompte.COURANT.getId());
			Document document = new Document();
				try {
					PdfWriter.getInstance(document, new FileOutputStream(".\\statement\\statement-"+compte.getClient().getNom() +" - "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString()+".pdf"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			document.open();
				addTitlePage(document, compte);
			PdfPTable table = new PdfPTable(4);
			float[] columnWidths = {2, 4, 1,1};

		    table.setWidths(columnWidths);
			addTableHeader(table);
			compte.getOperations().forEach(op -> addRows(table, op));
			addTableFooterTotaux(table,compte);
			addTableFooterSolde(table,compte);
			document.add(table);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addTableHeader(PdfPTable table) {
	    Stream.of("Date", "Nature des Operations", "Débit", "Crédit")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addTableFooterSolde(PdfPTable table, Compte compte) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthRight(0);
		cell.setBorderWidthLeft(0);
	    cell.setPhrase(new Phrase(""));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("NOUVEAU SOLDE AU "+ compte.getDateModification().toString()));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(""));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(compte.getSolde().toString()));
	    table.addCell(cell);
	}
	
	private void addTableFooterTotaux(PdfPTable table, Compte compte) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(1);
		cell.setBorderWidthRight(0);
		cell.setBorderWidthLeft(0);
		
	    cell.setPhrase(new Phrase(""));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(" TOTAUX DES MOUVEMENTS "));
	    table.addCell(cell);
	   
	    Optional<BigDecimal> totalCredit = compte.getOperations().stream().map(op -> op.getMontant()).filter(m -> m.compareTo(new BigDecimal("0")) > 0).reduce(BigDecimal:: add);
	    Optional<BigDecimal> totalDebit = compte.getOperations().stream().map(op -> op.getMontant()).filter(m -> m.compareTo(new BigDecimal("0")) < 0).reduce(BigDecimal:: add);

	    cell.setPhrase(new Phrase(totalDebit.get().abs().toString()));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(totalCredit.get().toString()));
	    table.addCell(cell);
	}
	
	private void addRows(PdfPTable table, Operation op) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		cell.setPhrase(new Phrase(op.getDateOperation().toString(), smallBold));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(op.getNatureOperation(), smallBold));
	    table.addCell(cell);
	    if(op.getTypeOperation().equals(EnumTypeOperation.SAVE)) {
	    	cell.setPhrase(new Phrase(""));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase(op.getMontant().toString(), smallBold));
		    table.addCell(cell);
	    } else {
	    	cell.setPhrase(new Phrase(op.getMontant().abs().toString(), smallBold));
		    table.addCell(cell);
		    cell.setPhrase(new Phrase(""));
		    table.addCell(cell);
	    }
	}
	
	private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	 private  void addTitlePage(Document document, Compte compte)
	            throws DocumentException {
	        Paragraph preface = new Paragraph();
	        // We add one empty line
	        addEmptyLine(preface, 1);
	        // Lets write a big header
	        preface.add(new Paragraph("RELEVE DE COMPTE", catFont));
	        preface.setAlignment(Element.ALIGN_RIGHT);
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph("numéro de compte: "+ compte.getRib(), smallBold));
	        preface.add(new Paragraph("Code client: ", smallBold));
	        preface.add(new Paragraph(compte.getClient().getNom() + " "+ compte.getClient().getPrenom(), smallBold));
	        preface.add(new Paragraph("Adresse: "+compte.getClient().getAdresse(), smallBold));
	        preface.add(new Paragraph("Tel: "+compte.getClient().getPhoneNumber(), smallBold));
	        preface.add(new Paragraph("email: "+compte.getClient().getEmail(), smallBold));
	        addEmptyLine(preface, 1);
	        addEmptyLine(preface, 1);

	        document.add(preface);
	    }
	

}
