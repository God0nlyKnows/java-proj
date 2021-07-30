Feature: zadanie2

    Scenario: Logowanie na  stronie
    Given nawiguje do: 
    | https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account |
    And wpisuje login i haslo
    And klikam zaloguj
    Then powinienem byc na stronie profilu



    Scenario Outline: kup sweter
    Given nawiguje do: 
    | https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account |
    And wpisuje login i haslo
    And klikam zaloguj
    Given nawiguje do:
    | https://prod-kurs.coderslab.pl/index.php?id_product=2&id_product_attribute=10&rewrite=brown-bear-printed-sweater&controller=product |
    And czy ma przecene
    | 20% |
    And wybiera rozmiar <rozmiar>
    And wybiera ilosc <ilosc>
    And dodaje do koszyka
    And kliknij opcje checkout
    And potwierdza kupno
    And wybiera i potwierdza adres <adres>
    And wybiera i potwierdza dostawe
    When wybiera i potwierdza platnosc
    Then zrzut ekranu zamowienia

    

Examples:
    | rozmiar | ilosc | adres |
    | M | 5 | uniqueAdress |
    | XL | 4 | uniqueAdress | 
    

