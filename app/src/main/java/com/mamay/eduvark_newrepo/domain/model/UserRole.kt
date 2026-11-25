package com.mamay.eduvark_newrepo.domain.model

enum class UserRole(
    val wireValue: String,
    val label: String,
) {
    STUDENT("STUDENT", "Mahasiswa"),
    LECTURER("LECTURER", "Dosen"),
    ADMIN("ADMIN", "Admin"),
    ;

    companion object {
        fun fromWire(value: String): UserRole? =
            entries.firstOrNull { it.wireValue == value }
    }
}
