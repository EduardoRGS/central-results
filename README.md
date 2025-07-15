# Central Results - Sistema de Gestão de Resultados de Exames

## 📝 Resumo do Projeto

O Central Results é uma API REST para gestão de exames médicos e pacientes, desenvolvida com foco em escalabilidade, segurança e integração com sistemas modernos de mensageria e cache. O sistema permite o cadastro, consulta, atualização e exclusão de pacientes e exames, além de autenticação de usuários e emissão de eventos assíncronos para integração com outros serviços.

## 🚀 Tecnologias Utilizadas

- **Java 17**: Linguagem principal, escolhida pela robustez e suporte a frameworks modernos.
- **Spring Boot 3.5.3**: Framework para desenvolvimento rápido de aplicações Java, com suporte a REST, segurança, testes e integração.
- **PostgreSQL**: Banco de dados relacional, utilizado para persistência dos dados.
- **Redis**: Utilizado como cache para melhorar a performance das consultas.
- **Apache Kafka**: Mensageria para eventos assíncronos, facilitando integração com outros sistemas.
- **Docker & Docker Compose**: Facilita a execução e orquestração dos serviços em containers.
- **Lombok**: Reduz o código boilerplate nas entidades e DTOs.
- **Bucket4j**: (Planejado) Implementação de rate limiting para proteger a API contra abusos.
- **Prometheus & Grafana**: Monitoramento e visualização de métricas da aplicação.
- **Swagger/OpenAPI**: Documentação automática dos endpoints da API.

## 🔄 Exemplo de Fluxo de Uso da API

1. **Cadastro de Usuário**
   - `POST /api/auth/register` com dados do usuário.
2. **Login**
   - `POST /api/auth/login` para obter o token JWT.
3. **Cadastro de Paciente**
   - `POST /api/patients` com o token JWT no header.
4. **Cadastro de Exame**
   - `POST /api/exams` vinculado a um paciente.
5. **Emissão de Evento**
   - Ao criar um exame, um evento é enviado para o Kafka.
6. **Consulta de Exames/Pacientes**
   - `GET /api/exams` e `GET /api/patients` para listar registros.

### Exemplo de Payload de Cadastro de Paciente
```json
{
  "name": "João da Silva",
  "email": "joao@email.com",
  "birthDate": "1980-05-10"
}
```

### Exemplo de Payload de Cadastro de Exame
```json
{
  "patientId": 1,
  "type": "Hemograma",
  "status": "PENDING"
}
```

---

## 📋 Descrição

Sistema de gestão de resultados de exames médicos desenvolvido em Spring Boot com arquitetura hexagonal. O projeto gerencia pacientes e seus exames, com integração a Kafka para eventos assíncronos e Redis para cache.

## 🏗️ Arquitetura

O projeto segue os princípios da Clean Architecture com as seguintes camadas:

- **Domain**: Entidades, repositórios e eventos
- **Application**: Casos de uso e serviços
- **Infrastructure**: Configurações de banco, Kafka, Redis
- **Web**: Controllers e DTOs

## 📦 Pré-requisitos

- Docker e Docker Compose
- Java 17+
- Gradle

## 🛠️ Como executar

### 1. Clone o repositório
```bash
git clone <repository-url>
cd central-results
```

### 2. Configure as variáveis de ambiente (opcional)
```bash
cp .env.example .env
# Edite o arquivo .env com suas configurações
```

### 3. Inicie os serviços com Docker Compose
```bash
docker-compose up -d
```

### 4. Execute a aplicação
```bash
./gradlew bootRun
```

A aplicação estará disponível em: `http://localhost:8080`

## 📊 Serviços Disponíveis

- **PostgreSQL**: `localhost:5432`
- **PgAdmin**: `http://localhost:5050` (admin@admin.com / admin)
- **Redis**: `localhost:6379`
- **Kafka**: `localhost:9092`
- **Prometheus**: `http://localhost:9090`
- **Grafana**: `http://localhost:3000` (admin/admin)

## 🔌 Endpoints da API

### Pacientes
- `GET /api/patients` - Listar todos os pacientes
- `GET /api/patients/{id}` - Buscar paciente por ID
- `POST /api/patients` - Criar novo paciente
- `PUT /api/patients/{id}` - Atualizar paciente
- `DELETE /api/patients/{id}` - Deletar paciente

### Exames
- `GET /api/exams` - Listar exames com paginação
- `GET /api/exams/all` - Listar todos os exames
- `GET /api/exams/{id}` - Buscar exame por ID
- `POST /api/exams` - Criar novo exame
- `PUT /api/exams/{id}` - Atualizar exame
- `DELETE /api/exams/{id}` - Deletar exame

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registrar usuário

## 🧪 Testes

```bash
./gradlew test
```

## 📝 Melhorias Implementadas

### ✅ **Segurança e Configuração**
- [x] Variáveis de ambiente para configurações sensíveis
- [x] Melhor tratamento de exceções com mensagens personalizadas
- [x] Validações robustas nos DTOs
- [x] Auditoria automática de entidades
- [x] Logs estruturados

### ✅ **Funcionalidades**
- [x] Paginação e ordenação nos endpoints
- [x] Cache com Redis
- [x] Eventos assíncronos com Kafka
- [x] Monitoramento com Prometheus/Grafana
- [x] Health checks customizados
- [x] Documentação Swagger/OpenAPI

### ✅ **Qualidade de Código**
- [x] Arquitetura hexagonal bem estruturada
- [x] Separação clara de responsabilidades
- [x] Testes unitários e de integração
- [x] Tratamento de exceções customizado
- [x] Validações de entrada robustas

## 🔐 Segurança

### Usuários Padrão
O sistema cria automaticamente os seguintes usuários:

| Email | Senha | Role | Descrição |
|-------|-------|------|-----------|
| admin@centralresults.com | admin123 | ADMIN | Administrador completo |
| doctor@centralresults.com | doctor123 | DOCTOR | Médico |
| lab@centralresults.com | lab123 | LAB_TECHNICIAN | Técnico de Laboratório |
| reception@centralresults.com | reception123 | RECEPTIONIST | Recepcionista |

### Autenticação
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@centralresults.com","password":"admin123"}'

# Registrar novo usuário
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Novo Usuário","email":"user@email.com","password":"senha123","role":"DOCTOR"}'
```

### Autorização
- **ADMIN**: Acesso completo a todos os recursos
- **DOCTOR**: Pode gerenciar pacientes e exames
- **LAB_TECHNICIAN**: Pode gerenciar exames
- **RECEPTIONIST**: Pode gerenciar pacientes

### Usar Token
```bash
curl -X GET http://localhost:8080/api/exams \
  -H "Authorization: Bearer SEU_JWT_TOKEN"
```

## 📡 Eventos Kafka

### Tópicos Disponíveis
- **exam-events**: Eventos de exames criados
- **exam-completed**: Eventos de exames completados
- **patient-events**: Eventos de pacientes criados
- **notifications**: Notificações gerais

### Consumidores
- **ExamEventConsumer**: Processa eventos de exames
- **PatientEventConsumer**: Processa eventos de pacientes

### Testar Eventos
```bash
# Testar evento de exame criado
curl -X POST http://localhost:8080/api/events/test/exam-created

# Testar evento de exame completado
curl -X POST http://localhost:8080/api/events/test/exam-completed

# Testar evento de paciente criado
curl -X POST http://localhost:8080/api/events/test/patient-created

# Verificar status dos eventos
curl -X GET http://localhost:8080/api/events/status
```

### Monitoramento Kafka
- **Kafka UI**: `http://localhost:8080/actuator/kafka`
- **Tópicos**: Criados automaticamente na inicialização

## 📚 Documentação da API

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔧 Próximos Passos

### 🚀 **Melhorias Planejadas**
- [ ] Implementar rate limiting com Bucket4j
- [ ] Configurar CI/CD com GitHub Actions
- [ ] Adicionar testes de performance
- [ ] Implementar cache distribuído
- [ ] Adicionar auditoria de logs avançada
- [ ] Implementar backup automático
- [ ] Adicionar notificações por email/SMS
- [ ] Implementar relatórios em PDF
- [ ] Adicionar upload de arquivos de exames
- [ ] Implementar busca avançada com Elasticsearch

### 🔒 **Segurança Avançada**
- [ ] Implementar refresh tokens
- [ ] Adicionar autenticação 2FA
- [ ] Implementar auditoria de segurança
- [ ] Adicionar criptografia de dados sensíveis
- [ ] Implementar rate limiting por usuário

### 📊 **Monitoramento e Observabilidade**
- [ ] Implementar distributed tracing
- [ ] Adicionar alertas automáticos
- [ ] Implementar dashboards customizados
- [ ] Adicionar métricas de negócio
- [ ] Implementar log aggregation

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Suporte

Para suporte, envie um email para suporte@centralresults.com ou abra uma issue no GitHub.