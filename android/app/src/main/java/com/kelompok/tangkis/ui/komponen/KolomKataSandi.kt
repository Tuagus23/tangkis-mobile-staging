package com.kelompok.tangkis.ui.komponen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.theme.GarisTegas
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

// Kolom kata sandi dengan label dan tombol tampilkan/sembunyikan
@Composable
fun KolomKataSandi(
    label: String,
    nilai: String,
    onNilaiBerubah: (String) -> Unit,
    modifier: Modifier = Modifier,
    galat: String? = null,
    aksiKeyboard: ImeAction = ImeAction.Done,
    onSelesai: () -> Unit = {},
) {
    var terlihat by remember { mutableStateOf(false) }

    Column(modifier) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = TeksSekunder)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = nilai,
            onValueChange = onNilaiBerubah,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = galat != null,
            supportingText = galat?.let { pesan -> { Text(pesan) } },
            visualTransformation = if (terlihat) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = aksiKeyboard),
            keyboardActions = KeyboardActions(onDone = { onSelesai() }),
            trailingIcon = {
                IconButton(onClick = { terlihat = !terlihat }) {
                    Icon(
                        painterResource(if (terlihat) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                        contentDescription = if (terlihat) "Sembunyikan kata sandi" else "Tampilkan kata sandi",
                        tint = TeksSekunder,
                    )
                }
            },
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Tinta,
                unfocusedBorderColor = GarisTegas,
                focusedContainerColor = Permukaan,
                unfocusedContainerColor = Permukaan,
                errorContainerColor = Permukaan,
            ),
        )
    }
}
