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

create table opt_comp
(
opt varchar (255) NOT NULL,
comp int (4) NOT NULL,
primary key (opt,comp)
);

alter table opt_comp add foreign key (opt) references optional(nome);

create table competizione
(
id int (4) NOT NULL,
prezzo float (5,2) NOT NULL,
nmin int (2) NOT NULL,
nmax int (3) NOT NULL,
tipo varchar (255) NOT NULL,
primary key (id)
);

alter table competizione add foreign key (tipo) references tipo_comp(nome);

alter table opt_comp add foreign key (comp) references competizione(id);

create table prenotazione
(
part char (16) NOT NULL,
comp int (4) NOT NULL,
primary key (part,comp)
);

alter table prenotazione add foreign key (part) references partecipante(codfisc);
alter table prenotazione add foreign key (comp) references competizione(id);

alter table competizione add manager_comp int (2) NOT NULL;

alter table competizione add foreign key (manager_comp) references mc(id);

alter table opt_comp add prezzo float (4,2) NOT NULL;

create table opt_pren
(
opt varchar (255) NOT NULL,
part char(16) NOT NULL,
comp int (4) NOT NULL,
primary key (opt,part,comp)
);

alter table opt_pren add foreign key (opt,comp) references opt_comp(opt,comp);
alter table opt_pren add foreign key (part,comp) references prenotazione (part,comp);
 
alter table partecipante drop src;

alter table partecipante add src varchar (255) NOT NULL;

alter table competizione add data_comp date NOT NULL;


