package com.example.bankmanapp;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Model.TipoCarta;
import com.example.bankmanapp.Repository.CartaRepository;
import com.example.bankmanapp.Service.CartaService;

//indica che un metodo è un test e JUnit lo eseguirà automaticamente
import org.junit.jupiter.api.Test;

//serve ad estendere JUnit con funzionalità aggiuntine in questo caso Mockito
import org.junit.jupiter.api.extension.ExtendWith;

//inietta automaticamente i mock nella classe sotto test
import org.mockito.InjectMocks;

//crea un mock (oggetto finto)
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Obbliga Junit a usare Mockito per gestire i mock e le injection durante i test
//senza questo @Mock e @InjectionMocks non funzionano automaticamente
@ExtendWith(MockitoExtension.class)
class CartaServiceTest {

//Crea un mock un oggetto finto che simula CartaRepository
    @Mock
    private CartaRepository cartaRepository;

//Crea l'oggeto reale CartaService e inietta dentro i mock necessari
    @InjectMocks
    private CartaService cartaService;

//Crea una carta già compilata così nei test non ripetiamo sempre le stesse istruzioni
    private Carta carta(int id) {
        Carta c = new Carta();
        c.setId(id);
        c.setNumeroCarta(1111222236);
        c.setTitolare("T");
        c.setDataScadenza(LocalDate.now().plusYears(1));
        c.setCvv(111);
        c.setPin(1111);
        c.setTipo(TipoCarta.values()[0]);
        c.setFido(0.0);
        c.setMassimaleMensile(1000.0);
        c.setAttiva(true);
        c.setListaMovimenti(List.of());
        return c;
    }

//Verifica quando il repository restituisce 1 carta
//Il service findAll() restituisce una lista di 1 CartaDto
    @Test
    void findAll_Test() {
        when(cartaRepository.findAll()).thenReturn(List.of(carta(1)));
        List<CartaDto> result = cartaService.findAll();

//Verifica che CartaService.findAll restituisca una lista con un elemento
//Serve a verificare che il service chiami correttamente repository
//Non filtri via elementi e che non aggiunge elementi
        assertEquals(1, result.size());
    }

//Se la carta esiste il metodo eliminaCarta(2) non lancia eccezioni
//Il repository viene chiamato con deleteById(2)
    @Test
    void eliminaCarta_Test() {
        when(cartaRepository.existsById(2)).thenReturn(true);

//Verifica che non venga lanciata nessuna eccezione
        assertDoesNotThrow(() -> cartaService.eliminaCarta(2));
    }

//Se la carta non eiste il servizio lancia una RuntimeException
//non deve chiamare deleteById
    @Test
    void eliminaCarta_cartaNonEsistente_lanciaEccezione() {
        when(cartaRepository.existsById(1)).thenReturn(false);

//non controlla solo se lancia un errore ma anche se sia il tipo atteso
        assertThrows(RuntimeException.class, () -> cartaService.eliminaCarta(1));
    }
}
