# Componentes Compose — MiniEstante

Todos os componentes estão em `ui/components/`. Componentes de tela estão em `ui/books/`.

---

## BooksScreen

**Arquivo:** `ui/books/BooksScreen.kt`
**Tipo:** Tela principal (Composable com ViewModel)

Composable raiz do app. Recebe `BookListViewModel` e orquestra todos os outros componentes. Gerencia launchers do SAF (Storage Access Framework) para exportação e importação de JSON.

**Parâmetros:**
- `viewModel: BookListViewModel`

---

## BookCard

**Arquivo:** `ui/components/BookCard.kt`
**Tipo:** Componente de item de lista

Exibe as informações de um livro: título, autor, intervalo de datas formatado e badges de status e avaliação. Contém botões de editar e excluir.

**Parâmetros:**
- `book: Book` — dados do livro
- `onEditClick: () -> Unit` — callback de edição
- `onDeleteClick: () -> Unit` — callback de exclusão
- `modifier: Modifier`

**Função auxiliar:** `String.formatDate()` — converte `"yyyy-MM-dd"` para `"dd mmm yyyy"` (ex: `"04 mai 2026"`).

---

## BookFormBottomSheet

**Arquivo:** `ui/components/BookFormBottomSheet.kt`
**Tipo:** Formulário em ModalBottomSheet

Formulário de criação e edição de livros. Gerencia estado local dos campos com `remember`. Detecta modo edição pela presença de `editingBook`.

**Parâmetros:**
- `editingBook: Book?` — `null` para criação, `Book` para edição
- `onDismiss: () -> Unit`
- `onSave: (Book) -> Unit`
- `sheetState: SheetState`

**Validação:** botão "Salvar" habilitado apenas quando `title.isNotBlank() && author.isNotBlank()`.

**Componentes internos:**
- `FormLabel` — label de campo (privado)
- `EnumDropdown<T>` — dropdown genérico para enums (privado)

---

## BackupDialog

**Arquivo:** `ui/components/BackupDialog.kt`
**Tipo:** Dialog de ação

Dialog com dois botões: exportar e importar JSON. Não executa as operações diretamente — apenas dispara os callbacks.

**Parâmetros:**
- `onDismiss: () -> Unit`
- `onExportClick: () -> Unit`
- `onImportClick: () -> Unit`

---

## SearchBookField

**Arquivo:** `ui/components/SearchBookField.kt`
**Tipo:** Campo de busca

Campo de texto para busca por título ou autor. Controlado externamente.

**Parâmetros:**
- `query: String`
- `onQueryChange: (String) -> Unit`

---

## FilterChipsSection (StatusFilterChips / RatingFilterChips)

**Arquivo:** `ui/components/FilterChipsSection.kt`
**Tipo:** Chips de filtro

Dois grupos de chips de filtro:
- `StatusFilterChips` — filtra por `BookStatus`
- `RatingFilterChips` — filtra por `BookRating`

Comportamento de toggle: tocar no chip selecionado remove o filtro (passa `null`).

**Parâmetros de StatusFilterChips:**
- `selectedStatus: BookStatus?`
- `onStatusSelected: (BookStatus?) -> Unit`
- `modifier: Modifier`

**Parâmetros de RatingFilterChips:**
- `selectedRating: BookRating?`
- `onRatingSelected: (BookRating?) -> Unit`
- `modifier: Modifier`

---

## SortDropdown

**Arquivo:** `ui/components/SortDropdown.kt`
**Tipo:** Dropdown de ordenação

Dropdown para selecionar a opção de ordenação da lista (`SortOption.START_DATE` ou `SortOption.END_DATE`).

**Parâmetros:**
- `selectedSort: SortOption`
- `onSortSelected: (SortOption) -> Unit`

---

## EmptyBooksState

**Arquivo:** `ui/components/EmptyBooksState.kt`
**Tipo:** Estado vazio

Exibido quando `filteredBooks` está vazio. Mostra mensagem orientativa para o usuário.

**Parâmetros:** nenhum (sem parâmetros externos)

---

## StatusBadge

**Arquivo:** `ui/components/StatusBadge.kt`
**Tipo:** Badge visual

Exibe o status do livro como um chip colorido.

**Parâmetros:**
- `status: BookStatus`

---

## RatingBadge

**Arquivo:** `ui/components/RatingBadge.kt`
**Tipo:** Badge visual

Exibe a avaliação do livro como um chip colorido.

**Parâmetros:**
- `rating: BookRating`

---

## DateInputField

**Arquivo:** `ui/components/DateInputField.kt`
**Tipo:** Campo de data com máscara

Campo de texto com máscara de data `dd/MM/yyyy`. Converte internamente para o formato de armazenamento `yyyy-MM-dd`.

**Parâmetros:**
- `label: String`
- `value: String` — no formato `"yyyy-MM-dd"` ou vazio
- `onDateSelected: (String) -> Unit` — retorna no formato `"yyyy-MM-dd"`
- `modifier: Modifier`

---

## PrimaryButton

**Arquivo:** `ui/components/PrimaryButton.kt`
**Tipo:** Botão primário

Botão de ação principal com estilo preenchido. Usado no formulário de livro.

**Parâmetros:**
- `text: String`
- `enabled: Boolean`
- `onClick: () -> Unit`
- `modifier: Modifier`
