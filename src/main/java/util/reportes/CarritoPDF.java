package util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import com.ecom.model.Cart;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class CarritoPDF {

    private List<Cart> listaCarts;

    public CarritoPDF(List<Cart> listaCarts) {
        this.listaCarts = listaCarts;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setBorderWidth(1.5f);

        Font fuenteCabecera = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteCabecera.setColor(Color.WHITE);

        String[] encabezados = {"ID", "Usuario", "Producto", "Cantidad", "Precio Unitario", "Precio Total"};
        for (String encabezado : encabezados) {
            celda.setPhrase(new Phrase(encabezado, fuenteCabecera));
            tabla.addCell(celda);
        }
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA);
        fuenteDatos.setColor(Color.BLACK);

        for (Cart cart : listaCarts) {
            PdfPCell celda;

            celda = new PdfPCell(new Phrase(String.valueOf(cart.getId()), fuenteDatos));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5);
            tabla.addCell(celda);

            celda = new PdfPCell(new Phrase(cart.getUser() != null ? cart.getUser().getName() : "N/A", fuenteDatos));
            celda.setPadding(5);
            tabla.addCell(celda);

            celda = new PdfPCell(new Phrase(cart.getProduct() != null ? cart.getProduct().getDescription() : "N/A", fuenteDatos));
            celda.setPadding(5);
            tabla.addCell(celda);

            celda = new PdfPCell(new Phrase(String.valueOf(cart.getQuantity()), fuenteDatos));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5);
            tabla.addCell(celda);

            celda = new PdfPCell(new Phrase(String.format("$%.2f", cart.getProduct().getDiscountPrice()), fuenteDatos));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setPadding(5);
            tabla.addCell(celda);

            celda = new PdfPCell(new Phrase(String.format("$%.2f", cart.getTotalPrice()), fuenteDatos));
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setPadding(5);
            tabla.addCell(celda);
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();

        // Agregar fecha actual
        Font fuenteFecha = FontFactory.getFont(FontFactory.HELVETICA);
        fuenteFecha.setColor(Color.DARK_GRAY);
        fuenteFecha.setSize(10);
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Paragraph fecha = new Paragraph("Fecha de generación: " + fechaActual, fuenteFecha);
        fecha.setAlignment(Paragraph.ALIGN_RIGHT);
        documento.add(fecha);
        
        // Agregar título
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTitulo.setColor(Color.BLUE);
        fuenteTitulo.setSize(18);
        Paragraph titulo = new Paragraph("Lista de Carritos", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        documento.add(titulo);

        // Configurar la tabla
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10);
        tabla.setWidths(new float[]{1f, 2f, 2f, 1.5f, 2f, 2f});
        tabla.setHeaderRows(1);

        // Escribir cabecera y datos
        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
