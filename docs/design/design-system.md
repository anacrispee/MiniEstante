# Design System — MiniEstante

## Base

O app usa **Material Design 3** via `androidx.compose.material3`. O tema é definido em `ui/theme/` e segue as convenções do Material You com suporte a dark mode.

## Arquivos de tema

| Arquivo | Responsabilidade |
|---|---|
| `Color.kt` | Paleta de cores (light e dark) |
| `Theme.kt` | `MiniEstanteTheme` — aplica ColorScheme, Typography e Shapes |
| `Type.kt` | Escala tipográfica (Material3 Typography) |

## Cores principais

As cores são definidas via `MaterialTheme.colorScheme`. Os tokens usados no app:

| Token | Uso |
|---|---|
| `primary` | FAB, botão primário, borda de campo focado, chip selecionado |
| `onPrimary` | Texto/ícone sobre fundo primary |
| `background` | Fundo da tela, fundo do bottom sheet e dialog |
| `onBackground` | Títulos e labels principais |
| `surface` | Fundo do `BookCard` |
| `onSurface` | Texto principal dentro do card |
| `onSurfaceVariant` | Texto secundário, ícones de ação, datas |
| `outline` | Borda de campos e botões não focados |
| `error` | Texto do botão "Excluir" no dialog de confirmação |

## Tipografia

Escala Material3 usada no app:

| Estilo | Uso |
|---|---|
| `headlineLarge` | Título "Meus Livros" no topo da tela |
| `headlineMedium` | Texto "+" no FAB |
| `titleLarge` | Título do bottom sheet e do dialog de backup |
| `titleMedium` | Título do livro no `BookCard` |
| `bodyMedium` | Autor, labels de formulário, texto de descrição |
| `bodySmall` | Datas no `BookCard` |

## Shapes

| Componente | Shape |
|---|---|
| `BookCard` | `RoundedCornerShape(16.dp)` |
| `BookFormBottomSheet` | `RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)` |
| `BackupDialog` | `RoundedCornerShape(24.dp)` |
| Campos de texto | `RoundedCornerShape(12.dp)` |
| Botões outlined | `RoundedCornerShape(12.dp)` |
| `PrimaryButton` | `RoundedCornerShape(12.dp)` (definido no componente) |

## Espaçamento

O app usa espaçamentos em múltiplos de 4dp. Padrões recorrentes:

- Padding horizontal de tela: `20.dp`
- Padding interno de cards e dialogs: `16.dp` ou `24.dp`
- Espaço entre elementos de formulário: `16.dp`
- Espaço entre chips: `8.dp`
- Espaço entre cards na lista: `12.dp`

## Elevação

| Componente | Elevação |
|---|---|
| `BookCard` | `1.dp` |
| `BackupDialog` (Surface) | `4.dp` (tonalElevation) |
| `BookFormBottomSheet` | Padrão do `ModalBottomSheet` |
