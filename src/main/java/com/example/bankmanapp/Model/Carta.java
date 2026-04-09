package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carte")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 16)
    private String numeroCarta;

    @Column(nullable = false)
    private String titolare;

    @Column(nullable = false)
    private LocalDate dataScadenza;

    @Column(nullable = false, length = 3)
    private Integer cvv;

    @Column(nullable = false)
    private Integer pin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarta tipo;

    private Double fido;

    @Column(nullable = false)
    private Double massimaleMensile;

    @Column(nullable = false)
    private boolean attiva = true;

    // FK verso il Conto
    //cambiamento fatto per non far creare un conto con lo stesso utente di un altro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conto", nullable = false, unique = true)
    private Conto conto;

    // Relazione verso i movimenti
    @OneToMany(mappedBy = "carta")
    private List<Movimenti> listaMovimenti = new ArrayList<>();

    public Carta() {}

    // --- Getters / Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroCarta() { return numeroCarta; }
    public void setNumeroCarta(String numeroCarta) { this.numeroCarta = numeroCarta; }
    public String getTitolare() { return titolare; }
    public void setTitolare(String titolare) { this.titolare = titolare; }
    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }
    public Integer getCvv() { return cvv; }
    public void setCvv(int cvv) { this.cvv = cvv; }
    public Integer getPin() { return pin; }
    public void setPin(Integer pin) { this.pin = pin; }
    public TipoCarta getTipo() { return tipo; }
    public void setTipo(TipoCarta tipo) { this.tipo = tipo; }
    public Double getFido() { return fido; }
    public void setFido(Double fido) { this.fido = fido; }
    public Double getMassimaleMensile() { return massimaleMensile; }
    public void setMassimaleMensile(Double massimaleMensile) { this.massimaleMensile = massimaleMensile; }
    public boolean isAttiva() { return attiva; }
    public void setAttiva(boolean attiva) { this.attiva = attiva; }
    public Conto getConto() { return conto; }
    public void setConto(Conto conto) { this.conto = conto; }
    public List<Movimenti> getListaMovimenti() { return listaMovimenti; }
    public void setListaMovimenti(List<Movimenti> listaMovimenti) { this.listaMovimenti = listaMovimenti; }
}