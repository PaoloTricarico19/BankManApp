package com.example.bankmanapp.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentiDto(
        int id,
        int idConto,
        int idCarta,
        BigDecimal importo,
        String tipo,
        LocalDateTime data
) {}