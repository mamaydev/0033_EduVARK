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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mamay.eduvark_newrepo.ui.components.EduField
import com.mamay.eduvark_newrepo.ui.components.EduScreen
import com.mamay.eduvark_newrepo.ui.components.Eyebrow
import com.mamay.eduvark_newrepo.ui.components.FieldHelper
import com.mamay.eduvark_newrepo.ui.components.FieldLabel
import com.mamay.eduvark_newrepo.ui.components.NoticeCard
import com.mamay.eduvark_newrepo.ui.components.PrimaryButton
import com.mamay.eduvark_newrepo.ui.components.ScrollColumn
import com.mamay.eduvark_newrepo.ui.components.TextLink
import com.mamay.eduvark_newrepo.ui.components.TintTone
import com.mamay.eduvark_newrepo.ui.theme.EduTheme
import com.mamay.eduvark_newrepo.R
import com.mamay.eduvark_newrepo.ui.theme.dimen

@Composable
fun ServerSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ServerSettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var url by remember(state.currentUrl) { mutableStateOf(state.currentUrl) }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            viewModel.consumeSaved()
            onBack()
        }
    }

    EduScreen(title = stringResource(R.string.auth_server_settings_title), onBack = onBack, modifier = modifier) { padding ->
        ScrollColumn(modifier = Modifier.padding(padding), spacing = dimen(R.dimen.space_0)) {
            Eyebrow(stringResource(R.string.auth_server_debug_eyebrow))
            Spacer(Modifier.height(dimen(R.dimen.space_10)))
            Text(text = stringResource(R.string.auth_server_headline), style = EduTheme.type.display)
            Spacer(Modifier.height(dimen(R.dimen.space_12)))
            Text(
                text = stringResource(R.string.auth_server_body),
                style = EduTheme.type.body,
            )

            Spacer(Modifier.height(dimen(R.dimen.space_22)))
            Column {
                FieldLabel(stringResource(R.string.auth_server_url_label))
                EduField(
                    value = url,
                    onValueChange = { url = it },
                    placeholder = stringResource(R.string.auth_server_url_placeholder),
                    keyboardType = KeyboardType.Uri,
                )
                FieldHelper(stringResource(R.string.auth_server_url_helper))
            }

            Spacer(Modifier.height(dimen(R.dimen.space_12)))
            Text(
                text = stringResource(R.string.auth_server_saved, state.currentUrl),
                style = EduTheme.type.bodySmall,
            )

            val error = state.error
            if (error != null) {
                Spacer(Modifier.height(dimen(R.dimen.space_14)))
                NoticeCard(text = error, tone = TintTone.Danger)
            }

            Spacer(Modifier.height(dimen(R.dimen.space_20)))
            PrimaryButton(text = stringResource(R.string.auth_server_save), onClick = { viewModel.save(url) })
            Spacer(Modifier.height(dimen(R.dimen.space_12)))
            TextLink(text = stringResource(R.string.auth_server_reset), onClick = viewModel::reset, icon = false)
        }
    }
}
