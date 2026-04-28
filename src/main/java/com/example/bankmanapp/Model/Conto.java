package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="conti")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //cambiamento fatto per non far creare un conto con lo stesso utente di un altro
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private User idUtente;

    @NotBlank(message = "Iban è obbligatorio")
    @Column(nullable = false, length = 27)
    private String iban;

    @Column
    private BigDecimal saldo;

    @OneToMany(mappedBy = "conto", cascade = CascadeType.ALL)
    private List<Movimenti> listaMovimenti = new ArrayList<>();

    @OneToMany(mappedBy = "conto", cascade = CascadeType.ALL)
    private List<Carta> listaCarte = new ArrayList<>();

    public Conto() { this.saldo = BigDecimal.ZERO; }

    public Conto(User idUtente, String iban) {
        this.idUtente = idUtente;
        this.iban = iban;
        this.saldo = BigDecimal.ZERO;
    }

    //Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public User getIdUtente() { return idUtente; }
    public void setIdUtente(User idUtente) { this.idUtente = idUtente; }
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public List<Movimenti> getListaMovimenti() { return Collections.unmodifiableList(listaMovimenti); }
    public List<Carta> getListaCarte() { return Collections.unmodifiableList(listaCarte); }
}