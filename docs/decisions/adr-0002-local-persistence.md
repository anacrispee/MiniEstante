# ADR 0002 — Persistência Local

**Data:** 2026-05-07
**Status:** Aceito

---

## Contexto

O MiniEstante não tem backend, autenticação ou sincronização em nuvem. Os dados do usuário precisam ser persistidos localmente no dispositivo de forma confiável, com suporte a leitura reativa (a UI atualiza automaticamente quando os dados mudam).

## Decisão

Usar **Room** (SQLite) como única camada de persistência, com `Flow<List<Book>>` para reatividade. O backup é feito via exportação/importação manual de arquivo JSON usando o Android Storage Access Framework.

## Motivo

- **Room** é a solução oficial do Android para persistência local estruturada
- Suporte nativo a `Flow` elimina a necessidade de polling ou callbacks manuais
- SQLite é robusto, confiável e não requer permissões especiais no Android moderno
- A exportação JSON via SAF é a abordagem correta para apps sem backend — o usuário controla onde o arquivo é salvo (Google Drive, pasta local, etc.)

## Consequências

- **Positivo:** dados persistem entre sessões sem internet
- **Positivo:** sem dependência de serviços externos
- **Positivo:** backup portátil em formato aberto (JSON)
- **Negativo:** sem sincronização automática entre dispositivos
- **Negativo:** backup é responsabilidade do usuário — se o dispositivo for perdido sem backup, os dados são perdidos
- **Negativo:** migração de schema do Room requer atenção ao incrementar a versão do banco

## Alternativas consideradas

- **DataStore (Preferences ou Proto):** descartado por não ser adequado para listas de entidades estruturadas
- **Firebase Firestore:** descartado por exigir autenticação e internet, contrariando o princípio offline-first
- **Arquivo JSON direto (sem Room):** descartado por não oferecer queries, reatividade ou integridade transacional
