package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Salva un nuovo utente e restituisce il DTO
    public UserDto registraUtente(User nuovoUtente) {
        User utenteSalvato = userRepository.save(nuovoUtente);
        return convertToDto(utenteSalvato);
    }

    // Trova un utente per ID.
    // Metodo robusto: valida l'input, previene SQL Injection tramite JPA
    // e gestisce l'assenza del dato con un'eccezione esplicita.
    public UserDto trovaPerId(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));

        return convertToDto(user);
    }

    // .stream() trasforma la lista in un flusso ordinato di dati
    // .map(this::convertToDto) per ogni record viene applicata la trasformaione
    // .collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<UserDto> findAll() {
        log.debug("Richiesta lista completa utenti");
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public UserDto aggiornaUtente(int id, UserDto dto) {
        // Recuperiamo l'Entity dal database
        log.debug("Aggiornamento utente {} con dati {}", id, dto);
        User esistente = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Impossibile aggiornare: utente {} non trovato", id);
                    return new RuntimeException("Impossibile aggiornare: Utente non trovato con ID: " + id);
                });


        // Aggiorniamo l'Entity usando i dati dal Record DTO (usando i tuoi setter con validazione)
        //Problema dell'aggiornamento era qui
        esistente.setNome(dto.nome());
        esistente.setCognome(dto.cognome());
        esistente.setCellulare(dto.cellulare());
        esistente.setCitta(dto.citta());
        esistente.setRegione(dto.regione());
        esistente.setProvincia(dto.provincia());
        esistente.setNazione(dto.nazione());
        esistente.setCap(dto.cap());
        esistente.setIndirizzo(dto.indirizzo());
        esistente.setCodiceFiscale(dto.codiceFiscale());
        esistente.setEmail(dto.email());
        esistente.setPassword(dto.password());

        // Salviamo le modifiche
        User salvato = userRepository.save(esistente);
        log.info("Utente {} aggiornato correttamente", id);


        // Restituiamo il DTO aggiornato
        return convertToDto(salvato);
    }

    public void eliminaUtente(int id) {
        log.debug("Eliminazione utente con ID {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Tentativo di eliminare utente inesistente con ID {}", id);
            throw new RuntimeException("Errore: Utente con ID " + id + " non esiste.");
        }
        userRepository.deleteById(id);
        log.info("Utente {} eliminato correttamente", id);
    }

    // Conversione Model -> DTO (Completo con i 14 campi richiesti)
    private UserDto convertToDto(User user) {


        // Lista dei ContoDto
        List<ContoDto> contiDto = user.getConti().stream().map(conto -> {

            // Movimenti del conto
            List<MovimentiDto> movimentiConto = conto.getListaMovimenti().stream()
                    .map(m -> new MovimentiDto(
                            m.getId(),
                            m.getConto() != null ? m.getConto().getId() : 0,
                            m.getCarta() != null ? m.getCarta().getId() : 0,
                            m.getImporto(),
                            m.getTipo(),
                            m.getData()
                    ))
                    .collect(Collectors.toList());

            // Liste delle carte del conto
            List<CartaDto> carteDto = conto.getListaCarte().stream()
                    .map(c -> {
                        List<MovimentiDto> movimentiCarta = c.getListaMovimenti().stream()
                                .map(m -> new MovimentiDto(
                                        m.getId(),
                                        m.getConto() != null ? m.getConto().getId() : 0,
                                        m.getCarta() != null ? m.getCarta().getId() : 0,
                                        m.getImporto(),
                                        m.getTipo(),
                                        m.getData()
                                ))
                                .collect(Collectors.toList());

                        return new CartaDto(
                                c.getId(),
                                c.getNumeroCarta(),
                                c.getTitolare(),
                                c.getDataScadenza(),
                                c.getCvv(),
                                c.getPin(),
                                c.getTipo(),
                                c.getFido(),
                                c.getMassimaleMensile(),
                                c.isAttiva(),
                                null,               // non includiamo il conto per evitare ricorsione
                                null,               // non includiamo l'user per evitare ricorsione
                                movimentiCarta
                        );
                    })
                    .collect(Collectors.toList());

            return new ContoDto(
                    conto.getId(),
                    null, // non includiamo UserDto dentro ContoDto per evitare ricorsione
                    conto.getIban(),
                    conto.getSaldo(),
                    movimentiConto,
                    carteDto
            );
        }).collect(Collectors.toList());

        //
        //  DTO finale dell'utente
        return new UserDto(
                user.getId(),
                user.getNome(),
                user.getCognome(),
                user.getCellulare(),
                user.getCitta(),
                user.getRegione(),
                user.getProvincia(),
                user.getNazione(),
                user.getCap(),
                user.getIndirizzo(),
                user.getCodiceFiscale(),
                user.getEmail(),
                null,         // password non esposta
                contiDto
        );
    }

}



/*
package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Genera in automatico un campo logger statico e finale
@Slf4j
@Service
public class UserService {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e inserisce i Bean
    @Autowired
    private UserRepository userRepository;

    // Salva un nuovo utente e restituisce il DTO
    public UserDto registraUtente(User nuovoUtente) {
        log.debug("Registrazione nuovo utente: {}", nuovoUtente);
        User utenteSalvato = userRepository.save(nuovoUtente);
        log.info("Utente salvato con ID {}", utenteSalvato.getId());
        return convertToDto(utenteSalvato);
    }

    public UserDto trovaPerId(int id) {
        log.debug("Ricerca utente con ID {}", id);

        if (id <= 0) {
            log.error("ID non valido: {}", id);
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }

        // Utilizzo di Optional per una gestione sicura
        // in caso un dato potrebbe non esistere
        //Mentre findAll() restituisce sempre una lista (che al massimo è vuota),
        // i metodi che cercano un singolo elemento (come findById) in Spring Data JPA restituiscono
        // un Optional<User>.
        return userRepository.findById(id)
                .map(user -> {
                    log.info("Utente {} trovato", id);
                    return convertToDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("Utente non trovato con ID {}", id);
                    return new RuntimeException("Utente non trovato con ID: " + id);
                });
    }


    //.stream() trasforma la lista in un flusso ordinato di dati
    //.map(this::convertToDto) per ogni record viene applicata la trasformaione
    //.collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<UserDto> findAll() {
        log.debug("Richiesta lista completa utenti");
        List<UserDto> lista = userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        log.info("Trovati {} utenti totali", lista.size());
        return lista;
    }

    //gestisce in modo dichiarativo le transazioni del database garantendo l'integrità dei dati
    //assicura che le operazioni vengano eseguite come un'unica unità di lavoro se hanno successo commit
    //altrimenti rollback
    @Transactional
    public UserDto aggiornaUtente(int id, UserDto dto) {
        log.debug("Aggiornamento utente {} con dati {}", id, dto);

        User esistente = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Impossibile aggiornare: utente {} non trovato", id);
                    return new RuntimeException("Impossibile aggiornare: Utente non trovato con ID: " + id);
                });

        esistente.setNome(dto.nome());
        esistente.setCognome(dto.cognome());
        esistente.setEmail(dto.email());
        esistente.setCodiceFiscale(dto.codiceFiscale());

        User salvato = userRepository.save(esistente);
        log.info("Utente {} aggiornato correttamente", id);

        return convertToDto(salvato);
    }

    public void eliminaUtente(int id) {
        log.debug("Eliminazione utente con ID {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("Tentativo di eliminare utente inesistente con ID {}", id);
            throw new RuntimeException("Errore: Utente con ID " + id + " non esiste.");
        }

        userRepository.deleteById(id);
        log.info("Utente {} eliminato correttamente", id);
    }


    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getNome(),
                user.getCognome(),
                user.getDataDiNascita(),
                user.getCellulare(),
                user.getCitta(),
                user.getRegione(),
                user.getProvincia(),
                user.getNazione(),
                user.getCap(),
                user.getIndirizzo(),
                user.getEmail(),
                null,
                user.getCodiceFiscale()
        );
    }
}

 */