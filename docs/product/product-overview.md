# MiniEstante — Visão Geral do Produto

## O que é

MiniEstante é um app Android de registro pessoal de leituras, exibido ao usuário como **Mini Estante**. O usuário cadastra os livros que leu, está lendo ou não terminou, com datas, status e avaliação. Tudo fica salvo localmente no dispositivo.

## Propósito

Substituir cadernos, planilhas e apps genéricos por uma ferramenta simples e focada: registrar sua estante pessoal sem fricção, sem conta, sem internet.

## Público-alvo

Leitores que querem manter um histórico pessoal de leituras sem depender de serviços externos como Goodreads ou Skoob.

## Princípios do produto

- **Offline-first**: nenhuma funcionalidade depende de internet
- **Sem conta**: sem cadastro, sem autenticação, sem backend
- **Simples**: uma tela principal, um formulário, um dialog de backup
- **Dados do usuário são do usuário**: exportação e importação JSON manual

## Escopo do MVP

| Funcionalidade | Status |
|---|---|
| Cadastrar livro (título, autor, datas, status, avaliação) | ✅ Implementado |
| Editar livro | ✅ Implementado |
| Excluir livro (com confirmação) | ✅ Implementado |
| Listar livros com busca | ✅ Implementado |
| Filtrar por status e avaliação | ✅ Implementado |
| Ordenar por data de início ou fim | ✅ Implementado |
| Exportar backup JSON | ✅ Implementado |
| Importar backup JSON | ✅ Implementado |

## Fora do escopo (MVP)

- Sincronização com nuvem
- Compartilhamento social
- Capas de livros
- Integração com APIs de livros (Google Books, Open Library)
- Múltiplos usuários ou perfis
- Notificações
- Estatísticas e gráficos

## Stack

- Kotlin + Jetpack Compose
- Room (SQLite)
- ViewModel + StateFlow (MVI leve)
- kotlinx.serialization (JSON)
- Android Storage Access Framework (backup)
