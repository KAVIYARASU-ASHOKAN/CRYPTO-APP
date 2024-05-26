
package com.granite.compose.ui.DynamicForm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoSuggestTextBox(
    Text : String = "",
    onValueChange : (String) -> Unit,
    modifier : Modifier = Modifier,
    label : String,
    suggestions : List<String>,
    inputType : KeyboardType = KeyboardType.Text,
    onClick : (Boolean) -> Unit,

){

    var projectName by remember { mutableStateOf(Text) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember  { mutableStateOf(Size.Zero)}

    onValueChange(projectName)

    Column() {
        OutlinedTextField(
            value = projectName,
            onValueChange = {
                projectName = it
                expanded = true
                onClick(true)
            },
            label = { label },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .onFocusEvent {
                    onClick(false)
                }
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                },
            textStyle = LocalTextStyle.current.copy(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        AnimatedVisibility(visible = expanded) {
            Card(
                shape = RoundedCornerShape(2.dp),
                modifier = Modifier
                    .padding(5.dp)
                    .width(textFieldSize.width.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 100.dp)
                ) {
                    if (projectName.isNotEmpty()) {
                        items(
                            suggestions.filter {
                                it.lowercase()
                                    .contains(projectName.lowercase()) || it.lowercase()
                                    .contains("others")
                            }
                                .sorted()
                        ) {
                            ItemsCategory(title = it) { title ->
                                projectName = title
                                expanded = false
                            }
                        }
                    }
                }

            }

        }


    }
}

@Composable
fun ItemsCategory(
    title : String,
    onSelect : (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ){
        Text(text = title, color = Color.White)
    }
}