# Roteiro de validação e apresentação

## Verificações automatizadas

Execute `gradlew.bat testDebugUnitTest assembleDebug` com JDK 17 e SDK 36.
Nove testes verificam: cenário exato do PDF; dinheiro e cartão; vazio; quantidade repetida; percentual variável do Pix; arredondamento; relatório agrupado; percentual inválido; quantidade inválida.

Execução em 04/10/2026: build e 9 testes aprovados. No emulador API 37 foram conferidos catálogo, adição dos três itens, contador, resumo em dinheiro, mudança para Pix e finalização com Logcat. As capturas estão em `docs/evidencias/`. Os demais passos abaixo são o roteiro adicional para o grupo executar.

## Verificação no aplicativo

1. Abra o catálogo: pratos e bebidas separados. Feijoada e água devem mostrar "Sem descrição". A descrição da pizza é longa e deve terminar em reticências quando faltar espaço.
2. Abra um resumo vazio: mensagem apropriada e finalização desabilitada.
3. Adicione pizza, feijoada e suco uma vez. Contador: 3. Selecione Pix: subtotal 112,00; taxa 11,20; desconto 11,20; total 112,00.
4. Alterne para dinheiro e cartão: total 123,20 sem desconto. Retorne ao Pix: 112,00.
5. Volte ao catálogo e adicione outra pizza. Quantidade da pizza: 2; subtotal: 154,00. Remova uma unidade: volta para 112,00. Remova novamente: pizza desaparece.
6. Refaça o cenário obrigatório e gire o dispositivo. Itens e pagamento devem continuar selecionados.
7. Teste tema escuro e fonte ampliada. Role ambas as telas e confira acesso aos botões.
8. Finalize: recibo continua visível, pagamento e itens bloqueados. No Logcat, filtre `tag:CatalogoPedido` e capture o agrupamento PRATOS/BEBIDAS.
9. Novo pedido: carrinho vazio e pagamento Dinheiro. Finalizar de novo o mesmo pedido não deve duplicar log.

## Evidências reais

Salvem capturas em `docs/evidencias/` e adicionem ao README com Markdown. Não apresentem o log esperado da documentação como captura do dispositivo.

## Vídeo

- Brunin: hierarquia selada, propriedades específicas, nulabilidade e dados externos à UI.
- Leo: cálculo em centavos, percentuais sobre subtotal, `when` exaustivo, testes e relatório por categoria.
- Mauricio: Card parametrizado, callback de adição, contador, layout e Material 3.
- Brunão: estado recebido, pagamento dinâmico, linhas reutilizáveis do recibo, navegação e integração.

Encerrem demonstrando o cenário R$ 112,00 e o Logcat. Publiquem o vídeo como Não listado no YouTube e incluam o link real no README antes de entregar.
