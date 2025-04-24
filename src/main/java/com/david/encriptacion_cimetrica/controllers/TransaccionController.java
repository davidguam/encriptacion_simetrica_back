package com.david.encriptacion_cimetrica.controllers;


import com.david.encriptacion_cimetrica.model.TransaccionBancaria;
import com.david.encriptacion_cimetrica.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    // Endpoint para guardar una transacción desencriptada en la base de datos
    @PostMapping
    public TransaccionBancaria guardarTransaccion(@RequestBody TransaccionBancaria transaccionBancaria) throws Exception {
        System.out.println(transaccionBancaria);
        return transaccionService.save(transaccionBancaria);
    }

    // Endpoint para obtener todas las transacciones almacenadas
    @GetMapping
    public List<TransaccionBancaria> obtenerTodasLasTransacciones() throws Exception {
        return transaccionService.getAll();
    }
}