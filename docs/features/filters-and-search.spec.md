# Busca, Filtros e Ordenação

## Objetivo

Permitir ao usuário encontrar livros rapidamente por texto, filtrar por status ou avaliação, e controlar a ordenação da lista.

## Escopo

- Busca textual em tempo real por título ou autor
- Filtro por status (`BookStatus`): um valor por vez ou nenhum
- Filtro por avaliação (`BookRating`): um valor por vez ou nenhum
- Ordenação por data de início ou data de fim (decrescente)
- Todos os filtros são combinados (AND)

## Fora de escopo

- Busca por data
- Filtros múltiplos simultâneos do mesmo tipo (ex: dois status ao mesmo tempo)
- Salvar filtros entre sessões
- Filtro por avaliação `NONE` ("Sem categoria")

## Regras de negócio

- Busca é case-insensitive e busca em `title` e `author` simultaneamente
- Filtros de status e avaliação são mutuamente exclusivos dentro do mesmo tipo (selecionar um deseleciona o anterior)
- Tocar no chip já selecionado remove o filtro (passa `null`)
- Todos os filtros ativos são aplicados em conjunto (AND lógico)
- Ordenação é sempre decrescente; livros sem a data ordenada ficam no final (string vazia como fallback)
- `filteredBooks` é recalculado a cada mudança de busca, filtro ou ordenação

## Estados de UI

- **content** — `filteredBooks` não vazio; lista exibida normalmente
- **empty (com filtros)** — `filteredBooks` vazio mas existem livros cadastrados; `EmptyBooksState` exibe "Nenhum resultado encontrado."
- **empty (sem livros)** — nenhum livro cadastrado; `EmptyBooksState` exibe convite para adicionar primeiro livro.

## Eventos do usuário

- Digitar no campo de busca
- Limpar o campo de busca
- Tocar em "Ordenar" para abrir o bottom sheet de ordenação
- Selecionar opção de ordenação no bottom sheet
- Fechar bottom sheet de ordenação
- Tocar em "Filtrar" para abrir o bottom sheet de filtros
- Selecionar filtro de status no bottom sheet
- Selecionar filtro de avaliação no bottom sheet
- Tocar em "Ver resultados" para aplicar filtros
- Fechar bottom sheet de filtros

## Comportamento esperado

| Evento | Comportamento |
|--------|---------------|
| Digitar na busca | `filteredBooks` atualiza em tempo real |
| Limpar busca | Lista volta a mostrar todos os livros (com filtros ativos) |
| Tocar em "Ordenar" | Abre bottom sheet com opções "Data de início" e "Data de fim" |
| Selecionar ordenação | Aplica ordenação, fecha bottom sheet |
| Tocar em "Filtrar" | Abre bottom sheet com seções STATUS e AVALIAÇÃO |
| Selecionar filtro de status | Chip fica selecionado com check; seleção é local até confirmar |
| Selecionar filtro de avaliação | Chip fica selecionado com check; seleção é local até confirmar |
| Tocar em "Ver resultados" | Aplica filtros selecionados, fecha bottom sheet, lista atualiza |
| Fechar bottom sheet sem confirmar | Filtros não são alterados |
| Ordenação "Data de início" | Lista ordenada por `startDate` decrescente |
| Ordenação "Data de fim" | Lista ordenada por `endDate` decrescente |

## Componentes Compose envolvidos

- `SearchBookField` — campo de busca textual
- `FilterBottomSheet` — bottom sheet com filtros de status e avaliação, com botão "Ver resultados"
- `SortBottomSheet` — bottom sheet de ordenação com opções "Data de início" e "Data de fim"
- `EmptyBooksState` — exibido quando `filteredBooks` está vazio

### Componentes legados (mantidos para compatibilidade)

- `StatusFilterChips` — chips de filtro por status (substituído por FilterBottomSheet na tela principal)
- `RatingFilterChips` — chips de filtro por avaliação (substituído por FilterBottomSheet na tela principal)
- `SortDropdown` — dropdown de ordenação (substituído por SortBottomSheet na tela principal)

## Modelo de dados relacionado

- `BookListUiState.searchQuery` — texto de busca atual
- `BookListUiState.selectedStatusFilter` — filtro de status ativo (`null` = todos)
- `BookListUiState.selectedRatingFilter` — filtro de avaliação ativo (`null` = todos)
- `BookListUiState.selectedSortOption` — opção de ordenação ativa
- `BookListUiState.filteredBooks` — resultado dos filtros aplicados
- `SortOption` — enum com `START_DATE` e `END_DATE`
- `BookStatus` — enum com `IN_PROGRESS`, `READ`, `NOT_FINISHED`
- `BookRating` — enum com `WORTH_VOTE`, `BLACKLIST`, `NONE`

## Critérios de aceite

- [ ] Busca por título filtra a lista em tempo real
- [ ] Busca por autor filtra a lista em tempo real
- [ ] Busca é case-insensitive
- [ ] Limpar busca restaura a lista completa
- [ ] Filtro de status exibe apenas livros com o status selecionado
- [ ] Tocar no chip de status selecionado remove o filtro
- [ ] Filtro de avaliação exibe apenas livros com a avaliação selecionada
- [ ] Tocar no chip de avaliação selecionado remove o filtro
- [ ] Busca e filtros são combinados (AND)
- [ ] Ordenação por "Início" ordena por `startDate` decrescente
- [ ] Ordenação por "Fim" ordena por `endDate` decrescente
- [ ] Livros sem a data ordenada aparecem no final
- [ ] Estado vazio exibe "Nenhum resultado encontrado." quando filtros não retornam resultados e há livros no banco.

## Casos de borda

- Busca com espaços apenas: deve tratar como busca em branco (sem filtro)
- Filtro de status ativo + busca sem resultado: exibe estado vazio
- Todos os livros têm `startDate = null`: todos ficam no final, ordem relativa indefinida
- Trocar ordenação com filtros ativos: reordena apenas os livros filtrados

## Histórico de alterações

| Data | Alteração | Motivo |
|------|-----------|--------|
| 2026-05-07 | Criação da spec | Documentação inicial do MVP |
| 2026-05-07 | Feedback visual para busca sem resultados | Adição de mensagem específica "Nenhum resultado encontrado." |
| 2026-05-09 | Filtros e ordenação movidos para bottom sheets | Alinhamento com telas de referência; melhor organização visual |
| 2026-05-09 | Botão "Ver resultados" no bottom sheet de filtros | Filtros são aplicados apenas ao confirmar |
| 2026-05-09 | Opções de ordenação renomeadas para "Data de início" / "Data de fim" | Clareza e consistência com referência visual |
