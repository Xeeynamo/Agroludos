use agroludos;

insert into TipoCompetizione values ("Tiro con l'arco", "Fai centro col tiro con l'arco!");
insert into TipoCompetizione values ("Corsa campestre", "Cerca di arrivare primo!");

insert into utente values ('sysman@agroludos.it', PASSWORD('L&L'), 2);
insert into utente values ('luigi.rosini@agroludos.it', PASSWORD('ros290'), 1);
insert into utente values ('luciano.ciccariello@agroludos.it', PASSWORD('Xeey'), 1);

insert into Manager values ('luigi.rosini@agroludos.it', 'Luigi', 'Rosini');
insert into Manager values ('luciano.ciccariello@agroludos.it', 'Luciano', 'Ciccariello');

insert into Optional values ('Colazione', 'Scelta tra caffé bicchere di latte', 1.00);
insert into Optional values ('Pranzo', 'Antipasti, spaghetti e scelta tra carne e pesce', 12.50);
insert into Optional values ('Pernotto', 'Comode camere a letto singolo con impianti di riscaldamento', 20.0);


insert into utente values ('geralt@str.com', PASSWORD('furia'), 0);
insert into partecipante values ('geralt@str.com','geralt','di rivia', "via temeria" ,'1992-10-10', 'MOCCATTE12LOLASD', 'M','IOMORO666','2013-3-11','Pazzo');

insert into competizione values (1, "Tiro con l'arco", 'luigi.rosini@agroludos.it','2014-09-11', 5, 50, 10.00, 0);
insert into optional_competizione values (DEFAULT, 'Colazione', 1, 1.50);

insert into competizione values (2, "Corsa campestre", 'luciano.ciccariello@agroludos.it','2014-10-23', 10, 30, 15.50, 0);
insert into optional_competizione values (DEFAULT, 'Pranzo', 2, 10.00);
insert into optional_competizione values (DEFAULT, 'Pernotto', 2, 15.00);