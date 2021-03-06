drop database academia;


CREATE USER 'usuario'@'%' IDENTIFIED BY 'usuario54321';
GRANT INSERT,SELECT,DELETE,UPDATE ON *.* TO 'usuario'@'%' WITH GRANT OPTION;

create database if not exists academia;
 
use academia;


create table if not exists usuario(
	CPF_U varchar(12) NOT NULL,
	NOME varchar(50) NOT NULL,
	SENHA varchar(50) NOT NULL DEFAULT '123456',
	CAMINHO_FOTO varchar(20) NOT NULL DEFAULT 'Default User.png',
    USUARIOALUNO tinyint(1) NOT NULL default 0,
    USUARIOPROFESSOR tinyint(1) NOT NULL default 0,
    USUARIOADMINISTRADOR tinyint(1) NOT NULL default 0,
	PRIMARY KEY (`CPF_U`)
);

CREATE TABLE if not exists professor(
  CPF_PROF varchar(12) NOT NULL,
  CREF varchar(14) NOT NULL,
  TURNO varchar(10) NOT NULL,
  COORDENADOR boolean NOT Null default 0,
  PRIMARY KEY (CPF_PROF),
  CONSTRAINT `FK_PROF_CPF` FOREIGN KEY (CPF_PROF) REFERENCES usuario (CPF_U) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE if not exists aluno(
  CPF_ALU varchar(12) NOT NULL,
  CPF_PROF varchar(12) NOT NULL,
  IDADE int(2) NOT NULL,
  ALTURA float NOT NULL,
  PESO float NOT NULL,	
  PRIMARY KEY (CPF_ALU),
  foreign key(CPF_PROF) references professor(CPF_PROF),
  CONSTRAINT `FK_ALU_CPF` FOREIGN KEY (CPF_ALU) REFERENCES usuario(CPF_U) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE if not exists administrador(
  CPF_ADM varchar(12) NOT NULL,
  CARGO varchar(11) NOT NULL,
  PRIMARY KEY (CPF_ADM),
  CONSTRAINT `FK_ADM_CPF` FOREIGN KEY (CPF_ADM) REFERENCES usuario(CPF_U) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE if not exists treino(
  CODIGO_T int NOT NULL AUTO_INCREMENT,
  CPF_P varchar(12) NOT NULL, 
  NOME varchar(20) NOT NULL,
  PADRAO tinyint(1) NOT NULL default 0,
  PRIMARY KEY (CODIGO_T),
  foreign key (CPF_P) references professor(CPF_PROF)
);

CREATE TABLE if not exists exercicio(
  CODIGO_E int NOT NULL AUTO_INCREMENT,
  CPF_P varchar(12) NOT NULL, 
  NOME varchar(30) NOT NULL,
  CARGA varchar(10) NOT NULL,
  REPETICOES int NOT NULL,
  INTERVALO int NOT NULL,
  SERIES int NOT NULL,
  DESCRICAO varchar(300),
  PADRAO tinyint(1) NOT NULL default 0,
  PRIMARY KEY (CODIGO_E),
  CONSTRAINT `FK_EXE_CPFP` FOREIGN KEY (CPF_P) REFERENCES `professor` (CPF_PROF) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE if not exists treino_exercicio(
    CODIGO_TRE int NOT NULL,
    CODIGO_EXE int NOT NULL,
    PRIMARY KEY (CODIGO_TRE,CODIGO_EXE),
    CONSTRAINT `FK_TREEXE_CODT` FOREIGN KEY (CODIGO_TRE) REFERENCES treino (CODIGO_T) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_TREEXE_CODE` FOREIGN KEY (CODIGO_EXE) REFERENCES exercicio(CODIGO_E) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE if not exists aluno_treino(
	CPF_ALUNO varchar(12) NOT NULL,
    CODIGO_TREINO int NOT NULL,
    ORDEM int NOT NULL,
    PRIMARY KEY (CPF_ALUNO,CODIGO_TREINO),
    CONSTRAINT `FK_ALUTRE_CPFALU` FOREIGN KEY (CPF_ALUNO) REFERENCES aluno (CPF_ALU) ,
    CONSTRAINT `FK_ALUTRE_CODTRE` FOREIGN KEY (CODIGO_TREINO) REFERENCES treino (CODIGO_T)
);

CREATE TABLE IF NOT EXISTS aluno_executa(
	CPF_ALUNO varchar(12) NOT NULL,
    CODIGO_TREINO int NOT NULL,
	DATAEXECUCAO timestamp default current_timestamp(),
    PRIMARY KEY (CPF_ALUNO,CODIGO_TREINO,DATAEXECUCAO),
	CONSTRAINT FOREIGN KEY (CPF_ALUNO) REFERENCES aluno_treino(CPF_ALUNO),
    CONSTRAINT FOREIGN KEY (CODIGO_TREINO) REFERENCES aluno_treino(CODIGO_TREINO)
);

