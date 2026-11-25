package com.mamay.eduvark_newrepo.view.student.materials

import com.mamay.eduvark_newrepo.R
import com.mamay.eduvark_newrepo.domain.model.MaterialOpenFailure

/** Pemetaan kegagalan buka materi ke string milik Paket 2. */
fun materialOpenFailureText(failure: MaterialOpenFailure): Int = when (failure) {
    MaterialOpenFailure.SESSION_EXPIRED -> R.string.p2_material_open_session_expired
    MaterialOpenFailure.FORBIDDEN -> R.string.p2_material_open_forbidden
    MaterialOpenFailure.NETWORK -> R.string.p2_material_open_network
    MaterialOpenFailure.GENERIC -> R.string.p2_material_open_generic
}
