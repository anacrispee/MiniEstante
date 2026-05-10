<p align="center">
  <img src="docs/assets/icon.png" alt="MiniEstante" width="120" />
</p>

<h1 align="center">Mini Estante</h1>

<p align="center">
  Sua estante pessoal de leituras. Simples, offline e sem fricção.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Android-Kotlin-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Persistence-Room-green" />
  <img src="https://img.shields.io/badge/Architecture-MVVM%20%2B%20MVI-orange" />
</p>

---

## Sobre o Projeto

MiniEstante é um aplicativo Android para registro pessoal de leituras. Cadastre os livros que leu, está lendo ou não terminou — com datas, status e avaliação. Tudo salvo localmente no dispositivo, sem conta, sem internet.

O objetivo é substituir cadernos, planilhas e apps genéricos por uma ferramenta simples e focada: registrar sua estante pessoal sem fricção.

---

## Screenshots

<p align="center">
  <img src="docs/assets/screenshot-lista-livros.png" alt="Lista de livros" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-formulario.png" alt="Formulário de cadastro" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-filtros.png" alt="Filtros" width="230" />
</p>

<p align="center">
  <img src="docs/assets/screenshot-busca.png" alt="Busca" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-ordenacao.png" alt="Ordenação" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-backup.png" alt="Backup" width="230" />
</p>

<p align="center">
  <img src="docs/assets/screenshot-lista-vazia.png" alt="Estado vazio" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-exclusao.png" alt="Exclusão" width="230" />
  &nbsp;&nbsp;
  <img src="docs/assets/screenshot-prototipo-lovable.png" alt="Ícone na home screen" width="230" />
</p>

---

## Funcionalidades

- Cadastrar, editar e excluir livros
- Status de leitura: Em andamento, Lido, Não finalizado
- Avaliação pessoal: Merece meu voto, Lista negra, Sem categoria
- Busca por título ou autor em tempo real
- Filtros por status e avaliação
- Ordenação por data de início ou fim
- Exportar e importar backup em JSON
- 100% offline — sem conta, sem internet

---

## Arquitetura

O app segue **MVVM com MVI leve** — estado centralizado, fluxo de dados unidirecional e reatividade via StateFlow.

```
UI (Compose) → Actions → ViewModel → Repository → Room (SQLite)
                              ↓
                     StateFlow → UI recompõe
```

| Camada | Responsabilidade |
|--------|-----------------|
| UI | Composables, observa estado, emite ações |
| ViewModel | Processa ações, gerencia estado |
| Repository | Abstrai acesso a dados |
| Data | Room DAO, entidades, banco local |

---

## Tecnologias

| Tecnologia | Uso |
|-----------|-----|
| Kotlin | Linguagem principal |
| Jetpack Compose | UI declarativa |
| Material Design 3 | Design system |
| Room (SQLite) | Persistência local |
| ViewModel + StateFlow | Gerenciamento de estado |
| kotlinx.serialization | Serialização JSON |
| Android SAF | Backup/restauração de arquivos |

---

## Spec-Driven Development

Este projeto segue a abordagem **Spec-Driven Development**: toda feature é especificada antes de ser implementada. As specs funcionam como fonte de verdade e checklist de desenvolvimento.

O fluxo é:
1. Escrever a spec da feature
2. Implementar seguindo a spec
3. Validar critérios de aceite
4. Manter spec e código sincronizados

Toda a documentação de specs, decisões arquiteturais e design está em [`/docs`](docs/README.md).

---

## Protótipo Inicial — Lovable

O primeiro protótipo do MiniEstante foi criado no [Lovable](https://lovable.dev), uma plataforma de prototipagem rápida com IA. O protótipo serviu como base visual e funcional para validar a ideia antes de iniciar o desenvolvimento nativo em Android.

🔗 **Acesse o protótipo:** [MiniEstante no Lovable](https://preview--readlist-design-kit.lovable.app/?__lovable_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNkxZa0ZhZ25FUlJacWt4OXA4ZkNLUmxHVlhvMiIsInByb2plY3RfaWQiOiJjZDU0MGI0Ni1hM2ExLTQ1ZGUtODIyZC0zZTRkYWQxMGEwZTAiLCJhY2Nlc3NfdHlwZSI6InByb2plY3QiLCJpc3MiOiJsb3ZhYmxlLWFwaSIsInN1YiI6ImNkNTQwYjQ2LWEzYTEtNDVkZS04MjJkLTNlNGRhZDEwYTBlMCIsImF1ZCI6WyJsb3ZhYmxlLWFwcCJdLCJleHAiOjE3Nzg5NzU4MzUsIm5iZiI6MTc3ODM3MTAzNSwiaWF0IjoxNzc4MzcxMDM1fQ.jSvWLOJYYle_15yr00sKbrDA-FL6a8F5eysmVw_Cf2rm40PUqAFmal5sOdzSgMCXCaugx-rlqm6YSHHC2FxM9Rr3OOAnMS_lZ4c_jSdNGCzo5T9hXALRhbOb-Wk9RYwvuN9Q_r2rn4Bjl-2WC-AlnCRa1Gprz26JgZlvnoB6hzhWzi2yH2JYFOVbUwINOebSDIDc6FJNSQevkeEPwS-gG_NdQgRJGaZMiBO-yeLmaqcfNFb66W2-MTFfV5W7UPMN4vIhd1yyKR4EMDZ9egTnQWeM1asrHVQbCPzahg4ZIAT1iqp-xdi5hQ_2zQVRjyNbZub6rkWW9hve7nVbtXWCCmBzg1-45Oo79tra0yabX7hpLDDTV9ts_xCTSIWOpOTlwYdyL4dKRWB7TtnQ3Hj-AktTA0vZq8KQGpizvi-pvBuYkzBE0GBXs3RbEFeWtHhAfynBgSTh72oFe29B6S5MDihdjndi3TM_3OgsSbOXPxaYBHNle-TmQfuYGbvuilzhPKzwLG-rSYWcY7FOlZlqxoReEXb7KTtoXxjo9U4tk9s7a4NrElCTrge8uH3YCxhctTA2atLD7EJMnelmV0_5Uv-3YcEU6txi0tUB0Ma87VAjsoLj92AciGu4ACpQYBLKWpMHAK5u2qmdd197Hc0qeR0gLWX7vihYSxhzCoH1WSk)

A partir do protótipo, o projeto evoluiu para um app Android nativo com Kotlin e Jetpack Compose, mantendo a essência da experiência original mas com arquitetura robusta, persistência local e funcionalidades completas de backup.

---

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/MiniEstante.git
   ```
2. Abra no Android Studio (Hedgehog ou superior)
3. Sincronize o Gradle
4. Execute no emulador ou dispositivo físico (API 26+)

---

## Documentação

A documentação completa do projeto está organizada em `/docs`:

| Documento | Descrição |
|-----------|-----------|
| [Visão de Produto](docs/product/product-overview.md) | O que é o app, princípios, escopo |
| [Features](docs/product/features.md) | Lista de features com status |
| [Fluxos do Usuário](docs/product/user-flows.md) | Jornadas passo a passo |
| [Arquitetura](docs/technical/architecture.md) | Camadas, padrões, pacotes |
| [Modelo de Dados](docs/technical/data-model.md) | Entidades e enums |
| [Persistência](docs/technical/persistence.md) | Room, DAO, banco |
| [Design System](docs/design/design-system.md) | Cores, tipografia, shapes |
| [Componentes](docs/design/components.md) | Catálogo de componentes Compose |
| [ADRs](docs/decisions/) | Decisões arquiteturais |
| [Specs de Features](docs/features/) | Especificações detalhadas |
| [Contribuindo com Specs](docs/contributing-to-specs.md) | Regras de documentação |

---

## Roadmap

| Feature | Status |
|---------|--------|
| Estatísticas de leitura | Planejado |
| Capas de livros | Planejado |
| Integração com Google Books | Planejado |
| Sincronização em nuvem | Planejado |
| Metas de leitura | Planejado |
| Tags personalizadas | Planejado |

---

<p align="center">
  Feito com ☕ e Kotlin
</p>
