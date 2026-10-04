package com.fatec.catalogo.domain

import com.fatec.catalogo.model.*
import org.junit.Assert.*
import org.junit.Test

// ===== PARTE B - LEO: VALIDAÇÃO DAS REGRAS, INDEPENDENTE DO ANDROID =====
class MotorPedidoTest {
    private val cenario = Cardapio.itens.take(3).map { ItemPedido(it, 1) }
    @Test fun cenarioExatoDoPdf() {
        val resultado = MotorPedido.calcular(cenario, FormaPagamento.Pix(10))
        assertEquals(Totais(11200, 1120, 1120), resultado)
        assertEquals(11200L, resultado.total)
    }
    @Test fun dinheiroECartaoSemDesconto() {
        listOf(FormaPagamento.Dinheiro, FormaPagamento.Cartao).forEach {
            assertEquals(12320L, MotorPedido.calcular(cenario, it).total)
        }
    }
    @Test fun carrinhoVazio() {
        Cardapio.pagamentos.forEach { assertEquals(Totais(0, 0, 0), MotorPedido.calcular(emptyList(), it)) }
    }
    @Test fun quantidadeMaiorQueUm() {
        assertEquals(9240L, MotorPedido.calcular(listOf(ItemPedido(Cardapio.itens.first(), 2)), FormaPagamento.Cartao).total)
    }
    @Test fun pixCarregaPercentualVariavel() {
        assertEquals(Totais(11200, 1120, 560), MotorPedido.calcular(cenario, FormaPagamento.Pix(5)))
    }
    @Test fun arredondamentoDeMeioCentavo() {
        val item = ItemMenu.Bebida("teste", "Teste", 5, alcoolica = false)
        assertEquals(Totais(5, 1, 1), MotorPedido.calcular(listOf(ItemPedido(item, 1)), FormaPagamento.Pix(10)))
    }
    @Test fun relatorioAgrupaPorCategoria() {
        val texto = MotorPedido.relatorio(cenario, FormaPagamento.Pix())
        assertTrue(texto.indexOf("PRATOS") < texto.indexOf("Pizza Margherita"))
        assertTrue(texto.indexOf("BEBIDAS") < texto.indexOf("Suco de laranja"))
        assertTrue(texto.contains("TOTAL: ${moeda(11200)}"))
    }
    @Test(expected = IllegalArgumentException::class) fun impedePixInvalido() { FormaPagamento.Pix(101) }
    @Test(expected = IllegalArgumentException::class) fun impedeQuantidadeZero() { ItemPedido(Cardapio.itens.first(), 0) }
}
