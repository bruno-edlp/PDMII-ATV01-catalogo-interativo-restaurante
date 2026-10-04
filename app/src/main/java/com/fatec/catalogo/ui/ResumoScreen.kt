package com.fatec.catalogo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fatec.catalogo.R
import com.fatec.catalogo.domain.Totais
import com.fatec.catalogo.domain.moeda
import com.fatec.catalogo.model.*

// ===== INÍCIO DA PARTE D - BRUNÃO: RESUMO, PAGAMENTO E RECIBO =====
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumoScreen(itens: List<ItemPedido>, pagamento: FormaPagamento, opcoes: List<FormaPagamento>,
                 totais: Totais, finalizado: Boolean, onPagamento: (FormaPagamento) -> Unit,
                 onRemover: (String) -> Unit, onVoltar: () -> Unit, onFinalizar: () -> Unit,
                 onNovoPedido: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.resumo), style = MaterialTheme.typography.titleLarge,
            maxLines = 1, overflow = TextOverflow.Ellipsis) }, navigationIcon = {
            TextButton(onClick = onVoltar) { Text(stringResource(R.string.voltar), style = MaterialTheme.typography.labelLarge) }
        })
    }) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (itens.isEmpty()) item {
                Text(stringResource(R.string.vazio), style = MaterialTheme.typography.bodyLarge)
            }
            items(itens, key = { it.item.id }) { linha ->
                Column {
                    LinhaRecibo(stringResource(R.string.linha_item, linha.quantidade, linha.item.nome),
                        moeda(linha.item.precoCentavos * linha.quantidade))
                    if (!finalizado) TextButton(onClick = { onRemover(linha.item.id) }) {
                        Text(stringResource(R.string.remover, linha.item.nome), style = MaterialTheme.typography.labelLarge,
                            maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            item {
                HorizontalDivider()
                Text(stringResource(R.string.pagamento), style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 12.dp))
                Column(Modifier.selectableGroup()) {
                    opcoes.forEach { opcao ->
                        val rotulo = when (opcao) {
                            FormaPagamento.Dinheiro -> stringResource(R.string.dinheiro)
                            FormaPagamento.Cartao -> stringResource(R.string.cartao)
                            is FormaPagamento.Pix -> stringResource(R.string.pix, opcao.percentualDesconto)
                        }
                        Row(Modifier.fillMaxWidth().selectable(selected = pagamento == opcao, enabled = !finalizado,
                            role = Role.RadioButton, onClick = { onPagamento(opcao) }).padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = pagamento == opcao, onClick = null, enabled = !finalizado)
                            Text(rotulo, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 12.dp))
                        }
                    }
                }
            }
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        LinhaRecibo(stringResource(R.string.subtotal), moeda(totais.subtotal))
                        LinhaRecibo(stringResource(R.string.taxa), moeda(totais.taxa))
                        LinhaRecibo(stringResource(R.string.desconto), "-${moeda(totais.desconto)}")
                        HorizontalDivider()
                        LinhaRecibo(stringResource(R.string.total), moeda(totais.total), destaque = true)
                    }
                }
            }
            item {
                if (finalizado) {
                    Text(stringResource(R.string.finalizado), style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = onNovoPedido, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.novo_pedido), style = MaterialTheme.typography.labelLarge)
                    }
                } else Button(onClick = onFinalizar, enabled = itens.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.finalizar), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
fun LinhaRecibo(rotulo: String, valor: String, destaque: Boolean = false) {
    val estilo = if (destaque) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(rotulo, modifier = Modifier.weight(1f), style = estilo, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(valor, modifier = Modifier.weight(1f), style = estilo, textAlign = androidx.compose.ui.text.style.TextAlign.End)
    }
}
// ===== FIM DA PARTE D - BRUNÃO =====
