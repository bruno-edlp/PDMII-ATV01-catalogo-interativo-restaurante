"""Gera pacotes por responsável e um arquivo Kotlin consolidado, sem efetuar commits."""
from pathlib import Path
from zipfile import ZipFile, ZIP_DEFLATED

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "entrega"
OUT.mkdir(exist_ok=True)
BASE = "app/src/main/java/com/fatec/catalogo/"

def arquivos(padroes):
    return sorted({p for padrao in padroes for p in ROOT.glob(padrao) if p.is_file()})

grupos = {
    "01-brunin": [".gitignore", "README.md", "docs/*.md", "docs/roteiros/01-brunin.md", "docs/evidencias/*", "scripts/*.py",
        "build.gradle.kts", "settings.gradle.kts", "gradle.properties", "gradlew", "gradlew.bat",
        "gradle/wrapper/*", "app/build.gradle.kts", "app/src/main/AndroidManifest.xml",
        "app/src/main/res/**/*", BASE + "model/*.kt"],
    "02-leo": [BASE + "domain/*.kt", "app/src/test/**/*.kt", "docs/roteiros/02-leo.md"],
    "03-mauricio": [BASE + "ui/CatalogoScreen.kt", "docs/roteiros/03-mauricio.md"],
    "04-brunao": [BASE + "MainActivity.kt", BASE + "ui/ResumoScreen.kt", "docs/roteiros/04-brunao.md"],
}
for nome, padroes in grupos.items():
    with ZipFile(OUT / (nome + ".zip"), "w", ZIP_DEFLATED) as z:
        for p in arquivos(padroes):
            z.write(p, p.relative_to(ROOT).as_posix())
        z.writestr("LEIA-ME-" + nome + ".txt",
            "PACOTE " + nome.upper() + "\n\n"
            "Ordem dos commits: 01 Brunin -> 02 Leo -> 03 Mauricio -> 04 Brunao.\n"
            "1. Clone o repositorio DO GRUPO em sua maquina (nao o do professor).\n"
            "2. Aguarde o push da pessoa anterior e atualize com git pull --ff-only origin main.\n"
            "   Brunin inicia o repositorio vazio, conforme docs/FLUXO_COMMITS.md.\n"
            "3. Extraia o conteudo deste ZIP na raiz do clone, mantendo os caminhos.\n"
            "4. Leia docs/FLUXO_COMMITS.md e execute somente os comandos da sua etapa.\n"
            "5. Revise sua parte e faca commit/push com sua propria identidade Git.\n"
            "6. Seu roteiro de gravacao esta em docs/roteiros/" + nome + ".md.\n"
            "O aplicativo completo depende dos quatro pacotes. Nao copie o codigo completo no primeiro commit.\n"
            "Este LEIA-ME serve apenas para orientacao; nao precisa ser commitado.\n")

ordem = ["model/Modelos.kt", "domain/MotorPedido.kt", "domain/PedidoViewModel.kt",
         "ui/CatalogoScreen.kt", "ui/ResumoScreen.kt", "MainActivity.kt"]
imports, blocos = set(), []
for nome in ordem:
    corpo = []
    for linha in (ROOT / BASE / nome).read_text(encoding="utf-8").splitlines():
        if linha.startswith("package "):
            continue
        if linha.startswith("import "):
            if not linha.startswith("import com.fatec.catalogo"):
                imports.add(linha)
        else:
            corpo.append(linha)
    blocos.append("\n".join(corpo).strip())
(OUT / "CodigoCompleto.kt.txt").write_text(
    "// Versão única alternativa. Não combinar com os arquivos modulares.\n"
    "package com.fatec.catalogo\n\n" + "\n".join(sorted(imports)) + "\n\n" +
    "\n\n".join(blocos) + "\n", encoding="utf-8")
print("Gerados 4 ZIPs e CodigoCompleto.kt.txt em", OUT)
