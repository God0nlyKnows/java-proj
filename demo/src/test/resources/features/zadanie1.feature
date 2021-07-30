Feature: zadanie1

    Scenario: Logowanie na  stronie
    Given nawiguje do: 
    | https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account |
    And wpisuje login i haslo
    And klikam zaloguj
    Then powinienem byc na stronie profilu


    

    Scenario Outline: doadaj adres
    Given nawiguje do: 
    | https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account |
    And wpisuje login i haslo
    And klikam zaloguj
    And klikam zakladke adreses
    And klikam dodaj adres
    When uzupelniam dane <alias> and <address> and <city> and <postal> and <country> and <phone>
    And klikam save
    Then powinno sie zapisac

Examples:
    | alias | address | city | postal | country | phone |
    | alias1 | address2 | city3 | postal4 | United Kingdom | 000111222 | 
    | alias11 | address22 | city33 | postal44 | United Kingdom | 000111222 | 
    

Scenario Outline: Usun adres
Given nawiguje do: 
    | https://prod-kurs.coderslab.pl/index.php?controller=authentication&back=my-account |
    And wpisuje login i haslo
    And klikam zaloguj
    And klikam zakladke adreses
    When usuwam adres <alias>
    Then powinien byc usuniety <alias>



    Examples:
    | alias |
    | alias1 |
    | alias11 |
    | alias111 |
    