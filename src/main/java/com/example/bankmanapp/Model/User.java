package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Nome è obbligatorio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Cognome è obbligatorio")
    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true, length = 10)
    private Integer cellulare;

    @NotBlank(message = "Città è obbligatoria")
    @Column(nullable = false)
    private String citta;

    @NotBlank(message = "Regione è obbligatoria")
    @Column(nullable = false)
    private String regione;

    @NotBlank(message = "Provincia è obbligatorio")
    @Column(nullable = false)
    private String provincia;

    @NotBlank(message = "Nazione è obbligatorio")
    @Column(nullable = false)
    private String nazione;

    @Column(nullable = false, length = 5)
    private Integer cap;

    @NotBlank(message = "Indirizzo è obbligatorio")
    @Column(nullable = false)
    private String indirizzo;

    @NotBlank(message = "Codice Fiscale è obbligatorio")
    @Column(nullable = false, unique = true, length = 16)
    private String codiceFiscale;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "Password è obbligatoria")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "idUtente", cascade = CascadeType.ALL)
    private List<Conto> conti = new ArrayList<>();

    public User() {}

    //Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public Integer getCellulare() { return cellulare; }
    public void setCellulare(Integer cellulare) { this.cellulare = cellulare; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getRegione() { return regione; }
    public void setRegione(String regione) { this.regione = regione; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getNazione() { return nazione; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    public Integer getCap() { return cap; }
    public void setCap(Integer cap) { this.cap = cap; }
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