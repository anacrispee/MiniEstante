# ADR 0001 — Arquitetura do Projeto

**Data:** 2026-05-07
**Status:** Aceito

---

## Contexto

O MiniEstante é um app pessoal de registro de leituras. O escopo é pequeno: uma tela principal, um formulário e um dialog de backup. Não há planos de múltiplas telas, navegação complexa ou módulos independentes no MVP.

A escolha de arquitetura precisa equilibrar:
- Simplicidade de implementação e manutenção
- Previsibilidade do fluxo de dados
- Facilidade de evolução futura sem reescrita

## Decisão

Usar **MVVM com MVI leve**: uma única tela (`BooksScreen`), um único ViewModel (`BookListViewModel`), estado centralizado em `BookListUiState` (data class imutável) e eventos via `BookListAction` (sealed class).

## Motivo

- **MVVM** é o padrão recomendado pelo Android Jetpack e bem suportado pelo Compose
- **MVI leve** (state + actions) torna o fluxo de dados unidirecional e previsível, sem a complexidade de frameworks MVI completos
- `StateFlow` é simples, eficiente e integra nativamente com `collectAsState()` no Compose
- Uma única tela elimina a necessidade de Navigation Component no MVP

## Consequências

- **Positivo:** estrutura clara, fácil de entender e testar
- **Positivo:** sem overhead de modularização ou injeção de dependência no MVP
- **Negativo:** ao adicionar novas telas, será necessário introduzir Navigation Component e possivelmente separar ViewModels
- **Negativo:** sem DI (Hilt/Koin), o ViewModel é instanciado via `ViewModelProvider.Factory` manual — aceitável para MVP

## Alternativas consideradas

- **Clean Architecture com Use Cases:** descartada por ser excessiva para o escopo atual
- **Compose Navigation desde o início:** descartada por não haver necessidade de múltiplas telas no MVP
- **Redux/MVI completo (Orbit, MVI Kotlin):** descartado por adicionar dependências e complexidade desnecessárias
