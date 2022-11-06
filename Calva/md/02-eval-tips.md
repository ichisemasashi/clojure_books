::: {md-component="skip"}
[Skip to content](#code-evaluation){.md-skip}
:::

::: {md-component="announce"}
:::

::: {.md-header md-component="header"}
[![logo](../img/calva-symbol-white.svg)](.. "Calva User Guide"){.md-header__button
.md-logo aria-label="Calva User Guide" md-component="logo"}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTMgNmgxOHYySDNWNm0wIDVoMTh2Mkgzdi0ybTAgNWgxOHYySDN2LTJaIj48L3BhdGg+PC9zdmc+)

::: {.md-header__title md-component="header-title"}
::: md-header__ellipsis
::: md-header__topic
[ Calva User Guide ]{.md-ellipsis}
:::

::: {.md-header__topic md-component="header-topic"}
[ Evaluate Clojure Code ]{.md-ellipsis}
:::
:::
:::

![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTkuNSAzQTYuNSA2LjUgMCAwIDEgMTYgOS41YzAgMS42MS0uNTkgMy4wOS0xLjU2IDQuMjNsLjI3LjI3aC43OWw1IDUtMS41IDEuNS01LTV2LS43OWwtLjI3LS4yN0E2LjUxNiA2LjUxNiAwIDAgMSA5LjUgMTYgNi41IDYuNSAwIDAgMSAzIDkuNSA2LjUgNi41IDAgMCAxIDkuNSAzbTAgMkM3IDUgNSA3IDUgOS41UzcgMTQgOS41IDE0IDE0IDEyIDE0IDkuNSAxMiA1IDkuNSA1WiI+PC9wYXRoPjwvc3ZnPg==)

::: {.md-search md-component="search" role="dialog"}
::: {.md-search__inner role="search"}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTkuNSAzQTYuNSA2LjUgMCAwIDEgMTYgOS41YzAgMS42MS0uNTkgMy4wOS0xLjU2IDQuMjNsLjI3LjI3aC43OWw1IDUtMS41IDEuNS01LTV2LS43OWwtLjI3LS4yN0E2LjUxNiA2LjUxNiAwIDAgMSA5LjUgMTYgNi41IDYuNSAwIDAgMSAzIDkuNSA2LjUgNi41IDAgMCAxIDkuNSAzbTAgMkM3IDUgNSA3IDUgOS41UzcgMTQgOS41IDE0IDE0IDEyIDE0IDkuNSAxMiA1IDkuNSA1WiI+PC9wYXRoPjwvc3ZnPg==)
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwIDExdjJIOGw1LjUgNS41LTEuNDIgMS40Mkw0LjE2IDEybDcuOTItNy45MkwxMy41IDUuNSA4IDExaDEyWiI+PC9wYXRoPjwvc3ZnPg==)

![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTE5IDYuNDEgMTcuNTkgNSAxMiAxMC41OSA2LjQxIDUgNSA2LjQxIDEwLjU5IDEyIDUgMTcuNTkgNi40MSAxOSAxMiAxMy40MSAxNy41OSAxOSAxOSAxNy41OSAxMy40MSAxMiAxOSA2LjQxWiI+PC9wYXRoPjwvc3ZnPg==)

::: md-search__output
::: {.md-search__scrollwrap md-scrollfix=""}
::: {.md-search-result md-component="search-result"}
::: md-search-result__meta
Initializing search
:::
:::
:::
:::
:::
:::

::: md-header__source
[](https://github.com/BetterThanTomorrow/calva "Go to repository"){.md-source
md-component="source"}

::: {.md-source__icon .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCA0NDggNTEyIj48IS0tISBGb250IEF3ZXNvbWUgRnJlZSA2LjEuMiBieSBAZm9udGF3ZXNvbWUgLSBodHRwczovL2ZvbnRhd2Vzb21lLmNvbSBMaWNlbnNlIC0gaHR0cHM6Ly9mb250YXdlc29tZS5jb20vbGljZW5zZS9mcmVlIChJY29uczogQ0MgQlkgNC4wLCBGb250czogU0lMIE9GTCAxLjEsIENvZGU6IE1JVCBMaWNlbnNlKSBDb3B5cmlnaHQgMjAyMiBGb250aWNvbnMsIEluYy4tLT48cGF0aCBkPSJNNDM5LjU1IDIzNi4wNSAyNDQgNDAuNDVhMjguODcgMjguODcgMCAwIDAtNDAuODEgMGwtNDAuNjYgNDAuNjMgNTEuNTIgNTEuNTJjMjcuMDYtOS4xNCA1Mi42OCAxNi43NyA0My4zOSA0My42OGw0OS42NiA0OS42NmMzNC4yMy0xMS44IDYxLjE4IDMxIDM1LjQ3IDU2LjY5LTI2LjQ5IDI2LjQ5LTcwLjIxLTIuODctNTYtMzcuMzRMMjQwLjIyIDE5OXYxMjEuODVjMjUuMyAxMi41NCAyMi4yNiA0MS44NSA5LjA4IDU1YTM0LjM0IDM0LjM0IDAgMCAxLTQ4LjU1IDBjLTE3LjU3LTE3LjYtMTEuMDctNDYuOTEgMTEuMjUtNTZ2LTEyM2MtMjAuOC04LjUxLTI0LjYtMzAuNzQtMTguNjQtNDVMMTQyLjU3IDEwMSA4LjQ1IDIzNS4xNGEyOC44NiAyOC44NiAwIDAgMCAwIDQwLjgxbDE5NS42MSAxOTUuNmEyOC44NiAyOC44NiAwIDAgMCA0MC44IDBsMTk0LjY5LTE5NC42OWEyOC44NiAyOC44NiAwIDAgMCAwLTQwLjgxeiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: md-source__repository
Calva
:::
:::
:::

::: {.md-container md-component="container"}
::: {.md-main role="main" md-component="main"}
::: {.md-main__inner .md-grid}
::: {.md-sidebar .md-sidebar--primary md-component="sidebar" md-type="navigation"}
::: md-sidebar__scrollwrap
::: md-sidebar__inner
[![logo](../img/calva-symbol-white.svg)](.. "Calva User Guide"){.md-nav__button
.md-logo aria-label="Calva User Guide" md-component="logo"} Calva User
Guide

::: md-nav__source
[](https://github.com/BetterThanTomorrow/calva "Go to repository"){.md-source
md-component="source"}

::: {.md-source__icon .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCA0NDggNTEyIj48IS0tISBGb250IEF3ZXNvbWUgRnJlZSA2LjEuMiBieSBAZm9udGF3ZXNvbWUgLSBodHRwczovL2ZvbnRhd2Vzb21lLmNvbSBMaWNlbnNlIC0gaHR0cHM6Ly9mb250YXdlc29tZS5jb20vbGljZW5zZS9mcmVlIChJY29uczogQ0MgQlkgNC4wLCBGb250czogU0lMIE9GTCAxLjEsIENvZGU6IE1JVCBMaWNlbnNlKSBDb3B5cmlnaHQgMjAyMiBGb250aWNvbnMsIEluYy4tLT48cGF0aCBkPSJNNDM5LjU1IDIzNi4wNSAyNDQgNDAuNDVhMjguODcgMjguODcgMCAwIDAtNDAuODEgMGwtNDAuNjYgNDAuNjMgNTEuNTIgNTEuNTJjMjcuMDYtOS4xNCA1Mi42OCAxNi43NyA0My4zOSA0My42OGw0OS42NiA0OS42NmMzNC4yMy0xMS44IDYxLjE4IDMxIDM1LjQ3IDU2LjY5LTI2LjQ5IDI2LjQ5LTcwLjIxLTIuODctNTYtMzcuMzRMMjQwLjIyIDE5OXYxMjEuODVjMjUuMyAxMi41NCAyMi4yNiA0MS44NSA5LjA4IDU1YTM0LjM0IDM0LjM0IDAgMCAxLTQ4LjU1IDBjLTE3LjU3LTE3LjYtMTEuMDctNDYuOTEgMTEuMjUtNTZ2LTEyM2MtMjAuOC04LjUxLTI0LjYtMzAuNzQtMTguNjQtNDVMMTQyLjU3IDEwMSA4LjQ1IDIzNS4xNGEyOC44NiAyOC44NiAwIDAgMCAwIDQwLjgxbDE5NS42MSAxOTUuNmEyOC44NiAyOC44NiAwIDAgMCA0MC44IDBsMTk0LjY5LTE5NC42OWEyOC44NiAyOC44NiAwIDAgMCAwLTQwLjgxeiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: md-source__repository
Calva
:::
:::

-   [[ Clojure Interactive Programming for Visual Studio Code
    ]{.md-ellipsis}](..){.md-nav__link}
-   [ Start Here ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} Start Here
    -   [[ Get Started with Calva
        ]{.md-ellipsis}](../getting-started/){.md-nav__link}
    -   [[ Connect the REPL ]{.md-ellipsis}](../connect/){.md-nav__link}
    -   [[ Try this First ]{.md-ellipsis}](../try-first/){.md-nav__link}
    -   [[ Finding Calva Commands
        ]{.md-ellipsis}](../finding-commands/){.md-nav__link}
    -   [ Evaluate Clojure Code ]{.md-ellipsis} []{.md-nav__icon
        .md-icon} [[ Evaluate Clojure Code
        ]{.md-ellipsis}](./){.md-nav__link .md-nav__link--active}
        []{.md-nav__icon .md-icon} Table of contents
        -   [[ Evaluation in a File Editor
            ]{.md-ellipsis}](#evaluation-in-a-file-editor){.md-nav__link}
        -   [[ Wait, Current Form? Top-level Form?
            ]{.md-ellipsis}](#wait-current-form-top-level-form){.md-nav__link}
            -   [[ Current Form
                ]{.md-ellipsis}](#current-form){.md-nav__link}
            -   [[ Current Top-level Form
                ]{.md-ellipsis}](#current-top-level-form){.md-nav__link}
            -   [[ Evaluate to Cursor
                ]{.md-ellipsis}](#evaluate-to-cursor){.md-nav__link}
            -   [[ Evaluate Top Level Form to Cursor
                ]{.md-ellipsis}](#evaluate-top-level-form-to-cursor){.md-nav__link}
            -   [[ Evaluate Enclosing Form
                ]{.md-ellipsis}](#evaluate-enclosing-form){.md-nav__link}
            -   [[ Copying the inline results
                ]{.md-ellipsis}](#copying-the-inline-results){.md-nav__link}
        -   [[ Evaluating in a REPL window
            ]{.md-ellipsis}](#evaluating-in-a-repl-window){.md-nav__link}
-   [[ Why you should consider Calva
    ]{.md-ellipsis}](../why-calva/){.md-nav__link}
-   [ Features ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} Features
    -   [[ The Top 10 Commands
        ]{.md-ellipsis}](../commands-top10/){.md-nav__link}
    -   [[ Evaluate Clojure Code
        ]{.md-ellipsis}](../evaluation/){.md-nav__link}
    -   [[ Rich Comments Support
        ]{.md-ellipsis}](../rich-comments/){.md-nav__link}
    -   [[ ClojureDocs Integration
        ]{.md-ellipsis}](../clojuredocs/){.md-nav__link}
    -   [[ The Output/REPL Window
        ]{.md-ellipsis}](../output/){.md-nav__link}
    -   [[ Clojure Formatting
        ]{.md-ellipsis}](../formatting/){.md-nav__link}
    -   [[ Paredit, a Visual Guide
        ]{.md-ellipsis}](../paredit/){.md-nav__link}
    -   [[ Debugger ]{.md-ellipsis}](../debugger/){.md-nav__link}
    -   [[ Pretty Printing ]{.md-ellipsis}](../pprint/){.md-nav__link}
    -   [[ Refactoring ]{.md-ellipsis}](../refactoring/){.md-nav__link}
    -   [[ Test Runner ]{.md-ellipsis}](../test-runner/){.md-nav__link}
    -   [[ Clojure Syntax Highlighting
        ]{.md-ellipsis}](../syntax-highlighting/){.md-nav__link}
    -   [[ Custom REPL Connect Sequences
        ]{.md-ellipsis}](../connect-sequences/){.md-nav__link}
    -   [[ Custom REPL Commands
        ]{.md-ellipsis}](../custom-commands/){.md-nav__link}
    -   [[ Superb Clojure Linting
        ]{.md-ellipsis}](../linting/){.md-nav__link}
    -   [[ Namespace Form Auto-creation
        ]{.md-ellipsis}](../namespace-form-auto-creation/){.md-nav__link}
    -   [[ Clojure Notebooks
        ]{.md-ellipsis}](../notebooks/){.md-nav__link}
-   [[ clojure-lsp ]{.md-ellipsis}](../clojure-lsp/){.md-nav__link}
-   [[ nREPL and cider-nrepl
    ]{.md-ellipsis}](../nrepl_and_cider-nrepl/){.md-nav__link}
-   [[ Customizing Calva
    ]{.md-ellipsis}](../customizing/){.md-nav__link}
-   [ Using Calva with \... ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} Using Calva with \...
    -   [[ WSL ]{.md-ellipsis}](../wsl/){.md-nav__link}
    -   [[ VS Code Remote Development
        ]{.md-ellipsis}](../remote-development/){.md-nav__link}
    -   [[ Live Share ]{.md-ellipsis}](../live-share/){.md-nav__link}
    -   [[ VIM ]{.md-ellipsis}](../vim/){.md-nav__link}
    -   [[ Reveal ]{.md-ellipsis}](../reveal/){.md-nav__link}
    -   [[ Babashka ]{.md-ellipsis}](../babashka/){.md-nav__link}
    -   [[ nbb ]{.md-ellipsis}](../nbb/){.md-nav__link}
    -   [[ Joyride ]{.md-ellipsis}](../joyride/){.md-nav__link}
    -   [[ ClojureDart ]{.md-ellipsis}](../clojuredart/){.md-nav__link}
    -   [[ Luminus ]{.md-ellipsis}](../luminus/){.md-nav__link}
    -   [[ The re-frame template
        ]{.md-ellipsis}](../re-frame-template/){.md-nav__link}
    -   [[ Polylith ]{.md-ellipsis}](../polylith/){.md-nav__link}
    -   [[ Krell ]{.md-ellipsis}](../krell/){.md-nav__link}
    -   [[ REBL ]{.md-ellipsis}](../rebl/){.md-nav__link}
    -   [[ Static Calva ]{.md-ellipsis}](../static-only/){.md-nav__link}
    -   [[ Parinfer ]{.md-ellipsis}](../parinfer/){.md-nav__link}
-   [ Tips and Tricks ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} Tips and Tricks
    -   [[ Emacs Keybindings
        ]{.md-ellipsis}](../emacs-keybindings/){.md-nav__link}
    -   [[ Workspace Layouts
        ]{.md-ellipsis}](../workspace-layouts/){.md-nav__link}
    -   [[ Quirks ]{.md-ellipsis}](../quirks/){.md-nav__link}
    -   [[ Viewing Async Output with Shadow-CLJS and Node
        ]{.md-ellipsis}](../async-out/){.md-nav__link}
    -   [[ Add custom commands for decompiling generated Clojure code
        ]{.md-ellipsis}](../clj-java-decompiler/){.md-nav__link}
-   [[ Extension API ]{.md-ellipsis}](../api/){.md-nav__link}
-   [ In Depth ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} In Depth
    -   [[ The Jack-in Academy
        ]{.md-ellipsis}](../jack-in-guide/){.md-nav__link}
-   [ Guides ]{.md-ellipsis} []{.md-nav__icon .md-icon}
    []{.md-nav__icon .md-icon} Guides
    -   [[ Get Started with Clojure
        ]{.md-ellipsis}](../get-started-with-clojure/){.md-nav__link}
-   [[ The Tao of Calva ]{.md-ellipsis}](../tao/){.md-nav__link}
-   [[ Contribute to Calva
    ]{.md-ellipsis}](../contribute/){.md-nav__link}
-   [[ Calva Sponsors ]{.md-ellipsis}](../sponsors/){.md-nav__link}
-   [[ Calva T-shirts and Stickers!
    ]{.md-ellipsis}](../merch/){.md-nav__link}
:::
:::
:::

::: {.md-sidebar .md-sidebar--secondary md-component="sidebar" md-type="toc"}
::: md-sidebar__scrollwrap
::: md-sidebar__inner
[]{.md-nav__icon .md-icon} Table of contents

-   [[ Evaluation in a File Editor
    ]{.md-ellipsis}](#evaluation-in-a-file-editor){.md-nav__link}
-   [[ Wait, Current Form? Top-level Form?
    ]{.md-ellipsis}](#wait-current-form-top-level-form){.md-nav__link}
    -   [[ Current Form ]{.md-ellipsis}](#current-form){.md-nav__link}
    -   [[ Current Top-level Form
        ]{.md-ellipsis}](#current-top-level-form){.md-nav__link}
    -   [[ Evaluate to Cursor
        ]{.md-ellipsis}](#evaluate-to-cursor){.md-nav__link}
    -   [[ Evaluate Top Level Form to Cursor
        ]{.md-ellipsis}](#evaluate-top-level-form-to-cursor){.md-nav__link}
    -   [[ Evaluate Enclosing Form
        ]{.md-ellipsis}](#evaluate-enclosing-form){.md-nav__link}
    -   [[ Copying the inline results
        ]{.md-ellipsis}](#copying-the-inline-results){.md-nav__link}
-   [[ Evaluating in a REPL window
    ]{.md-ellipsis}](#evaluating-in-a-repl-window){.md-nav__link}
:::
:::
:::

::: {.md-content md-component="content"}
[![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwLjcxIDcuMDRjLjM5LS4zOS4zOS0xLjA0IDAtMS40MWwtMi4zNC0yLjM0Yy0uMzctLjM5LTEuMDItLjM5LTEuNDEgMGwtMS44NCAxLjgzIDMuNzUgMy43NU0zIDE3LjI1VjIxaDMuNzVMMTcuODEgOS45M2wtMy43NS0zLjc1TDMgMTcuMjVaIj48L3BhdGg+PC9zdmc+)](https://github.com/BetterThanTomorrow/calva/edit/published/docs/site/eval-tips.md "Edit this page"){.md-content__button
.md-icon}

# Code Evaluation

Calva tries to make it easy to evaluate code, supporting interactive
development. The fastest path to learning about it is to use the **Fire
up the Getting Started REPL** command, which you can learn more about in
the [Getting Started](../getting-started/) section.

NB: *The below assumes you have read about [Finding Calva Commands and
Shortcuts](../finding-commands/).*

## Evaluation in a File Editor

Calva has many commands for evaluating forms, including the **current
form** and the **current top-level form**.

Some of the commands also let you choose what should happen with the
results:

1.  **Inline.** This will display the results (or some of it, if it is
    long) inline in the editor.
2.  This also creates a hover pane including the full results and a
    button which will copy the results to the clipboard.
3.  There is also a command for copying the last result to the
    clipboard.
4.  The full results are always available in the [output
    window](../output/).
    -   There is a command for showing the output window, allowing for a
        workflow where you either generally have it closed, or have it
        as one of the tabs in the same editor group as the files you are
        working with.
5.  **To comments.** This will add the results as line comments below
    the current line.
6.  **Replace the evaluated code.** This will do what it says, the
    evaluated code will be replaced with its results.

## Wait, Current Form? Top-level Form?

These are important concepts in Calva in order for you to create your
most effective workflow. This video explains it a bit:

::: iframe
::: {#player}
:::

::: player-unavailable
# エラーが発生しました。 {#エラーが発生しました .message}

::: submessage
[www.youtube.com](https://www.youtube.com/watch?v=8ygw7LLLU1w){target="_blank"}
での動画の視聴をお試しください。また、お使いのブラウザで JavaScript
が無効になっている場合は有効にしてください。
:::
:::
:::

### Current Form

Default shortcut for evaluating the current form: `ctrl+enter`.

The **current form** either means the current selection, or otherwise is
based on the cursor position. Play some with the command **Calva: Select
current form**, `ctrl+alt+c s`, to figure out what Calva thinks is the
current form for some different situations. Try it inside a symbol,
adjacent to a symbol (both sides) and adjacent to an opening or closing
bracket (again, both sides). Generally the current form is determined
like so:

1.  If text is selected, then that text
2.  If the cursor is "in" a symbol, then that symbol

    ::: highlight
        foob|ar ; foobar
    :::
3.  If the cursor is adjacent to a form (a symbol or a list of some
    kind), then that form

    ::: highlight
        (foo bar |(baz)) ; (baz)
    :::
4.  If the cursor is between to forms, then the left side form

    ::: highlight
        (foo bar | (baz)) ; bar
    :::
5.  If the cursor is before the first form of a line, then that form

    ::: highlight
        (foo
        | bar (baz)) ; bar
    :::

### Current Top-level Form

Default shortcut for evaluating the current top level form: `alt+enter`.

The **current top-level form** means top-level in a structural sense. It
is *not* the topmost form in the file. Typically in a Clojure file you
will find `def` and `defn` (and `defwhatever`) forms at the top level,
which also is one major intended use for evaluating top level form: *to
define and redefine variables*. However, Calva does not check the
contents of the form in order to determine it as a top-level forms: *all
forms not enclosed in any other form are top level forms*.

An "exception" is introduced by the `comment` form. It will create a new
top level context, so that any forms immediately inside a
`(comment ...)` form will be considered top-level by Calva. This is to
support a workflow with what is often referred to the [Rich
Comments](../rich-comments/).

At the top level the selection of which form is the current top level
form follows the same rules as those for [the current
form](#current-form).

### Evaluate to Cursor

There is also a command for evaluating the text from the start of the
current list to where the cursor is. Convenient for checking
intermediate results in thread or `doto`, or similar pipelines. The
cursor is right behind `:d` in this form:

::: highlight
      (->> [1 1 2 3 5 8 13 21]
           (partition 2)
           (zipmap [:a :b :c :d])
           :d| ; => (13 21)
           (apply -)
           (Math/abs))
:::

The default shortcut for this command is `ctrl+alt+enter`.

### Evaluate Top Level Form to Cursor

This command has a default shortcut keybinding of `shift+alt+enter`. It
will create a form from the start of the current top level form, up to
the cursor, then fold the form, closing all brackets, and this will then
be evaluated. Good for examining code blocks up to a certain point.

Take this example and paste it in a file loaded into the REPL, then
place the cursor in front of each line comment and try the command.

::: highlight
    (comment
     (do
       (def colt-express
         {:name "Colt Express"
          :categories ["Family"
                       "Strategy"]
          :play-time 40
          :ratings {:pez 5.0
                    :kat 5.0
                    :wiw 5.0   ; 1, then eval `colt-express`
                    :vig 3.0
                    :rex 5.0
                    :lun 4.0}})

       (defn average [coll]
         (/ (apply + coll) (count coll)))

       (let [foo-express (-> colt-express
                             (assoc :name "Foo Express")
                             (assoc-in [:ratings :lyr] 5.0)
                             (update-in [:ratings :vig] inc))]
         (->> foo-express   ; 2
              :ratings      ; 3
              vals          ; 4
              average       ; 5
              ))))
:::

### Evaluate Enclosing Form

The default keyboard shortcut for evaluating the current enclosing form
(the list the cursor is in) is `ctrl+shift+enter`.

::: highlight
    (let [foo :bar]
      (when false (str| foo))) ; => ":bar"
:::

### Copying the inline results

There is a command called **Copy last evaluation results**,
`ctrl+alt+c ctrl+c`.

This works regardless if you have evaluated in a file editor or in a
REPL window.

## Evaluating in a REPL window

Since the REPL Window is mostly just a regular file, things work pretty
similar at the REPL prompt. You use `alt+enter` to evaluate. Selecting
the current form (default key binding `ctrl+w` on Mac and
`shift+alt+right` on Windows and Linux) after evaluating will select the
result.
:::
:::
:::

[](../finding-commands/){.md-footer__link .md-footer__link--prev
aria-label="Previous: Finding Calva Commands" rel="prev"}

::: {.md-footer__button .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwIDExdjJIOGw1LjUgNS41LTEuNDIgMS40Mkw0LjE2IDEybDcuOTItNy45MkwxMy41IDUuNSA4IDExaDEyWiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: md-footer__title
::: md-ellipsis
[ Previous ]{.md-footer__direction} Finding Calva Commands
:::
:::

[](../why-calva/){.md-footer__link .md-footer__link--next
aria-label="Next: Why you should consider Calva" rel="next"}

::: md-footer__title
::: md-ellipsis
[ Next ]{.md-footer__direction} Why you should consider Calva
:::
:::

::: {.md-footer__button .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTQgMTF2MmgxMmwtNS41IDUuNSAxLjQyIDEuNDJMMTkuODQgMTJsLTcuOTItNy45MkwxMC41IDUuNSAxNiAxMUg0WiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: {.md-footer-meta .md-typeset}
::: {.md-footer-meta__inner .md-grid}
::: md-copyright
Made with [Material for MkDocs
Insiders](https://squidfunk.github.io/mkdocs-material/){target="_blank"
rel="noopener"}
:::
:::
:::
:::

::: {.md-dialog md-component="dialog"}
::: {.md-dialog__inner .md-typeset}
:::
:::
