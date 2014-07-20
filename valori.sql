use agroludos;

insert into TipoCompetizione values ("Tiro con l'arco", "Descrizione?");
insert into TipoCompetizione values ("Corsa campestre", "Descrizione plz");

insert into utente values ('sysman@agroludos.it', PASSWORD('L&L'), 2);
insert into utente values ('luigi.rosini@agroludos.it', PASSWORD('ros290'), 1);
insert into utente values ('luciano.ciccariello@agroludos.it', PASSWORD('Xeey'), 1);

insert into Manager values ('luigi.rosini@agroludos.it', 'Luigi', 'Rosini');
insert into Manager values ('luciano.ciccariello@agroludos.it', 'Luciano', 'Ciccariello');

insert into Optional values ('Colazione', '-',1);
insert into Optional values ('Pranzo', '-', 1);
insert into Optional values ('Pernotto', '-', 1);


insert into utente values ('geralt@str.com',PASSWORD('furia'),0);

insert into partecipante values ('geralt@str.com','geralt','di rivia', "via temeria" ,'1992-10-10', 'MOCCATTE12LOLASD', 'M','IOMORO666','2013-3-11','Pazzo');

insert into competizione values (0,"Tiro con l'arco",'luigi.rosini@agroludos.it','2014-09-11',5,50,10.00,0);
insert into competizione values (0,"Corsa campestre",'luciano.ciccariello@agroludos.it','2014-10-23',10,30,15.50,0);

insert into optional_competizione values (0,'Colazione',1,1);
insert into optional_competizione values (0,'Pranzo',2,1);
insert into optional_competizione values (0,'Pernotto',2,1);