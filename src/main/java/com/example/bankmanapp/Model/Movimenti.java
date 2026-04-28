package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimenti")
public class Movimenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //FK verso il Conto (Obbligatoria)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conto", nullable = false)
    private Conto conto;

    //FK verso la Carta (Opzionale: es. nulla per un bonifico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carta", nullable = false)
    private Carta carta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal importo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarta tipo;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    //Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Conto getConto() { return conto; }
    public void setConto(Conto conto) { this.conto = conto; }
    public Carta getCarta() { return carta; }
    public void setCarta(Carta carta) { this.carta = carta; }
    public BigDecimal getImporto() { return importo; }
    public void setImporto(BigDecimal importo) { this.importo = importo; }
    public TipoCarta getTipo() { return tipo; }
    public void setTipo(TipoCarta tipo) { this.tipo = tipo; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}