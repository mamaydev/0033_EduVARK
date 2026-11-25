## Nama: Fannandya Sutan Sakti Pratama
## NIM: 20240140033
## Kelas: D

### Judul: EduVARK

EduVARK adalah aplikasi pembelajaran IBD berbasis preferensi VARK (Visual, Auditory, Read/Write, Kinesthetic) untuk mahasiswa, dosen, dan admin. Repositori ini berisi aplikasi Android (Kotlin + Jetpack Compose) hasil migrasi penuh dari proyek EduVARK sebelumnya; backend berjalan sebagai layanan terpisah dan tidak termasuk dalam repositori ini.

## Status

- `./gradlew :app:assembleDebug` dan `:app:lintDebug` **lulus** (0 error, 73 warning non-blocking); APK debug tersedia.

## Struktur Paket

```text
app/src/main/java/com/mamay/eduvark_newrepo/
├── MainActivity.kt / EduVarkApplication.kt
├── core/        common, datastore, di, files, network, text
├── data/        remote (DTO, ApiExecutor, SafeApiCall), repository
├── domain/      error, model, repository (kontrak), usecase
├── ui/          components, format, theme
└── view/        account, admin/users, auth, home, lecturer/*, student/*, vark, navigation
```


## Fitur

- **Autentikasi & akun:** login, logout, refresh sesi, ganti password, pengaturan server (debug), profil.
- **VARK:** pengisian preferensi VARK sekali (write-once).
- **Mahasiswa:** home/dashboard, daftar topik, materi (unduh & buka via FileProvider), feedback V/A/R, studi kasus + pengumpulan jawaban.
- **Dosen:** hub, topik, materi, daftar submission, penilaian + komentar, analitik feedback.
- **Admin:** daftar/filter pengguna, tambah pengguna, detail, ubah role, reset password, nonaktifkan akun.
- **Navigasi:** satu NavHost dengan rute per peran (student/lecturer/admin).

## Prasyarat & Build

- JDK 17 (Gradle memakai `jvmToolchain(17)`; daemon dapat berjalan dengan JBR Android Studio).
- Android SDK 37 (compileSdk/targetSdk 37, minSdk 24).
- Gradle Wrapper 9.6.0, AGP 9.4.1, Kotlin 2.2.10.

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew :app:assembleDebug
./gradlew :app:lintDebug
```


## Konfigurasi API

| Build | API Base URL | Override runtime |
|---|---|---|
| debug | `http://10.0.2.2:3000/api/v1/` (emulator) | ya — layar **Pengaturan server (debug)** |
| release | `https://pam.sequein.my.id/api/v1/` | tidak (dikunci saat build) |

Untuk HP fisik dengan backend lokal, isi `http://<IP-LAN-Mac>:3000/api/v1/` melalui Pengaturan server (debug). Build debug mengizinkan HTTP cleartext dan permission `ACCESS_LOCAL_NETWORK`; build release tidak.

## Teknologi

| Komponen | Versi |
|---|---|
| Kotlin / Compose BOM | 2.2.10 / 2026.02.01 |
| AGP / Gradle | 9.4.1 / 9.6.0 |
| Hilt / KSP | 2.60.1 / 2.2.10-2.0.2 |
| Navigation Compose | 2.10.2 |
| Retrofit / OkHttp | 2.11.0 / 4.12.0 |
| kotlinx.serialization / coroutines | 1.9.0 |
| DataStore Preferences | 1.3.0 |


### Laporan Progress Mingguan
##### Minggu Pertama
- 23 November 2026: inisialisasi projek dan package
- 25 November 2026: membuat seluruh file jetpack compose dan integrasi Gradle/manifest/resource; `assembleDebug` + `lintDebug` lulus

##### Minggu Kedua


##### Minggu Ketiga


#### Catatan: repo saya yang asli "Fannandya" terkena flagged oleh github, yang mengakibatkan repo tersebut unvisible untuk orang lain, jadi saya menggunakan repo ini untuk pengganti tugas akhir agar bisa dilihat progressnya
