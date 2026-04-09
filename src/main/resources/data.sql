INSERT INTO users (nome, cognome, cellulare, citta, regione, provincia, nazione, cap, indirizzo, codice_fiscale, email, password)
VALUES ('Gennaro', 'Gennaro', 23142143, 'Bari', 'Puglia', 'Bari', 'Italia', 20039, 'Viale Via', '231231IASD23', 'Gennar@gmail.com', '2ASDW31234');

INSERT INTO users (nome, cognome, cellulare, citta, regione, provincia, nazione, cap, indirizzo, codice_fiscale, email, password)
VALUES ('Martino', 'Mar', 23222143, 'Bari', 'Puglia', 'Bari', 'Italia', 20039, 'Viale Verso', '23123AKLSDSD23', 'Mart@gmail.com', '2312FASD24');

INSERT INTO users (nome, cognome, cellulare, citta, regione, provincia, nazione, cap, indirizzo, codice_fiscale, email, password)
VALUES ('Franco', 'Fran', 23772143, 'Bari', 'Puglia', 'Bari', 'Italia', 20039, 'Viale Viale', '231DASDAWIASD23', 'Fran@gmail.com', '2377GASDA34');

INSERT INTO conti(id_utente, iban, saldo)
VALUES (1,'ADWY872HDA', 800);

INSERT INTO conti(id_utente, iban, saldo)
VALUES (2,'ADWY8ADWAHDA', 30);

INSERT INTO conti(id_utente, iban, saldo)
VALUES (3,'ADWYASDW872HDA', 200);

INSERT INTO carte(numero_carta, titolare, data_scadenza, cvv, pin, tipo, fido, massimale_mensile, attiva, id_conto)
VALUES ('2213412', 'Gennaro', DATE '2029-06-20', '213', '7211', 'DEBITO', 66.77, 1000, TRUE, 1);

INSERT INTO carte(numero_carta, titolare, data_scadenza, cvv, pin, tipo, fido, massimale_mensile, attiva, id_conto)
VALUES ('2266412', 'Martino', DATE '2029-02-09', '210', '7911', 'CREDITO', 399, 1000, FALSE, 2);

INSERT INTO carte(numero_carta, titolare, data_scadenza, cvv, pin, tipo, fido, massimale_mensile, attiva, id_conto)
VALUES ('2299412', 'Franco', DATE '2029-10-31', '215', '7611', 'DEBITO', 900, 1000, TRUE, 3);

INSERT INTO movimenti(id_carta, id_conto, importo, data, tipo)
VALUES (1,1,88,'2030-12-18','DEBITO');

INSERT INTO movimenti(id_carta, id_conto, importo, data, tipo)
VALUES (2,2,889,'2030-01-02','CREDITO');

INSERT INTO movimenti(id_carta, id_conto, importo, data, tipo)
VALUES (3,3,654,'2030-07-22','DEBITO');