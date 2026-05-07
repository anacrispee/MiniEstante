# Backup, Exportação e Importação — MiniEstante

## Visão geral

O app não tem backend. O backup é feito manualmente pelo usuário via exportação de arquivo JSON. A importação substitui todos os dados locais pelo conteúdo do arquivo.

---

## Exportação

### Fluxo

1. Usuário toca no ícone de backup na tela principal
2. `BackupDialog` abre
3. Usuário toca em "Exportar JSON"
4. Dialog fecha; `exportLauncher` é acionado (`ActivityResultContracts.CreateDocument`)
5. Android abre o file picker para o usuário escolher onde salvar
6. Nome sugerido: `miniestante_backup.json`
7. Após escolher o destino, `viewModel.getBooksAsJson()` serializa os livros
8. O JSON é escrito no URI via `Context.writeTextToUri()`
9. Snackbar exibe "Exportado com sucesso!"

### Serialização

- Usa `kotlinx.serialization` com `Json { prettyPrint = true; ignoreUnknownKeys = true }`
- Serializa `List<Book>` completa
- Enums são serializados pelo nome (`"READ"`, `"WORTH_VOTE"`, etc.)
- Datas permanecem no formato `"yyyy-MM-dd"`

### Função responsável

```kotlin
// BookListViewModel.kt
fun getBooksAsJson(): String
```

Retorna string JSON ou string vazia em caso de erro (sem feedback de erro neste caso).

---

## Importação

### Fluxo

1. Usuário toca no ícone de backup na tela principal
2. `BackupDialog` abre
3. Usuário toca em "Importar JSON"
4. Dialog fecha; `importLauncher` é acionado (`ActivityResultContracts.OpenDocument`)
5. Android abre o file picker; tipos aceitos: `application/json`, `text/plain`, `*/*`
6. Após selecionar o arquivo, `Context.readTextFromUri()` lê o conteúdo
7. O JSON é enviado via `BookListAction.OnImportJsonClicked(jsonString)`
8. ViewModel deserializa e chama `repository.replaceAll(books)`
9. Snackbar exibe "N livro(s) importado(s) com sucesso!" ou mensagem de erro

### Comportamento de substituição

- `replaceAll()` executa `deleteAll()` seguido de `insertAll()` em sequência
- **Todos os dados locais são substituídos** — não há merge
- Operação não é reversível após confirmação

### Validações

| Situação | Comportamento |
|----------|---------------|
| Arquivo não pode ser lido | Snackbar: "Não foi possível ler o arquivo." |
| JSON inválido / mal formatado | Snackbar: "Arquivo inválido. Verifique o formato JSON." |
| JSON válido mas lista vazia | Snackbar: "Nenhum livro encontrado no arquivo." |
| JSON válido com livros | Substitui dados e exibe contagem importada |

---

## Funções de I/O

**Arquivo:** `ui/books/BooksScreen.kt` (extension functions)

```kotlin
fun Context.readTextFromUri(uri: Uri): String?
fun Context.writeTextToUri(uri: Uri, text: String)
```

Erros de I/O são tratados silenciosamente — a UI exibe feedback via Snackbar quando necessário.

---

## Formato do arquivo JSON

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

O parser usa `ignoreUnknownKeys = true`, então campos extras em versões futuras não causam erro na importação de arquivos antigos.
