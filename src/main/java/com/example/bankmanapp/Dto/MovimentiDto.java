package com.example.bankmanapp.Dto;

import com.example.bankmanapp.Model.TipoCarta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentiDto(
        int id,
        int idConto,
        int idCarta,
        BigDecimal importo,
        TipoCarta tipo,
        LocalDateTime data
) {}