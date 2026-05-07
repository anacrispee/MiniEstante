# Telas — MiniEstante

O app tem uma única tela principal. Overlays (bottom sheet e dialogs) são exibidos sobre ela.

---

## Tela Principal — BooksScreen

**Arquivo:** `ui/books/BooksScreen.kt`

### Layout (de cima para baixo)

```
┌─────────────────────────────────────┐
│  Meus Livros          3 livros  [📦] │  ← TopBar (Row)
├─────────────────────────────────────┤
│  🔍 Buscar por título ou autor...   │  ← SearchBookField
├─────────────────────────────────────┤
│  [Em andamento] [Lido] [Não fin.]   │  ← StatusFilterChips
├─────────────────────────────────────┤
│  [Merece meu voto] [Lista negra] ▼  │  ← RatingFilterChips + SortDropdown
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐    │
│  │ Título do Livro             │    │  ← BookCard (repetido em LazyColumn)
│  │ Nome do Autor               │    │
│  │ 10 jan 2024 → 20 abr 2024  │    │
│  │ [Em andamento] [Merece voto]│    │
│  └─────────────────────────────┘    │
│                                     │
│  (estado vazio: EmptyBooksState)    │
│                                     │
├─────────────────────────────────────┤
│                              [  +  ]│  ← FAB
└─────────────────────────────────────┘
```

### Comportamentos

- O ícone `[📦]` no topo abre o `BackupDialog`
- O FAB `[+]` abre o `BookFormBottomSheet` em modo criação
- Cada `BookCard` tem ícones de editar (lápis) e excluir (lixeira)
- Excluir abre um `AlertDialog` de confirmação
- Mensagens de sucesso e erro aparecem via `SnackbarHost` na parte inferior

---

## Overlay — BookFormBottomSheet

**Arquivo:** `ui/components/BookFormBottomSheet.kt`

### Layout

```
┌─────────────────────────────────────┐
│         Novo livro / Editar livro [X]│  ← Header
├─────────────────────────────────────┤
│  Título                             │
│  [________________________]         │
│  Autor                              │
│  [________________________]         │
│  Início          Fim                │
│  [__________]    [__________]       │
│  Status                             │
│  [Em andamento              ▼]      │
│  Avaliação                          │
│  [Sem categoria             ▼]      │
│                                     │
│  [        Salvar livro        ]     │  ← PrimaryButton (desabilitado se inválido)
└─────────────────────────────────────┘
```

### Comportamentos

- Abre com `skipPartiallyExpanded = true` (sempre expandido)
- Campos de data usam `DateInputField` com máscara `dd/MM/yyyy`
- Status e avaliação usam `EnumDropdown` (OutlinedButton + DropdownMenu)
- Botão "Salvar" habilitado apenas quando título e autor estão preenchidos
- Fecha ao tocar no [X] ou ao salvar com sucesso

---

## Overlay — BackupDialog

**Arquivo:** `ui/components/BackupDialog.kt`

### Layout

```
┌─────────────────────────────────────┐
│           Backup               [X]  │
│  Exporte seus livros como JSON...   │
│                                     │
│  [↓  Exportar JSON              ]   │  ← OutlinedButton (primary border)
│  [↑  Importar JSON              ]   │  ← OutlinedButton (outline border)
└─────────────────────────────────────┘
```

---

## Overlay — AlertDialog de Exclusão

Exibido inline em `BooksScreen.kt` via `AlertDialog` do Material3.

```
┌─────────────────────────────────────┐
│  Excluir livro                      │
│  Tem certeza que deseja excluir     │
│  "Título do Livro"? Esta ação não   │
│  pode ser desfeita.                 │
│                                     │
│              [Cancelar] [Excluir]   │
└─────────────────────────────────────┘
```

- "Excluir" usa `MaterialTheme.colorScheme.error`
- "Cancelar" fecha o dialog sem ação
