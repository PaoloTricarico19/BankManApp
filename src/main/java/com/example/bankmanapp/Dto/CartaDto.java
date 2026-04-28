package com.example.bankmanapp.Dto;

import com.example.bankmanapp.Model.TipoCarta;
import java.time.LocalDate;
import java.util.List;

public record CartaDto(
        int id,
        int numeroCarta,
        String titolare,
        LocalDate dataScadenza,
        Integer cvv,
        Integer pin,
        TipoCarta tipo,
        Double fido,
        Double massimaleMensile,
        boolean attiva,
        ContoDto conto,
        List<MovimentiDto> movimenti,
        List<MovimentiDto> movimentiCarta) {}