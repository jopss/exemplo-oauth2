CREATE TABLE hibernate_sequences(
	sequence_name varchar(255) NOT NULL,
	sequence_next_hi_value bigint NOT NULL
);

CREATE TABLE permissao(
  id bigint NOT NULL,
  dataatualizacao timestamp,
  datacriacao timestamp,
  descricao varchar(255),
  nome varchar(255) NOT NULL,
  papel varchar(255) NOT NULL,
  permissao_pai_id bigint,
  CONSTRAINT permissao_pkey PRIMARY KEY (id),
  CONSTRAINT permissao_pai_fkey FOREIGN KEY (permissao_pai_id) REFERENCES permissao (id)
);

CREATE TABLE perfil(
  id bigint NOT NULL,
  dataatualizacao timestamp,
  datacriacao timestamp,
  descricao varchar(255),
  nome varchar(255) NOT NULL,
  CONSTRAINT perfil_pkey PRIMARY KEY (id)
);

CREATE TABLE perfil_permissao(
  perfil_id bigint NOT NULL,
  permissoes_id bigint NOT NULL,
  CONSTRAINT perfilpermissao_perm_fkey FOREIGN KEY (permissoes_id) REFERENCES permissao (id),
  CONSTRAINT perfilpermissao_perfil_fkey FOREIGN KEY (perfil_id) REFERENCES perfil (id)
);

CREATE TABLE usuario (
    id bigint NOT NULL,
    datacriacao timestamp,
    dataatualizacao timestamp,
    nome varchar(255) NOT NULL,
    login varchar(20) NOT NULL UNIQUE,
    senha varchar(50) NOT NULL,
    perfil_id  bigint NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_perfil_fkey FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);

CREATE TABLE segurancaapi (
    id bigint NOT NULL,
    datacriacao timestamp,
    dataatualizacao timestamp,
    token varchar(1000) NOT NULL,
    expiracaoToken timestamp,
    usuario_id  bigint NOT NULL,
    CONSTRAINT segurancaapi_pkey PRIMARY KEY (id),
    CONSTRAINT segurancaapi_usuario_fkey FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
