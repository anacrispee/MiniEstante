# Modelo de Dados — MiniEstante

## Entidade principal: `Book`

**Arquivo:** `data/model/Book.kt`
**Anotações:** `@Entity(tableName = "books")`, `@Serializable`

| Campo | Tipo | Obrigatório | Padrão | Descrição |
|-------|------|-------------|--------|-----------|
| `id` | `String` | ✅ | — | UUID gerado na criação (`UUID.randomUUID().toString()`) |
| `title` | `String` | ✅ | — | Título do livro |
| `author` | `String` | ✅ | — | Nome do autor |
| `startDate` | `String?` | ❌ | `null` | Data de início no formato `"yyyy-MM-dd"` |
| `endDate` | `String?` | ❌ | `null` | Data de fim no formato `"yyyy-MM-dd"` |
| `status` | `BookStatus` | ✅ | `IN_PROGRESS` | Status de leitura |
| `rating` | `BookRating` | ✅ | `NONE` | Avaliação do livro |
| `createdAt` | `Long` | ✅ | `System.currentTimeMillis()` | Timestamp de criação (milissegundos) |
| `updatedAt` | `Long` | ✅ | `System.currentTimeMillis()` | Timestamp da última atualização |

### Observações

- `id` é a chave primária (`@PrimaryKey`) — nunca deve ser alterado após a criação
- Datas são armazenadas como `String` no formato ISO `"yyyy-MM-dd"` para simplicidade e serialização direta
- A exibição formata as datas para `"dd mmm yyyy"` via `String.formatDate()` em `BookCard.kt`
- `createdAt` é preservado na edição; `updatedAt` é atualizado a cada `updateBook()`

---

## Enum: `BookStatus`

**Arquivo:** `data/model/BookStatus.kt`

| Valor | Label exibido |
|-------|---------------|
| `IN_PROGRESS` | "Em andamento" |
| `READ` | "Lido" |
| `NOT_FINISHED` | "Não finalizado" |

- Padrão ao criar um livro: `IN_PROGRESS`
- Armazenado no Room via `TypeConverter` (nome do enum como String)

---

## Enum: `BookRating`

**Arquivo:** `data/model/BookRating.kt`

| Valor | Label exibido |
|-------|---------------|
| `WORTH_VOTE` | "Merece meu voto" |
| `BLACKLIST` | "Lista negra" |
| `NONE` | "Sem categoria" |

- Padrão ao criar um livro: `NONE`
- Padrão no formulário (campo avaliação): `WORTH_VOTE`
- Armazenado no Room via `TypeConverter` (nome do enum como String)

---

## Enum: `SortOption`

**Arquivo:** `ui/books/BookListUiState.kt`

| Valor | Label exibido | Comportamento |
|-------|---------------|---------------|
| `START_DATE` | "Início" | Ordena por `startDate` decrescente |
| `END_DATE` | "Fim" | Ordena por `endDate` decrescente |

- Não é persistido — é estado de UI apenas
- Padrão: `START_DATE`
- Livros sem data ficam no final (string vazia `""` como fallback)

---

## Serialização JSON

A entidade `Book` é anotada com `@Serializable` (kotlinx.serialization). O JSON gerado na exportação segue a estrutura:

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "O Senhor dos Anéis",
    "author": "J.R.R. Tolkien",
    "startDate": "2026-01-10",
    "endDate": "2026-04-20",
    "status": "READ",
    "rating": "WORTH_VOTE",
    "createdAt": 1746000000000,
    "updatedAt": 1746000000000
  }
]
```

- Enums são serializados pelo nome (ex: `"READ"`, `"WORTH_VOTE"`)
- Campos nulos (`startDate`, `endDate`) são omitidos ou `null` no JSON
- O parser usa `ignoreUnknownKeys = true` para tolerância a versões futuras
