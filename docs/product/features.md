# Features do MiniEstante

Lista de todas as features do produto, com status e referência para a spec detalhada.

## Features implementadas no MVP

### 1. Lista de Livros
**Spec:** [`/docs/features/books-list.spec.md`](../features/books-list.spec.md)

Tela principal do app. Exibe todos os livros cadastrados em cards, com contador no topo. Suporta estado vazio com mensagem orientativa.

---

### 2. Cadastro e Edição de Livro
**Spec:** [`/docs/features/book-form.spec.md`](../features/book-form.spec.md)

Formulário em bottom sheet para criar ou editar um livro. Campos: título, autor, data de início, data de fim, status e avaliação. Validação mínima: título e autor obrigatórios.

---

### 3. Busca, Filtros e Ordenação
**Spec:** [`/docs/features/filters-and-search.spec.md`](../features/filters-and-search.spec.md)

Busca textual por título ou autor em tempo real. Filtros por status (Em andamento, Lido, Não finalizado) e por avaliação (Merece meu voto, Lista negra). Ordenação por data de início ou data de fim.

---

### 4. Backup e Restauração JSON
**Spec:** [`/docs/features/backup-restore.spec.md`](../features/backup-restore.spec.md)

Exportação de todos os livros como arquivo JSON via Android Storage Access Framework. Importação de arquivo JSON com substituição completa dos dados locais. Feedback via Snackbar.

---

## Roadmap (pós-MVP)

Estas features não estão no escopo atual e não possuem spec. Registradas aqui para referência futura.

| Feature | Descrição |
|---|---|
| Estatísticas | Gráficos de leituras por mês, por status, por avaliação |
| Capas de livros | Foto ou imagem associada ao livro |
| Integração com APIs | Busca automática de metadados via Google Books |
| Sincronização | Backup automático em nuvem (Google Drive, Dropbox) |
| Metas de leitura | Definir e acompanhar meta anual de livros |
| Tags personalizadas | Categorização livre além de status e avaliação |
