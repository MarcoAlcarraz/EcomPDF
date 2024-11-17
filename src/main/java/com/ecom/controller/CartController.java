package com.ecom.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Cart;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.UserService;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class CartController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping("/carts/exportarPDF")
    public ResponseEntity<byte[]> exportarPDF(@RequestParam("userId") Integer userId) throws IOException {
        // Obtener detalles del usuario
        UserDtls user = userService.getUserById(userId);

        // Validar si se encontró el usuario
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado.".getBytes());
        }

        List<Cart> carts = cartService.getCartsByUser(userId);
        double totalGeneral = 0.0;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Título del PDF
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Carrito de Compras MARKITO STORE");
                contentStream.endText();

                // Mostrar detalles del usuario
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Cliente: " + user.getName());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Dirección: " + user.getAddress() + ", " + user.getCity() + ", " + user.getState() + " - " + user.getPincode());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Correo: " + user.getEmail());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Teléfono: " + user.getMobileNumber());
                contentStream.endText();

                // Configuraciones para la tabla
                float margin = 50;
                float tableWidth = 500;
                float yStart = 650;
                float yPosition = yStart;
                float rowHeight = 20;
                float colWidth1 = 200; // Producto
                float colWidth2 = 100; // Precio
                float colWidth3 = 100; // Cantidad
                float colWidth4 = 100; // Precio Total

                // Cabecera de la tabla con fondo gris
                contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
                contentStream.addRect(margin, yPosition - 20, tableWidth, 20);
                contentStream.fill();
                contentStream.setNonStrokingColor(Color.BLACK);

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 5, yPosition - 15);
                contentStream.showText("Producto");
                contentStream.newLineAtOffset(colWidth2, 0);
                contentStream.showText("Precio");
                contentStream.newLineAtOffset(colWidth3, 0);
                contentStream.showText("Cantidad");
                contentStream.newLineAtOffset(colWidth4, 0);
                contentStream.showText("Precio Total");
                contentStream.endText();

                yPosition -= 25; // bajar después del encabezado

                // Línea debajo de la cabecera
                contentStream.setLineWidth(0.75f);
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                yPosition -= 5; // Espacio entre cabecera y contenido

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                // Dibujar líneas de la tabla y llenar con datos del carrito
                for (Cart cart : carts) {
                    double totalItemPrice = cart.getTotalPrice();
                    totalGeneral += totalItemPrice;

                    // Líneas de la fila
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(margin + tableWidth, yPosition);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + 5, yPosition - 15);
                    contentStream.showText(cart.getProduct().getTitle());
                    contentStream.newLineAtOffset(colWidth2, 0);
                    contentStream.showText(String.format("$%.2f", cart.getProduct().getDiscountPrice()));
                    contentStream.newLineAtOffset(colWidth3, 0);
                    contentStream.showText(String.valueOf(cart.getQuantity()));
                    contentStream.newLineAtOffset(colWidth4, 0);
                    contentStream.showText(String.format("$%.2f", totalItemPrice));
                    contentStream.endText();

                    yPosition -= rowHeight; // Baja para la siguiente fila
                }

                // Línea final de la tabla
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(margin + tableWidth, yPosition);
                contentStream.stroke();
                yPosition -= 5; // Espacio después de la última fila

                // Mostrar subtotal, IGV y total general
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition - 15);
                contentStream.showText(String.format("Subtotal: $%.2f", totalGeneral * 0.82)); // 82% del total (sin IGV)
                contentStream.endText();
                yPosition -= rowHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition - 15);
                contentStream.showText(String.format("IGV (18%%): $%.2f", totalGeneral * 0.18)); // 18% del total
                contentStream.endText();
                yPosition -= rowHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition - 15);
                contentStream.showText(String.format("Total General: $%.2f", totalGeneral));
                contentStream.endText();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=carrito_compras.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());
        }
    }
}
