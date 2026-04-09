package com.example.bankmanapp.Model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
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

    // --- Getters / Setters ---
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

/*
package com.example.bankmanapp.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="conto")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)

    //utilizzate per gestire le relazioni bidirezionali nei modelli di dati Java
    //serve a interrompere il loop di serializzazione JSON nelle relazioni @OneToMany, @ManyToOne
    @JsonManagedReference
    private User idUtente;

    @Column(name= "iban", nullable = false)
    private String iban;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @OneToMany(mappedBy = "conto", cascade = CascadeType.ALL)

    //utilizzate per gestire le relazioni bidirezionali nei modelli di dati Java
    //serve a interrompere il loop di serializzazione JSON nelle relazioni @OneToMany, @ManyToOne
    @JsonManagedReference
    private List<Carta> listaCarte = new ArrayList<>();

    @OneToMany(mappedBy = "conto", cascade = CascadeType.ALL)
    private List<Movimenti> listaMovimenti = new ArrayList<>();

    public Conto() { this.saldo = BigDecimal.ZERO; }

    public Conto(int id, User idUtente, String iban) {
        this.id = id;
        this.idUtente = idUtente;
        this.iban = iban;
        this.saldo = BigDecimal.ZERO;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal nuovoSaldo) {
        if (nuovoSaldo == null) throw new IllegalArgumentException("Il saldo non può essere null");
        this.saldo = nuovoSaldo;
    }

    public List<Movimenti> getListaMovimenti() {
        return listaMovimenti;
    }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public User getIdUtente() { return idUtente; }
    public void setIdUtente(User idUtente) { this.idUtente = idUtente; }
}
*/