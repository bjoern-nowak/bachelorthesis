
## Helpful
- https://www.semipol.de/posts/2018/06/latex-best-practices-lessons-learned-from-writing-a-phd-thesis/#building-latex-documents


## Setup (Linux)

### Install Latex
For faster install may use ``basic`` scheme instead of ``full``
```
wget http://mirror.ctan.org/systems/texlive/tlnet/install-tl-unx.tar.gz
tar -xvf ./install-tl-unx.tar.gz
cd install-tl-*
./install-tl --scheme=full
```
Activate option to create symlinks (press ``O``, press ``l`` (small L); confirm each time with ``ENTER``).

Start install (press ``I``; confirm with ``ENTER``).


### IntelliJ IDEA Support
Install ``TeXiFy IDEA`` Plugin


### Install deprecated KOMA-Script srcpage2

``cd src/library/koma-script-obsolete && tex srcpage2-obsolete.dtx``

``mkdir -P ~/texmf/tex/latex/local``

``cp src/library/koma-script-obsolete/srcpage2.sty ~/texmf/tex/latex/local/``

Else following error will pop up: ``! LaTeX Error: File `scrpage2.sty' not found.``

## Trobuleshoot

### Fix old/wrong TEX Live version is used
Edit your ~/.profile and add:
```
## set latex version
export PATH="/usr/local/texlive/2022/bin/x86_64-linux:$PATH"
export INFOPATH=$INFOPATH:/usr/local/texlive/2022/texmf-dist/doc/info
export MANPATH=$MANPATH:/usr/local/texlive/2022/texmf-dist/doc/man
```
