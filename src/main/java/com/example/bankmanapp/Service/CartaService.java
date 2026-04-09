package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Repository.CartaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Genera in automatico un campo logger statico e finale
@Slf4j
@Service
public class CartaService {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private CartaRepository cartaRepository;

    public CartaDto creaCarta(Carta nuovoCarta) {
        log.debug("Creazione nuova carta: {}", nuovoCarta);
        Carta salvato = cartaRepository.save(nuovoCarta);
        log.info("Carta salvata con ID {}", salvato.getId());
        return toDto(salvato);
    }

    //.stream() trasforma la lista in un flusso ordinato di dati
    //.map(this::convertToDto) per ogni record viene applicata la trasformaione
    //.collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<CartaDto> findAll() {
        log.debug("Richiesta lista completa carte");
        return cartaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    public CartaDto findById(int id) {
        log.debug("Ricerca carta con ID {}", id);
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));
        log.info("Carta {} trovata", id);
        return toDto(carta);
    }

    // Aggiunta @Transactional per assicurare il salvataggio dei setter
    //gestisce in modo dichiarativo le transazioni del database garantendo l'integrità dei dati
    //assicura che le operazioni vengano eseguite come un'unica unità di lavoro se hanno successo commit
    //altrimenti rollback
    @Transactional
    public CartaDto aggiornaCarta(int id, CartaDto cartaDto) {

        log.debug("Aggiornamento carta {} con dati {}", id, cartaDto);

        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));

        carta.setNumeroCarta(cartaDto.numeroCarta());
        carta.setTitolare(cartaDto.titolare());
        carta.setDataScadenza(cartaDto.dataScadenza());
        carta.setCvv(cartaDto.cvv());
        carta.setPin(cartaDto.pin());
        carta.setTipo(cartaDto.tipo());
        carta.setFido(cartaDto.fido());
        carta.setMassimaleMensile(cartaDto.massimaleMensile());
        carta.setAttiva(cartaDto.attiva());

        if (cartaDto.conto() != null) {
            Conto conto = new Conto();
            conto.setId(cartaDto.conto().id());
            carta.setConto(conto);
        }

        return toDto(cartaRepository.save(carta));
    }

    public void eliminaCarta(int id) {
        log.debug("Eliminazione carta con ID {}", id);
        if (!cartaRepository.existsById(id)) {
            log.warn("Tentativo di eliminare carta inesistente con ID {}", id);
            throw new RuntimeException("Errore: Carta con ID " + id + " non esiste.");
        }
        cartaRepository.deleteById(id);
        log.info("Carta {} eliminato correttamente", id);
    }

    private CartaDto toDto(Carta carta) {

        List<MovimentiDto> movimenti = carta.getListaMovimenti().stream()
                .map(m -> new MovimentiDto(
                        m.getId(),
                        m.getConto() != null ? m.getConto().getId() : 0,
                        carta.getId(),
                        m.getImporto(),
                        m.getTipo(),
                        m.getData()
                )).collect(Collectors.toList());

        return new CartaDto(
                carta.getId(),
                carta.getNumeroCarta(),
                carta.getTitolare(),
                carta.getDataScadenza(),
                null,
                null,
                carta.getTipo(),
                carta.getFido(),
                carta.getMassimaleMensile(),
                carta.isAttiva(),
                null,
                null,
                movimenti
        );
    }
}


/*
package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Repository.CartaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Genera in automatico un campo logger statico e finale
@Slf4j
@Service
public class CartaService {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private CartaRepository cartaRepository;

    public CartaDto creaCarta(Carta nuovaCarta) {
        log.debug("Creazione nuova carta: {}", nuovaCarta);
        Carta salvato = cartaRepository.save(nuovaCarta);
        log.info("Carta salvata con ID {}", salvato.getId());
        return toDto(salvato);
    }

    //.stream() trasforma la lista in un flusso ordinato di dati
    //.map(this::convertToDto) per ogni record viene applicata la trasformaione
    //.collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<CartaDto> findAll() {
        log.debug("Richiesta lista completa carte");
        List<CartaDto> lista = cartaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Trovate {} carte totali", lista.size());
        return lista;
    }

    public CartaDto findById(int id) {
        log.debug("Ricerca carta con ID {}", id);

        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carta {} non trovata", id);
                    return new RuntimeException("Carta non trovata con ID: " + id);
                });

        log.info("Carta {} trovata", id);
        return toDto(carta);
    }

    public CartaDto findUser(int id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));

        return new CartaDto(
                carta.getId(),
                null,
                carta.getTitolare(),
                null,
                null,
                null,
                null,
                null,
                null,
                null, // attiva -> NULL così Jackson lo omette
                carta.getConto().getIdUtente().getId()
        );
    }

    //gestisce in modo dichiarativo le transazioni del database garantendo l'integrità dei dati
    //assicura che le operazioni vengano eseguite come un'unica unità di lavoro se hanno successo commit
    //altrimenti rollback
    @Transactional
    public CartaDto update(int id, CartaDto cartaDto) {
        log.debug("Aggiornamento carta {} con dati {}", id, cartaDto);

        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Impossibile aggiornare: carta {} non trovata", id);
                    return new RuntimeException("Carta non trovata con ID: " + id);
                });

        carta.setNumeroCarta(cartaDto.numeroCarta());
        carta.setTitolare(cartaDto.titolare());
        carta.setDataScadenza(cartaDto.dataScadenza());
        carta.setCvv(cartaDto.cvv());
        carta.setPin(cartaDto.pin());
        carta.setTipo(cartaDto.tipo());
        carta.setFido(cartaDto.fido());
        carta.setMassimaleMensile(cartaDto.massimaleMensile());
        carta.setAttiva(cartaDto.attiva());

        Carta salvata = cartaRepository.save(carta);
        log.info("Carta {} aggiornata correttamente", id);

        return toDto(salvata);
    }

    public void delete(int id) {
        log.debug("Eliminazione carta con ID {}", id);

        if (!cartaRepository.existsById(id)) {
            log.warn("Tentativo di eliminare carta inesistente con ID {}", id);
            throw new RuntimeException("Impossibile eliminare: carta inesistente.");
        }

        cartaRepository.deleteById(id);
        log.info("Carta {} eliminata correttamente", id);
    }

    private CartaDto toDto(Carta carta) {
        return new CartaDto(
                carta.getId(),
                //numero carta che non vogliamo mostrare
                null,
                carta.getTitolare(),
                carta.getDataScadenza(),
                //cvv che non vogliamo mostrare
                null,
                //pin che non vogliamo mostrare
                null,
                carta.getTipo(),
                carta.getFido(),
                carta.getMassimaleMensile(),
                carta.isAttiva(),
                carta.getConto().getIdUtente().getId() // idUtente
        );
    }
}
 */