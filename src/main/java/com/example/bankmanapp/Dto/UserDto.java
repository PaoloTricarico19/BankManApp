package com.example.bankmanapp.Dto;

import java.util.List;

public record UserDto(
        Integer id,
        String nome,
        String cognome,
        Integer cellulare,
        String citta,
        String regione,
        String provincia,
        String nazione,
        Integer cap,
        String indirizzo,
        String codiceFiscale,
        String email,
        String password,
        List<ContoDto> conti // lista dei conti associati come DTO
) {}