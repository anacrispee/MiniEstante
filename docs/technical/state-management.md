# Gerenciamento de Estado — MiniEstante

## Visão geral

O estado da tela é centralizado em `BookListUiState` e gerenciado pelo `BookListViewModel`. A UI observa o estado via `StateFlow` e envia eventos via `BookListAction`.

---

## BookListUiState

**Arquivo:** `ui/books/BookListUiState.kt`

```kotlin
data class BookListUiState(
    val books: List<Book> = emptyList(),          // todos os livros do banco
    val filteredBooks: List<Book> = emptyList(),  // livros após filtros e ordenação
    val searchQuery: String = "",                 // texto de busca atual
    val selectedStatusFilter: BookStatus? = null, // filtro de status ativo (null = todos)
    val selectedRatingFilter: BookRating? = null, // filtro de avaliação ativo (null = todos)
    val selectedSortOption: SortOption = SortOption.START_DATE,
    val isLoading: Boolean = false,               // reservado para uso futuro
    val isBookFormVisible: Boolean = false,       // controla visibilidade do bottom sheet
    val editingBook: Book? = null,                // null = criação, Book = edição
    val formTitle: String = "",                   // estado do campo título no formulário
    val formAuthor: String = "",                  // estado do campo autor no formulário
    val formStartDate: String = "",               // estado do campo data de início
    val formEndDate: String = "",                 // estado do campo data de fim
    val formStatus: BookStatus = BookStatus.IN_PROGRESS,
    val formRating: BookRating = BookRating.WORTH_VOTE,
    val isBackupDialogVisible: Boolean = false,   // controla visibilidade do BackupDialog
    val errorMessage: String? = null,             // mensagem de erro para Snackbar
    val successMessage: String? = null            // mensagem de sucesso para Snackbar
)
```

### Invariantes do estado

- `filteredBooks` é sempre um subconjunto de `books` após aplicar `searchQuery`, `selectedStatusFilter`, `selectedRatingFilter` e `selectedSortOption`
- `editingBook` só é não-nulo quando `isBookFormVisible = true`
- Os campos `form*` são inicializados por `OnAddBookClicked` (valores padrão) e `OnEditBookClicked` (valores do livro); são limpos ao fechar o formulário via `OnDismissForm`
- `errorMessage` e `successMessage` são mutuamente exclusivos na prática (nunca ambos não-nulos ao mesmo tempo)
- Após exibir uma mensagem via Snackbar, a UI dispara `OnClearMessages` para limpar o estado

### Por que o estado do formulário vive no ViewModel

O Android recria a Activity em configuration changes (rotação de tela, troca de tema claro/escuro, mudança de idioma, etc.). Estado gerenciado com `remember` em Composables é perdido nesse processo. Ao centralizar os campos do formulário no `BookListUiState`, o `ViewModel` — que sobrevive à recreation — preserva os dados em edição.

---

## BookListAction

**Arquivo:** `ui/books/BookListAction.kt`

| Action | Parâmetros | Efeito |
|--------|-----------|--------|
| `OnSearchChanged` | `query: String` | Atualiza busca e refiltra lista |
| `OnStatusFilterSelected` | `status: BookStatus?` | Atualiza filtro de status e refiltra |
| `OnRatingFilterSelected` | `rating: BookRating?` | Atualiza filtro de avaliação e refiltra |
| `OnSortSelected` | `sort: SortOption` | Atualiza ordenação e refiltra |
| `OnAddBookClicked` | — | Abre formulário em modo criação; inicializa campos `form*` com valores padrão |
| `OnEditBookClicked` | `book: Book` | Abre formulário em modo edição; inicializa campos `form*` com dados do livro |
| `OnDeleteBookClicked` | `book: Book` | Exclui livro do banco |
| `OnSaveBookClicked` | `book: Book` | Insere ou atualiza livro no banco |
| `OnDismissForm` | — | Fecha formulário |
| `OnFormTitleChanged` | `title: String` | Atualiza `formTitle` |
| `OnFormAuthorChanged` | `author: String` | Atualiza `formAuthor` |
| `OnFormStartDateChanged` | `date: String` | Atualiza `formStartDate`; limpa `formEndDate` se anterior à nova data de início |
| `OnFormEndDateChanged` | `date: String` | Atualiza `formEndDate` |
| `OnFormStatusChanged` | `status: BookStatus` | Atualiza `formStatus` |
| `OnFormRatingChanged` | `rating: BookRating` | Atualiza `formRating` |
| `OnBackupClicked` | — | Abre dialog de backup |
| `OnExportJsonClicked` | `jsonString: String` | Inicia exportação (não usado diretamente) |
| `OnImportJsonClicked` | `jsonString: String` | Importa JSON e substitui dados |
| `OnDismissBackupDialog` | — | Fecha dialog de backup |
| `OnClearMessages` | — | Limpa `errorMessage` e `successMessage` |

---

## BookListViewModel

**Arquivo:** `ui/books/BookListViewModel.kt`

### Responsabilidades

- Coletar `Flow<List<Book>>` do repositório e atualizar `books` e `filteredBooks`
- Processar todas as `BookListAction` via `onAction()`
- Aplicar filtros e ordenação via `applyFilters()`
- Serializar/deserializar JSON para backup
- Expor `getBooksAsJson()` para uso direto pela UI no fluxo de exportação

### Função `applyFilters()`

Aplica em sequência:
1. Filtro de busca textual (título ou autor, case-insensitive)
2. Filtro de status (se não-nulo)
3. Filtro de avaliação (se não-nulo)
4. Ordenação por `startDate` ou `endDate` (decrescente, string vazia como fallback)

### Fluxo de atualização

```
repository.books (Flow) → collect → _uiState.update { books + filteredBooks }
```

Toda vez que o banco muda (insert/update/delete), o Room emite novo valor no Flow, o ViewModel coleta e recalcula `filteredBooks` mantendo os filtros ativos.

---

## Fluxo de mensagens (Snackbar)

```
ViewModel seta errorMessage ou successMessage
  → LaunchedEffect na BooksScreen detecta mudança
    → snackbarHostState.showSnackbar(mensagem)
      → viewModel.onAction(OnClearMessages)
        → errorMessage e successMessage voltam a null
```
