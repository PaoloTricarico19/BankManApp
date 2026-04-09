package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/User")
public class UserController {

    @Autowired
    UserService userService;

    // Metodo per la registrazione di un nuovo utente
    // Usa ResponseEntity per restituire lo status 201 Created
    @PostMapping(value = "/Create")

    public ResponseEntity<UserDto> createUser(@RequestBody User nuovoUtente) {
        UserDto creato = userService.registraUtente(nuovoUtente);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    // Metodo che trova un solo utente tramite id
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

    // Metodo che restituisce tutti i record in db
    @GetMapping(value= "/Users")
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }


    // Metodo per aggiornare un utente esistente
    // Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            UserDto aggiornato = userService.aggiornaUtente(id, userDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un utente tramite ID
    // Restituisce 204 No Content se l'operazione va a buon fine
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


/*
package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Service.UserService;
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
@RequestMapping(value = "/User")
public class UserController {

    //abilita l'iniezione automatica delle dipendenze
    //permette al framework di cercare e insierire Bean
    @Autowired
    private UserService userService;

    //metodo che trova un solo utente tramite id
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable int id) {
        log.info("Richiesta recupero profilo utente ID: {}", id);
        return userService.trovaPerId(id);
    }

    //metodo che restituisce tutti i record in db
    //mappa le richieste http get su specifici metodi del controller
    @GetMapping(value= "/Users")
    public List<UserDto> getAllUsers(){
        log.info("Richiesta lista completa utenti");
        List<UserDto> lista = userService.findAll();
        log.info("Recuperati {} utenti totali", lista.size());
        return lista;
    }

    //serve a mappare le richiest http post sui metodi del controller
    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User nuovoUtente) {

        // SICUREZZA CRITICA: Mai loggare l'intero oggetto 'nuovoUtente' perché contiene la password!
        log.info("Tentativo di registrazione nuovo utente con email: {}", nuovoUtente.getEmail());

        UserDto creato = userService.registraUtente(nuovoUtente);

        log.info("Utente registrato con successo - ID: {}", creato.id());
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    //serve a mappare le richieste http di tipo put su metodi del controller
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        log.info("Richiesta aggiornamento dati per utente ID: {}", id);

        UserDto aggiornato = userService.aggiornaUtente(id, userDto);

        log.info("Profilo utente {} aggiornato correttamente", id);
        return ResponseEntity.ok(aggiornato);
    }

    //serve a mappare le richieste http di tipo delete su metodi del controller
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        log.info("Richiesta eliminazione account ID: {}", id);

        userService.eliminaUtente(id);

        log.info("Account utente {} rimosso dal sistema", id);
        return ResponseEntity.noContent().build();
    }
}
 */