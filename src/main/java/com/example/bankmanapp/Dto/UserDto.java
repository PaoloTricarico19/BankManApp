package com.example.bankmanapp.Dto;

import java.util.List;

public record UserDto(
        int id,
        String nome,
        String cognome,
        int cellulare,
        String citta,
        String regione,
        String provincia,
        String nazione,
        int cap,
        String indirizzo,
        String codiceFiscale,
        String email,
        String password,
        List<ContoDto> conti // lista dei conti associati come DTO
) {}