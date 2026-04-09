package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Service.CartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Carta")
public class CartaController {

    @Autowired
    private CartaService cartaService;

    // Metodo per creare una nuova carta
    @PostMapping(value = "/Create")
    public ResponseEntity<CartaDto> create(@RequestBody Carta nuovaCarta) {
        CartaDto creata = cartaService.creaCarta(nuovaCarta);
        return new ResponseEntity<>(creata, HttpStatus.CREATED);
    }

    // Metodo per trovare una carta tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> getCartaById(@PathVariable int id) {
        return ResponseEntity.ok(cartaService.findById(id));
    }

    // Metodo per trovare tutte le carte
    @GetMapping(value = "/Carte")
    public List<CartaDto> getAllCarta() {
        return cartaService.findAll();
    }


    // Metodo per aggiornare un utente esistente
    // Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<CartaDto> updateCarta(@PathVariable int id, @RequestBody CartaDto cartaDto) {
        try {
            CartaDto aggiornato = cartaService.aggiornaCarta(id, cartaDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un utente tramite ID
    // Restituisce 204 No Content se l'operazione va a buon fine
    @DeleteMapping(value = "/Delete/{id}")
    public ResponseEntity<Void> deleteCarta(@PathVariable int id) {
        try {
            cartaService.eliminaCarta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


/*
package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;

import com.example.bankmanapp.Service.CartaService;
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
@RequestMapping(value = "/Carta")
public class CartaController {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private CartaService cartaService;

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping
    public List<CartaDto> getAll() {
        log.info("Richiesta GET /Carta");
        return cartaService.findAll();
    }

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> getById(@PathVariable int id) {
        log.info("Richiesta GET /Carta/{}", id);

        return ResponseEntity.ok(cartaService.findById(id));
    }

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping("/{id}/user")
    public ResponseEntity<CartaDto> getUser(@PathVariable int id) {
        log.info("Richiesta GET /Carta/{}/user", id);
        return ResponseEntity.ok(cartaService.findUser(id));
    }

    //serve a mappare le richieste http post sui metodi del controller
    @PostMapping
    public ResponseEntity<CartaDto> create(@RequestBody Carta nuovaCarta) {

        // SICUREZZA: Logghiamo solo il titolare, non tutto il payload (niente PIN/CVV)
        log.info("Richiesta POST /Carta per titolare: {}", nuovaCarta.getTitolare());

        CartaDto creata = cartaService.creaCarta(nuovaCarta);
        log.info("Carta creata con successo - ID: {}", creata.id());
        return new ResponseEntity<>(creata, HttpStatus.CREATED);
    }

    //serve a mappare le richieste http di tipo put su metodi del controller
    @PutMapping("/{id}")
    public ResponseEntity<CartaDto> update(@PathVariable int id, @RequestBody CartaDto cartaDto) {
        log.info("Richiesta PUT /Carta/{} - Aggiornamento dati", id);
        return ResponseEntity.ok(cartaService.update(id, cartaDto));
    }

    //serve a mappare le richieste http di tipo delete su metodi del controller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        log.info("Richiesta DELETE /Carta/{}", id);
        cartaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
 */