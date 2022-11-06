::: {md-component="skip"}
[Skip to content](#connect-calva-to-your-project){.md-skip}
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
[ Connect the REPL ]{.md-ellipsis}
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
    -   [ Connect the REPL ]{.md-ellipsis} []{.md-nav__icon .md-icon} [[
        Connect the REPL ]{.md-ellipsis}](./){.md-nav__link
        .md-nav__link--active}
        []{.md-nav__icon .md-icon} Table of contents
        -   [[ Jack-in: Let Calva Start the REPL For You
            ]{.md-ellipsis}](#jack-in-let-calva-start-the-repl-for-you){.md-nav__link}
            -   [[ Aliases, Profiles, Builds
                ]{.md-ellipsis}](#aliases-profiles-builds){.md-nav__link}
            -   [[ Customizing Jack-in
                ]{.md-ellipsis}](#customizing-jack-in){.md-nav__link}
        -   [[ Connecting Without Jack-in
            ]{.md-ellipsis}](#connecting-without-jack-in){.md-nav__link}
        -   [[ Monorepos / multiple Clojure projects in one workspace
            ]{.md-ellipsis}](#monorepos-multiple-clojure-projects-in-one-workspace){.md-nav__link}
        -   [[ Troubleshooting
            ]{.md-ellipsis}](#troubleshooting){.md-nav__link}
            -   [[ Command Not Found Errors When Jacking In
                ]{.md-ellipsis}](#command-not-found-errors-when-jacking-in){.md-nav__link}
            -   [[ Go to Definition Not Working for Java Definitions
                ]{.md-ellipsis}](#go-to-definition-not-working-for-java-definitions){.md-nav__link}
            -   [[ Environment Variables Are Not Readable From REPL
                ]{.md-ellipsis}](#environment-variables-are-not-readable-from-repl){.md-nav__link}
            -   [[ Viewing the Communication Between nREPL and Calva
                ]{.md-ellipsis}](#viewing-the-communication-between-nrepl-and-calva){.md-nav__link}
    -   [[ Try this First ]{.md-ellipsis}](../try-first/){.md-nav__link}
    -   [[ Finding Calva Commands
        ]{.md-ellipsis}](../finding-commands/){.md-nav__link}
    -   [[ Evaluate Clojure Code
        ]{.md-ellipsis}](../eval-tips/){.md-nav__link}
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

-   [[ Jack-in: Let Calva Start the REPL For You
    ]{.md-ellipsis}](#jack-in-let-calva-start-the-repl-for-you){.md-nav__link}
    -   [[ Aliases, Profiles, Builds
        ]{.md-ellipsis}](#aliases-profiles-builds){.md-nav__link}
    -   [[ Customizing Jack-in
        ]{.md-ellipsis}](#customizing-jack-in){.md-nav__link}
-   [[ Connecting Without Jack-in
    ]{.md-ellipsis}](#connecting-without-jack-in){.md-nav__link}
-   [[ Monorepos / multiple Clojure projects in one workspace
    ]{.md-ellipsis}](#monorepos-multiple-clojure-projects-in-one-workspace){.md-nav__link}
-   [[ Troubleshooting ]{.md-ellipsis}](#troubleshooting){.md-nav__link}
    -   [[ Command Not Found Errors When Jacking In
        ]{.md-ellipsis}](#command-not-found-errors-when-jacking-in){.md-nav__link}
    -   [[ Go to Definition Not Working for Java Definitions
        ]{.md-ellipsis}](#go-to-definition-not-working-for-java-definitions){.md-nav__link}
    -   [[ Environment Variables Are Not Readable From REPL
        ]{.md-ellipsis}](#environment-variables-are-not-readable-from-repl){.md-nav__link}
    -   [[ Viewing the Communication Between nREPL and Calva
        ]{.md-ellipsis}](#viewing-the-communication-between-nrepl-and-calva){.md-nav__link}
:::
:::
:::

::: {.md-content md-component="content"}
[![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwLjcxIDcuMDRjLjM5LS4zOS4zOS0xLjA0IDAtMS40MWwtMi4zNC0yLjM0Yy0uMzctLjM5LTEuMDItLjM5LTEuNDEgMGwtMS44NCAxLjgzIDMuNzUgMy43NU0zIDE3LjI1VjIxaDMuNzVMMTcuODEgOS45M2wtMy43NS0zLjc1TDMgMTcuMjVaIj48L3BhdGg+PC9zdmc+)](https://github.com/BetterThanTomorrow/calva/edit/published/docs/site/connect.md "Edit this page"){.md-content__button
.md-icon}

# Connect Calva to Your Project

The recommended way is to:

## Jack-in: Let Calva Start the REPL For You

This way Calva can make sure it is started with the dependencies needed
for a working Clojure and/or ClojureScript session. This is often
referred to as **Jack in** (because that is what it is called in CIDER).

Jack-in supports both CLJ and for CLJS, and has built-in configurations
for **Leiningen**, **deps.edn**, **shadow-cljs**, **Gradle** projects,
as well as for the CLJS repl types: **Figwheel Main**, **lein-figwheel**
(legacy Figwheel), **shadow-cljs**, and ClojureScript built-ins for both
browser and node.js. Using jack-in provides your development environment
with all the dependencies you need for Calva to work.

It works like so:

1.  Open your project in VS Code.
2.  Issue the command **Start a Project REPL and Connect**:
    `ctrl+alt+c ctrl+alt+j`.
3.  Answer the quick-pick prompts telling Calva about project types and
    what profiles to start. (See the [Jack-in Project Types and
    Profiles](https://github.com/BetterThanTomorrow/calva/wiki/Jack-In-Project-Types-and-Profiles)
    wiki page for more info if needed.)

See also: [Workspace Layouts](../workspace-layouts/)

::: {.admonition .note}
About project roots

You must have a project file, such as `project.clj` for Leiningen, or
`deps.edn` for deps.edn, or `shadow-cljs.edn` for shadow-cljs, or
`settings.gradle`/`settings.gradle.kts` for Gradle in the directory
opened in VS Code in order for jack-in to work. If, after adding the
project file, you experience an error during jack-in that says something
could not be located, make sure you have the correct dependencies in
your project file. For example, when using the **Figwheel Main** project
type, you should have `com.bhauman/figwheel-main` in your project
dependencies.

See also below, regarding [multiple projects in a
workspace](#monorepos-multiple-clojure-projects-in-one-workspace)
:::

### Aliases, Profiles, Builds

When Jack-in starts it will depend on the project type, and whether
ClojureScript is involved or not, and if it is, what kind of
ClojureScript project, what will happen next. Calva will analyze the
project files and will then give you prompts with selections based on
what is found there.

You will need some basic knowledge about the project and the project
type terminologies to answer the prompts.

There are ways to tell Calva the answers to these prompts beforehand, so
that Jack-in can be a zero-prompting command. Read on.

### Customizing Jack-in

The main mechanism for customizing your Jack-in, including automating
menu selections, and custom CLJS REPL types is [Custom Connect
Sequences](../connect-sequences/).

There are also these settings:

-   `calva.jackInEnv`: An object with environment variables that will be
    added to the environment of the Jack-in process.
-   `calva.myCljAliases`: An array of `deps.edn` aliases not found in
    the project file. Use this to tell Calva Jack-in to launch your REPL
    using your user defined aliases.
-   `calva.myLeinProfiles`: An array of Leiningen profiles not found in
    `project.clj`. Use this to tell Calva Jack-in to launch your REPL
    using your user defined profiles.
-   `calva.openBrowserWhenFigwheelStarted`: *For Legacy Figwheel only.*
    A boolean controlling if Calva should automatically launch your
    ClojureScript app, once it is compiled by Figwheel. Defaults to
    `true`.

::: {.admonition .note}
Note

When processing the `calva.jackInEnv` setting you can refer to existing
ENV variables with `${env:VARIABLE}`.
:::

## Connecting Without Jack-in

If, for whatever reasons, you can\'t use Jack-in with your project
(possibly because the REPL is started as part of some other job) all is
not lost. Old fashioned **Connect to a running REPL** is still there for
you. For all features to work in Calva while connecting to a running
REPL, your environment needs to have REPL related dependencies set up.

However, just as before it can be tricky to get the dependencies right.
Consider using **Jack in** to inform yourself on how to start your REPL
to Calva\'s satisfaction. When you use Jack in, Calva starts a VS Code
task for it and the command line used is displayed in the terminal pane
used to handle the task. Reading that command line tells you what
dependencies are needed for your project.

Even better: Copying that command line gives you the command to start
the REPL with the correct dependencies.

All this said, I still recommend you challenge the conclusion that you
can\'t use Jack-in.

::: {.admonition .note}
Note

There is a Calva command for copying the Jack-in command line to the
clipboard.
:::

## Monorepos / multiple Clojure projects in one workspace

If the workspace is a monorepo, Polylith repo or just a repository with
more than one Clojure project, Calva will start the connect sequence
with prompting for which project to start/connect to.

![The project roots
menu](../images/calva-monorepo-project-roots-menu.png)

When searching for project roots in your workspace, Calva will glob for
all files matching `project.clj`, `deps.edn`, or `shadow-cljs.edn`. This
is done using VS Code\'s workspace search engine, and is very efficient.
However, in a large monorepo, it is still a substantial task. In order
to not waste resources Calva will exclude any directories in the setting
`calva.projectRootsSearchExclude`.

![calva.projectRootsSearchExclude
setting](../images/calva-project-roots-search-exclude.png)

::: {.admonition .note}
Exclude entry globs

Each entry is a partial *glob* and will be part of a resulting *glob* of
the form `**/{glob1,glob2,...,globN}`. This means that all directories
in the workspace matching an entry will be excluded, regardless of where
in the workspace they reside.
:::

## Troubleshooting

### Command Not Found Errors When Jacking In

If you get `command not found` error when Calva tries to start your
project, and you know you have the command installed, it\'s probably
because VS Code starts from an environment where the command is not on
the `$PATH`. It can look like so:

::: highlight
    lein update-in :dependencies conj '[nrepl,"0.8.3"]' -- update-in :plugins conj '[cider/cider-nrepl,"0.25.8"]' -- update-in '[:repl-options,:nrepl-middleware]' conj '["cider.nrepl/cider-middleware"]' -- repl :headless
    /bin/sh: lein: command not found
    Jack-in process exited. Status: 127
:::

The fix is to always start VS Code from the command line:

::: highlight
    $ code
:::

You might need to first run the **Shell Command: Install `code` command
in PATH**.

This will also make sure your REPL has access to the environment you
probably expect it to have access to. See below.

### Go to Definition Not Working for Java Definitions

On some systems, the Java source may not be installed along with the
JDK. The source must be present on your system in order to navigate to
Java definitions. See [this
comment](https://github.com/clojure-emacs/orchard/issues/103#issuecomment-764936527)
for more details.

### Environment Variables Are Not Readable From REPL

If you\'ve added environment variables in your OS, such as in your
`~/.bashrc` file (Linux), in order for them to be read in a REPL created
by Calva\'s jackin command, VS Code must be started from a shell where
the environment variables are defined. For example, if you can open a
bash terminal and run `echo $SOME_VAR` and see the value there, then
open VS Code from that terminal with `code <project path>`.

### Viewing the Communication Between nREPL and Calva

It may be helpful to view the messages sent between nREPL and Calva when
troubleshooting an issue related to the REPL. See how to do that
[here](../nrepl_and_cider-nrepl/#viewing-the-communication-between-calva-and-nrepl).
:::
:::
:::

[](../getting-started/){.md-footer__link .md-footer__link--prev
aria-label="Previous: Get Started with Calva" rel="prev"}

::: {.md-footer__button .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwIDExdjJIOGw1LjUgNS41LTEuNDIgMS40Mkw0LjE2IDEybDcuOTItNy45MkwxMy41IDUuNSA4IDExaDEyWiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: md-footer__title
::: md-ellipsis
[ Previous ]{.md-footer__direction} Get Started with Calva
:::
:::

[](../try-first/){.md-footer__link .md-footer__link--next
aria-label="Next: Try this First" rel="next"}

::: md-footer__title
::: md-ellipsis
[ Next ]{.md-footer__direction} Try this First
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
