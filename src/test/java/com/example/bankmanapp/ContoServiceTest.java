package com.example.bankmanapp;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Repository.ContoRepository;
import com.example.bankmanapp.Service.ContoService;

//indica che un metodo è un test e JUnit lo eseguirà automaticamente
import org.junit.jupiter.api.Test;

//serve ad estendere JUnit con funzionalità aggiuntine in questo caso Mockito
import org.junit.jupiter.api.extension.ExtendWith;

//inietta automaticamente i mock nella classe sotto test
import org.mockito.InjectMocks;

//crea un mock (oggetto finto)
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Obbliga Junit a usare Mockito per gestire i mock e le injection durante i test
//senza questo @Mock e @InjectionMocks non funzionano automaticamente
@ExtendWith(MockitoExtension.class)
class ContoServiceTest {

//Crea un mock un oggetto finto che simula ContoRepository
    @Mock
    private ContoRepository contoRepository;

//Crea l'oggetto reale CartaService e inietta dentro i mock necessari
    @InjectMocks
    private ContoService contoService;

    @Test
    void findAll_listaVuota() {
        when(contoRepository.findAll()).thenReturn(List.of());
        List<ContoDto> result = contoService.findAll();

//controlla che il result non sia null altrimenti fallisce
        assertNotNull(result);

//controlla che il result sia vuoto altrimenti fallisce
        assertTrue(result.isEmpty());
    }

    @Test
    void eliminaConto() {
        when(contoRepository.existsById(10)).thenReturn(true);

//Verifica che non venga lanciata nessuna eccezione
        assertDoesNotThrow(() -> contoService.eliminaConto(10));
    }

    @Test
    void eliminaConto_nonEsistente_lanciaEccezione() {
        when(contoRepository.existsById(99)).thenReturn(false);

//Esegue il codice movimentiService.eliminaMovimenti(99)
//Si aspetta che venga lanciata un’eccezione di un certo tipo
//Cattura l’eccezione lanciata dentro la variabile ex per poterla analizzare
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> contoService.eliminaConto(99)
        );

//Verifica che il messaggio dell'eccezione contenga la stringa non esiste
//se contine quella frase tutto ok se diverso fallisce
        assertTrue(ex.getMessage().contains("non esiste"));
    }
}