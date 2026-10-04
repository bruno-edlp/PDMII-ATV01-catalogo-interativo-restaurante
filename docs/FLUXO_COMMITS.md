# Fluxo de cópia e commits por integrante

## Preparação

Este diretório contém a solução completa, mas ainda não é um repositório Git. Usem um **novo repositório público do grupo**, vazio, sem README inicial. O repositório do professor é referência, não o destino dos commits.

Distribua os quatro ZIPs da pasta `entrega/` por um canal do grupo. Cada ZIP contém somente os arquivos atribuídos ao integrante, preservando os caminhos. Não se deve copiar a pasta completa da solução para o repositório compartilhado no primeiro passo.

Ordem: **Brunin → Leo → Mauricio → Brunão**. O app integrado fica executável após a quarta parte; as etapas anteriores são montagem de camadas e não versões finais do app. Quem quiser estudar/testar a solução completa antes usa esta pasta separada, sem publicar todos os arquivos de uma vez.

Cada pessoa configura sua identidade real no seu clone, se necessário:

```powershell
git config user.name "SEU NOME"
git config user.email "SEU EMAIL VINCULADO AO GITHUB"
```

Substituam os valores de exemplo e o endereço do repositório. Não usem a identidade de outro integrante. Cada pessoa deve revisar sua parte, compreender os comentários e conseguir explicá-la no vídeo.

## 1. Brunin: estrutura e modelagem

Crie o repositório vazio no GitHub e clone em **outra pasta**:

```powershell
git clone https://github.com/SEU-GRUPO/SEU-REPOSITORIO.git
cd SEU-REPOSITORIO
git switch -c main
```

Extraia `01-brunin.zip` na raiz desse clone. O resultado deve conter `app/`, `gradle/`, scripts Gradle e documentação diretamente na raiz.

```powershell
git add .gitignore README.md docs scripts build.gradle.kts settings.gradle.kts gradle.properties gradle gradlew gradlew.bat app/build.gradle.kts app/src/main/AndroidManifest.xml app/src/main/res app/src/main/java/com/fatec/catalogo/model
git diff --cached --stat
git commit -m "feat(model): estrutura Android, itens de menu e pagamentos"
git push -u origin main
```

Convide os outros três como colaboradores pelo GitHub para que possam enviar as próximas partes.

## 2. Leo: motor, estado e testes

Clone o repositório depois do push de Brunin e entre na pasta. Extraia `02-leo.zip` na raiz.

```powershell
git pull --ff-only origin main
git add app/src/main/java/com/fatec/catalogo/domain app/src/test docs/roteiros/02-leo.md
git diff --cached --stat
git commit -m "feat(domain): calculos, carrinho, recibo e testes"
git push origin main
```

Revisão: mostrar por que o desconto é sobre o subtotal, como centavos são usados e como `when` trata todos os pagamentos. Os testes Android completos serão executados após a integração da quarta parte.

## 3. Mauricio: catálogo

Clone ou atualize após o push de Leo. Extraia `03-mauricio.zip` na raiz.

```powershell
git pull --ff-only origin main
git add app/src/main/java/com/fatec/catalogo/ui/CatalogoScreen.kt docs/roteiros/03-mauricio.md
git diff --cached --stat
git commit -m "feat(ui): catalogo e cards com callback de adicao"
git push origin main
```

Revisão: mostrar parâmetros do Card, callbacks, tratamento de descrição nula, truncamento e tipografia.

## 4. Brunão: resumo e integração

Clone ou atualize após o push de Mauricio. Extraia `04-brunao.zip` na raiz.

```powershell
git pull --ff-only origin main
git add app/src/main/java/com/fatec/catalogo/MainActivity.kt app/src/main/java/com/fatec/catalogo/ui/ResumoScreen.kt docs/roteiros/04-brunao.md
.\gradlew.bat testDebugUnitTest assembleDebug
git diff --cached --stat
git commit -m "feat(ui): resumo, pagamento dinamico e integracao do app"
git push origin main
```

Antes de commitar, resolva eventuais falhas de ambiente e valide o aplicativo usando `docs/VALIDACAO.md`. Revise o pagamento controlado por parâmetros e o vínculo entre telas, ViewModel e motor.

## Finalização coletiva

Todos executam `git pull --ff-only origin main` para receber a solução completa. Registrem capturas, Logcat e vídeo reais; o responsável pela captura faz um commit adicional da documentação.

```powershell
git log --format="%h | %an <%ae> | %s"
git shortlog -sne HEAD
```

Esses comandos conferem autores, mas não substituem a revisão de cada integrante. Não alterem autores nem datas para simular participação. Se um push for recusado porque outro colega atualizou a branch, usem `git pull --rebase origin main`, revisem o resultado e façam novo push; não usem force push.

## Arquivo único solicitado

`entrega/CodigoCompleto.kt.txt` reúne todos os blocos com imports unificados e comentários por integrante, para leitura/cópia. O projeto executável usa arquivos separados por camada, reduzindo conflitos.

Se decidirem usar a versão de arquivo único em outro projeto com o mesmo namespace, renomeiem-na para `MainActivity.kt` e não incluam também os arquivos Kotlin modulares, pois isso duplica as classes. O fluxo de ZIPs acima usa exclusivamente a versão modular e é a opção indicada para os quatro commits.

Para regenerar os ZIPs após alterações: `python scripts/preparar_entrega.py`. O script só prepara arquivos locais; não cria commits nem envia nada ao GitHub.
