# Contribuindo com Specs — MiniEstante

Regras para manter a documentação útil, atualizada e alinhada com o código.

---

## Regras obrigatórias

1. **Toda mudança funcional começa pela spec.**
   Antes de alterar comportamento, fluxo ou regra de negócio, atualize ou crie a spec correspondente.

2. **Toda alteração de UI relevante atualiza a documentação de telas e componentes.**
   Mudanças em layout, novos componentes ou remoção de elementos devem refletir em `design/screens.md` e `design/components.md`.

3. **Toda mudança em `Book`, `BookStatus`, `BookRating`, `BookRepository`, `BookListViewModel` ou `BookListUiState` atualiza a documentação técnica correspondente.**
   Arquivos afetados: `technical/data-model.md`, `technical/state-management.md`, `technical/persistence.md`.

4. **Toda decisão arquitetural importante gera um ADR.**
   Mudança de biblioteca, padrão de arquitetura, estratégia de persistência ou abordagem de backup → crie um ADR em `decisions/`.

5. **Specs devem ser pequenas, claras e atualizadas junto com o código.**
   Evite documentação genérica. Foque no que é específico do MiniEstante.

6. **Se código e spec divergirem, a spec é a fonte de verdade.**
   Corrija o código para alinhar com a spec, ou atualize a spec com justificativa explícita no histórico de alterações.

7. **Ao finalizar qualquer tarefa, revise se alguma spec precisa ser atualizada.**
   Isso inclui specs de features, documentação técnica e fluxos de usuário.

---

## Template de spec de feature

Use este template ao criar uma nova spec em `/docs/features/`:

```markdown
# Nome da Feature

## Objetivo
Descrição curta do que a feature resolve.

## Escopo
O que faz parte desta feature.

## Fora de escopo
O que não será implementado agora.

## Regras de negócio
- Regra 1
- Regra 2

## Estados de UI
- **loading** — descrição
- **empty** — descrição
- **content** — descrição
- **error** — descrição
- **success** — descrição

## Eventos do usuário
- Ação 1
- Ação 2

## Comportamento esperado
Descreva como o sistema reage a cada evento.

## Componentes Compose envolvidos
- `NomeDoComponente` — papel na feature

## Modelo de dados relacionado
- `NomeDaEntidade` — campos relevantes

## Critérios de aceite
- [ ] Critério 1
- [ ] Critério 2

## Casos de borda
- Situação especial 1
- Situação especial 2

## Histórico de alterações

| Data | Alteração | Motivo |
|------|-----------|--------|
| AAAA-MM-DD | Criação da spec | — |
```

---

## Template de ADR

Use este template ao criar um novo ADR em `/docs/decisions/`:

```markdown
# ADR XXXX — Título

**Data:** AAAA-MM-DD
**Status:** Aceito

## Contexto
Descreva o problema ou situação que motivou a decisão.

## Decisão
O que foi decidido.

## Motivo
Por que esta foi a melhor opção.

## Consequências
O que muda, o que fica mais fácil, o que fica mais difícil.

## Alternativas consideradas
Outras opções avaliadas e por que foram descartadas.
```

---

## O que NÃO fazer

- Não implemente uma feature sem spec mínima
- Não deixe mudanças relevantes documentadas apenas em commits
- Não crie documentação genérica copiada de templates sem adaptar ao projeto
- Não ignore divergências entre código e spec — resolva-as
- Não crie ADRs para decisões triviais (escolha de nome de variável, formatação de código)
