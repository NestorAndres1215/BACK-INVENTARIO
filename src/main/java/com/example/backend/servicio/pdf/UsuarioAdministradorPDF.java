package com.example.backend.servicio.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.entidades.Producto;
import com.example.backend.modelo.Usuario;
import com.example.backend.modelo.UsuarioRol;
import com.example.backend.repositorios.ProveedorRepository;
import com.example.backend.repositorios.UsuarioRepository;
import com.example.backend.repositorios.UsuarioRolRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class UsuarioAdministradorPDF {
	
	@Autowired
	private UsuarioRolRepository usuarioRepository;
	
	public byte[] generarInformePdf() throws DocumentException {
		List<UsuarioRol> productosActivos = usuarioRepository.findByRolRolNombreAndUsuarioEnabled("ADMIN", true);;

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter.getInstance(document, byteArrayOutputStream);

		document.open();

		// Crear un Paragraph para el título con estilo y alineación centrados
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
		Paragraph title = new Paragraph("Informe de Usuario Administrador", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// Agregar espacio (párrafo vacío) debajo del título
		Paragraph emptySpace = new Paragraph(" "); // Puede personalizar el espacio aquí
		document.add(emptySpace);

		// Crear una tabla
		PdfPTable table = new PdfPTable(6); // 3 columnas para ID, Nombre y Precio
		table.setWidthPercentage(100);

		// Configurar el ancho de las columnas (en porcentaje)
		float[] columnWidths = { 10f, 15f, 10f,10f,10f,10f }; // Por ejemplo, 20% para la columna ID, 40% para Nombre, 20% para
													// Precio
		table.setWidths(columnWidths);

		// Encabezados de la tabla con estilo
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
		PdfPCell headerCell;

		headerCell = new PdfPCell(new Phrase("ID", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Nombre", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Apellido", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);
		
		headerCell = new PdfPCell(new Phrase("Direccion", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);
		
		headerCell = new PdfPCell(new Phrase("Telefono", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);
		
		headerCell = new PdfPCell(new Phrase("Dni", headerFont));
		headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
		headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(headerCell);
		
		// Agregar datos de productos a la tabla con estilo
		Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell;

		for (UsuarioRol producto : productosActivos) {
		    PdfPCell cell1; // Declarar la variable cell una vez

		    // Crea una celda para el ID de UsuarioRol
		    cell1 = new PdfPCell(new Phrase(String.valueOf(producto.getUsuarioRolId()), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);

		    // Crea una celda para el Nombre del Usuario
		    cell1 = new PdfPCell(new Phrase(producto.getUsuario().getNombre(), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);

		    // Crea una celda para el Apellido del Usuario
		    cell1 = new PdfPCell(new Phrase(producto.getUsuario().getApellido(), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);

		    // Crea una celda para la Dirección del Usuario
		    cell1 = new PdfPCell(new Phrase(producto.getUsuario().getDireccion(), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);

		    // Crea una celda para el Teléfono del Usuario
		    cell1 = new PdfPCell(new Phrase(producto.getUsuario().getTelefono(), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);

		    // Crea una celda para el DNI del Usuario
		    cell1 = new PdfPCell(new Phrase(String.valueOf(producto.getUsuario().getDni()), cellFont));
		    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell1);
		}

		// Agregar la tabla al documento
		document.add(table);

		document.close();

		return byteArrayOutputStream.toByteArray();
	}
}
