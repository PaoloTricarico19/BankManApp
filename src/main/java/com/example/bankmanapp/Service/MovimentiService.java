package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Repository.MovimentoRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Genera in automatico un campo logger statico e finale
@Slf4j
@Service
public class MovimentiService {

    //private static final Logger log = LoggerFactory.getLogger(MovimentiService.class);

    @Autowired
    private MovimentoRepository movimentoRepository;

    public MovimentiDto creaMovimento(Movimenti nuovoMovimenti) {
        log.debug("Creazione nuovo movimento: {}", nuovoMovimenti);
        Movimenti movimentoSalvato = movimentoRepository.save(nuovoMovimenti);
        log.info("Movimento salvato con ID {}", movimentoSalvato.getId());
        return toDto(movimentoSalvato);
    }

    public MovimentiDto findById(int id) {
        log.debug("Ricerca movimento con ID {}", id);

        if (id <= 0) {
            log.error("ID non valido: {}", id);
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }


        return movimentoRepository.findById(id)
                .map(mov -> {
                    log.info("Movimento {} trovato", id);
                    return toDto(mov);
                })
                .orElseThrow(() -> {
                    log.warn("Movimento {} non trovato", id);
                    return new RuntimeException("Movimento non trovato con ID: " + id);
                });
    }

    //.stream() trasforma la lista in un flusso ordinato di dati
    //.map(this::convertToDto) per ogni record viene applicata la trasformaione
    //.collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<MovimentiDto> findAll() {
        log.debug("Richiesta lista completa movimenti");
        List<MovimentiDto> lista = movimentoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Trovati {} movimenti totali", lista.size());
        return lista;
    }

    public MovimentiDto aggiornaMovimenti(int id, MovimentiDto movimentiDto) {

        log.debug("Aggiornamento movimento {} con dati {}", id, movimentiDto);

        Movimenti esistente = movimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Movimento non trovato con ID: " + id
                ));

        Conto conto = new Conto();
        conto.setId(movimentiDto.idConto());
        esistente.setConto(conto);

        if (movimentiDto.idCarta() > 0) {
            Carta carta = new Carta();
            carta.setId(movimentiDto.idCarta());
            esistente.setCarta(carta);
        } else {
            esistente.setCarta(null);
        }

        esistente.setImporto(movimentiDto.importo());
        esistente.setTipo(movimentiDto.tipo());

        Movimenti salvato = movimentoRepository.save(esistente);

        return toDto(salvato);
    }

    public void eliminaMovimenti(int id) {
        log.debug("Eliminazione movimento con ID {}", id);
        if (!movimentoRepository.existsById(id)) {
            log.warn("Tentativo di eliminare movimento inesistente con ID {}", id);
            throw new RuntimeException("Errore: Movimento con ID " + id + " non esiste.");
        }
        movimentoRepository.deleteById(id);
        log.info("Movimento {} eliminato correttamente", id);
    }

    private MovimentiDto toDto(Movimenti m) {
        return new MovimentiDto(
                m.getId(),
                m.getConto() != null ? m.getConto().getId() : 0,
                m.getCarta() != null ? m.getCarta().getId() : 0,
                m.getImporto(),
                m.getTipo(),
                m.getData()
        );
    }
}