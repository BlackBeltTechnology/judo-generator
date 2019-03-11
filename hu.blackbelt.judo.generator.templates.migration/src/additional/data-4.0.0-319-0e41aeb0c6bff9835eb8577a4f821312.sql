
INSERT INTO ENUM_Aktiv_e VALUES (12, 'aktiv');
INSERT INTO ENUM_Aktiv_e VALUES (13, 'inaktiv');

INSERT INTO ENUM_FiokTipus VALUES (6, 'BAV');
INSERT INTO ENUM_FiokTipus VALUES (7, 'VT1');
INSERT INTO ENUM_FiokTipus VALUES (8, 'VT2');
INSERT INTO ENUM_FiokTipus VALUES (9, 'OREX');

INSERT INTO ENUM_PmtAdatkor VALUES (110, 'egyszeruAtvilagitas');
INSERT INTO ENUM_PmtAdatkor VALUES (111, 'teljesAtvilagitas');
INSERT INTO ENUM_PmtAdatkor VALUES (112, 'nincs');

INSERT INTO ENUM_KifizetesModja VALUES (139, 'penztari_kifizetes');
INSERT INTO ENUM_KifizetesModja VALUES (140, 'atutalas');

INSERT INTO ENUM_Kockazat VALUES (129, 'alacsony');
INSERT INTO ENUM_Kockazat VALUES (130, 'magas');

INSERT INTO ENUM_SzerzodesTipusa VALUES (131, 'zalogjegy');
INSERT INTO ENUM_SzerzodesTipusa VALUES (132, 'nevesitett');

INSERT INTO ENUM_Ugylettipus VALUES (133, 'zalogugyfel');
INSERT INTO ENUM_Ugylettipus VALUES (134, 'dolgozo');
INSERT INTO ENUM_Ugylettipus VALUES (135, 'vip');

INSERT INTO ENUM_AjanlatStatusz VALUES (136, 'opcio');
INSERT INTO ENUM_AjanlatStatusz VALUES (137, 'elfogadott');
INSERT INTO ENUM_AjanlatStatusz VALUES (138, 'ervenytelen');

INSERT INTO ENUM_RovancsAnyag VALUES (14, 'nemesfem');
INSERT INTO ENUM_RovancsAnyag VALUES (15, 'nem_nemesfem');

INSERT INTO ENUM_TetelHolVan VALUES (16, 'visszaadvaUgyfelnek');
INSERT INTO ENUM_TetelHolVan VALUES (17, 'elveszett');
INSERT INTO ENUM_TetelHolVan VALUES (18, 'tarolohelyen');
INSERT INTO ENUM_TetelHolVan VALUES (19, 'raktarosnal');
INSERT INTO ENUM_TetelHolVan VALUES (20, 'szemelynel');

INSERT INTO ENUM_KezelesiDijEsedekessege VALUES (159, 'ELORE');
INSERT INTO ENUM_KezelesiDijEsedekessege VALUES (160, 'UTOLAG');
INSERT INTO ENUM_KezelesiDijEsedekessege VALUES (161, 'RESZLET');

INSERT INTO ENUM_ToketorlesztesModja VALUES (162, 'EGYOSSZEGU');
INSERT INTO ENUM_ToketorlesztesModja VALUES (163, 'RESZLET');

INSERT INTO ENUM_TargyHolVan VALUES (79, 'szemelynel');
INSERT INTO ENUM_TargyHolVan VALUES (80, 'raktariTetelben');
INSERT INTO ENUM_TargyHolVan VALUES (81, 'elveszett');
INSERT INTO ENUM_TargyHolVan VALUES (82, 'visszaadvaUgyfelnek');

INSERT INTO ENUM_TargyTipus VALUES (75, 'zalog');
INSERT INTO ENUM_TargyTipus VALUES (76, 'talalt');
INSERT INTO ENUM_TargyTipus VALUES (77, 'kihullott');
INSERT INTO ENUM_TargyTipus VALUES (78, 'visszamaradt');

INSERT INTO ENUM_BecslesTipus VALUES (83, 'ertekbecsles');
INSERT INTO ENUM_BecslesTipus VALUES (84, 'ellenorzo_becsles');
INSERT INTO ENUM_BecslesTipus VALUES (85, 'auto_ujraertekeles');


INSERT INTO ET_Zalognem VALUES (85422, 'ékszer'  , 'N', 'Y', 'N', 'Y', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85424, 'óra'     , 'N', 'Y', 'N', 'Y', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85426, 'bőr'     , 'N', 'N', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85428, 'szőrme'  , 'N', 'N', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85430, 'festmény', 'N', 'N', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85432, 'műszaki' , 'N', 'N', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85434, 'porcelán', 'N', 'N', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);
INSERT INTO ET_Zalognem VALUES (85436, 'bca'     , 'N', 'Y', 'N', 'N', 'N', '2016-01-01 00:00:00', NULL);


INSERT INTO ET_Termek VALUES (85648, 'standard'    , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85654, 'dolgozói'    , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85660, 'std-balaton' , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85666, 'dolg-balaton', 159, 162, 132);
INSERT INTO ET_Termek VALUES (85672, 'vip'         , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85677, 'fél-thm'     , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85683, 'tavasz'      , 159, 162, 132);
INSERT INTO ET_Termek VALUES (85689, 'bca'         , 159, 162, 132);


INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85696, 85648, 'standard'    , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85713, 85648, 'standard'    , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85747, 85654, 'dolgozói'    , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85764, 85660, 'std-balaton' , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85781, 85666, 'dolg-balaton', '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85815, 85672, 'vip'         , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85849, 85683, 'tavasz'      , '2016-01-01 00:00:00');
INSERT INTO ET_Termekverzio (id, termek, nev, ervenyessegkezd) VALUES (85861, 85689, 'bca'         , '2016-01-01 00:00:00');



INSERT INTO ET_KolcsonSema (id, nev) VALUES (85490, 'std-30');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85492, 'std-60');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85494, 'std-60-fix');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85496, 'std-90');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85498, 'bca-90');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85500, 'vip-60');
INSERT INTO ET_KolcsonSema (id, nev) VALUES (85502, 'bca-90');




INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85866, 85490, 85696, 'std-30'    , 30, 1.74899995, 0, 3, 1, 90, 1, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85868, 85492, 85696, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85870, 85494, 85696, 'std-60-fix', 60, 0         , 3, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85872, 85496, 85696, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85874, 85490, 85713, 'std-30'    , 30, 1.74899995, 0, 3, 1, 90, 1, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85876, 85492, 85713, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85878, 85494, 85713, 'std-60-fix', 60, 0         , 3, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85880, 85496, 85713, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85890, 85490, 85747, 'std-30'    , 30, 1.74899995, 0, 3, 1, 90, 1, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85892, 85492, 85747, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85894, 85494, 85747, 'std-60-fix', 60, 0         , 3, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85896, 85496, 85747, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85898, 85490, 85764, 'std-30'    , 30, 1.74899995, 0, 3, 1, 90, 1, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85900, 85492, 85764, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85902, 85494, 85764, 'std-60-fix', 60, 0         , 3, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85904, 85496, 85764, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85906, 85490, 85781, 'std-30'    , 30, 1.74899995, 0, 3, 1, 90, 1, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85908, 85492, 85781, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85910, 85494, 85781, 'std-60-fix', 60, 0         , 3, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85912, 85496, 85781, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85926, 85500, 85815, 'vip-60'    , 60, 2.38000011, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85928, 85502, 85815, 'vip-90'    , 90, 3.55999994, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85932, 85498, 85861, 'bca-90'    , 90, 0         , 3, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85934, 85492, 85849, 'std-60'    , 60, 3.48000002, 0, 3, 1, 90, 2, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
INSERT INTO ET_Dijszabas (id, kolcsonSema, termekverzio, nev, futamidoNap, kezelesiDijElore, kezelesiDijUtolag, kezelesiPotdij, hetiKezelesiPotdij, kesedelmiKezelesMax , kesedelmiDijmentesNapok, prolongalasiDij, elotorlesztesiDij, ugyletiKamat, futamidosReszkivaltasiDij, futamidonKivuliReszkivaltasiDij, kesedelmiKamat, kamatmentesNapokSzama, kesedelmesNapokSzamaFelmondasig, toketartozasAranyaFelmondashoz, thm) VALUES (85936, 85492, 85849, 'std-90'    , 90, 5.19000006, 0, 3, 1, 90, 3, 2, 10, 10, 10, 10, 5, NULL, 3, 5, 10);
