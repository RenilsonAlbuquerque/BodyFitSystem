package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import beans.Exercicio;
import beans.Treino;

public class GeradorPDF {

	private static GeradorPDF instance;
	
	private GeradorPDF(){
		
	}
	public static GeradorPDF getInstance(){
		if(instance == null)
			instance = new GeradorPDF();
		return instance;
	}
	
	public File criarPDFTreino(Treino treino) throws Exception{
		Document documento = new Document();
		try {
			
			File arquivo = File.createTempFile("treino do dia", ".tmp");
			PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
			
			 documento.open();
			 documento.addTitle("Treino do dia: " + treino.getNome());
			 
			 documento.add(new Paragraph("Treino do dia: " +treino.getNome()));
			 documento.add(new Paragraph("   "));
             
			 PdfPTable table = new PdfPTable(4);
			 
			 PdfPCell c1 = new PdfPCell(new Phrase("Nome do Execício"));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);

             c1 = new PdfPCell(new Phrase("Carga"));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);

             c1 = new PdfPCell(new Phrase("Intervalo"));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             
             c1 = new PdfPCell(new Phrase("Repetições"));
             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(c1);
             
             table.setHeaderRows(1);
             
             for(Exercicio e : treino.getExerciciosArray()){
            	 table.addCell(e.getNome());
                 table.addCell(e.getCarga());
                 table.addCell(String.valueOf(e.getIntervalo()));
                 table.addCell(String.valueOf(e.getRepeticao()));
             }
             documento.add(table);
             documento.close();
             return arquivo;

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
			throw new Exception("Não foi possível escrever o arquivo");
		}
	}
	public File criarPDFPadraoDeTreinos(ArrayList<Treino> treinos,String nomeAluno) throws Exception{
		Document documento = new Document();
		try {
			
			File arquivo = File.createTempFile("Treino do dia", ".tmp");
			PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
			
			 documento.open();
			 documento.addTitle("Treinos do aluno");
			 
			 documento.add(new Paragraph("Aluno: " + nomeAluno));
			 documento.add(new Paragraph("   "));
			 for(Treino t : treinos){
				 
				 Paragraph pulaLinha = new Paragraph(" ");
				 documento.add(pulaLinha);
				 
				 Paragraph preface = new Paragraph(" ");
				 preface.add(t.getNome());
				 PdfPTable table = new PdfPTable(4);
				 
				 PdfPCell c1 = new PdfPCell(new Phrase("Nome do Execício"));
	             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	             table.addCell(c1);

	             c1 = new PdfPCell(new Phrase("Carga"));
	             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	             table.addCell(c1);

	             c1 = new PdfPCell(new Phrase("Intervalo"));
	             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	             table.addCell(c1);
	             
	             c1 = new PdfPCell(new Phrase("Repetições"));
	             c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	             table.addCell(c1);
	             
	             table.setHeaderRows(1);
	             
	             for(Exercicio e : t.getExerciciosArray()){
	            	 table.addCell(e.getNome());
	                 table.addCell(e.getCarga());
	                 table.addCell(String.valueOf(e.getIntervalo()));
	                 table.addCell(String.valueOf(e.getRepeticao()));
	             }
	             preface.add(table);
	             documento.add(preface);
			 }
			 
             documento.close();
             return arquivo;

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
			throw new Exception("Não foi possível escrever o arquivo");
		}
	}
}
