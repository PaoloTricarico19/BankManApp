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