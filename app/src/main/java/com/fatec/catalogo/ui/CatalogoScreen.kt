package com.fatec.catalogo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fatec.catalogo.R
import com.fatec.catalogo.domain.moeda
import com.fatec.catalogo.model.ItemMenu

// ===== INÍCIO DA PARTE C - MAURICIO: CATÁLOGO E CARD PARAMETRIZADO =====
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(itens: List<ItemMenu>, quantidade: Int, onAdicionar: (ItemMenu) -> Unit,
                   onResumo: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.catalogo), style = MaterialTheme.typography.titleLarge) }) },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Column(Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp)) {
                    Text(stringResource(R.string.carrinho, quantidade), style = MaterialTheme.typography.bodyLarge)
                    Button(onClick = onResumo, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.ver_resumo), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text(stringResource(R.string.pratos), style = MaterialTheme.typography.titleMedium) }
            items(itens.filterIsInstance<ItemMenu.Prato>(), key = { it.id }) { item ->
                ItemMenuCard(item, onAdicionar)
            }
            item { Text(stringResource(R.string.bebidas), style = MaterialTheme.typography.titleMedium) }
            items(itens.filterIsInstance<ItemMenu.Bebida>(), key = { it.id }) { item ->
                ItemMenuCard(item, onAdicionar)
            }
        }
    }
}

@Composable
fun ItemMenuCard(item: ItemMenu, onAdicionar: (ItemMenu) -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(item.nome, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.descricao?.takeIf { it.isNotBlank() } ?: stringResource(R.string.sem_descricao),
                style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            val marcador = when (item) {
                is ItemMenu.Prato -> if (item.vegetariano) R.string.vegetariano else R.string.nao_vegetariano
                is ItemMenu.Bebida -> if (item.alcoolica) R.string.alcoolica else R.string.sem_alcool
            }
            Text(stringResource(marcador), style = MaterialTheme.typography.labelMedium)
            Text(moeda(item.precoCentavos), style = MaterialTheme.typography.titleMedium)
            Button(onClick = { onAdicionar(item) }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.adicionar), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
// ===== FIM DA PARTE C - MAURICIO =====
