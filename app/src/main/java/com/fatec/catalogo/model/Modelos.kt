package com.fatec.catalogo.model

// ===== INÍCIO DA PARTE A - BRUNIN: MODELAGEM E DADOS =====
// Hierarquia fechada: o compilador conhece todos os tipos possíveis.
sealed class ItemMenu(val id: String, val nome: String, val precoCentavos: Long, val descricao: String?) {
    init { require(id.isNotBlank() && nome.isNotBlank() && precoCentavos >= 0) }
    class Prato(id: String, nome: String, precoCentavos: Long, descricao: String? = null,
                val vegetariano: Boolean) : ItemMenu(id, nome, precoCentavos, descricao)
    class Bebida(id: String, nome: String, precoCentavos: Long, descricao: String? = null,
                 val alcoolica: Boolean) : ItemMenu(id, nome, precoCentavos, descricao)
}

sealed interface FormaPagamento {
    data object Dinheiro : FormaPagamento
    data object Cartao : FormaPagamento
    data class Pix(val percentualDesconto: Int = 10) : FormaPagamento {
        init { require(percentualDesconto in 0..100) }
    }
}

data class ItemPedido(val item: ItemMenu, val quantidade: Int) {
    init { require(quantidade > 0) }
}

object Cardapio {
    // Dados fora dos Composables. Os três primeiros reproduzem o enunciado.
    val itens: List<ItemMenu> = listOf(
        ItemMenu.Prato("pizza", "Pizza Margherita", 4200,
            "Molho de tomate, muçarela, manjericão fresco e azeite sobre massa artesanal de longa fermentação.", true),
        ItemMenu.Prato("feijoada", "Feijoada completa", 5800, vegetariano = false),
        ItemMenu.Bebida("suco", "Suco de laranja", 1200, "Preparado com laranjas frescas.", false),
        ItemMenu.Prato("risoto", "Risoto de cogumelos", 4600, "Arroz cremoso com cogumelos frescos.", true),
        ItemMenu.Bebida("agua", "Água mineral", 600, alcoolica = false),
        ItemMenu.Bebida("cerveja", "Cerveja artesanal", 1800, "Garrafa de 350 ml.", true)
    )
    val pagamentos = listOf(FormaPagamento.Dinheiro, FormaPagamento.Cartao, FormaPagamento.Pix(10))
}
// ===== FIM DA PARTE A - BRUNIN =====
