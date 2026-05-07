# Fluxos do Usuário

## Fluxo 1 — Cadastrar um livro

```
Tela principal (lista vazia ou com livros)
  └─ Toca no FAB "+"
       └─ BookFormBottomSheet abre (modo "Novo livro")
            ├─ Preenche título e autor (obrigatórios)
            ├─ Preenche datas de início e fim (opcionais)
            ├─ Seleciona status (padrão: Em andamento)
            ├─ Seleciona avaliação (padrão: Sem categoria)
            └─ Toca em "Salvar livro"
                 └─ Bottom sheet fecha
                      └─ Livro aparece no topo da lista
```

## Fluxo 2 — Editar um livro

```
Tela principal
  └─ Toca no ícone de edição (lápis) no BookCard
       └─ BookFormBottomSheet abre (modo "Editar livro")
            ├─ Campos pré-preenchidos com dados do livro
            ├─ Usuário altera os campos desejados
            └─ Toca em "Salvar livro"
                 └─ Bottom sheet fecha
                      └─ Card atualizado na lista
```

## Fluxo 3 — Excluir um livro

```
Tela principal
  └─ Toca no ícone de lixeira no BookCard
       └─ AlertDialog de confirmação abre
            ├─ Toca em "Cancelar" → dialog fecha, nada muda
            └─ Toca em "Excluir"
                 └─ Dialog fecha
                      └─ Livro removido da lista
```

## Fluxo 4 — Buscar e filtrar

```
Tela principal
  ├─ Digita no SearchBookField → lista filtra em tempo real
  ├─ Toca em chip de status (Em andamento / Lido / Não finalizado)
  │    └─ Lista filtra pelo status selecionado
  │         └─ Toca no mesmo chip novamente → filtro removido
  ├─ Toca em chip de avaliação (Merece meu voto / Lista negra)
  │    └─ Lista filtra pela avaliação selecionada
  │         └─ Toca no mesmo chip novamente → filtro removido
  └─ Abre SortDropdown → seleciona ordenação por Início ou Fim
```

## Fluxo 5 — Exportar backup

```
Tela principal
  └─ Toca no ícone de arquivo (backup) no topo
       └─ BackupDialog abre
            └─ Toca em "Exportar JSON"
                 └─ Dialog fecha
                      └─ Android file picker abre (CreateDocument)
                           └─ Usuário escolhe onde salvar "miniestante_backup.json"
                                └─ Arquivo salvo
                                     └─ Snackbar "Exportado com sucesso!"
```

## Fluxo 6 — Importar backup

```
Tela principal
  └─ Toca no ícone de arquivo (backup) no topo
       └─ BackupDialog abre
            └─ Toca em "Importar JSON"
                 └─ Dialog fecha
                      └─ Android file picker abre (OpenDocument)
                           └─ Usuário seleciona arquivo JSON
                                ├─ Arquivo inválido → Snackbar de erro
                                └─ Arquivo válido
                                     └─ Todos os livros atuais são substituídos
                                          └─ Snackbar "N livro(s) importado(s) com sucesso!"
```

## Estados da tela principal

| Situação | O que aparece |
|---|---|
| Nenhum livro cadastrado | `EmptyBooksState` centralizado |
| Livros existem, nenhum filtro ativo | Lista completa ordenada por data de início |
| Filtro ativo sem resultados | `EmptyBooksState` centralizado |
| Carregando dados do Room | `isLoading = true` (estado transitório) |
