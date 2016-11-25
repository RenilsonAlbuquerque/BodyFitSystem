create database if not exists `academia`;

use academia;

create table `usuario`(
`CPF_U` varchar(12) NOT NULL,
`NOME` varchar(50) NOT NULL,
`SENHA` varchar(20) NOT NULL DEFAULT '123456',
`CAMINHO_FOTO` varchar(20) NOT NULL DEFAULT '/users/foto.jpg',
PRIMARY KEY (`CPF_U`)
);

CREATE TABLE `aluno` (
  `CPF_ALU` varchar(12) NOT NULL,
  `IDADE` int(2) NOT NULL,
  `ALTURA` float NOT NULL,
  `PESO` float NOT NULL,
  PRIMARY KEY (`CPF_ALU`),
  CONSTRAINT `fk_cpf_alu` FOREIGN KEY (`CPF_ALU`) REFERENCES `usuario` (`CPF_U`) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `professor` (
  `CPF_PROF` varchar(12) NOT NULL,
  `CREF` varchar(14) NOT NULL,
  `TURNO` varchar(10) NOT NULL,
  PRIMARY KEY (`CPF_PROF`),
  CONSTRAINT `fk_cpf_prof` FOREIGN KEY (`CPF_PROF`) REFERENCES `usuario` (`CPF_U`) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `administrador` (
  `CPF_ADM` varchar(12) NOT NULL,
  `CARGO` varchar(11) NOT NULL,
  PRIMARY KEY (`CPF_ADM`),
  CONSTRAINT `fk_cpf_adm` FOREIGN KEY (`CPF_ADM`) REFERENCES `usuario` (`CPF_U`) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE `treino_padrao` (
  `CODIGO_TP` int(12) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(11) NOT NULL,
  PRIMARY KEY (`CODIGO_TP`)
);

CREATE TABLE `exercicio_padrao` (
  `CODIGO_EP` int(12) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(11) NOT NULL,
  `CARGA` varchar(10) NOT NULL,
  `REPETICOES` int(3) NOT NULL,
  `INTERVALO` int(2) NOT NULL,
  PRIMARY KEY (`CODIGO_EP`),
  CONSTRAINT `fk_cod_ep` FOREIGN KEY (`CODIGO_EP`) REFERENCES `treino_padrao` (`CODIGO_TP`) ON DELETE NO ACTION ON UPDATE CASCADE
);

