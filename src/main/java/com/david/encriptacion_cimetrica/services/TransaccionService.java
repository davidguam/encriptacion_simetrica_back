package com.david.encriptacion_cimetrica.services;

import com.david.encriptacion_cimetrica.model.TransaccionBancaria;
import com.david.encriptacion_cimetrica.repositories.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private DesencriptationService encriptationService;

    // Método para desencriptar y guardar una transacción
    public TransaccionBancaria save(TransaccionBancaria transaccionBancaria) throws Exception {
        // Desencriptar cuenta remitente
        String cuentaRemitenteDescifrada = encriptationService.decryptFromClient(
                transaccionBancaria.getCuentaRemitente(), // Texto cifrado
                transaccionBancaria.getIvCuentaRemitente() // IV correspondiente
        );
        System.out.println("remi: " + cuentaRemitenteDescifrada);
        transaccionBancaria.setCuentaRemitente(cuentaRemitenteDescifrada);

        // Desencriptar cuenta destinatario
        String cuentaDestinatarioDescifrada = encriptationService.decryptFromClient(
                transaccionBancaria.getCuentaDestinatario(), // Texto cifrado
                transaccionBancaria.getIvCuentaDestinatario() // IV correspondiente
        );
        System.out.println("dest: " + cuentaDestinatarioDescifrada);
        transaccionBancaria.setCuentaDestinatario(cuentaDestinatarioDescifrada);

        // Guardar la transacción desencriptada en la base de datos
        return transaccionRepository.save(transaccionBancaria);
    }

    // Método para leer todas las transacciones y encriptar las cuentas antes de devolverlas
    public List<TransaccionBancaria> getAll() throws Exception {
        List<TransaccionBancaria> transacciones = transaccionRepository.findAll();

        // Encriptar las cuentas de cada transacción antes de devolverlas
        for (TransaccionBancaria transaccion : transacciones) {
            // Encriptar cuenta remitente
            transaccion.setCuentaRemitente(encriptationService.encryptForClient(
                    transaccion.getCuentaRemitente(),
                    transaccion.getIvCuentaRemitente()
            ));

            // Encriptar cuenta destinatario
            transaccion.setCuentaDestinatario(encriptationService.encryptForClient(
                    transaccion.getCuentaDestinatario(),
                    transaccion.getIvCuentaDestinatario()
            ));
        }

        return transacciones;
    }
}

