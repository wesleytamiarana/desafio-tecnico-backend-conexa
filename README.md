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
git clone https://github.com/wesleytamiarana/desafio-tecnico-backend-conexa.git conexa
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

