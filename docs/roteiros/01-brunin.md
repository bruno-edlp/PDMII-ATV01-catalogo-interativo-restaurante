# Brunin — abertura e modelagem

Tempo sugerido: 1min30s. Arquivo principal: `app/src/main/java/com/fatec/catalogo/model/Modelos.kt`.

## 1. Apresentação do projeto

**Mostrar:** aplicativo aberto no catálogo e tabela de responsabilidades do README.

**Fala sugerida:**

“Nosso projeto é um catálogo interativo de restaurante, feito em Kotlin com Jetpack Compose e Material 3. O usuário escolhe pratos e bebidas, acompanha o carrinho e vê o total conforme a forma de pagamento. Dividimos o projeto em quatro partes: eu fiquei com a modelagem, o Leo com as regras de negócio, o Mauricio com o catálogo e o Brunão com o resumo e a integração.”

## 2. Itens do menu

**Mostrar:** `sealed class ItemMenu`, depois `Prato` e `Bebida`.

**Fala sugerida:**

“A base do cardápio é ItemMenu. Ela reúne identificador, nome, preço e descrição opcional. É uma classe selada, então os tipos dessa hierarquia ficam controlados. Prato acrescenta a informação de vegetariano e Bebida informa se é alcoólica. O preço é guardado em centavos: 4200 representa 42 reais. A descrição pode ser nula, e a interface trata esse caso.”

## 3. Pagamentos e dados

**Mostrar:** `FormaPagamento`, `ItemPedido` e `Cardapio`.

**Fala sugerida:**

“As formas de pagamento também usam uma estrutura selada: Dinheiro, Cartão e Pix. O Pix carrega o percentual de desconto e aceita valores de zero a cem. ItemPedido associa um item a uma quantidade positiva. Os produtos e as opções de pagamento ficam em Cardapio, fora das funções que desenham a tela. Assim, a interface recebe os dados por parâmetro.”

**Encerramento:**

“Esses modelos são o contrato usado pelas outras camadas. Agora o Leo vai mostrar como os cálculos e o estado do pedido usam essa estrutura.”

## Para se preparar

- Saiba apontar `String?` e explicar que a interrogação permite `null`.
- `sealed` não significa que os dados nunca mudam; significa que a hierarquia de tipos é restrita.
- O cardápio é local e definido no código, mas fica fora da renderização. Não existe banco de dados neste projeto.
