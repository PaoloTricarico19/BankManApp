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

    @Column(nullable = false, unique = true)
    private int cellulare;

    @Column(nullable = false)
    private String citta;

    @Column(nullable = false)
    private String regione;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String nazione;

    @Column(nullable = false)
    private int cap;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false, unique = true)
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


/*
package com.example.bankmanapp.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name= "cognome", nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false )
    private LocalDate dataDiNascita;

    @Column(name = "cellulare", nullable = false, unique = true)
    private int cellulare;

    @Column(name = "citta", nullable = false)
    private String citta;

    @Column(name = "regione", nullable = false)
    private String regione;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    @Column(name = "nazione", nullable = false )
    private String nazione;

    @Column(name = "cap" , nullable = false)
    private int cap;

    @Column(name = "indirizzo", nullable = false)
    private String indirizzo;

    @Column(name = "codice_fiscale", nullable = false, unique = true)
    private String codiceFiscale;

    @Column(name= "email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "idUtente", cascade = CascadeType.ALL)

    //utilizzate per gestire le relazioni bidirezionali nei modelli di dati Java
    //serve a interrompere il loop di serializzazione JSON nelle relazioni @OneToMany, @ManyToOne
    @JsonBackReference
    private List<Conto> conti = new ArrayList<>();

    public User() {}

    public User(int id, String nome,String cognome, LocalDate dataDiNascita,int cellulare, String citta, String regione,
                String provincia, String nazione, int cap, String indirizzo,
                String email, String password, String codiceFiscale) {

        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.cellulare = cellulare;
        this.citta = citta;
        this.regione = regione;
        this.provincia = provincia;
        this.nazione = nazione;
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.email = email;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) System.out.println("Nome non valido");
        else this.nome = nome;
    }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) {
        if (cognome == null || cognome.isEmpty()) System.out.println("Cognome non valido");
        else this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(LocalDate dataDiNascita) {
        if (dataDiNascita == null) System.out.println("Data di nascita non valida");
        else this.dataDiNascita = dataDiNascita;
    }

    public int getCellulare() { return cellulare; }
    public void setCellulare(int cellulare) {
        if (String.valueOf(cellulare).length() < 8) System.out.println("Numero cellulare non valido");
        else this.cellulare = cellulare;
    }

    public String getCitta() { return citta; }
    public void setCitta(String citta) {
        if (citta == null || citta.isEmpty()) System.out.println("Città non valida");
        else this.citta = citta;
    }

    public String getRegione() { return regione; }
    public void setRegione(String regione) {
        if (regione == null || regione.isEmpty()) System.out.println("Regione non valida");
        else this.regione = regione;
    }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) {
        if (provincia == null || provincia.isEmpty()) System.out.println("Provincia non valida");
        else this.provincia = provincia;
    }

    public String getNazione() { return nazione; }
    public void setNazione(String nazione) {
        if (nazione == null || nazione.isEmpty()) System.out.println("Nazione non valida");
        else this.nazione = nazione;
    }

    public int getCap() { return cap; }
    public void setCap(int cap) {
        if (String.valueOf(cap).length() != 5) System.out.println("CAP non valido");
        else this.cap = cap;
    }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) {
        if (indirizzo == null || indirizzo.isEmpty()) System.out.println("Indirizzo non valido");
        else this.indirizzo = indirizzo;
    }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) {
        if (codiceFiscale == null || codiceFiscale.length() != 16) System.out.println("Codice fiscale non valido");
        else this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) System.out.println("Email non valida");
        else this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) System.out.println("Password non valida");
        else this.password = password;
    }
}
*/