package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true, length = 10)
    private int cellulare;

    @Column(nullable = false)
    private String citta;

    @Column(nullable = false)
    private String regione;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String nazione;

    @Column(nullable = false, length = 5)
    private int cap;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false, unique = true, length = 16)
    private String codiceFiscale;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "idUtente", cascade = CascadeType.ALL)
    private List<Conto> conti = new ArrayList<>();

    public User() {}

    // --- Getters / Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public int getCellulare() { return cellulare; }
    public void setCellulare(int cellulare) { this.cellulare = cellulare; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getRegione() { return regione; }
    public void setRegione(String regione) { this.regione = regione; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getNazione() { return nazione; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    public int getCap() { return cap; }
    public void setCap(int cap) { this.cap = cap; }
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Conto> getConti() { return conti; }
    public void setConti(List<Conto> conti) { this.conti = conti; }
}