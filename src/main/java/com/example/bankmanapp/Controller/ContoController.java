package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Service.ContoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/Conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    //Metodo per creare un nuovo conto (POST)
    @PostMapping(value = "/Create")
    public ResponseEntity<ContoDto> createConto(@RequestBody Conto nuovoConto) {
        ContoDto creato = contoService.creaConto(nuovoConto);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    //Metodo per trovare un conto tramite ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ContoDto> getContoById(@PathVariable int id) {
        return ResponseEntity.ok(contoService.trovaPerId(id));
    }

    //Metodo per restituire tutti i conti (GET)
    @GetMapping("/Conti")
    public List<ContoDto> getAllConti() {
        return contoService.findAll();
    }


    //Metodo per aggiornare un utente esistente
    //Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/Update/{id}")
    public ResponseEntity<ContoDto> updateConto(@PathVariable int id, @RequestBody ContoDto contoDto) {
        try {ContoDto aggiornato = contoService.aggiornaConto(id, contoDto);
            //update riuscito
            log.info("Conto {} aggiornato correttamente", id);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            //update fallito
            log.warn("Conto {} non aggiornato corretamente", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Metodo per eliminare un conto (DELETE)
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