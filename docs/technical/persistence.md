# Persistência — MiniEstante

## Estratégia

O app usa **Room** (SQLite) como única fonte de persistência. Não há backend, cache em memória persistente ou preferências compartilhadas para dados de livros. A abordagem é **offline-first** por design.

---

## AppDatabase

**Arquivo:** `data/local/AppDatabase.kt`

```kotlin
@Database(entities = [Book::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()
```

- Nome do banco: `"miniestante_db"`
- Versão atual: `1`
- Singleton via `companion object` com `@Volatile` + `synchronized`
- `exportSchema = false` — schema não é exportado para arquivo (adequado para MVP)

### Migração

Não há estratégia de migração definida no MVP. Ao incrementar a versão do banco, será necessário adicionar `Migration` ou usar `fallbackToDestructiveMigration()` (com perda de dados).

---

## BookDao

**Arquivo:** `data/local/BookDao.kt`

| Método | Tipo | Descrição |
|--------|------|-----------|
| `getAllBooks()` | `Flow<List<Book>>` | Retorna todos os livros ordenados por `createdAt DESC` |
| `insertBook(book)` | `suspend` | Insere um livro; substitui em conflito de `id` |
| `insertAll(books)` | `suspend` | Insere lista de livros; substitui em conflito |
| `updateBook(book)` | `suspend` | Atualiza livro existente pelo `id` |
| `deleteBook(book)` | `suspend` | Remove livro pelo objeto completo |
| `deleteAll()` | `suspend` | Remove todos os livros (usado na importação) |

- `OnConflictStrategy.REPLACE` em `insertBook` e `insertAll` garante idempotência na importação
- `getAllBooks()` retorna `Flow` — o Room emite automaticamente quando os dados mudam

---

## Converters

**Arquivo:** `data/local/Converters.kt`

TypeConverters para armazenar enums no SQLite como String:

- `BookStatus` ↔ `String` (nome do enum, ex: `"READ"`)
- `BookRating` ↔ `String` (nome do enum, ex: `"WORTH_VOTE"`)

---

## BookRepository

**Arquivo:** `data/repository/BookRepository.kt`

Camada de abstração entre ViewModel e DAO. Não adiciona lógica de negócio — apenas delega ao DAO.

```kotlin
val books: Flow<List<Book>> = dao.getAllBooks()

suspend fun addBook(book: Book)
suspend fun updateBook(book: Book)
suspend fun deleteBook(book: Book)
suspend fun replaceAll(books: List<Book>)  // deleteAll() + insertAll()
```

- `replaceAll()` é usado exclusivamente na importação de backup
- Não há cache — o `Flow` do Room é a única fonte de verdade em memória

---

## Tabela `books` no SQLite

| Coluna | Tipo SQLite | Nullable |
|--------|-------------|----------|
| `id` | TEXT (PK) | NOT NULL |
| `title` | TEXT | NOT NULL |
| `author` | TEXT | NOT NULL |
| `startDate` | TEXT | NULL |
| `endDate` | TEXT | NULL |
| `status` | TEXT | NOT NULL |
| `rating` | TEXT | NOT NULL |
| `createdAt` | INTEGER | NOT NULL |
| `updatedAt` | INTEGER | NOT NULL |
