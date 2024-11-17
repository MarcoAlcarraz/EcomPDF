package com.ecom.controller;

import com.ecom.model.ProductOrder;
import com.ecom.service.OrderService;
import util.reportes.OrderExporterExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orden")
public class OrderController {

    @Autowired
    private OrderService orderService;  // Servicio que obtiene las órdenes

    // Método para exportar las órdenes a un archivo Excel
    @GetMapping("/exportarOrdenesExcel")
    public void exportarListadoDeOrdenesEnExcel(HttpServletResponse response) throws IOException {
        // Obtener todas las órdenes
        List<ProductOrder> orders = orderService.getAllOrders();

        // Crear el exportador de Excel y exportar las órdenes
        OrderExporterExcel exporter = new OrderExporterExcel(orders);
        exporter.exportar(response);
    }
}
