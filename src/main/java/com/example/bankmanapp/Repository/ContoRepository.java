package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContoRepository extends JpaRepository<Conto, Integer> {

    //gestisce il caricamento delle relazioni tra entità risolve il problema delle N+1 query
    //evita di scaricare l'intero database ogni volta che si recupera un oggetto
    @EntityGraph(attributePaths = {"idUtente",
            "listaMovimenti",
            "listaCarte",
            "listaCarte.listaMovimenti"})
    Optional<Conto> findById(int id);

}