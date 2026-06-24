# Health Center API

REST API para gerenciamento de um centro de saúde, desenvolvida com Spring Boot 4 e PostgreSQL.

---

## Requisitos

- Java 26+
- Maven 3.9+
- Docker e Docker Compose (para rodar com containers)

---

## Como rodar

### Com Docker Compose (recomendado)

Sobe a aplicação e o banco PostgreSQL juntos:

```bash
docker-compose up --build
```

A API ficará disponível em `http://localhost:8080`.

### Localmente (sem Docker)

1. Suba um PostgreSQL local com as seguintes credenciais (ou ajuste o `application.properties`):
   - **Host:** `localhost:5432`
   - **Database:** `healthcenter`
   - **Usuário:** `admin`
   - **Senha:** `admin`

2. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

### Testes

Os testes usam H2 em memória — não precisam do PostgreSQL rodando.

```bash
# Todos os testes
./mvnw test

# Uma classe específica
./mvnw test -Dtest=PatientServiceTest

# Um método específico
./mvnw test -Dtest=PatientControllerIntegrationTest#testCreatePatient
```

---

## Rotas

Base URL: `http://localhost:8080`

### Health Check

| Método | Rota               | Descrição              |
|--------|--------------------|------------------------|
| GET    | `/actuator/health` | Status da aplicação    |

**Resposta:**
```json
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "diskSpace": { "status": "UP" }
  }
}
```

---

### Pacientes

| Método | Rota             | Descrição              |
|--------|------------------|------------------------|
| POST   | `/patients`      | Cadastrar paciente     |
| GET    | `/patients`      | Listar todos           |
| GET    | `/patients/{id}` | Buscar por ID          |
| DELETE | `/patients/{id}` | Remover paciente       |

#### POST `/patients`

```json
{
  "name": "João da Silva",
  "cpf": "12345678901",
  "birthDate": "1990-05-15",
  "phone": "11999999999"
}
```

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "name": "João da Silva",
  "cpf": "12345678901",
  "birthDate": "1990-05-15",
  "phone": "11999999999"
}
```

#### GET `/patients`

**Resposta `200 OK`:**
```json
[
  {
    "id": 1,
    "name": "João da Silva",
    "cpf": "12345678901",
    "birthDate": "1990-05-15",
    "phone": "11999999999"
  }
]
```

#### GET `/patients/{id}`

**Resposta `200 OK`:**
```json
{
  "id": 1,
  "name": "João da Silva",
  "cpf": "12345678901",
  "birthDate": "1990-05-15",
  "phone": "11999999999"
}
```

#### DELETE `/patients/{id}`

**Resposta `204 No Content`**

---

### Médicos

| Método | Rota               | Descrição                              |
|--------|--------------------|----------------------------------------|
| POST   | `/doctors`         | Cadastrar médico                       |
| GET    | `/doctors`         | Listar todos                           |
| GET    | `/doctors/ranking` | Ranking por total de consultas         |

#### POST `/doctors`

```json
{
  "name": "Dra. Ana Paula",
  "crm": "CRM/SP 123456",
  "specialty": "Cardiologia"
}
```

**Resposta `201 Created`:**
```json
{
  "id": 2,
  "name": "Dra. Ana Paula",
  "crm": "CRM/SP 123456",
  "specialty": "Cardiologia"
}
```

#### GET `/doctors`

**Resposta `200 OK`:**
```json
[
  {
    "id": 2,
    "name": "Dra. Ana Paula",
    "crm": "CRM/SP 123456",
    "specialty": "Cardiologia"
  }
]
```

#### GET `/doctors/ranking`

Retorna médicos ordenados pelo total de consultas realizadas.

**Resposta `200 OK`:**
```json
[
  {
    "id": 2,
    "name": "Dra. Ana Paula",
    "crm": "CRM/SP 123456",
    "specialty": "Cardiologia",
    "totalConsultations": 42
  }
]
```

---

### Consultas

| Método | Rota             | Descrição             |
|--------|------------------|-----------------------|
| POST   | `/consultations` | Registrar consulta    |

#### POST `/consultations`

O campo `consultationDate` deve seguir o formato `YYYY-MM-DDTHH:mm:ss`.

```json
{
  "patientId": 1,
  "doctorId": 2,
  "consultationDate": "2026-08-22T10:30:00",
  "notes": "Paciente tem anemia"
}
```

**Resposta `201 Created`:**
```json
{
  "id": 10,
  "consultationDate": "2026-08-22T10:30:00",
  "notes": "Paciente tem anemia",
  "patientId": 1,
  "doctorId": 2
}
```

---

## Validações

Campos obrigatórios retornam `400 Bad Request` quando ausentes ou inválidos:

| Campo                          | Regra                        |
|--------------------------------|------------------------------|
| `patient.name`                 | Não pode ser vazio           |
| `patient.cpf`                  | Não pode ser vazio           |
| `doctor.name`                  | Não pode ser vazio           |
| `doctor.crm`                   | Não pode ser vazio           |
| `doctor.specialty`             | Não pode ser vazio           |
| `consultation.patientId`       | Não pode ser nulo            |
| `consultation.doctorId`        | Não pode ser nulo            |
| `consultation.consultationDate`| Não pode ser nulo            |
