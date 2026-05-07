# Backup e Restauração JSON

## Objetivo

Permitir ao usuário exportar seus livros como arquivo JSON e importar um backup anterior, garantindo que os dados não sejam perdidos ao trocar de dispositivo ou reinstalar o app.

## Escopo

- Exportação de todos os livros como arquivo JSON via Android Storage Access Framework
- Importação de arquivo JSON com substituição completa dos dados locais
- Feedback via Snackbar para sucesso e erro
- Dialog de backup com as duas opções

## Fora de escopo

- Backup automático (agendado ou em background)
- Sincronização com Google Drive, Dropbox ou qualquer nuvem
- Exportação parcial (apenas livros filtrados)
- Merge de dados na importação (a importação sempre substitui tudo)
- Exportação em outros formatos (CSV, PDF)
- Histórico de backups

## Regras de negócio

- A exportação serializa **todos** os livros (`books`, não `filteredBooks`)
- A importação **substitui todos** os dados locais — não há merge
- A importação só prossegue se o JSON for válido e a lista não estiver vazia
- O nome sugerido para o arquivo exportado é `miniestante_backup.json`
- O app aceita arquivos dos tipos `application/json`, `text/plain` e `*/*` na importação
- O JSON usa `ignoreUnknownKeys = true` para compatibilidade com versões futuras

## Estados de UI

- **idle** — `BackupDialog` fechado; nenhuma operação em andamento
- **dialog aberto** — `isBackupDialogVisible = true`; `BackupDialog` visível
- **success** — operação concluída; Snackbar de sucesso exibido
- **error** — operação falhou; Snackbar de erro exibido

## Eventos do usuário

- Tocar no ícone de backup (arquivo) na tela principal
- Tocar em "Exportar JSON" no `BackupDialog`
- Tocar em "Importar JSON" no `BackupDialog`
- Fechar o `BackupDialog` sem ação
- Selecionar destino no file picker (exportação)
- Selecionar arquivo no file picker (importação)
- Cancelar o file picker sem selecionar arquivo

## Comportamento esperado

| Evento | Comportamento |
|--------|---------------|
| Tocar no ícone de backup | Abre `BackupDialog` |
| Fechar dialog | `isBackupDialogVisible = false`; nenhuma ação |
| Tocar em "Exportar JSON" | Dialog fecha; file picker de criação abre |
| Selecionar destino (exportação) | JSON escrito no URI; Snackbar "Exportado com sucesso!" |
| Cancelar file picker (exportação) | Nenhuma ação; sem feedback |
| Tocar em "Importar JSON" | Dialog fecha; file picker de abertura abre |
| Selecionar arquivo válido | Dados substituídos; Snackbar "N livro(s) importado(s) com sucesso!" |
| Arquivo não pode ser lido | Snackbar "Não foi possível ler o arquivo." |
| JSON inválido | Snackbar "Arquivo inválido. Verifique o formato JSON." |
| JSON válido mas vazio | Snackbar "Nenhum livro encontrado no arquivo." |
| Cancelar file picker (importação) | Nenhuma ação; sem feedback |

## Componentes Compose envolvidos

- `BackupDialog` — dialog com as opções de exportar e importar
- `BooksScreen` — gerencia os launchers SAF e os callbacks
- `SnackbarHost` — exibe feedback de sucesso e erro

## Modelo de dados relacionado

- `Book` — entidade serializada/deserializada
- `BookListUiState.isBackupDialogVisible` — controla visibilidade do dialog
- `BookListUiState.successMessage` — mensagem de sucesso para Snackbar
- `BookListUiState.errorMessage` — mensagem de erro para Snackbar
- `BookRepository.replaceAll()` — substitui todos os dados na importação

## Critérios de aceite

- [ ] Ícone de backup abre o `BackupDialog`
- [ ] "Exportar JSON" abre o file picker de criação com nome sugerido
- [ ] Arquivo exportado contém todos os livros em formato JSON válido
- [ ] Snackbar "Exportado com sucesso!" é exibido após exportação
- [ ] "Importar JSON" abre o file picker de abertura
- [ ] Importação de JSON válido substitui todos os dados locais
- [ ] Snackbar com contagem é exibido após importação bem-sucedida
- [ ] Importação de JSON inválido exibe Snackbar de erro
- [ ] Importação de JSON vazio exibe Snackbar de erro
- [ ] Arquivo ilegível exibe Snackbar de erro
- [ ] Cancelar o file picker não altera dados nem exibe feedback

## Casos de borda

- Exportar com zero livros: arquivo JSON exportado contém array vazio `[]`
- Importar arquivo com campos extras (versão futura): `ignoreUnknownKeys = true` garante compatibilidade
- Importar arquivo com enums desconhecidos: causa erro de deserialização → Snackbar de arquivo inválido
- Importar enquanto há filtros ativos: dados são substituídos; filtros permanecem ativos na UI

## Histórico de alterações

| Data | Alteração | Motivo |
|------|-----------|--------|
| 2026-05-07 | Criação da spec | Documentação inicial do MVP |
