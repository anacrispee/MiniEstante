# Documentação — MiniEstante

Esta pasta é a **fonte de verdade** do projeto. Toda decisão de produto, design e implementação deve estar refletida aqui.

---

## Estrutura

```
docs/
├── product/              # Visão de produto
│   ├── product-overview.md   # O que é o app, princípios, escopo do MVP
│   ├── features.md           # Lista de features com status e links para specs
│   └── user-flows.md         # Fluxos do usuário passo a passo
│
├── design/               # Design e UI
│   ├── design-system.md      # Cores, tipografia, shapes, espaçamento
│   ├── screens.md            # Layout e comportamento de cada tela
│   └── components.md         # Catálogo de componentes Compose
│
├── technical/            # Documentação técnica
│   ├── architecture.md       # Arquitetura geral, camadas, padrões
│   ├── data-model.md         # Entidades, enums e campos
│   ├── state-management.md   # UiState, ViewModel, Actions, fluxo de dados
│   ├── persistence.md        # Room, DAO, banco de dados
│   └── backup-import-export.md  # Exportação e importação JSON
│
├── decisions/            # Decisões arquiteturais (ADRs)
│   ├── adr-0001-project-architecture.md
│   └── adr-0002-local-persistence.md
│
├── features/             # Specs detalhadas por feature
│   ├── books-list.spec.md
│   ├── book-form.spec.md
│   ├── filters-and-search.spec.md
│   └── backup-restore.spec.md
│
├── contributing-to-specs.md  # Regras para manter a documentação
└── README.md                 # Este arquivo
```

---

## Como usar as specs

1. **Antes de implementar qualquer coisa**, leia a spec da feature em `/docs/features/`
2. Se a spec não existir, crie uma antes de escrever código
3. Use a spec como checklist durante o desenvolvimento
4. Ao finalizar, marque os critérios de aceite como concluídos

## Quando atualizar uma spec

Atualize a spec correspondente sempre que houver mudança em:

- Comportamento de uma feature
- Fluxo do usuário
- Layout ou componentes de UI
- Modelo de dados (entidade, enum, campo)
- Estado da tela (UiState)
- Regra de negócio
- Persistência ou backup

## Como criar uma nova spec

1. Crie o arquivo em `/docs/features/nome-da-feature.spec.md`
2. Use o template definido em `contributing-to-specs.md`
3. Adicione a feature em `/docs/product/features.md` com link para a spec
4. Só então comece a implementação

## Como registrar decisões arquiteturais

1. Crie um novo arquivo em `/docs/decisions/adr-XXXX-titulo.md`
2. Incremente o número sequencialmente
3. Preencha: contexto, decisão, motivo e consequências
4. ADRs são imutáveis — se a decisão mudar, crie um novo ADR referenciando o anterior

## Como manter a documentação sincronizada com o código

- Trate divergência entre código e spec como um bug
- A spec é a fonte de verdade — corrija o código ou atualize a spec com justificativa
- Revise os docs relevantes ao final de cada tarefa
- Nunca deixe uma mudança relevante documentada apenas em commits ou comentários de código
