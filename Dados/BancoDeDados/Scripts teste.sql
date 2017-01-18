create database if not exists `academia`;

use academia;

SET SQL_SAFE_UPDATES=0;

create table `usuario`(
	`CPF_U` varchar(12) NOT NULL,
	`NOME` varchar(50) NOT NULL,
	`SENHA` varchar(20) NOT NULL DEFAULT '123456',
	`CAMINHO_FOTO` varchar(20) NOT NULL DEFAULT 'Default User.png',
	PRIMARY KEY (`CPF_U`)
);

CREATE TABLE `aluno` (
  `CPF_ALU` varchar(12) NOT NULL,
  `IDADE` int(2) NOT NULL,
  `ALTURA` float NOT NULL,
  `PESO` float NOT NULL,	
  PRIMARY KEY (`CPF_ALU`),
  CONSTRAINT `FK_ALU_CPF` FOREIGN KEY (`CPF_ALU`) REFERENCES `usuario` (`CPF_U`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `professor` (
  `CPF_PROF` varchar(12) NOT NULL,
  `CREF` varchar(14) NOT NULL,
  `TURNO` varchar(10) NOT NULL,
  `COORDENADOR` boolean NOT NULL DEFAULT 0,
  PRIMARY KEY (`CPF_PROF`),
  CONSTRAINT `FK_PROF_CPF` FOREIGN KEY (`CPF_PROF`) REFERENCES `usuario` (`CPF_U`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `administrador` (
  `CPF_ADM` varchar(12) NOT NULL,
  `CARGO` varchar(11) NOT NULL,
  PRIMARY KEY (`CPF_ADM`),
  CONSTRAINT `FK_ADM_CPF` FOREIGN KEY (`CPF_ADM`) REFERENCES `usuario` (`CPF_U`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `treino_padrao` (
  `CODIGO_TP` int NOT NULL AUTO_INCREMENT,
  `NOME` varchar(11) NOT NULL,
  PRIMARY KEY (`CODIGO_TP`)
);

CREATE TABLE `exercicio_padrao` (
  `CODIGO_EP` int NOT NULL AUTO_INCREMENT,
  `NOME` varchar(11) NOT NULL,
  `CARGA` varchar(10) NOT NULL,
  `REPETICOES` int(3) NOT NULL,
  `INTERVALO` int(2) NOT NULL,
  PRIMARY KEY (`CODIGO_EP`),
  CONSTRAINT `FK_EP_CODIGO` FOREIGN KEY (`CODIGO_EP`) REFERENCES `treino_padrao` (`CODIGO_TP`) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `treino` (
  `CODIGO_T` int NOT NULL AUTO_INCREMENT,
  `CPF_P` varchar(12) NOT NULL, 
  `NOME` varchar(11) NOT NULL,
  PRIMARY KEY (`CODIGO_T`,`CPF_P`),
  CONSTRAINT `FK_TRE_CPFP` FOREIGN KEY (`CPF_P`) REFERENCES `professor` (`CPF_PROF`) ON DELETE 	NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `exercicio` (
  `CODIGO_E` int NOT NULL AUTO_INCREMENT,
  `CPF_P` varchar(12) NOT NULL, 
  `NOME` varchar(11) NOT NULL,
  `CARGA` varchar(10) NOT NULL,
  `REPETICOES` int NOT NULL,
  `INTERVALO` int NOT NULL,
   PRIMARY KEY (`CODIGO_E`),
   CONSTRAINT `FK_EXE_CPFP` FOREIGN KEY (`CPF_P`) REFERENCES `professor` (`CPF_PROF`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `treino_exercicio`(
    `CODIGO_TRE` int NOT NULL,
    `CODIGO_EXE` int NOT NULL,
	CONSTRAINT `PK_TREEXE` PRIMARY KEY (`CODIGO_TRE`,`CODIGO_EXE`),
    CONSTRAINT `FK_TREEXE_CODT` FOREIGN KEY (`CODIGO_TRE`) REFERENCES `treino` (`CODIGO_T`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_TREEXE_CODE` FOREIGN KEY (`CODIGO_EXE`) REFERENCES `exercicio` (`CODIGO_E`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `aluno_treino`(
	`CPF_ALUNO` varchar(12) NOT NULL,
    `CODIGO_TREINO` int NOT NULL,
	CONSTRAINT `PK_ALUTRE` PRIMARY KEY (`CPF_ALUNO`,`CODIGO_TREINO`),
    CONSTRAINT `FK_ALUTRE_CPFALU` FOREIGN KEY (`CPF_ALUNO`) REFERENCES `aluno` (`CPF_ALU`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_ALUTRE_CODTRE` FOREIGN KEY (`CODIGO_TREINO`) REFERENCES `treino` (`CODIGO_T`)ON DELETE CASCADE ON UPDATE CASCADE
);

DELIMITER $$
CREATE PROCEDURE `removerTreino`(IN codigo int,IN cpfProf varchar(12))
BEGIN
	
    DELETE FROM `treino_exercicio` WHERE (CODIGO_TRE,CODIGO_EXE) = (codigo,cpfProf);
    DELETE FROM `treino` WHERE CODIGO_T = (codigo);
    
END $$
DELIMITER ;   
    
insert into usuario (CPF_U,NOME,SENHA) VALUES ('10870298488','Renilson','1234');
insert into usuario (CPF_U,NOME,SENHA) VALUES ('10870298499','Josivaldo','123');

insert into professor (CPF_PROF,CREF,TURNO,COORDENADOR) VALUES ('10870298488','573822','tarde',1);
insert into professor (CPF_PROF,CREF,TURNO,COORDENADOR) VALUES ('10870298499','573452','manha',0);


insert into administrador(CPF_ADM,CARGO) VALUES ('10870298488','gerente');

insert into administrador (CPF_ADM,CARGO) VALUES ('184719301','patrao');
insert into treino (CPF_P,NOME) values ('10870298488','treino s');
insert into exercicio (CODIGO_E,CPF_P,NOME,CARGA,REPETICOES,INTERVALO) values (1,'10870298488','exercicio1','100',12,30);
insert into treino_exercicio (CODIGO_TRE,CODIGO_EXE) values (1,1);
INSERT INTO academia.aluno(CPF_ALU, IDADE, ALTURA, PESO) values('10870298488',22,1.82,78);
INSERT INTO academia.aluno(CPF_ALU, IDADE, ALTURA, PESO) values('10870298499',27,1.90,85);

DELETE FROM `treino` WHERE (CODIGO_T,CPF_P) = (1,'10870298488');
DELETE FROM `exercicio` WHERE CODIGO_E = (1);
DELETE FROM `aluno` WHERE CPF_ALU = ('10870298488');
DELETE FROM `usuario` WHERE CPF_U = ('10870298488');

call removerTreino(1,'10870298488');


UPDATE academia.usuario SET  NOME = 'Carlos',SENHA = '1234', CAMINHO_FOTO = 'caminhododestino' WHERE CPF_U = (10870298488);

select * from treino_exercicio;
select * from exercicio where CODIGO_E = '1' AND CPF_P = '10870298488';
select * from treino;
select *from aluno;
select *from usuario;

drop procedure if exists removerTreino;
drop table if exists aluno_treino;
drop table if exists treino_exercicio;
drop table if exists exercicio;
drop table if exists treino;
drop table if exists exercicio_padrao;
drop table if exists treino_padrao;
drop table if exists aluno;
drop table if exists professor;
drop table if exists administrador;
drop table if exists usuario;
