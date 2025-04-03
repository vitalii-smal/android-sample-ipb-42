package com.ipb42.android.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun CounterView(viewModel: CounterViewModel, modifier: Modifier = Modifier) {
    val count by viewModel.count

    Column(modifier = Modifier.composed { modifier }) {
        Text(text = "Count: $count")
        Row {
            Button(onClick = { viewModel.decrement() }) {
                Text("-")
            }
            Button(onClick = { viewModel.increment() }) {
                Text("+")
            }
        }
    }
}