# Arquitetura — MiniEstante

## Visão geral

O app segue uma arquitetura **MVVM com MVI leve**, usando uma única tela principal com ViewModel e StateFlow. A estrutura é monolítica e intencional para um MVP simples.

## Camadas

```
┌─────────────────────────────────────────┐
│              UI Layer                   │
│  BooksScreen (Composable)               │
│  BookFormBottomSheet, BookCard, etc.    │
│  Observa: uiState (StateFlow)           │
│  Envia: BookListAction                  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│           ViewModel Layer               │
│  BookListViewModel                      │
│  Processa: BookListAction               │
│  Expõe: BookListUiState via StateFlow   │
│  Chama: BookRepository                  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│           Repository Layer              │
│  BookRepository                         │
│  Abstrai: BookDao                       │
│  Expõe: Flow<List<Book>>                │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│            Data Layer                   │
│  BookDao (Room DAO)                     │
│  AppDatabase (RoomDatabase)             │
│  Converters (TypeConverters)            │
│  Book, BookStatus, BookRating (models)  │
└─────────────────────────────────────────┘
```

## Padrão de UI: MVI leve

- **State:** `BookListUiState` — data class imutável com todo o estado da tela
- **Actions:** `BookListAction` — sealed class com todos os eventos possíveis
- **ViewModel:** recebe actions via `onAction()`, atualiza o state via `_uiState.update {}`
- **UI:** observa `uiState` com `collectAsState()`, envia actions ao ViewModel

## Fluxo de dados

```
Usuário interage com UI
  → UI chama viewModel.onAction(BookListAction.X)
    → ViewModel processa a action
      → Atualiza _uiState (operações síncronas)
      → Chama repository (operações assíncronas via viewModelScope.launch)
        → Repository chama DAO
          → Room emite novo Flow
            → ViewModel coleta e atualiza _uiState
              → UI recompõe automaticamente
```

## Estrutura de pacotes

```
com.example.miniestante/
├── data/
│   ├── local/          # Room: AppDatabase, BookDao, Converters
│   ├── model/          # Entidades e enums: Book, BookStatus, BookRating
│   └── repository/     # BookRepository
├── ui/
│   ├── books/          # Tela principal: BooksScreen, ViewModel, UiState, Action
│   ├── components/     # Componentes reutilizáveis
│   └── theme/          # Color, Theme, Type
└── MainActivity.kt
```

## Decisões de arquitetura

Ver ADRs em `/docs/decisions/`:
- [ADR 0001 — Arquitetura do projeto](../decisions/adr-0001-project-architecture.md)
- [ADR 0002 — Persistência local](../decisions/adr-0002-local-persistence.md)
