# Central Results - Sistema de Gestão de Resultados de Exames

## 📋 Descrição

Sistema de gestão de resultados de exames médicos desenvolvido em Spring Boot com arquitetura hexagonal. O projeto gerencia pacientes e seus exames, com integração a Kafka para eventos assíncronos e Redis para cache.

## 🏗️ Arquitetura

O projeto segue os princípios da Clean Architecture com as seguintes camadas:

- **Domain**: Entidades, repositórios e eventos
- **Application**: Casos de uso e serviços
- **Infrastructure**: Configurações de banco, Kafka, Redis
- **Web**: Controllers e DTOs

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.3**
- **PostgreSQL** - Banco de dados principal
- **Redis** - Cache
- **Apache Kafka** - Eventos assíncronos
- **Docker & Docker Compose** - Containerização
- **Lombok** - Redução de boilerplate
- **Bucket4j** - Rate limiting (planejado)

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