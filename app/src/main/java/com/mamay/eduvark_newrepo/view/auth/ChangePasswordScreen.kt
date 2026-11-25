package com.mamay.eduvark_newrepo.view.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mamay.eduvark_newrepo.ui.components.EduField
import com.mamay.eduvark_newrepo.ui.components.PasswordField
import com.mamay.eduvark_newrepo.ui.components.EduScreen
import com.mamay.eduvark_newrepo.ui.components.Eyebrow
import com.mamay.eduvark_newrepo.ui.components.FieldHelper
import com.mamay.eduvark_newrepo.ui.components.FieldLabel
import com.mamay.eduvark_newrepo.ui.components.NoticeCard
import com.mamay.eduvark_newrepo.ui.components.PrimaryButton
import com.mamay.eduvark_newrepo.ui.components.ScrollColumn
import com.mamay.eduvark_newrepo.ui.components.TintTone
import com.mamay.eduvark_newrepo.ui.theme.EduVarkTheme
import com.mamay.eduvark_newrepo.ui.theme.EduTheme
import com.mamay.eduvark_newrepo.R
import com.mamay.eduvark_newrepo.ui.theme.dimen

@Composable
fun ChangePasswordScreen(
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    forced: Boolean = true,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmation by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) {
            viewModel.consumeSuccess()
            onSuccess()
        }
    }

    EduScreen(title = stringResource(R.string.auth_change_password_title), onBack = onBack, modifier = modifier) { padding ->
        ScrollColumn(modifier = Modifier.padding(padding), spacing = dimen(R.dimen.space_0)) {
            Eyebrow(stringResource(R.string.auth_security_eyebrow))
            Spacer(Modifier.height(dimen(R.dimen.space_10)))
            Text(
                text = if (forced) {
                    stringResource(R.string.auth_forced_headline)
                } else {
                    stringResource(R.string.auth_self_headline)
                },
                style = EduTheme.type.display,
            )
            Spacer(Modifier.height(dimen(R.dimen.space_12)))
            Text(
                text = if (forced) {
                    stringResource(R.string.auth_forced_body)
                } else {
                    stringResource(R.string.auth_self_body)
                },
                style = EduTheme.type.body,
            )

            Spacer(Modifier.height(dimen(R.dimen.space_24)))
            Column {
                FieldLabel(stringResource(R.string.auth_current_password_label))
                PasswordField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    placeholder = stringResource(R.string.auth_current_password_placeholder),
                    enabled = !state.isLoading,
                    leadingLabel = stringResource(R.string.auth_current_password_leading),
                )
            }

            Spacer(Modifier.height(dimen(R.dimen.space_16)))
            Column {
                FieldLabel(stringResource(R.string.auth_new_password_label))
                PasswordField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = stringResource(R.string.auth_new_password_placeholder),
                    enabled = !state.isLoading,
                    leadingLabel = stringResource(R.string.auth_new_password_leading),
                )
                FieldHelper(stringResource(R.string.auth_new_password_helper))
            }

            Spacer(Modifier.height(dimen(R.dimen.space_16)))
            Column {
                FieldLabel(stringResource(R.string.auth_confirm_password_label))
                PasswordField(
                    value = confirmation,
                    onValueChange = { confirmation = it },
                    placeholder = stringResource(R.string.auth_confirm_password_placeholder),
                    enabled = !state.isLoading,
                    leadingLabel = stringResource(R.string.auth_confirm_password_leading),
                )
            }

            val error = state.error
            if (error != null) {
                Spacer(Modifier.height(dimen(R.dimen.space_16)))
                NoticeCard(text = error, tone = TintTone.Danger)
            }

            Spacer(Modifier.height(dimen(R.dimen.space_22)))
            PrimaryButton(
                text = stringResource(R.string.auth_save_password),
                onClick = { viewModel.submit(currentPassword, newPassword, confirmation) },
                loading = state.isLoading,
                enabled = !state.isLoading,
            )

            Spacer(Modifier.height(dimen(R.dimen.space_16)))
            Text(
                text = stringResource(R.string.auth_password_tip),
                style = EduTheme.type.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangePasswordScreenPreview() {
    EduVarkTheme {
        ChangePasswordScreen(onSuccess = {})
    }
}
