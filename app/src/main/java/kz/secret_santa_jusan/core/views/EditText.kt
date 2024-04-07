package kz.secret_santa_jusan.core.views

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.Red
import kz.secret_santa_jusan.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteEditText(
    modifier: Modifier = Modifier,
    text: String = "",
    readOnly: Boolean = false,
    enable:Boolean = true,
    containerColor: Color = White,
    isError: Boolean = false,
    placeholder: String? = null,
    label: String? = null,
    onSelected: (text: String) -> Unit,
    onValueChange: ((text: String) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var filteringOptions by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(true) }
    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = {
            if(filteringOptions.isNotEmpty()) {
                expanded = !expanded
            }
        }
    ) {
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        expanded = false
                    }
                },
            keyboardOptions = keyboardOptions,
            containerColor = containerColor,
            readOnly = readOnly,
            singleLine = true,
            enabled = enable,
            label = label,
            placeholder = placeholder,
            value = text,
            isError = isError,
            onValueChange = {newText ->
                onValueChange?.invoke(newText)
                expanded = filteringOptions.isNotEmpty()
            },
        )
        DropdownMenu(
            modifier = Modifier
                .background(White)
                .exposedDropdownSize(true),
            properties = PopupProperties(focusable = false, dismissOnClickOutside = false),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            filteringOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier,
                    onClick = {
                        onSelected.invoke(selectionOption)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = selectionOption,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 20.dp)
                                .weight(1f)
                        )
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    maxLines:Int = 100,
    minLines:Int = 1,
    isError: Boolean = false,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    containerColor: Color = White,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { SsTextNormal(text = label?: "") },
        placeholder = { Text(placeholder?: "", color = DarkGray) },
        modifier = modifier,
        isError = isError,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        maxLines = maxLines,
        minLines = minLines,
        readOnly = readOnly,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = containerColor,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
            focusedLabelColor = White,
            unfocusedLabelColor = White,
            errorBorderColor = White,
            errorLabelColor = White,
            errorTrailingIconColor = White,
            focusedTrailingIconColor = White,
            unfocusedTrailingIconColor = White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextPassword(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isError: Boolean = false,
    containerColor: Color = White,
){
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { SsTextNormal(text = label?: "") },
        modifier = modifier,
        isError = isError,
        enabled = enabled,
        shape = RoundedCornerShape(32.dp),
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value)
                R.drawable.open_password
            else  R.drawable.close_pasword

            // Please provide localized description for accessibility services
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(painter = painterResource(id = image), description)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
            errorBorderColor = Red,
            focusedTrailingIconColor = DarkGray,
            unfocusedTrailingIconColor = Gray,
            errorTrailingIconColor = Red,
            focusedLabelColor = DarkGray,
            unfocusedLabelColor = Gray,
            errorLabelColor = Red,
        )
    )
}
