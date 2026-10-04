package com.fatec.catalogo.domain

import com.fatec.catalogo.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

// ===== INÍCIO DA PARTE B - LEO: MOTOR DE CÁLCULOS =====
data class Totais(val subtotal: Long, val taxa: Long, val desconto: Long) {
    val total: Long get() = subtotal + taxa - desconto
}

fun moeda(centavos: Long): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    .format(BigDecimal.valueOf(centavos, 2))

object MotorPedido {
    private fun percentual(valor: Long, percentual: Int): Long = BigDecimal.valueOf(valor)
        .multiply(BigDecimal.valueOf(percentual.toLong())).divide(BigDecimal(100), 0, RoundingMode.HALF_UP)
        .longValueExact()

    fun calcular(itens: List<ItemPedido>, pagamento: FormaPagamento): Totais {
        val subtotal = itens.sumOf { Math.multiplyExact(it.item.precoCentavos, it.quantidade.toLong()) }
        // Todas as formas cobram 10%. Sem else: um novo tipo exige revisão explícita.
        val taxa = when (pagamento) {
            FormaPagamento.Dinheiro -> percentual(subtotal, 10)
            FormaPagamento.Cartao -> percentual(subtotal, 10)
            is FormaPagamento.Pix -> percentual(subtotal, 10)
        }
        val desconto = when (pagamento) {
            FormaPagamento.Dinheiro -> 0L
            FormaPagamento.Cartao -> 0L
            is FormaPagamento.Pix -> percentual(subtotal, pagamento.percentualDesconto)
        }
        return Totais(subtotal, taxa, desconto)
    }

    fun relatorio(itens: List<ItemPedido>, pagamento: FormaPagamento): String = buildString {
        val totais = calcular(itens, pagamento)
        appendLine("RECIBO - CATÁLOGO INTERATIVO")
        itens.groupBy { when (it.item) {
            is ItemMenu.Prato -> "PRATOS"
            is ItemMenu.Bebida -> "BEBIDAS"
        } }.forEach { (categoria, linhas) ->
            appendLine(categoria)
            linhas.forEach { appendLine("${it.quantidade} x ${it.item.nome}: ${moeda(it.item.precoCentavos * it.quantidade)}") }
        }
        appendLine("Pagamento: ${when (pagamento) {
            FormaPagamento.Dinheiro -> "Dinheiro"
            FormaPagamento.Cartao -> "Cartão"
            is FormaPagamento.Pix -> "Pix (${pagamento.percentualDesconto}%)"
        }}")
        appendLine("Subtotal: ${moeda(totais.subtotal)}")
        appendLine("Taxa de serviço: ${moeda(totais.taxa)}")
        appendLine("Desconto: -${moeda(totais.desconto)}")
        append("TOTAL: ${moeda(totais.total)}")
    }
}
// ===== FIM DA PARTE B - LEO =====
