package com.fatec.catalogo.domain

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fatec.catalogo.model.*

// ===== INÍCIO DA PARTE B - LEO: ESTADO E CONSOLIDAÇÃO =====
// ViewModel mantém o pedido em rotações. Não persiste após encerramento do processo.
class PedidoViewModel : ViewModel() {
    var itens by mutableStateOf<List<ItemPedido>>(emptyList())
        private set
    var pagamento by mutableStateOf<FormaPagamento>(FormaPagamento.Dinheiro)
        private set
    var finalizado by mutableStateOf(false)
        private set

    fun adicionar(item: ItemMenu) {
        if (finalizado) return
        itens = if (itens.any { it.item.id == item.id }) itens.map {
            if (it.item.id == item.id) it.copy(quantidade = it.quantidade + 1) else it
        } else itens + ItemPedido(item, 1)
    }
    fun remover(id: String) {
        if (finalizado) return
        itens = itens.mapNotNull {
            if (it.item.id != id) it else if (it.quantidade > 1) it.copy(quantidade = it.quantidade - 1) else null
        }
    }
    fun selecionar(pagamento: FormaPagamento) { if (!finalizado) this.pagamento = pagamento }
    fun finalizar() {
        if (itens.isEmpty() || finalizado) return
        MotorPedido.relatorio(itens, pagamento).lineSequence().forEach { Log.i("CatalogoPedido", it) }
        finalizado = true
    }
    fun novoPedido() {
        itens = emptyList()
        pagamento = FormaPagamento.Dinheiro
        finalizado = false
    }
}
// ===== FIM DA PARTE B - LEO =====
