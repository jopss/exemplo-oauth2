# exemplo-oauth-2
Projeto de exemplo para configuração de segurança OAuth2 em java.

Feito com:
+ Java 8.
+ Spring Rest retornando JSon.
+ Apache Oltu.
+ JPA e Spring Data.
+ Migration com FlyWay.
+ Tomcat.

Para subir o projeto:
+ Crie um banco de dados Postgres local na porta padrão: bd_exemplo_oauth
+ Voce pode mudar o banco, local e senha no arquivo "environment_DESENVOLVIMENTO.properties"

Pode-se testar com os usuarios abaixo.

Perfil Administrador
+ login: admin
+ senha: admin123

Perfil de Visitante
+ login: visitante
+ senha: visitante123

### Acessando com cURL

A forma mais simples de testar é usar a linha de comando com cURL, simulando um aplicativo ou um AJAX qualquer (seja JQuery ou Angular).

Para logar com o exemplo do usuario admin:

```
curl -X POST -H "Accept: application/json" -d "client_id=exemploaplicativocliente&client_secret=9834ba657bb2c60b5bb53de6f4201905&grant_type=password&username=admin&password=admin123" http://localhost:8080/exemplo-oauth2/endpoints/seguranca/logar
```

Mude a propriedade "username" e "password" para acesso com o outro usuario. Esse comando irá retornar um JSON contendo o Token de acesso aos recursos, que você deverá usá-lo nas demais chamadas.

### Teste de acessos a recursos

Mude a palavra TOKEN dos comandos com o valor retornado após a chamada ao "logar" acima.

Retorno do usuário atual:
```
curl -X GET -H "Accept: application/json" -H "Authorization: Bearer TOKEN" http://localhost:8080/exemplo-oauth2/endpoints/seguranca/usuario/logado --verbose
```

Acesso a um recurso geral (os dois usuarios de testes teriam acesso):
```
curl -X GET -H "Accept: application/json" -H "Authorization: Bearer TOKEN" http://localhost:8080/exemplo-oauth2/endpoints/home/acesso/geral --verbose
```

Acesso a um recurso somente de visitante (e admin tambem poderia):
```
curl -X GET -H "Accept: application/json" -H "Authorization: Bearer TOKEN" http://localhost:8080/exemplo-oauth2/endpoints/home/acesso/visitante --verbose
```

Acesso a um recurso somente de admin (visitante nao pode):
```
curl -X GET -H "Accept: application/json" -H "Authorization: Bearer e40b9d488fbbfc2730b94b1c2fe9c972" http://localhost:8080/exemplo-oauth2/endpoints/home/acesso/admin --verbose
```
