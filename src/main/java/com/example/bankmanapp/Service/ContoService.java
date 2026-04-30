package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Repository.ContoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ContoService {
    @Autowired
    private ContoRepository contoRepository;

    public ContoDto creaConto(Conto nuovoConto) {
        log.debug("Creazione nuovo conto: {}", nuovoConto);
        Conto salvato = contoRepository.save(nuovoConto);
        log.info("Conto salvato con ID {}", salvato.getId());
        return convertToDto(salvato);
    }


    public ContoDto trovaPerId(int id) {
        log.debug("Ricerca conto con ID {}", id);

        if (id <= 0) {
            log.error("ID non valido: {}", id);
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }

        Conto conto = contoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conto non trovato con ID: " + id));
        log.info("Conto {} trovato", id);
        return convertToDto(conto);
    }


    public List<ContoDto> findAll() {
        log.debug("Richiesta lista completa conti");
        return contoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ContoDto aggiornaConto(int id, ContoDto dto) {
        log.debug("Aggiornamento conto {} con dati {}", id, dto);

        Conto esistente = contoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile aggiornare: ID non trovato"));


        //usiamo dto.saldo() e non getSaldo() perché è un Record
        esistente.setSaldo(dto.saldo());
        esistente.setIban(dto.iban());

        Conto salvato = contoRepository.save(esistente);
        return convertToDto(salvato);
    }


    public void eliminaConto(int id) {
        log.debug("Eliminazione conto con ID {}", id);

        if (!contoRepository.existsById(id)) {
            log.warn("Tentativo di eliminare conto inesistente con ID {}", id);
            throw new RuntimeException("Errore: Conto con ID " + id + " non esiste.");
        }

        Conto conto = contoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Errore: Conto con ID " + id + " non esiste."));

        //controlla se esiste almeno un movimento associato all'utente più precisamente
        //un movimento diretto su un conto o un movimento su una carta collegata a un conto
        boolean Movimenti =
                !conto.getListaMovimenti().isEmpty() ||
                        conto.getListaCarte().stream()
                                .anyMatch(carta -> !carta.getListaMovimenti().isEmpty());

        if (Movimenti) {
            log.warn("Impossibile eliminare conto {}: movimenti associati presenti", id);
            throw new RuntimeException(
                    "Impossibile eliminare il conto: eliminare prima i movimenti associati"
            );
        }

        contoRepository.deleteById(id);
        log.info("Conto {} eliminato correttamente", id);
    }


    private ContoDto convertToDto(Conto conto) {

        UserDto userDto = new UserDto(
                conto.getIdUtente().getId(),
                conto.getIdUtente().getNome(),
                conto.getIdUtente().getCognome(),
                conto.getIdUtente().getCellulare(),
                conto.getIdUtente().getCitta(),
                conto.getIdUtente().getRegione(),
                conto.getIdUtente().getProvincia(),
                conto.getIdUtente().getNazione(),
                conto.getIdUtente().getCap(),
                conto.getIdUtente().getIndirizzo(),
                conto.getIdUtente().getCodiceFiscale(),
                conto.getIdUtente().getEmail(),
                null,
                null
        );

        List<MovimentiDto> movimenti = conto.getListaMovimenti().stream()
                .map(m -> new MovimentiDto(
                        m.getId(),
                        conto.getId(),
                        m.getCarta() != null ? m.getCarta().getId() : 0,
                        m.getImporto(),
                        m.getTipo(),
                        m.getData()
                )).collect(Collectors.toList());

        List<CartaDto> carte = conto.getListaCarte().stream().map(c -> {

            List<MovimentiDto> movCarta = c.getListaMovimenti().stream()
                    .map(m -> new MovimentiDto(
                            m.getId(),
                            conto.getId(),
                            c.getId(),
                            m.getImporto(),
                            m.getTipo(),
                            m.getData()
                    )).collect(Collectors.toList());

            return new CartaDto(
                    c.getId(),
                    c.getNumeroCarta(),
                    c.getTitolare(),
                    c.getDataScadenza(),
                    null,
                    null,
                    c.getTipo(),
                    c.getFido(),
                    c.getMassimaleMensile(),
                    c.isAttiva(),
                    null,
                    null,
                    movCarta
            );
        }).collect(Collectors.toList());

        return new ContoDto(
                conto.getId(),
                userDto,
                conto.getIban(),
                conto.getSaldo(),
                movimenti,
                carte
        );
    }
}