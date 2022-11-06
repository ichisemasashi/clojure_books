::: {md-component="skip"}
[Skip to content](#why-calva){.md-skip}
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
[ Why you should consider Calva ]{.md-ellipsis}
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
    -   [[ Evaluate Clojure Code
        ]{.md-ellipsis}](../eval-tips/){.md-nav__link}
-   [ Why you should consider Calva ]{.md-ellipsis} []{.md-nav__icon
    .md-icon} [[ Why you should consider Calva
    ]{.md-ellipsis}](./){.md-nav__link .md-nav__link--active}
    []{.md-nav__icon .md-icon} Table of contents
    -   [[ Nick Cernis on ClojureVerse
        ]{.md-ellipsis}](#nick-cernis-on-clojureverse){.md-nav__link}
    -   [[ 100% Five-star Marketplace Reviews
        ]{.md-ellipsis}](#100-five-star-marketplace-reviews){.md-nav__link}
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

-   [[ Nick Cernis on ClojureVerse
    ]{.md-ellipsis}](#nick-cernis-on-clojureverse){.md-nav__link}
-   [[ 100% Five-star Marketplace Reviews
    ]{.md-ellipsis}](#100-five-star-marketplace-reviews){.md-nav__link}
:::
:::
:::

::: {.md-content md-component="content"}
[![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwLjcxIDcuMDRjLjM5LS4zOS4zOS0xLjA0IDAtMS40MWwtMi4zNC0yLjM0Yy0uMzctLjM5LTEuMDItLjM5LTEuNDEgMGwtMS44NCAxLjgzIDMuNzUgMy43NU0zIDE3LjI1VjIxaDMuNzVMMTcuODEgOS45M2wtMy43NS0zLjc1TDMgMTcuMjVaIj48L3BhdGg+PC9zdmc+)](https://github.com/BetterThanTomorrow/calva/edit/published/docs/site/why-calva.md "Edit this page"){.md-content__button
.md-icon}

# Why Calva?

The main reason you would choose Calva for your
[Clojure](https://clojure.org) and/or
[ClojureScript](https://clojurescript.org) coding is that you want to
use [Visual Studio Code](https://code.visualstudio.com/). Calva provides
VS Code users with a comprehensive set of features to keep you
productive and make it easy to follow Clojure coding best practices.
This also means that if your choice of editor is not made yet, we think
you should give VS Code and Calva a try.

While Calva is a good choice for professional and experienced Clojure
developers, great care has been taken in making Calva a really good
choice for beginners of Clojure as well.

We who make Calva are actively [stewarding, maintaining, documenting and
supporting
it](https://github.com/BetterThanTomorrow/calva/wiki#the-tao-of-calva).
We are also very active Clojure (and Calva) users, participating in the
community. Clojure is dear to us, a lot because it keeps programming fun
and rewarding.

Calva has very happy users! Check out the Programming Languages section
on the Visual Studio Code Marketplace, [sorted by
rating](https://marketplace.visualstudio.com/search?target=VSCode&category=Programming%20Languages&sortBy=Rating):

![VS Code Extensions by Rating](/images/vscode-extension-by-rating.png)

Recently there was a [thread over at
ClojureVerse](https://clojureverse.org/t/gnu-emacs-cider-vs-vs-code-calva/7829/26),
asking about how Calva Compares to Emacs with [CIDER](https://cider.mx).
It is well worth reading. We would like to highlight [the answer by Nick
Cernis](https://clojureverse.org/t/gnu-emacs-cider-vs-vs-code-calva/7829/26),
which focuses on Calva. We\'ll even quote parts of it. 😍

## Nick Cernis on ClojureVerse

> My advice to anyone starting their Clojure journey who is unsure about
> what editor to use:
>
> -   Pick something today and start writing Clojure.
> -   Probably pick an editor you are familiar with already.
> -   **If you're not familiar with any editor yet or you don't have a
>     strong allegiance to one, choose VS Code and Calva.**
> -   Switch to something else only if you encounter persistent
>     annoyances that you can't remove with plugins, code/config
>     changes, help from the community, or more sleep.
>
> **I now use VS Code with Calva every day** but went through a long
> journey trying almost every other editor and plugin combo first. I
> switched from Emacs to VS Code, which might make my perspective
> different to others here.
>
> ...
>
> I started with the jaded assumption that VS Code was probably bad
> because it\'s built by committee at Microsoft on a web-tech based
> Electron stack, only to find that it succeeds in embodying the spirit
> of a "hacker\'s editor" more than even Emacs does in many ways:
>
> ...
>
> **On the benefits of Calva:**
>
> -   **Of all the amazing Clojure community projects, Calva seems most
>     likely to encourage new users to try Clojure and ClojureScript**.
>     A lot of developers use VS Code. It's been tricky to convince
>     frontend developer friends to try ClojureScript, but at least they
>     don't have the excuse that they'll need to switch editors to even
>     try it now. I think as a community we should try to support the
>     projects that encourage Clojure's adoption and ease of use,
>     including by using those products ourselves.
>
> -   **Calva provides a better first-time experience than any other
>     editor/plugin combo whether you're new to Clojure or not.** You
>     can install the plugin and be chatting with your REPL in under a
>     minute without any knowledge of Elisp or VimScript/Lua or how to
>     configure Run Configurations in IntelliJ.
>
> -   **The default key bindings are good and the commands are easily
>     discoverable.**
>
> -   **For its age it's surprisingly feature rich.**

## 100% Five-star Marketplace Reviews {#100-five-star-marketplace-reviews}

We are super proud of the [Calva reviews on the Visual Studio Code
Marketplace](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva&ssr=false#review-details).
Please read them all. 😄 Here\'s a selection that we think captures what
we focus on in when developing Calva:

> ★️️️️️★️️️️️★️️️️️★️️️️️★️️️️️ **Calva has become an essential part of my Clojure workflow.**
>
> It\'s an incredible piece of work by the team behind it.
>
> *Sean Corfield*

------------------------------------------------------------------------

> ★️️️️️★️️️️️★️️️️️★️️️️️★️️️️️ **Calva hits the sweet spot of being both approachable for new
> users and powerful for seasoned ones.**
>
> The creators/maintainers are fantastic individuals that care deeply
> about streamlining the user experience, and it shows.
>
> Good stuff, check it out.
>
> *Clay Hopperdietzel*

------------------------------------------------------------------------

> ★️️️️️★️️️️️★️️️️️★️️️️️★️️️️️ **I switched from IntelliJ / Cursive to VS Code and Calva and
> it\'s been amazing.**
>
> \...
>
> That is the biggest thing I can say for Calva, it just works. I was
> never a fan of VS Code before, but VS Code + Calva for Clojure is now
> my favorite language / IDE experience.
>
> Plus, the #calva on the clojurians slack is brilliant, always someone
> there to help if you have issues (although any issue I\'ve had has
> been squarely on me, and never Calva itself).
>
> I often feel we live in an age where so much software is badly
> written, without care, slow, buggy and just generally awful. Calva is
> the complete opposite. I think the maintainers want to, and have, made
> a wonderful piece of software for Clojure developers.
>
> *Stuart Stein*

------------------------------------------------------------------------

> ★️️️️️★️️️️️★️️️️️★️️️️️★️️️️️ **This is great, and makes VS Code a truly viable IDE/editor for
> clojure development.**
>
> It already has great REPL support (including inline evaluation), an
> extensive Paredit implementation, and excellent linting (care of the
> bundled clj-kondo). Calva is being improved on at an impressive clip
> by maintainers who appear solidly committed to its ongoing
> development. It\'s well-documented, and manages to be both
> approachable and capable.
>
> A no-brainer if you\'re already a VS Code user, and well worth a look
> if you\'re not.
>
> *Crispin Bennett*

------------------------------------------------------------------------

> ★️️️️️★️️️️️★️️️️️★️️️️️★️️️️️ **I\'m using Calva now for a few months and I\'m loving it.**
>
> I joined the Slack channel about 2 wks ago and I must say that I\'m
> very impressed by how active and responsive this community is. Already
> 2 of my issues fixed and I really like Calva (and the extensions it
> uses!).
>
> These are professional people and they make me very happy!
>
> *Uitbeijerse, E (Eric)*
:::
:::
:::

[](../eval-tips/){.md-footer__link .md-footer__link--prev
aria-label="Previous: Evaluate Clojure Code" rel="prev"}

::: {.md-footer__button .md-icon}
![](data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdib3g9IjAgMCAyNCAyNCI+PHBhdGggZD0iTTIwIDExdjJIOGw1LjUgNS41LTEuNDIgMS40Mkw0LjE2IDEybDcuOTItNy45MkwxMy41IDUuNSA4IDExaDEyWiI+PC9wYXRoPjwvc3ZnPg==)
:::

::: md-footer__title
::: md-ellipsis
[ Previous ]{.md-footer__direction} Evaluate Clojure Code
:::
:::

[](../commands-top10/){.md-footer__link .md-footer__link--next
aria-label="Next: The Top 10 Commands" rel="next"}

::: md-footer__title
::: md-ellipsis
[ Next ]{.md-footer__direction} The Top 10 Commands
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
