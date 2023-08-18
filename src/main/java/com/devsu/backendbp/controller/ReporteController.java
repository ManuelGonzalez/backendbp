package com.devsu.backendbp.controller;

import com.devsu.backendbp.entity.dto.ReporteDTO;
import com.devsu.backendbp.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ReporteDTO generarReporte(@RequestParam("clienteId") Long clienteId, @RequestParam("fechaInicio") Date fechaInicio, @RequestParam("fechaFin") Date fechaFin) {
        return reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
    }
}
