create schema track;

use track;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on track.* to user@'localhost';

create table usr_usuario(
    usr_id bigint auto_increment primary key,
    usr_nome varchar(20) not null unique key,
    usr_senha varchar(100) not null,
	usr_meta_diaria bigint
);

create table aut_autorizacao(
	aut_id bigint auto_increment primary key,
	aut_nome varchar(20) not null unique
);

create table uau_usuario_autorizacao(
	usr_id bigint not null,
     aut_id bigint not null,
     primary key (usr_id, aut_id),
     foreign key aut_usuario_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
     foreign key aut_autorizacao_fk (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);

create table hist_historico_consumo(
	hist_id bigint auto_increment primary key,
	hist_datahora datetime not null,
	hist_consumo_ml bigint not null,
	hist_observacao varchar(50),
	usr_id bigint not null,
	foreign key hist_usr_fk (usr_id) references usr_usuario(usr_id) on delete restrict on update cascade
);

insert into usr_usuario (usr_nome,  usr_senha)
  values ('admin', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');

insert into usr_usuario (usr_nome,  usr_senha)
  values ('teste', '$2a$10$y4jfnSO7vDPc06yAPIzN0udaCB.oB9eZjOriJaEQwnFI3yggnuLsO');


insert into aut_autorizacao (aut_nome)
  value ('ROLE_ADMIN');

insert into aut_autorizacao (aut_nome)
  value ('ROLE_USER');

insert into uau_usuario_autorizacao (usr_id, aut_id)
  select usr_id, aut_id
    from usr_usuario, aut_autorizacao
    where usr_nome = 'admin'
    and aut_nome = 'ROLE_ADMIN';

insert into uau_usuario_autorizacao (usr_id, aut_id)
  select usr_id, aut_id
    from usr_usuario, aut_autorizacao
    where usr_nome = 'teste'
    and aut_nome = 'ROLE_USER';