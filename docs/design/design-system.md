# Design System — MiniEstante

## Ícone e Splash Screen

### Ícone do Launcher

O ícone usa **adaptive icon** (API 26+) com:
- **Foreground**: imagem rasterizada (`.webp`) da estante de livros em tons de laranja/marrom sobre fundo creme `#F5DDD0`
- **Background**: cor definida em `@color/ic_launcher_background`
- **Monochrome**: mesmo foreground para ícones monocromáticos (Material You)

Arquivos:
- `mipmap-*/ic_launcher_foreground.webp` — ícone da estante em múltiplas densidades
- `mipmap-anydpi-v26/ic_launcher.xml` e `ic_launcher_round.xml` — adaptive icon config

### Splash Screen

Implementada via `androidx.core:core-splashscreen:1.0.1` (Splash Screen API oficial):
- **Background**: `#F5DDD0` (mesma cor de fundo do ícone, uniforme em light e dark)
- **Ícone**: `@drawable/ic_splash_icon` — imagem em alta resolução (1024x1024px com padding para safe zone) em `drawable-nodpi/`
- **Tema**: `Theme.MiniEstante.Splash` aplicado na Activity via Manifest
- **Chamada**: `installSplashScreen()` antes de `super.onCreate()`

Notas técnicas:
- A imagem da splash possui padding interno (~176px por lado) para caber na safe zone circular da Splash Screen API
- A pasta `drawable-nodpi` evita escalonamento por densidade, preservando a qualidade original
- A cor de fundo da splash (`#F5DDD0`) é idêntica ao fundo do ícone para transição visual uniforme

A transição para a tela inicial ocorre automaticamente após o carregamento do Compose.

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
