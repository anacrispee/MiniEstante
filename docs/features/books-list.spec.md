# Lista de Livros

## Objetivo

Exibir todos os livros cadastrados em uma lista scrollável, com contador, estado vazio e acesso rápido às ações de editar, excluir e adicionar.

## Escopo

- Exibição de todos os livros em `LazyColumn` com `BookCard`
- Contador de livros no topo ("N livro(s)")
- Estado vazio quando não há livros ou nenhum resultado para os filtros ativos
- FAB para adicionar novo livro
- Botões de editar e excluir em cada card
- Dialog de confirmação antes de excluir
- Feedback via Snackbar para erros e sucessos

## Fora de escopo

- Paginação
- Seleção múltipla de livros
- Reordenação manual (drag and drop)
- Agrupamento por status ou período
- Detalhes expandidos do livro (tela separada)

## Regras de negócio

- A lista exibe `filteredBooks` (resultado dos filtros ativos), não `books` diretamente
- O contador no topo exibe o total de `books` (todos os livros), não o total filtrado
- A ordenação padrão é por `startDate` decrescente
- Livros sem data ficam no final da lista
- A exclusão requer confirmação via `AlertDialog`
- Após excluir, o livro é removido imediatamente da lista (Room emite novo Flow)

## Estados de UI

- **empty (lista vazia)** — `books` está vazio; exibe `EmptyBooksState` com convite para adicionar primeiro livro.
- **empty (sem resultados)** — `books` não está vazio, mas `filteredBooks` está; exibe `EmptyBooksState` com a mensagem "Nenhum resultado encontrado.".
- **content** — `filteredBooks` tem itens; exibe `LazyColumn` com `BookCard`
- **loading** — `isLoading = true`; reservado para uso futuro (atualmente não exibido)

## Eventos do usuário

- Tocar no FAB "+"
- Tocar no ícone de editar (lápis) em um `BookCard`
- Tocar no ícone de excluir (lixeira) em um `BookCard`
- Confirmar exclusão no `AlertDialog`
- Cancelar exclusão no `AlertDialog`
- Tocar no ícone de backup no topo

## Comportamento esperado

| Evento | Comportamento |
|--------|---------------|
| Tocar no FAB | Abre `BookFormBottomSheet` em modo criação (`editingBook = null`) |
| Tocar em editar | Abre `BookFormBottomSheet` em modo edição com dados do livro |
| Tocar em excluir | Armazena livro em `bookToDelete` e exibe `AlertDialog` |
| Confirmar exclusão | Chama `OnDeleteBookClicked`, fecha dialog, livro some da lista |
| Cancelar exclusão | Fecha dialog, nenhuma ação |
| Tocar em backup | Abre `BackupDialog` |

## Componentes Compose envolvidos

- `BooksScreen` — tela principal, orquestra tudo
- `BookCard` — item da lista
- `EmptyBooksState` — estado vazio
- `AlertDialog` (Material3) — confirmação de exclusão
- `SnackbarHost` — feedback de mensagens
- `FloatingActionButton` — adicionar livro

## Modelo de dados relacionado

- `Book` — entidade exibida em cada card
- `BookListUiState.books` — lista completa
- `BookListUiState.filteredBooks` — lista exibida
- `BookListUiState.isLoading` — estado de carregamento

## Critérios de aceite

- [ ] Lista exibe todos os livros cadastrados
- [ ] Contador mostra o total correto de livros
- [ ] Estado vazio de lista (sem livros) exibe mensagem de convite
- [ ] Estado vazio de pesquisa (com filtros) exibe "Nenhum resultado encontrado."
- [ ] FAB abre o formulário em modo criação
- [ ] Botão de editar abre o formulário com dados pré-preenchidos
- [ ] Botão de excluir exibe dialog de confirmação
- [ ] Confirmar exclusão remove o livro da lista
- [ ] Cancelar exclusão não altera nada
- [ ] Snackbar exibe mensagens de erro e sucesso

## Casos de borda

- Lista com um único livro: contador exibe "1 livro" (singular)
- Excluir o último livro: lista passa para estado vazio
- Excluir livro com filtro ativo: lista refiltra após exclusão
- Snackbar de sucesso e erro não aparecem simultaneamente

## Histórico de alterações

| Data | Alteração | Motivo |
|------|-----------|--------|
| 2026-05-07 | Criação da spec | Documentação inicial do MVP |
| 2026-05-07 | Título da tela atualizado para "Mini Estante" | Refatoração: alinhamento com nome oficial do app e extração para `strings.xml` |
| 2026-05-07 | Diferenciação de empty states (lista vs busca) | Melhoria de UX: evitar ambiguidade quando a busca não retorna resultados |
