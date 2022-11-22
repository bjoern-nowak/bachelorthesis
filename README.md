
## ToDos
- Use better fonts (may install cm-super)


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


## Trobuleshoot

### Fix old/wrong TEX Live version is used
Edit your ~/.profile and add:
```
## set latex version
export PATH="/usr/local/texlive/2022/bin/x86_64-linux:$PATH"
export INFOPATH=$INFOPATH:/usr/local/texlive/2022/texmf-dist/doc/info
export MANPATH=$MANPATH:/usr/local/texlive/2022/texmf-dist/doc/man
```
