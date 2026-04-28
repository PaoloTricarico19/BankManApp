package com.example.bankmanapp;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Repository.UserRepository;
import com.example.bankmanapp.Service.UserService;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Obbliga Junit a usare Mockito per gestire i mock e le injection durante i test
//senza questo @Mock e @InjectionMocks non funzionano automaticamente
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

//Crea un mock un oggetto finto che simula UserRepository
    @Mock
    private UserRepository userRepository;

//Crea l'oggetto reale CartaService e inietta dentro i mock necessari
    @InjectMocks
    private UserService userService;

    @Test
    void findAll_listaVuota() {
        when(userRepository.findAll()).thenReturn(List.of());
        List<UserDto> result = userService.findAll();

//Controlla che il result non sia null altrimenti fallisce
        assertNotNull(result);

//Controlla che il result sia vuoto altrimenti fallisce
        assertTrue(result.isEmpty());
    }

    @Test
    void trovaPerId_utenteMinimo() {
        User user = new User();
        user.setId(10);
        user.setNome("Luca");
        when(userRepository.findById(10)).thenReturn(Optional.of(user));
        UserDto dto = userService.trovaPerId(10);

//Verifica che il service abbia l'utente giusto
//e che il mapping verso DTO sia popolato correttamente dall'id
        assertEquals(10, dto.id());
    }

    @Test
    void eliminaUtente_conMovimentiNelConto_lanciaEccezione_eNonElimina() {

        User userMock = mock(User.class);
        Conto contoMock = mock(Conto.class);
        when(contoMock.getListaMovimenti()).thenReturn(List.of(new Movimenti()));
        when(userMock.getConti()).thenReturn(List.of(contoMock));
        when(userRepository.findById(1)).thenReturn(Optional.of(userMock));

//Verifica che il blocco elimini l'utente se esistono movimenti sul conto
        assertThrows(RuntimeException.class, () -> userService.eliminaUtente(1));
    }

    @Test
    void eliminaUtente_conMovimentiSuCarta_lanciaEccezione_eNonElimina() {
        User userMock = mock(User.class);

        Carta cartaMock = mock(Carta.class);
        when(cartaMock.getListaMovimenti()).thenReturn(List.of(new Movimenti()));
        Conto contoMock = mock(Conto.class);

//Vuota -> forza valutazione del ramo carte
        when(contoMock.getListaMovimenti()).thenReturn(List.of());
        when(contoMock.getListaCarte()).thenReturn(List.of(cartaMock));
        when(userMock.getConti()).thenReturn(List.of(contoMock));
        when(userRepository.findById(1)).thenReturn(Optional.of(userMock));

//Non controlla solo se lancia un errore ma anche se sia il tipo atteso
        assertThrows(RuntimeException.class, () -> userService.eliminaUtente(1));
    }
}