insert into tipo_comp values ("Tiro con l'arco", "Descrizione?");
insert into tipo_comp values ("Corsa campestre", "Descrizione plz");

insert into utente values ('sysman@agroludos.it', PASSWORD('L&L'), 2);
insert into utente values ('luigi.rosini@agroludos.it', PASSWORD('ros290'), 1);
insert into utente values ('luciano.ciccariello@agroludos.it', PASSWORD('Xeey'), 1);

insert into ms values ('sysman@agroludos.it', 0);
insert into mc values ('luigi.rosini@agroludos.it', 'Luigi', 'Rosini', 0);
insert into mc values ('luciano.ciccariello@agroludos.it', 'Luciano', 'Ciccariello', 1);


insert into utente values ('geralt@str.com',PASSWORD('furia'),0);

insert into partecipante values ('MOCCATTE12LOLASD','geralt','di rivia',"via temeria",'1992-10-10','M','IOMORO666','2011-8-23','geralt@str.com','C:\File SRC\MOCCATTE12LOLASD.txt');

insert into competizione values (0,20.50,10,40,"Tiro con l'arco",0,'2014-11-20');
insert into competizione values (1,10.00,5,50,"Corsa campestre",1,'2014-11-10');

describe competizione;
use agroludos;
select * from agroludos.competizione join agroludos.mc on agroludos.competizione.manager_comp=agroludos.mc.id;

show tables;

describe opt_comp;

select * from optional;
select * from competizione;


insert into opt_comp values ('Colazione',0,3.50);

select opt_comp.opt,competizione.tipo,competizione.data_comp from opt_comp join competizione on opt_comp.comp=competizione.id;

select * from prenotazione;

select count(*) from competizione join prenotazione on competizione.id=prenotazione.comp;

select * from optional join opt_comp on optional.nome=opt_comp.opt;