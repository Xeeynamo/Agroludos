CREATE SCHEMA `agroludos`;

CREATE TABLE IF NOT EXISTS `agroludos`.`Utente` (
  `mail` VARCHAR(255) NOT NULL,
  `password` BLOB NOT NULL,
  `tipo` INT NOT NULL,
  PRIMARY KEY (`mail`),
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Manager` (
  `mail` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`mail`),
  CONSTRAINT `mail`
    FOREIGN KEY (`mail`)
    REFERENCES `agroludos`.`Utente` (`mail`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`TipoCompetizione` (
  `nome` VARCHAR(45) NOT NULL,
  `descrizione` VARCHAR(255) NULL,
  PRIMARY KEY (`nome`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Competizione` (
  `idCompetizione` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  `manager` VARCHAR(255) NOT NULL,
  `data` DATE NOT NULL,
  `partMin` INT NOT NULL,
  `partMax` INT NOT NULL,
  `prezzo` FLOAT(5,2) NOT NULL,
  `annullata` TINYINT(1) NULL,
  PRIMARY KEY (`idCompetizione`),
  UNIQUE INDEX `idCompetizione_idx` (`idCompetizione` ASC),
  INDEX `tipo_idx` (`tipo` ASC),
  INDEX `manager_idx` (`manager` ASC),
  CONSTRAINT `tipo`
    FOREIGN KEY (`tipo`)
    REFERENCES `agroludos`.`TipoCompetizione` (`nome`),
  CONSTRAINT `manager`
    FOREIGN KEY (`manager`)
    REFERENCES `agroludos`.`Manager` (`mail`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Partecipante` (
  `email` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `indirizzo` VARCHAR(255) NOT NULL,
  `datanascita` DATE NOT NULL,
  `codicefiscale` CHAR(16) NOT NULL,
  `sesso` CHAR NOT NULL,
  `tesserasan` CHAR(20) NOT NULL,
  `datasrc` DATE NOT NULL,
  `src` LONGTEXT BINARY NOT NULL,
  UNIQUE INDEX `codicefiscale_UNIQUE` (`codicefiscale` ASC),
  PRIMARY KEY (`codicefiscale`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `email`
    FOREIGN KEY (`email`)
    REFERENCES `agroludos`.`Utente` (`mail`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Prenotazione` (
  `idPrenotazione` INT NOT NULL AUTO_INCREMENT,
  `partecipante` VARCHAR(255) NOT NULL,
  `competizione` INT NOT NULL,
  PRIMARY KEY (`idPrenotazione`),
  INDEX `partecipante_idx` (`partecipante` ASC),
  INDEX `competizione_idx` (`competizione` ASC),
  CONSTRAINT `partecipante`
    FOREIGN KEY (`partecipante`)
    REFERENCES `agroludos`.`Partecipante` (`email`),
  CONSTRAINT `competizione`
    FOREIGN KEY (`competizione`)
    REFERENCES `agroludos`.`Competizione` (`idCompetizione`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Optional` (
  `nome` VARCHAR(45) NOT NULL,
  `descrizione` LONGTEXT NULL,
  `prezzo` FLOAT(5,2) NULL,
  PRIMARY KEY (`nome`)
);


CREATE TABLE IF NOT EXISTS `agroludos`.`Optional_Competizione` (
  `idOptionalCompetizione` INT NOT NULL AUTO_INCREMENT,
  `optional` VARCHAR(45) NOT NULL,
  `competizione` INT NOT NULL,
  `prezzo` FLOAT(5,2) NOT NULL,
  PRIMARY KEY (`optional`, `competizione`),
  UNIQUE INDEX `idOptionalCompetizione` (`idOptionalCompetizione` ASC),
  CONSTRAINT `optional`
    FOREIGN KEY (`optional`)
    REFERENCES `agroludos`.`Optional` (`nome`),
  CONSTRAINT `idCompetizione`
    FOREIGN KEY (`competizione`)
    REFERENCES `agroludos`.`Competizione` (`idCompetizione`)
);

CREATE TABLE IF NOT EXISTS `agroludos`.`Optional_Prenotazione` (
  `optional` INT NULL,
  `prenotazione` INT NULL,
  `selezionato` TINYINT(1) NULL,
  PRIMARY KEY (`optional`,`prenotazione`),
  INDEX `optional_idx` (`optional` ASC),
  INDEX `prenotazione_idx` (`prenotazione` ASC),
  CONSTRAINT `idOptionalCompetizione`
    FOREIGN KEY (`optional`)
    REFERENCES `agroludos`.`Optional_Competizione` (`idOptionalCompetizione`),
  CONSTRAINT `idPrenotazione`
    FOREIGN KEY (`prenotazione`)
    REFERENCES `agroludos`.`Prenotazione` (`idPrenotazione`)
);