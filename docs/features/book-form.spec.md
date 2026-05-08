# Cadastro e Edição de Livro

## Objetivo

Permitir ao usuário criar um novo livro ou editar um existente via formulário em bottom sheet, com validação mínima e feedback imediato.

## Escopo

- Formulário em `ModalBottomSheet` com campos: título, autor, data de início, data de fim, status e avaliação
- Modo criação (novo livro) e modo edição (livro existente)
- Validação: título e autor são obrigatórios
- Botão "Salvar livro" desabilitado enquanto formulário inválido
- Fechamento ao salvar ou ao tocar no botão [X]

## Fora de escopo

- Validação de formato de data no formulário (tratada no `DateInputField`)
- Busca automática de metadados por título (Google Books, etc.)
- Upload de capa do livro
- Campos adicionais (ISBN, editora, número de páginas, notas)

## Regras de negócio

- Título e autor são obrigatórios; demais campos são opcionais
- Em modo criação: `id` é gerado via `UUID.randomUUID()`, `createdAt` é definido no momento do save
- Em modo edição: `id` e `createdAt` são preservados; `updatedAt` é atualizado
- Status padrão ao criar: `IN_PROGRESS` ("Em andamento")
- Avaliação padrão no formulário: `WORTH_VOTE` ("Merece meu voto")
- Datas são armazenadas no formato `"yyyy-MM-dd"`; o `DateInputField` faz a conversão de `dd/MM/yyyy`
- Campos de data em branco são salvos como `null`

## Estados de UI

- **content** — formulário visível com campos editáveis
- **invalid** — botão "Salvar" desabilitado (título ou autor em branco)
- **valid** — botão "Salvar" habilitado (título e autor preenchidos)

> Não há estado de loading ou error no formulário — erros de persistência são tratados via Snackbar na tela principal.

## Eventos do usuário

- Preencher campo de título
- Preencher campo de autor
- Preencher data de início
- Preencher data de fim
- Selecionar status no dropdown
- Selecionar avaliação no dropdown
- Tocar em "Salvar livro"
- Tocar no botão [X] (fechar)
- Arrastar o bottom sheet para baixo (dismiss)

## Comportamento esperado

| Evento | Comportamento |
|--------|---------------|
| Abrir em modo criação | Campos em branco; status = `IN_PROGRESS`; avaliação = `WORTH_VOTE` |
| Abrir em modo edição | Campos pré-preenchidos com dados do livro |
| Título ou autor em branco | Botão "Salvar" desabilitado |
| Título e autor preenchidos | Botão "Salvar" habilitado |
| Tocar em "Salvar" (criação) | Cria `Book` com novo UUID, chama `OnSaveBookClicked`, fecha sheet |
| Tocar em "Salvar" (edição) | Atualiza `Book` preservando `id` e `createdAt`, fecha sheet |
| Tocar em [X] ou arrastar | Chama `OnDismissForm`, fecha sheet sem salvar |

## Componentes Compose envolvidos

- `BookFormBottomSheet` — formulário principal
- `DateInputField` — campos de data com máscara
- `EnumDropdown<T>` — dropdowns de status e avaliação (privado)
- `PrimaryButton` — botão de salvar
- `FormLabel` — labels dos campos (privado)

## Modelo de dados relacionado

- `Book` — entidade criada ou atualizada
- `BookStatus` — enum para o campo status
- `BookRating` — enum para o campo avaliação
- `BookListUiState.isBookFormVisible` — controla visibilidade
- `BookListUiState.editingBook` — `null` = criação, `Book` = edição
- `BookListUiState.formTitle` / `formAuthor` / `formStartDate` / `formEndDate` / `formStatus` / `formRating` — estado dos campos do formulário, centralizado no ViewModel para sobreviver a configuration changes

## Critérios de aceite

- [ ] Formulário abre em modo criação com campos em branco
- [ ] Formulário abre em modo edição com campos pré-preenchidos
- [ ] Botão "Salvar" desabilitado quando título ou autor estão em branco
- [ ] Botão "Salvar" habilitado quando título e autor estão preenchidos
- [ ] Salvar em modo criação adiciona livro à lista
- [ ] Salvar em modo edição atualiza o livro na lista
- [ ] `id` e `createdAt` são preservados na edição
- [ ] `updatedAt` é atualizado na edição
- [ ] Fechar sem salvar não altera dados
- [ ] Campos de data aceitam entrada no formato `dd/MM/yyyy`
- [ ] Datas em branco são salvas como `null`

## Casos de borda

- Título com apenas espaços: deve ser tratado como inválido (`isNotBlank()`)
- Editar livro sem datas: campos de data ficam em branco, salvos como `null`
- Fechar o teclado não fecha o bottom sheet
- Bottom sheet é sempre expandido (`skipPartiallyExpanded = true`)
- Campos de data não abrem o teclado — a entrada é feita exclusivamente via `DatePickerDialog`
- Data de fim não pode ser anterior à data de início — o `DatePickerDialog` do campo "Fim" restringe a seleção via `minDate`; se o usuário alterar a data de início para depois da data de fim já selecionada, o campo "Fim" é limpo automaticamente
- Trocar o tema do dispositivo (claro/escuro) durante a edição não perde os dados — o estado do formulário vive no `ViewModel`, que sobrevive a configuration changes

## Histórico de alterações

| Data | Alteração | Motivo |
|------|-----------|--------|
| 2026-05-07 | Criação da spec | Documentação inicial do MVP |
| 2026-05-07 | Esclarecimento sobre campos de data | Correção de bug: campos de data usam `DatePickerDialog`, não entrada manual via teclado. O `OutlinedTextField` usa `enabled=false` dentro de um `Box` com `clickable` para garantir que o toque seja capturado corretamente dentro do `ModalBottomSheet` |
| 2026-05-07 | Validação de data de fim e preservação de estado | Data de fim não pode ser anterior à data de início. Estado do formulário movido para o `BookListUiState` para sobreviver a configuration changes (troca de tema, rotação de tela, etc.) |
