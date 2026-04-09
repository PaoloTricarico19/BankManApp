package com.example.bankmanapp.Dto;

import java.math.BigDecimal;
import java.util.List;

public record ContoDto(
        int id,
        UserDto user, // utente proprietario come DTO
        String iban,
        BigDecimal saldo,
        List<MovimentiDto> movimenti,
        List<CartaDto> carte
) {}