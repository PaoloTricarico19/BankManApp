package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Service.ContoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    // Metodo per creare un nuovo conto (POST)
    @PostMapping(value = "/Create")
    public ResponseEntity<ContoDto> createConto(@RequestBody Conto nuovoConto) {
        ContoDto creato = contoService.creaConto(nuovoConto);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    // Metodo per trovare un conto tramite ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ContoDto> getContoById(@PathVariable int id) {
        return ResponseEntity.ok(contoService.trovaPerId(id));
    }

    // Metodo per restituire tutti i conti (GET)
    @GetMapping("/Conti")
    public List<ContoDto> getAllConti() {
        return contoService.findAll();
    }


    // Metodo per aggiornare un utente esistente
    // Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<ContoDto> updateConto(@PathVariable int id, @RequestBody ContoDto contoDto) {
        try {
            ContoDto aggiornato = contoService.aggiornaConto(id, contoDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un conto (DELETE)
    @DeleteMapping(value = "/Delete/{id}")
    public ResponseEntity<Void> deleteConto(@PathVariable int id) {
        try {
            contoService.eliminaConto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



/*
package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Service.ContoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

//gestisce richieste http e restituisce dati direttamente nel corpo della risposta
@RestController
@RequestMapping(value = "/Conto")
public class ContoController {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private ContoService contoService;

    //serve a mappare le richiest http post sui metodi del controller
    @PostMapping
    public ResponseEntity<ContoDto> createConto(@RequestBody Conto nuovoConto) {
        log.info("Richiesta POST /Conto - Creazione nuovo conto");
        ContoDto creato = contoService.creaConto(nuovoConto);
        log.info("Conto creato con successo - ID assegnato: {}", creato.id());
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping("/{id}")
    public ResponseEntity<Conto> getConto(@PathVariable int id) {
        log.info("Richiesta GET /Conto/{}", id);
        Conto conto = contoService.trovaPerId(id);
        return ResponseEntity.ok(conto);
    }

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping("/Conti")
    public List<ContoDto> getAllConti() {
        log.info("Richiesta GET /Conto/Conti - Recupero lista totale");
        List<ContoDto> lista = contoService.findAll();
        log.info("Restituiti {} conti", lista.size());
        return lista;
    }

    //serve a mappare le richieste http di tipo put su metodi del controller
    @PutMapping("/{id}")
    public ResponseEntity<ContoDto> updateConto(@PathVariable int id, @RequestBody ContoDto contoDto) {
        log.info("Richiesta PUT /Conto/{} - Aggiornamento dati", id);
        ContoDto aggiornato = contoService.aggiornaConto(id, contoDto);
        log.info("Conto {} aggiornato correttamente", id);
        return ResponseEntity.ok(aggiornato);
    }

    //serve a mappare le richieste http di tipo delete su metodi del controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConto(@PathVariable int id) {
        log.info("Richiesta DELETE /Conto/{}", id);
        contoService.eliminaConto(id);
        log.info("Conto {} eliminato con successo", id);
        return ResponseEntity.noContent().build();
    }
}
 */