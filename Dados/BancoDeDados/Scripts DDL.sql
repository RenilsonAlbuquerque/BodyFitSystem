drop database academia;

create database if not exists `academia`;

use academia;

create table `usuario`(
	`CPF_U` varchar(12) NOT NULL,
	`NOME` varchar(50) NOT NULL,
	`SENHA` varchar(50) NOT NULL DEFAULT '123456',
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
  `COORDENADOR` boolean NOT Null default 0,
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
    `DIA_DA_SEMANA` int NOT NULL check(DIA_DA_SEMANA > 1 and DIA_DA_SEMANA <8),
	CONSTRAINT `PK_ALUTRE` PRIMARY KEY (`CPF_ALUNO`,`CODIGO_TREINO`),
    CONSTRAINT `FK_ALUTRE_CPFALU` FOREIGN KEY (`CPF_ALUNO`) REFERENCES `aluno` (`CPF_ALU`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK_ALUTRE_CODTRE` FOREIGN KEY (`CODIGO_TREINO`) REFERENCES `treino` (`CODIGO_T`)ON DELETE CASCADE ON UPDATE CASCADE
);



DELIMITER $
CREATE FUNCTION autenticar(cpf varchar(12),senha varchar(50)) RETURNS int
BEGIN
    return (
		select COUNT(1) FROM `usuario` WHERE (CPF_U = cpf and SENHA = md5(senha)) 
    );
END
$


DELIMITER $$
CREATE FUNCTION alterarSenha(cpf varchar(12), senhaAntiga varchar(50), novaSenha varchar(50))RETURNS int(1) 
BEGIN
DECLARE resultado int(1) DEFAULT 0;
    UPDATE usuario SET `SENHA` =
		IF( `SENHA` = MD5(senhaAntiga), MD5(novaSenha),senhaAntiga)
	WHERE `CPF_U` = cpf;
    
    SET resultado =  IFNULL(( SELECT distinct 1 FROM  usuario WHERE CPF_U = CPF_U and SENHA = md5(novaSenha)),0);
RETURN resultado;    
END $$
DELIMITER $$;


select * from usuario;



DELIMITER $$
CREATE FUNCTION teste(cpf varchar(12)) RETURNS varchar(50)
BEGIN
DECLARE resultado varchar(50) default 0;
	select SENHA into resultado from usuario where CPF_U = '10870298488';
    set resultado = md5('senha');
return resultado;   
END $$
DELIMITER $$



select *from usuario;
SELECT * FROM `usuario` WHERE CPF_U = '10870298488' and SENHA = (md5('1234'));

    
select autenticar('10870298488','1');
select teste('10870298488');
call alterarSenha('10870298488','123','123456');

    
delete from aluno_treino;
delete from treino_exercicio;
delete from exercicio;
delete from treino;
delete from exercicio_padrao;
delete from treino_padrao;
delete from aluno;
delete from professor;
delete from administrador;
delete from usuario;
    