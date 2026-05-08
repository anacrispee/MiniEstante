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

**Formatação de datas:** usa `String.formatDate()` de `ui/util/DateUtils.kt`.

---

## BookFormBottomSheet

**Arquivo:** `ui/components/BookFormBottomSheet.kt`
**Tipo:** Formulário em ModalBottomSheet

Formulário de criação e edição de livros. Componente stateless — não gerencia estado local. Recebe todos os valores dos campos e callbacks de alteração via parâmetros, com estado centralizado no `BookListViewModel`.

**Parâmetros:**
- `editingBook: Book?` — `null` para criação, `Book` para edição
- `title: String`
- `author: String`
- `startDate: String`
- `endDate: String`
- `status: BookStatus`
- `rating: BookRating`
- `onTitleChange: (String) -> Unit`
- `onAuthorChange: (String) -> Unit`
- `onStartDateChange: (String) -> Unit`
- `onEndDateChange: (String) -> Unit`
- `onStatusChange: (BookStatus) -> Unit`
- `onRatingChange: (BookRating) -> Unit`
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

Exibido quando `filteredBooks` está vazio. Pode exibir um convite para adicionar livros (lista vazia) ou um feedback de busca sem resultados.

**Parâmetros:**
- `title: String` — título em destaque
- `message: String?` — mensagem descritiva opcional
- `icon: ImageVector` — ícone exibido no topo (padrão: `MenuBook`)
- `modifier: Modifier`

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
**Tipo:** Campo de data com seletor

Campo de data que exibe o valor no formato `dd/MM/yyyy` e abre um `DatePickerDialog` ao ser tocado. A entrada é feita exclusivamente via seletor — não há digitação manual via teclado.

Internamente usa um `Box` com `Modifier.clickable` envolvendo um `OutlinedTextField` com `enabled=false`. Essa estrutura garante que o toque seja capturado corretamente dentro de `ModalBottomSheet`, onde `readOnly=true` com `clickable` no modifier do campo não propaga o evento de toque de forma confiável.

**Parâmetros:**
- `label: String`
- `value: String` — no formato `"yyyy-MM-dd"` ou vazio
- `onDateSelected: (String) -> Unit` — retorna no formato `"yyyy-MM-dd"`
- `modifier: Modifier`
- `minDate: String` — data mínima selecionável no formato `"yyyy-MM-dd"` (opcional, padrão vazio)

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
