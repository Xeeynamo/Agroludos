create table mc
(
mail varchar (255) NOT NULL,
nome varchar (45) NOT NULL,
cognome varchar (45) NOT NULL,
id int (2) NOT NULL,
primary key (id)
);

create table ms
(
mail varchar (255) NOT NULL,
id int (2) NOT NULL,
primary key (id)
);

create table tipo_comp
(
nome varchar (255) NOT NULL,
descrizione longtext,
primary key (nome)
);

alter table mc add foreign key (mail) references utente(mail);
alter table ms add foreign key (mail) references utente(mail);
