```
springbatch-showcases/
├── HELP.md                     # Arquivo de ajuda do projeto
├── mvnw                        # Script para execução do Maven no Linux
├── mvnw.cmd                    # Script para execução do Maven no Windows
├── pom.xml                     # Arquivo de configuração do Maven(POM Parent)
├── projects/                   # Diretório contendo os subprojetos
│   └── springbatch-simple-task/
│       ├── docker-compose.yml  # Configuração para execução com Docker
│       ├── pom.xml             # Arquivo de configuração do Maven do subprojeto
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/
│       │   │   │       └── github/
│       │   │   │           └── marciokleber/
│       │   │   │               ├── SpringbatchSimpleTaskApplication.java  # Classe principal
│       │   │   │               ├── config/
│       │   │   │               │   └── BatchConfiguration.java           # Configuração do Spring Batch
│       │   │   │               └── controller/
│       │   │   │                   └── BatchController.java              # Controlador REST
│       │   ├── resources/
│       │   │   └── application.properties                                # Configurações da aplicação
```
