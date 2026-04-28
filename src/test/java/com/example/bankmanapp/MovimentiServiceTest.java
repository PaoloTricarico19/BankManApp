package com.example.bankmanapp;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Repository.MovimentoRepository;
import com.example.bankmanapp.Service.MovimentiService;

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
class MovimentiServiceTest {

//Crea un mock un oggetto finto che simula MovimentoRepository
    @Mock
    private MovimentoRepository movimentoRepository;

//Crea l'oggetto reale CartaService e inietta dentro i mock necessari
    @InjectMocks
    private MovimentiService movimentiService;

    @Test
    void findAll_listaVuota() {
        when(movimentoRepository.findAll()).thenReturn(List.of());
        List<MovimentiDto> result = movimentiService.findAll();

//Controlla che il result non sia null altrimenti fallisce
        assertNotNull(result);

//Controlla che il result sia vuoto altrimenti fallisce
        assertTrue(result.isEmpty());
    }

    @Test
    void eliminaMovimenti() {
        when(movimentoRepository.existsById(10)).thenReturn(true);

//Verifica che non venga lanciata nessuna eccezione
        assertDoesNotThrow(() -> movimentiService.eliminaMovimenti(10));
    }

    @Test
    void eliminaMovimenti_nonEsistente_lanciaEccezione() {
        when(movimentoRepository.existsById(99)).thenReturn(false);

//Esegue il codice movimentiService.eliminaMovimenti(99)
//Si aspetta che venga lanciata un’eccezione di un certo tipo
//Cattura l’eccezione lanciata dentro la variabile ex per poterla analizzare
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> movimentiService.eliminaMovimenti(99)
        );

//Verifica che il messaggio dell'eccezione contenga la stringa non esistente
//se contiene quella frase tutto ok se diversa fallisce
        assertTrue(ex.getMessage().contains("non esiste"));
    }
}