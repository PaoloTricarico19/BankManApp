package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Service.MovimentiService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Genera in automatico un campo logger statico e finale
@Slf4j
@RestController
@RequestMapping(value = "/Movimento")
public class MovimentiController {


    @Autowired
    private MovimentiService movimentiService;

    @PostMapping(value = "/Create")
    public ResponseEntity<MovimentiDto> create(@RequestBody Movimenti nuovoMovimento) {

        // Logghiamo l'azione senza esporre importo o causale nel log INFO
        log.info("Richiesta creazione nuovo movimento");

        MovimentiDto creato = movimentiService.creaMovimento(nuovoMovimento);

        log.info("Movimento registrato con successo - ID: {}", creato.id());
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentiDto> getMovimentoById(@PathVariable int id) {
        return ResponseEntity.ok(movimentiService.findById(id));
    }

    @GetMapping(value= "/Movimenti")
    public List<MovimentiDto> getAllMovimenti() {
        log.info("Richiesta recupero lista completa movimenti");
        List<MovimentiDto> lista = movimentiService.findAll();
        log.info("Restituiti {} movimenti totali", lista.size());
        return lista;
    }


    // Metodo per aggiornare un utente esistente
    // Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<MovimentiDto> updateMovimenti(@PathVariable int id, @RequestBody MovimentiDto movimentiDto) {
        try {
            MovimentiDto aggiornato = movimentiService.aggiornaMovimenti(id, movimentiDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un utente tramite ID
    // Restituisce 204 No Content se l'operazione va a buon fine
    @DeleteMapping(value = "/Delete/{id}")
    public ResponseEntity<Void> deleteMovimenti(@PathVariable int id) {
        try {
            movimentiService.eliminaMovimenti(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


/*
package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Service.MovimentiService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Genera in automatico un campo logger statico e finale
@Slf4j

//gestisce richieste http e restituisce dati direttamente nel corpo della risposta
@RestController
@RequestMapping(value = "/Movimenti")
public class MovimentiController {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private MovimentiService movimentiService;

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping
    public List<MovimentiDto> getAll() {
        log.info("Richiesta recupero lista completa movimenti");
        List<MovimentiDto> lista = movimentiService.findAll();
        log.info("Restituiti {} movimenti totali", lista.size());
        return lista;
    }

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping("/{id}")
    public ResponseEntity<MovimentiDto> getById(@PathVariable int id) {
        log.info("Richiesta dettaglio movimento ID: {}", id);

        // La gestione dell'eccezione 404 è ora delegata al GlobalExceptionHandler
        return ResponseEntity.ok(movimentiService.findById(id));
    }

    //serve a mappare le richiest http post sui metodi del controller
    @PostMapping
    public ResponseEntity<MovimentiDto> create(@RequestBody Movimenti nuovoMovimento) {

        // Logghiamo l'azione senza esporre importo o causale nel log INFO
        log.info("Richiesta creazione nuovo movimento");

        MovimentiDto creato = movimentiService.creaMovimento(nuovoMovimento);

        log.info("Movimento registrato con successo - ID: {}", creato.id());
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    //serve a mappare le richieste http di tipo put su metodi del controller
    @PutMapping("/{id}")
    public ResponseEntity<MovimentiDto> update(@PathVariable int id, @RequestBody Movimenti movimentoAggiornato) {
        log.info("Richiesta modifica movimento ID: {}", id);
        MovimentiDto aggiornato = movimentiService.update(id, movimentoAggiornato);
        log.info("Movimento {} aggiornato con successo", id);
        return ResponseEntity.ok(aggiornato);
    }

    //serve a mappare le richieste http di tipo delete su metodi del controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        log.info("Richiesta storno/eliminazione movimento ID: {}", id);
        movimentiService.delete(id);
        log.info("Movimento {} eliminato correttamente", id);
        return ResponseEntity.noContent().build();
    }
}
 */