package com.david.encriptacion_cimetrica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transacciones")
public class TransaccionBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_remitente", nullable = false)
    private String cuentaRemitente; // Aquí guardas el valor cifrado

    @Column(name = "iv_cuenta_remitente", nullable = false)
    private String ivCuentaRemitente; // Aquí guardas el IV usado para cifrar la cuenta remitente

    @Column(name = "cuenta_destinatario", nullable = false)
    private String cuentaDestinatario; // Aquí guardas el valor cifrado

    @Column(name = "iv_cuenta_destinatario", nullable = false)
    private String ivCuentaDestinatario; // Aquí guardas el IV usado para cifrar la cuenta destinataria

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "descripcion")
    private String descripcion;
}