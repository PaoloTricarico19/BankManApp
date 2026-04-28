package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/User")
public class UserController {

    @Autowired
    UserService userService;

    //Metodo per la registrazione di un nuovo utente
    //Usa ResponseEntity per restituire lo status 201 Created
    @PostMapping(value = "/Create")

    public ResponseEntity<UserDto> createUser(@RequestBody @Valid User nuovoUtente) {
        UserDto creato = userService.registraUtente(nuovoUtente);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    //Metodo che trova un solo utente tramite id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        try {
            UserDto userDto = userService.trovaPerId(id);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            // ID non valido
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // Utente non trovato
            return ResponseEntity.notFound().build();
        }
    }
    //Metodo che restituisce tutti i record in db
    @GetMapping(value= "/Users")
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }

    //Metodo per aggiornare un utente esistente
    //Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            UserDto aggiornato = userService.aggiornaUtente(id, userDto);
            //log SOLO se la transazione è andata a buon fine
            log.info("Utente {} aggiornato correttamente", id);

            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            //log di warning (update FALLITO)
            log.warn("Utente {} non aggiornato corretamente", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Metodo per eliminare un utente tramite ID
    //Restituisce 204 No Content se l'operazione va a buon fine
    @DeleteMapping(value = "/Delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.eliminaUtente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
