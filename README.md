# Desafio Técnico backend Conexa

Construir uma API REST para que nossos médicos de plantão consigam se logar na plataforma e agendar atendimentos para pacientes.

## Requisitos

- [Java 17](https://adoptopenjdk.net/)
- [MySQL 8.x](https://dev.mysql.com/downloads/installer/)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://www.docker.com/products/docker-desktop)

# Instalação

### 1. Clone o repositório

Clone o repositório para sua máquina local:

```bash
git clone -b snapshot https://github.com/wesleytamiarana/desafio-tecnico-backend-conexa.git conexa

cd conexa
````

### 2. Instale de dependências

##### 1. Maven
Caso ainda não tenha o Maven de deseje executar localmente, veja o procedimento de instalação no link acima.

##### 3.Banco de Dados (MySQL)
Usando Docker basta executar o comando abaixo, ele preparar a base de dados
caso ela ainda não exista mas se preferir e posssivel realizar a instalação conforme o linke de
requisitos enviado acima. Se nao desejares fazer isto agora, podes "pular" para o passo seguinte

```bash
docker compose --profile db
````
Isso irá configurar um contêiner MySQL com as seguintes configurações:

- Banco de dados: cnx_db
- Usuário: cnx
- Senha: itIs@Secret
- Porta: 3306

Para parar sua execução basta executar o comando a seguir

```bash
docker compose --profile dev down
````

### 3. Executando a aplicacãoo

Para executar/parar a aplicação foram desenvolvidos alguns scripts, bastando executá-los.

```bash
./start.sh
````
OBS: Pode acontecer de acontecer erros na inicialização na primeira vez que o script e execitar, 
basta interromper o funcionamento e executar novamente.

Isto ira compilar e exeutar a aplicação permanecendo com suas mensages de execução permanecam 
no console. Para interromper sua execução basta pressionar control + C.

```bash
./startInBackgroud.sh
````

Isto irá compilar e exeutar a aplicação sem que suas informaçoes de execução permanecam no console.
Para interromper sua execução basta aplicar o comando a seguir.

```bash
docker compose --profile dev down
````

Para encerra a aplicação e todas as dependencias associadas a sua execução, como a aplicação em 
background, basta executar o script: stopAndRemoveAll.sh.

```bash
./stopAndRemoveAlll.sh
````
## Autenticação

### Signup
Para para testar essa funcionalidade basta executar o comando aseguir.

```bash
curl -X POST http://localhost:8080/api/v1/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ummedicolegal@email.com",
    "senha": "issoeumsegredo",
    "confirmacaoSenha": "issoeumsegredo",
    "especialidade": "Cardiologista",
    "cpf": "107.736.360-56",
    "dataNascimento": "10/03/1980",
    "telefone": "(21) 3232-6565"
  }'
```
### Login
Para para testar essa funcionalidade basta executar o comando aseguir. Como resultado você receberá
um token que deve ser guardado para os proximos testes.

```bash
curl -X POST http://localhost:8080/api/v1/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ummedicolegal@email.com",
    "senha": "issoeumsegredo"
  }'
```
O resultado pode ser gravado em arquivo texto com o comando abaixo.

```bash
curl -X POST http://localhost:8080/api/v1/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ummedicolegal@email.com",
    "senha": "issoeumsegredo"
  }' >> token.txt
```

### Logoff
Para realizar o logoff, basta exeitar comando a seguir substituindo "token" recebido no processo de 
login.

```
curl -X POST http://localhost:8080/api/v1/logoff \
  -H "Content-Type: application/json" \
  -H "Authorization: token "
```

## Agendamento

### Criação de atendimento
De posse do token de autenticacao obtido basta executar o comando abaixo para realiza agendamento 
de consulta para um paciente. Substitua o "token" pelo token recebido.

```bash
curl -X POST http://localhost:8080/api/v1/attendance \
  -H "Content-Type: application/json" \
  -H "Authorization: token " \
  -d '{
  "dataHora": "2025-08-03 09:00:00",
  "paciente": {
    "nome": "João Castro",
    "cpf": "124.797.750-11"
  }
}'
```
