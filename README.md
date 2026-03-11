# Comic Shop – Web Application
<p align="center">
  <img src="https://github.com/Vincenzo2002/ComicShop/blob/main/src/main/webapp/assets/img/logo/logo.png" alt="LogoComicShop" width="500"/>
</p>
Applicazione web sviluppata per l’esame di **Tecnologie Software per il Web**.  
Il progetto consiste nello sviluppo di un sito web per la gestione e la consultazione del catalogo di una **fumetteria online**.

L’applicazione è realizzata utilizzando **Java, Servlet e JSP**, seguendo il pattern architetturale **MVC (Model–View–Controller)** e utilizzando **MySQL** per la gestione dei dati. Il progetto è progettato per essere eseguito su **Apache Tomcat**.

---

## Funzionalità del sito

L’applicazione implementa le principali funzionalità di un sistema e-commerce per la gestione e la vendita di fumetti.

### Funzionalità amministrative

- Gestione degli articoli in vendita (aggiunta, modifica e rimozione dei prodotti)
- Gestione degli ordini
  - Visualizzazione degli ordini per data
  - Ricerca degli ordini per cliente
- Gestione dei codici coupon

### Funzionalità utente registrato

- Aggiungere e rimuovere elementi dal carrello
- Completare un acquisto
- Ricercare e visualizzare i prodotti
- Visualizzare la lista degli ordini effettuati ordinata per data
- Modificare i propri dati anagrafici e le informazioni di pagamento
- Possibilità di aggiungere una recensione ai prodotti acquistati
- Possibilità di ricevere codici coupon

### Funzionalità utente guest

- Aggiungere e rimuovere elementi dal carrello
- Ricercare e visualizzare i prodotti
- Per completare un acquisto è necessario registrarsi (la registrazione può essere effettuata anche durante il checkout)

---

## Tecnologie utilizzate

- Java  
- Jakarta Servlet API  
- JSP  
- Apache Tomcat  
- MySQL  
- JDBC  
- HTML  
- CSS   

---

## Architettura del progetto

Il progetto segue il pattern **MVC** per separare logica applicativa, gestione dei dati e interfaccia utente.

### Model
Contiene le classi responsabili dell’accesso ai dati:

- **DAO (Data Access Object)** per la comunicazione con il database
- **JavaBeans** che rappresentano le entità dell’applicazione (Product, Category, ecc.)

### View
Costituita dalle **pagine JSP**, che gestiscono la presentazione dei dati e l’interfaccia utente.

### Controller
Composto dalle **Servlet**, che gestiscono le richieste HTTP e coordinano l’interazione tra Model e View.

---

## Database

L’applicazione utilizza **MySQL** per la memorizzazione dei dati 

L’accesso al database è gestito tramite **JDBC** e il pattern **DAO**.

---

## Deploy dell'applicazione

L’applicazione è progettata per essere eseguita su **Apache Tomcat**.

Passaggi principali:

1. Clonare il repository

```bash
git clone https://github.com/username/repository-name.git
```

2. Importare il progetto in un IDE.

3. Configurare un database MySQL e creare il database utilizzato dall'applicazione.

4. Aggiornare i parametri di connessione al database nel progetto

5. Deployare il progetto su Apache Tomcat.

6. Avviare Tomcat.

7. Aprire il browser e accedere all'applicazione:
```bash
   http://localhost:8080/nome-applicazione.
```

---

## Requisiti

Per eseguire il progetto sono necessari:

- Java JDK

- Apache Tomcat

- MySQL Server

- Driver JDBC MySQL

- IDE Java

---

## Obiettivo del progetto

Il progetto è stato sviluppato con finalità didattiche per applicare e dimostrare competenze in:

- sviluppo di applicazioni web in Java

- utilizzo del pattern MVC

- integrazione tra applicazione web e database

- gestione delle richieste HTTP tramite Servlet

- deploy di applicazioni web su Apache Tomcat
