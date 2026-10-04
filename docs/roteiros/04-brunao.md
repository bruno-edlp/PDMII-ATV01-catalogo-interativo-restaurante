# Brunão — resumo, integração e demonstração final

Tempo sugerido: 2min30s. Arquivos: `ui/ResumoScreen.kt` e `MainActivity.kt`, dentro de `app/src/main/java/com/fatec/catalogo/`.

## 1. Resumo e pagamento

**Mostrar:** assinatura de `ResumoScreen`, componente `LinhaRecibo` e seleção de pagamento.

**Fala sugerida:**

“Minha parte é o resumo do pedido e a integração das telas. O resumo recebe os itens, o pagamento selecionado, as opções disponíveis e os totais que vieram do motor. A LinhaRecibo é reutilizada para exibir rótulos e valores. Quando o usuário escolhe outro pagamento, a tela dispara o callback e o ViewModel atualiza o estado.”

## 2. Integração

**Mostrar:** `CatalogoApp` em `MainActivity.kt`, especialmente as chamadas de `CatalogoScreen`, `ResumoScreen` e `MotorPedido.calcular`.

**Fala sugerida:**

“Na MainActivity, conectamos as quatro partes. O catálogo recebe os produtos da modelagem e envia as ações ao ViewModel. O resumo recebe esse mesmo estado e os resultados do motor. Quando o pagamento muda, o Compose recompõe a interface e os valores são recalculados. A variável resumo controla qual das duas telas está aberta.”

## 3. Demonstração completa

**Preparar:** começar com um pedido novo e deixar o Logcat com o filtro `tag:CatalogoPedido`.

**Fazer e falar, nesta ordem:**

1. **Adicionar uma pizza, uma feijoada e um suco.**
   “Vou reproduzir o cenário do enunciado com uma unidade de cada um destes três itens.”
2. **Abrir o resumo em Dinheiro.**
   “O subtotal é de 112 reais. Em dinheiro, temos a taxa de 11 reais e 20 centavos, sem desconto, totalizando 123 reais e 20 centavos.”
3. **Selecionar Cartão e depois Pix.**
   “O cartão mantém esse total. Ao selecionar Pix, entra o desconto de dez por cento sobre o subtotal. O desconto é de 11 reais e 20 centavos e o total muda imediatamente para 112 reais.”
4. **Apontar subtotal, taxa, desconto e total.**
   “Esses são os quatro valores exigidos no cenário de validação.”
5. **Finalizar e mostrar o Logcat.**
   “Ao finalizar, o recibo fica visível e as alterações ficam bloqueadas. No Logcat, os itens aparecem agrupados em pratos e bebidas, seguidos do pagamento e dos valores finais.”
6. **Clicar em Novo pedido.**
   “O botão Novo pedido limpa o carrinho para começar outra compra.”

## 4. Fechamento

**Mostrar:** tabela de responsabilidades ou histórico real dos commits do grupo.

**Fala sugerida:**

“A integração mantém cada responsabilidade em sua camada: modelagem, regras de negócio, catálogo e resumo. O histórico do repositório registra as contribuições de cada integrante. Com isso, concluímos a demonstração do catálogo interativo.”

## Para se preparar

- A finalização gera um recibo local; o aplicativo não faz cobrança real nem se conecta a um serviço de Pix.
- Não selecione os três itens duas vezes: confira quantidade 1 em cada linha e contador 3.
- Antes de afirmar que o histórico registra todos, confira se os quatro commits já foram enviados.
