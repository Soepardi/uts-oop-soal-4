# Dokumentasi Sistem Aplikasi Penjualan Motor

## Deskripsi Program
`AplikasiPenjualanMotor.java` adalah aplikasi berbasis Java Swing yang dirancang untuk mensimulasikan sistem kasir pada dealer motor. Program ini menggunakan antarmuka grafis (GUI) modern dengan desain layout yang responsif dan memanfaatkan *library* **FlatLaf** (apabila tersedia) untuk memberikan tampilan estetis dan profesional. 

Fitur utama aplikasi mencakup:
- Input data transaksi: nama pembeli, merk motor, jenis motor, dan metode pembayaran.
- Perhitungan harga otomatis berdasarkan kombinasi merk dan jenis motor.
- Perhitungan diskon secara otomatis berdasarkan metode pembayaran (Tunai atau Kredit).
- Pratinjau "Struk Kasir" secara *real-time* yang menampilkan rincian transaksi layaknya sistem Point of Sale (POS) modern.

## Analisis Komponen Utama dan Cuplikan Kode

Program ini dibangun menggunakan konsep *Object-Oriented Programming* (OOP) yang memisahkan logika bisnis dengan antarmuka pengguna. Berikut adalah analisis komponen-komponen utamanya:

### 1. Kelas `Motor` (Domain Object)
Kelas ini bertanggung jawab untuk merepresentasikan entitas motor, menyimpan atribut `merk` dan `jenis`, serta memiliki logika untuk menentukan harga dasar kendaraan.

**Cuplikan Kode: Penentuan Harga Motor**
```java
class Motor {
    private String merk;
    private String jenis;
    private double harga;

    public Motor(String merk, String jenis) {
        this.merk = merk;
        this.jenis = jenis;
        this.harga = hitungHarga();
    }

    private double hitungHarga() {
        if (merk.equalsIgnoreCase("HONDA")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 12000000;
            if (jenis.equalsIgnoreCase("SPORT")) return 32000000;
            if (jenis.equalsIgnoreCase("MATIK")) return 15000000;
        } else if (merk.equalsIgnoreCase("YAMAHA")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 13500000;
            // ... (logika lainnya)
        }
        // ...
        return 0;
    }
    // getters
}
```

### 2. Kelas `Transaksi` (Business Logic)
Kelas ini menangani proses kalkulasi transaksi, mencakup perhitungan diskon berdasarkan merk motor dan metode pembayaran, serta menghitung total yang harus dibayar.

**Cuplikan Kode: Logika Perhitungan Diskon**
```java
class Transaksi {
    private String namaPembeli;
    private Motor motor;
    private String pembayaran;

    // constructor & getters...

    public double hitungDiskon() {
        double harga = motor.getHarga();
        if (pembayaran.equalsIgnoreCase("TUNAI")) {
            return 0.20 * harga; // Diskon tunai: 20%
        } else if (pembayaran.equalsIgnoreCase("KREDIT")) {
            String merk = motor.getMerk();
            double pct = 0;
            if (merk.equalsIgnoreCase("HONDA")) pct = 0.08;
            else if (merk.equalsIgnoreCase("YAMAHA")) pct = 0.07;
            else if (merk.equalsIgnoreCase("SUZUKI")) pct = 0.06;
            else if (merk.equalsIgnoreCase("KAWASAKI")) pct = 0.05;
            return pct * harga;
        }
        return 0;
    }

    public double hitungTotalBayar() {
        return motor.getHarga() - hitungDiskon();
    }
}
```

### 3. Kelas `AplikasiPenjualanMotor` (Main GUI)
Ini adalah kelas utama (turunan dari `JFrame`) yang mengatur seluruh tampilan antarmuka dan *event handling*. Antarmuka dirancang dengan paradigma responsif dengan membagi layar menjadi sisi kiri (Formulir) dan kanan (Pratinjau Struk).

**Cuplikan Kode: Event Listener & Update Real-time**
```java
// Event Listener untuk tombol Hitung
btnHitung.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        hitungTransaksi();
    }
});

// Auto-update harga di form ketika Merk atau Jenis diubah
cbMerk.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        updateHargaField();
    }
});
```

**Cuplikan Kode: Integrasi UI Modern dengan FlatLaf (Menggunakan Reflection)**
```java
public static void main(String[] args) {
    // Memuat FlatLaf Look and Feel secara dinamis untuk menghindari error kompilasi jika library tidak ada
    try {
        Class<?> clazz = Class.forName("com.formdev.flatlaf.FlatIntelliJLaf");
        java.lang.reflect.Method setupMethod = clazz.getMethod("setup");
        setupMethod.invoke(null);
    } catch (Exception ex) {
        System.err.println("Gagal memuat FlatLaf Look and Feel. Menggunakan default sistem.");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    SwingUtilities.invokeLater(() -> new AplikasiPenjualanMotor().setVisible(true));
}
```

## Keunggulan Kode
1. **Pemisahan Peran (Separation of Concerns):** Logika kalkulasi harga dan transaksi dipisah dari kelas antarmuka grafis, membuat kode lebih rapi dan dapat di-*maintain*.
2. **Fleksibilitas (Dynamic UI Loading):** Penggunaan Reflection untuk memanggil `FlatLaf` membuat program tidak langsung *crash* jika library eksternal hilang, dan dapat beralih ke Look and Feel bawaan sistem secara otomatis.
3. **Desain Antarmuka Estetis:** Implementasi desain Struk (*Receipt Card*) dengan `GridBagLayout` memberikan tampilan seperti program POS modern.
