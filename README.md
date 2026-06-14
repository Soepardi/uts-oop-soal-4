# Aplikasi Penjualan Motor (UNSIA Dealer Motor)

Proyek ini dibuat untuk memenuhi tugas **Ujian Tengah Semester (UTS)**.

---

## Identitas Mahasiswa

*   **Nama:** Supardi Akhiyat
*   **NIM:** 230101010026

---

## Prasyarat Sistem (Prerequisites)
Sebelum menjalankan aplikasi, pastikan sistem Anda telah terpasang:
*   **Java Development Kit (JDK)** versi 8 atau yang lebih baru.
*   Terminal / Command Prompt / PowerShell.

---

## Struktur File Penting
Untuk memastikan program berjalan dengan lancar (termasuk aset gambar dan pustaka UI), pastikan file-file berikut berada dalam direktori yang sama:
*   `AplikasiPenjualanMotor.java` — Source code utama program.
*   `flatlaf-3.7.1.jar` — Pustaka tampilan UI modern (FlatLaf).
*   `logo.png` — Logo dealer yang digunakan pada *header* aplikasi dan ikon jendela.

---

## Cara Menginstal & Menjalankan Aplikasi

Ikuti langkah-langkah di bawah ini untuk mengompilasi dan menjalankan program melalui terminal:

### 1. Buka Terminal / Command Prompt
Arahkan direktori terminal ke folder tempat file proyek ini disimpan. Contoh:
```bash
cd "D:\Kuliah\semester 6\Pemrograman Berbasis Object\UTS\Soal 4"
```

### 2. Kompilasi Program (Compile)
Kompilasikan kode program Java dengan menyertakan pustaka `flatlaf` ke dalam *classpath*:
```bash
javac -cp flatlaf-3.7.1.jar AplikasiPenjualanMotor.java
```
*Perintah di atas akan menghasilkan file `.class` dari kode Java.*

### 3. Jalankan Aplikasi (Run)
Jalankan program berdasarkan sistem operasi yang Anda gunakan:

*   **Windows (Command Prompt / PowerShell):**
    ```cmd
    java -cp ".;flatlaf-3.7.1.jar" AplikasiPenjualanMotor
    ```
*   **Linux / macOS (Terminal):**
    ```bash
    java -cp ".:flatlaf-3.7.1.jar" AplikasiPenjualanMotor
    ```
