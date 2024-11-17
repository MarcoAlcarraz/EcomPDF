package util.reportes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ecom.model.ProductOrder;
import com.ecom.model.Product;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class OrderExporterExcel {

    private List<ProductOrder> orders;

    // Constructor
    public OrderExporterExcel(List<ProductOrder> orders) {
        this.orders = orders;
    }

    // Método para exportar a Excel
    public void exportar(jakarta.servlet.http.HttpServletResponse response) throws IOException {
        // Crear un libro de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ordenes");

        // Crear fila de cabecera
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Order ID");
        headerRow.createCell(1).setCellValue("Product Name");
        headerRow.createCell(2).setCellValue("Quantity");
        headerRow.createCell(3).setCellValue("Price");
        headerRow.createCell(4).setCellValue("Status");
        headerRow.createCell(5).setCellValue("Payment Type");

        // Llenar el Excel con los datos
        int rowNum = 1;
        for (ProductOrder order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getOrderId());  // ID de la orden
            Product product = order.getProduct();  // Obtener el producto
            row.createCell(1).setCellValue(product != null ? product.getDescription() : "N/A");  // Nombre del producto
            row.createCell(2).setCellValue(order.getQuantity());  // Cantidad
            row.createCell(3).setCellValue(order.getPrice());  // Precio
            row.createCell(4).setCellValue(order.getStatus());  // Estado
            row.createCell(5).setCellValue(order.getPaymentType());  // Tipo de pago
        }

        // Escribir el archivo en la respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Ordenes.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
