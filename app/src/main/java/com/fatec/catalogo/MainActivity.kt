package com.fatec.catalogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatec.catalogo.domain.*
import com.fatec.catalogo.model.Cardapio
import com.fatec.catalogo.ui.*

// ===== INÍCIO DA PARTE D - BRUNÃO: INTEGRAÇÃO FINAL DAS QUATRO CAMADAS =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme()
                else lightColorScheme(primary = Color(0xFF78532B), secondary = Color(0xFF526443))) {
                CatalogoApp()
            }
        }
    }
}

@Composable
private fun CatalogoApp(vm: PedidoViewModel = viewModel()) {
    var resumo by rememberSaveable { mutableStateOf(false) }
    BackHandler(enabled = resumo && !vm.finalizado) { resumo = false }
    // Após finalizar, o recibo fica preservado até o usuário iniciar um novo pedido.
    if (resumo || vm.finalizado) {
        ResumoScreen(vm.itens, vm.pagamento, Cardapio.pagamentos,
            MotorPedido.calcular(vm.itens, vm.pagamento), vm.finalizado,
            vm::selecionar, vm::remover,
            onVoltar = { if (vm.finalizado) vm.novoPedido(); resumo = false },
            onFinalizar = vm::finalizar,
            onNovoPedido = { vm.novoPedido(); resumo = false })
    } else CatalogoScreen(Cardapio.itens, vm.itens.sumOf { it.quantidade }, vm::adicionar,
        onResumo = { resumo = true })
}
// ===== FIM DA PARTE D - BRUNÃO =====
