package com.josense.inf.topic.concurrency.coroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

@Composable
fun CoroutineCancellationExperiment2Demo() {
    val viewModel: MoneyTransferViewModel = viewModel()

    val scope = rememberCoroutineScope()
//    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        throwable.printStackTrace()
//    }
    LaunchedEffect(true) {
        scope.launch {
            supervisorScope {
                launch {
                    delay(1000L)
                    throw Exception("oops!")
                }
                launch {
                    delay(2000L)
                    println("Coroutine finished!")
                }
            }
        }
    }


    MoneyTransferScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

sealed interface MoneyTransferAction {
    data object TransferFunds : MoneyTransferAction
    data object CancelTransfer : MoneyTransferAction
}

data class MoneyTransferState(
    val savingsBalance: Double = 1000.0,
    val checkingBalance: Double = 500.0,
    val transferAmount: TextFieldState = TextFieldState(),
    val isTransferring: Boolean = false,
    val resultMessage: String? = null,
    val processingState: ProcessingState? = null,
)

sealed interface ProcessingState {
    data object CheckingFunds : ProcessingState
    data object DebitingAccount : ProcessingState
    data object CreditingAccount : ProcessingState
    data object CleanupResources : ProcessingState
}



class MoneyTransferViewModel : ViewModel() {

    var state by mutableStateOf(MoneyTransferState())
        private set

    private var job: Job? = null

    fun onAction(action: MoneyTransferAction) {
        when (action) {
            MoneyTransferAction.TransferFunds -> transferFunds()
            MoneyTransferAction.CancelTransfer -> {
                job?.cancel()
                state = state.copy(
                    isTransferring = false,
                    processingState = null,
                    resultMessage = null,
                )
            }
        }
    }

    private fun transferFunds() {
        job = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    state = state.copy(
                        isTransferring = true,
                        resultMessage = null,
                    )
                    val amountToTransfer = state.transferAmount.text.toString().toDoubleOrNull()

                    if (amountToTransfer == null) {
                        state = state.copy(
                            resultMessage = "Invalid Amount"
                        )
                        return@withContext
                    }

                    if (amountToTransfer == 0.0) {
                        state = state.copy(
                            resultMessage = "Enter amount greater than 0"
                        )
                        return@withContext
                    }

                    state = state.copy(
                        processingState = ProcessingState.CheckingFunds,
                    )
                    val hasEnoughFunds = checkHasEnoughFunds(state.savingsBalance, amountToTransfer)

                    if (!hasEnoughFunds) {
                        state = state.copy(
                            resultMessage = "Insufficient funds",
                        )
                        return@withContext
                    }

                    withContext(NonCancellable) {
                        debitAccount(state.savingsBalance, amountToTransfer)
                        creditAccount(state.checkingBalance, amountToTransfer)
                    }

                    state = state.copy(
                        resultMessage = "Transfer complete!",
                    )

                } catch (e: Exception) {
                    println("Error processing transfer: ${e.message}")
                    currentCoroutineContext().ensureActive()
                } finally {
                    withContext(NonCancellable) {
                        cleanupResources()
                        state = state.copy(
                            processingState = null,
                            isTransferring = false,
                        )
                    }
                }
            }
        }
    }

    private suspend fun creditAccount(toAccountBalance: Double, amount: Double) {
        state = state.copy(
            processingState = ProcessingState.CreditingAccount,
        )
        println("Crediting account with balance $toAccountBalance and amount $amount")
        delay(3000)
        state = state.copy(
            checkingBalance = toAccountBalance + amount,
        )
    }

    private suspend fun debitAccount(fromAccountBalance: Double, amount: Double) {
        state = state.copy(processingState = ProcessingState.DebitingAccount)
        delay(3000)
        state = state.copy(
            savingsBalance = fromAccountBalance - amount,
        )
    }

    private suspend fun checkHasEnoughFunds(fromAccountBalance: Double, amount: Double): Boolean {
        println("Checking balance with balance $fromAccountBalance and amount $amount")
        delay(2000)
        return amount <= fromAccountBalance
    }

    private suspend fun cleanupResources() {
        state = state.copy(
            processingState = ProcessingState.CleanupResources,
        )
        delay(2000)
    }
}

@Composable
fun MoneyTransferScreen(
    state: MoneyTransferState,
    onAction: (MoneyTransferAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val primaryGreen = Color(0xFF4CAF50)
    val secondaryGreen = Color(0xFF81C784)
    val background = Color(0xFFE8F5E9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Savings: $${String.format("%.2f", state.savingsBalance)}",
            color = primaryGreen,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Checking: $${String.format("%.2f", state.checkingBalance)}",
            color = primaryGreen,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            state = state.transferAmount,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            enabled = !state.isTransferring,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp),
            decorator = { innerBox ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.transferAmount.text.isEmpty()) {
                            Text(
                                text = "Enter amount",
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                                    alpha = 0.4f
                                )
                            )
                        }
                        innerBox()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                onAction((MoneyTransferAction.TransferFunds))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = secondaryGreen
            ),
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(15.dp)
                        .alpha(if (state.isTransferring) 1f else 0f),
                    strokeWidth = 1.5.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Transfer Funds",
                    color = Color.White,
                    modifier = Modifier
                        .alpha(if (state.isTransferring) 0f else 1f)
                )
            }
        }
        if (state.isTransferring) {
            Button(
                onClick = {
                    onAction(MoneyTransferAction.CancelTransfer)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryGreen
                ),
            ) {
                Text(text = "Cancel Transfer")
            }
        }
        val processingStateText = when (state.processingState) {
            ProcessingState.CheckingFunds -> {
                "Checking if there is enough funds..."
            }
            ProcessingState.CreditingAccount -> {
                "Depositing money to checking account..."
            }
            ProcessingState.DebitingAccount -> {
                "Withdrawing money from savings account..."
            }
            ProcessingState.CleanupResources -> {
                "Cleaning up resources used..."
            }
            else -> null
        }

        if (processingStateText != null) {
            Text(text = processingStateText)
        }
        if (state.resultMessage != null) {
            Text(text = state.resultMessage)
        }
    }
}