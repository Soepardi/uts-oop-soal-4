import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



class Motor {
    private String merk;
    private String jenis;
    private double harga;

    public Motor(String merk, String jenis) {
        this.merk = merk;
        this.jenis = jenis;
        this.harga = hitungHarga();
    }

    public String getMerk() {
        return merk;
    }

    public String getJenis() {
        return jenis;
    }

    private double hitungHarga() {
        if (merk.equalsIgnoreCase("HONDA")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 12000000;
            if (jenis.equalsIgnoreCase("SPORT")) return 32000000;
            if (jenis.equalsIgnoreCase("MATIK")) return 15000000;
        } else if (merk.equalsIgnoreCase("YAMAHA")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 13500000;
            if (jenis.equalsIgnoreCase("SPORT")) return 30000000;
            if (jenis.equalsIgnoreCase("MATIK")) return 14000000;
        } else if (merk.equalsIgnoreCase("SUZUKI")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 14000000;
            if (jenis.equalsIgnoreCase("SPORT")) return 33000000;
            if (jenis.equalsIgnoreCase("MATIK")) return 13000000;
        } else if (merk.equalsIgnoreCase("KAWASAKI")) {
            if (jenis.equalsIgnoreCase("BEBEK")) return 12500000;
            if (jenis.equalsIgnoreCase("SPORT")) return 28000000;
            if (jenis.equalsIgnoreCase("MATIK")) return 13500000;
        }
        return 0;
    }

    public double getHarga() {
        return harga;
    }
}

class Transaksi {
    private String namaPembeli;
    private Motor motor;
    private String pembayaran;

    public Transaksi(String namaPembeli, Motor motor, String pembayaran) {
        this.namaPembeli = namaPembeli;
        this.motor = motor;
        this.pembayaran = pembayaran;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public double hitungDiskon() {
        double harga = motor.getHarga();
        if (pembayaran.equalsIgnoreCase("TUNAI")) {
            return 0.20 * harga; // Cash discount: 20%
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

// --- Main GUI Class ---

public class AplikasiPenjualanMotor extends JFrame {
    private JTextField txtNama;
    private JComboBox<String> cbMerk;
    private JRadioButton rbBebek, rbSport, rbMatik;
    private ButtonGroup bgJenis;
    private JTextField txtHarga;
    private JComboBox<String> cbPembayaran;
    private JButton btnHitung;
    private JTextField txtDiskon;
    private JTextField txtTotalBayar;
    private JButton btnHitungLagi, btnSelesai;
    private JButton btnBayar;

    // Modern Graphical Receipt Labels
    private JLabel lblReceiptNo;
    private JLabel lblReceiptDate;
    private JLabel lblReceiptPelanggan;
    private JLabel lblReceiptItem;
    private JLabel lblReceiptHarga;
    private JLabel lblReceiptBayar;
    private JLabel lblReceiptDiskon;
    private JLabel lblReceiptTotal;

    public AplikasiPenjualanMotor() {
        // Setup Window JFrame
        setTitle("APLIKASI PENJUALAN MOTOR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(1280, 720));

        // Load logo as Window Icon
        try {
            File logoFile = new File("logo.png");
            if (logoFile.exists()) {
                setIconImage(new ImageIcon("logo.png").getImage());
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat icon jendela: " + e.getMessage());
        }

        // Set Main Content Pane with BorderLayout and Padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 10)); // Reduced vertical gap from 25 to 10
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20)); // Adjusted padding
        setContentPane(mainPanel);

        // --- 1. HEADER PANEL (Dealer Style - Scaled for 1280x720) ---
        JPanel headerPanel = new JPanel(new BorderLayout(25, 5));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(46, 117, 89)), // Elegant green line
                BorderFactory.createEmptyBorder(0, 0, 20, 0)
        ));

        // Load logo banner image proportionally
        ImageIcon logoIcon = loadLogoProportionally("logo.png", 180, 90);
        if (logoIcon != null) {
            JLabel lblLogo = new JLabel(logoIcon);
            headerPanel.add(lblLogo, BorderLayout.WEST);
        }

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel lblTitle = new JLabel("UNSIA DEALER MOTOR");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26)); // Larger premium font
        lblTitle.setForeground(new Color(33, 37, 41));
        
        JLabel lblSubtitle = new JLabel("dibuat oleh : Supardi Akhiyat | 230101010026");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitle.setForeground(Color.GRAY);

        titlePanel.add(lblTitle);
        titlePanel.add(lblSubtitle);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. CENTER PANEL (Side-by-Side Widescreen Dashboard Layout) ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 0)); // 30px gap between columns

        // 2a. Left Panel: Input Data Form
        JPanel panelDataMotor = new JPanel(new GridBagLayout());
        panelDataMotor.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "DATA PEMBELIAN",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14) // Larger border font
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 20, 12, 20); // Spacious vertical insets
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        // Row 0: Nama Pembeli
        c.gridx = 0; c.gridy = 0; c.weightx = 0.3;
        JLabel lblNama = new JLabel("Nama Pembeli");
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblNama, c);

        c.gridx = 1; c.weightx = 0.7;
        txtNama = new JTextField();
        txtNama.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNama.putClientProperty("JTextField.placeholderText", "Masukkan nama lengkap pembeli...");
        panelDataMotor.add(txtNama, c);

        // Row 1: Merk Motor
        c.gridx = 0; c.gridy = 1; c.weightx = 0.3;
        JLabel lblMerk = new JLabel("Merk Motor");
        lblMerk.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblMerk, c);

        c.gridx = 1; c.weightx = 0.7;
        String[] merkList = {"HONDA", "YAMAHA", "SUZUKI", "KAWASAKI"};
        cbMerk = new JComboBox<>(merkList);
        cbMerk.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelDataMotor.add(cbMerk, c);

        // Row 2: Jenis Motor
        c.gridx = 0; c.gridy = 2; c.weightx = 0.3;
        JLabel lblJenis = new JLabel("Jenis Motor");
        lblJenis.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblJenis, c);

        c.gridx = 1; c.weightx = 0.7;
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbBebek = new JRadioButton("BEBEK");
        rbSport = new JRadioButton("SPORT");
        rbMatik = new JRadioButton("MATIK");
        rbBebek.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbSport.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbMatik.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbBebek.setSelected(true);
        bgJenis = new ButtonGroup();
        bgJenis.add(rbBebek);
        bgJenis.add(rbSport);
        bgJenis.add(rbMatik);
        panelRadio.add(rbBebek);
        panelRadio.add(Box.createHorizontalStrut(20));
        panelRadio.add(rbSport);
        panelRadio.add(Box.createHorizontalStrut(20));
        panelRadio.add(rbMatik);
        panelDataMotor.add(panelRadio, c);

        // Row 3: Harga Motor
        c.gridx = 0; c.gridy = 3; c.weightx = 0.3;
        JLabel lblHarga = new JLabel("Harga Motor");
        lblHarga.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblHarga, c);

        c.gridx = 1; c.weightx = 0.7;
        txtHarga = new JTextField();
        txtHarga.setEditable(false);
        txtHarga.setHorizontalAlignment(JTextField.RIGHT);
        txtHarga.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelDataMotor.add(txtHarga, c);

        // Row 4: Pembayaran
        c.gridx = 0; c.gridy = 4; c.weightx = 0.3;
        JLabel lblPembayaran = new JLabel("Metode Pembayaran");
        lblPembayaran.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblPembayaran, c);

        c.gridx = 1; c.weightx = 0.7;
        String[] bayarList = {"TUNAI", "KREDIT"};
        cbPembayaran = new JComboBox<>(bayarList);
        cbPembayaran.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelDataMotor.add(cbPembayaran, c);

        // Row 5: Diskon
        c.gridx = 0; c.gridy = 5; c.weightx = 0.3;
        JLabel lblDiskon = new JLabel("Diskon");
        lblDiskon.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblDiskon, c);

        c.gridx = 1; c.weightx = 0.7;
        txtDiskon = new JTextField();
        txtDiskon.setEditable(false);
        txtDiskon.setHorizontalAlignment(JTextField.RIGHT);
        txtDiskon.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelDataMotor.add(txtDiskon, c);

        // Row 6: Total Bayar
        c.gridx = 0; c.gridy = 6; c.weightx = 0.3;
        JLabel lblTotalBayar = new JLabel("Total Bayar");
        lblTotalBayar.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelDataMotor.add(lblTotalBayar, c);

        c.gridx = 1; c.weightx = 0.7;
        txtTotalBayar = new JTextField();
        txtTotalBayar.setEditable(false);
        txtTotalBayar.setHorizontalAlignment(JTextField.RIGHT);
        txtTotalBayar.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelDataMotor.add(txtTotalBayar, c);

        // Row 7: Action Buttons (Hitung & Bayar)
        c.gridx = 0; c.gridy = 7; c.gridwidth = 2; c.weightx = 1.0;
        c.insets = new Insets(25, 20, 15, 20);
        JPanel panelButtons = new JPanel(new GridLayout(1, 2, 10, 0));
        
        btnHitung = new JButton("HITUNG");
        btnHitung.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnHitung.setPreferredSize(new Dimension(0, 40));
        btnHitung.putClientProperty("JButton.buttonType", "accent");
        
        btnBayar = new JButton("BAYAR");
        btnBayar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBayar.setPreferredSize(new Dimension(0, 40));
        btnBayar.putClientProperty("JButton.buttonType", "accent");

        panelButtons.add(btnHitung);
        panelButtons.add(btnBayar);
        panelDataMotor.add(panelButtons, c);

        // Row 8: Vertical spacer to align components to top
        c.gridx = 0; c.gridy = 8; c.gridwidth = 2; c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panelDataMotor.add(Box.createVerticalGlue(), c);

        centerPanel.add(panelDataMotor);

        // 2b. Right Panel: Modern Graphical Receipt Card Container
        JPanel receiptContainer = new JPanel(new GridBagLayout());
        receiptContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "STRUK PEMBELIAN",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14)
        ));
        receiptContainer.setBackground(new Color(245, 247, 250)); // Soft background layout

        // Create the paper receipt card panel
        JPanel receiptCard = new JPanel(new GridBagLayout());
        receiptCard.setBackground(Color.WHITE);
        receiptCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 235), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints rc = new GridBagConstraints();
        rc.fill = GridBagConstraints.HORIZONTAL;
        rc.weightx = 1.0;
        rc.gridx = 0;
        int row = 0;

        // Store Header branding
        JLabel lblStoreName = new JLabel("UNSIA DEALER MOTOR", SwingConstants.CENTER);
        lblStoreName.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblStoreName.setForeground(new Color(33, 37, 41));
        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 4, 0);
        receiptCard.add(lblStoreName, rc);

        JLabel lblStoreAddress = new JLabel("Jl. R.M Harsono No.1 RT09/04 Ragunan, Jakarta, Indonesia", SwingConstants.CENTER);
        lblStoreAddress.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStoreAddress.setForeground(Color.GRAY);
        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 2, 0);
        receiptCard.add(lblStoreAddress, rc);

        JLabel lblStoreTelp = new JLabel("Telp: 0821-5139-4583 | Supardi Akhiyat", SwingConstants.CENTER);
        lblStoreTelp.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStoreTelp.setForeground(Color.GRAY);
        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 10, 0);
        receiptCard.add(lblStoreTelp, rc);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(createSeparator(), rc);

        // Transaction Meta Table
        JPanel metaPanel = new JPanel(new GridBagLayout());
        metaPanel.setBackground(Color.WHITE);
        GridBagConstraints mc = new GridBagConstraints();
        mc.fill = GridBagConstraints.HORIZONTAL;
        mc.anchor = GridBagConstraints.WEST;
        mc.insets = new Insets(2, 0, 2, 0);

        mc.gridx = 0; mc.gridy = 0; mc.weightx = 0.4;
        JLabel l1 = new JLabel("No. Transaksi");
        l1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l1.setForeground(Color.GRAY);
        metaPanel.add(l1, mc);

        mc.gridx = 1; mc.weightx = 0.6;
        lblReceiptNo = new JLabel("-");
        lblReceiptNo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblReceiptNo.setHorizontalAlignment(SwingConstants.RIGHT);
        metaPanel.add(lblReceiptNo, mc);

        mc.gridx = 0; mc.gridy = 1; mc.weightx = 0.4;
        JLabel l2 = new JLabel("Tanggal");
        l2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l2.setForeground(Color.GRAY);
        metaPanel.add(l2, mc);

        mc.gridx = 1; mc.weightx = 0.6;
        lblReceiptDate = new JLabel("-");
        lblReceiptDate.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblReceiptDate.setHorizontalAlignment(SwingConstants.RIGHT);
        metaPanel.add(lblReceiptDate, mc);

        mc.gridx = 0; mc.gridy = 2; mc.weightx = 0.4;
        JLabel l3 = new JLabel("Nama Pelanggan");
        l3.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l3.setForeground(Color.GRAY);
        metaPanel.add(l3, mc);

        mc.gridx = 1; mc.weightx = 0.6;
        lblReceiptPelanggan = new JLabel("-");
        lblReceiptPelanggan.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblReceiptPelanggan.setHorizontalAlignment(SwingConstants.RIGHT);
        metaPanel.add(lblReceiptPelanggan, mc);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(metaPanel, rc);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(createSeparator(), rc);

        // Product Details Section Card
        JPanel itemBox = new JPanel(new BorderLayout(10, 5));
        itemBox.setBackground(new Color(248, 249, 250));
        itemBox.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        lblReceiptItem = new JLabel("Pilih kendaraan...");
        lblReceiptItem.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblReceiptItem.setForeground(new Color(33, 37, 41));
        
        JLabel lblQty = new JLabel("1 Unit Kendaraan Baru");
        lblQty.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblQty.setForeground(Color.GRAY);

        itemBox.add(lblReceiptItem, BorderLayout.CENTER);
        itemBox.add(lblQty, BorderLayout.SOUTH);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(itemBox, rc);

        // Pricing details
        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setBackground(Color.WHITE);
        GridBagConstraints pc = new GridBagConstraints();
        pc.fill = GridBagConstraints.HORIZONTAL;
        pc.anchor = GridBagConstraints.WEST;
        pc.insets = new Insets(3, 0, 3, 0);

        pc.gridx = 0; pc.gridy = 0; pc.weightx = 0.4;
        JLabel lp1 = new JLabel("Harga Kendaraan");
        lp1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lp1.setForeground(Color.GRAY);
        pricePanel.add(lp1, pc);

        pc.gridx = 1; pc.weightx = 0.6;
        lblReceiptHarga = new JLabel("Rp 0");
        lblReceiptHarga.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblReceiptHarga.setHorizontalAlignment(SwingConstants.RIGHT);
        pricePanel.add(lblReceiptHarga, pc);

        pc.gridx = 0; pc.gridy = 1; pc.weightx = 0.4;
        JLabel lp2 = new JLabel("Cara Bayar");
        lp2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lp2.setForeground(Color.GRAY);
        pricePanel.add(lp2, pc);

        pc.gridx = 1; pc.weightx = 0.6;
        lblReceiptBayar = new JLabel("-");
        lblReceiptBayar.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblReceiptBayar.setHorizontalAlignment(SwingConstants.RIGHT);
        pricePanel.add(lblReceiptBayar, pc);

        pc.gridx = 0; pc.gridy = 2; pc.weightx = 0.4;
        JLabel lp3 = new JLabel("Diskon");
        lp3.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lp3.setForeground(Color.GRAY);
        pricePanel.add(lp3, pc);

        pc.gridx = 1; pc.weightx = 0.6;
        lblReceiptDiskon = new JLabel("Rp 0");
        lblReceiptDiskon.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblReceiptDiskon.setForeground(new Color(220, 53, 69)); // Elegant red color
        lblReceiptDiskon.setHorizontalAlignment(SwingConstants.RIGHT);
        pricePanel.add(lblReceiptDiskon, pc);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(pricePanel, rc);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 8, 0);
        receiptCard.add(createSeparator(), rc);

        // Total Bill Highlight Box
        JPanel totalBox = new JPanel(new BorderLayout(10, 0));
        totalBox.setBackground(new Color(230, 245, 238)); // Soft green background
        totalBox.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel lblTotalTitle = new JLabel("TOTAL BAYAR");
        lblTotalTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTotalTitle.setForeground(new Color(46, 117, 89));

        lblReceiptTotal = new JLabel("Rp 0");
        lblReceiptTotal.setFont(new Font("SansSerif", Font.BOLD, 18)); // Reduced from 22 to 18 to fit nicely without cutting off
        lblReceiptTotal.setForeground(new Color(46, 117, 89));
        lblReceiptTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        totalBox.add(lblTotalTitle, BorderLayout.WEST);
        totalBox.add(lblReceiptTotal, BorderLayout.EAST);

        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 12, 0);
        receiptCard.add(totalBox, rc);

        // Spacer to push the footer to the bottom of the card
        rc.gridy = row++;
        rc.weighty = 1.0;
        rc.fill = GridBagConstraints.BOTH;
        receiptCard.add(Box.createVerticalGlue(), rc);

        // Footer details
        rc.weighty = 0.0;
        rc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblThanks = new JLabel("Terima Kasih Atas Kunjungan & Pembelian Anda", SwingConstants.CENTER);
        lblThanks.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblThanks.setForeground(Color.GRAY);
        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 4, 0);
        receiptCard.add(lblThanks, rc);

        JLabel lblSafety = new JLabel("Semoga Anda Beli Motor Lagi", SwingConstants.CENTER);
        lblSafety.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblSafety.setForeground(Color.GRAY);
        rc.gridy = row++;
        rc.insets = new Insets(0, 0, 0, 0);
        receiptCard.add(lblSafety, rc);

        // Place the receipt Card inside the centered panel layout
        GridBagConstraints rcWrap = new GridBagConstraints();
        rcWrap.fill = GridBagConstraints.BOTH;
        rcWrap.weightx = 1.0;
        rcWrap.weighty = 1.0;
        rcWrap.insets = new Insets(20, 20, 20, 20);
        receiptContainer.add(receiptCard, rcWrap);

        centerPanel.add(receiptContainer);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- 3. BOTTOM BUTTONS ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        // Removed empty top border to tighten spacing
        
        btnHitungLagi = new JButton("RESET FORM");
        btnHitungLagi.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnHitungLagi.setPreferredSize(new Dimension(140, 35));
        btnHitungLagi.putClientProperty("JButton.buttonType", "roundRect");
        
        btnSelesai = new JButton("KELUAR KASIR");
        btnSelesai.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSelesai.setPreferredSize(new Dimension(140, 35));
        btnSelesai.putClientProperty("JButton.buttonType", "roundRect");

        bottomPanel.add(btnHitungLagi);
        bottomPanel.add(btnSelesai);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Initial Price Load
        updateHargaField();

        // --- Register Event Listeners ---

        // Auto-update price when Merk or Jenis changes
        cbMerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHargaField();
            }
        });

        ActionListener jenisListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHargaField();
            }
        };
        rbBebek.addActionListener(jenisListener);
        rbSport.addActionListener(jenisListener);
        rbMatik.addActionListener(jenisListener);

        // "HITUNG" Action Listener
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungTransaksi();
            }
        });

        // "BAYAR" Action Listener
        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bayarTransaksi();
            }
        });

        // "HITUNG LAGI" Action Listener
        btnHitungLagi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // "SELESAI" Action Listener
        btnSelesai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set to HD resolution
        pack();
        setSize(1280, 720);
        setLocationRelativeTo(null);
    }

    // Proportional Image Resizer Helper to prevent squished logo
    private ImageIcon loadLogoProportionally(String path, int maxTargetWidth, int maxTargetHeight) {
        try {
            File file = new File(path);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(path);
                int originalWidth = icon.getIconWidth();
                int originalHeight = icon.getIconHeight();
                
                if (originalWidth <= 0 || originalHeight <= 0) {
                    return null;
                }
                
                double ratioX = (double) maxTargetWidth / originalWidth;
                double ratioY = (double) maxTargetHeight / originalHeight;
                double ratio = Math.min(ratioX, ratioY);
                
                int targetWidth = (int) (originalWidth * ratio);
                int targetHeight = (int) (originalHeight * ratio);
                
                targetWidth = Math.max(1, targetWidth);
                targetHeight = Math.max(1, targetHeight);
                
                Image img = icon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat logo: " + e.getMessage());
        }
        return null;
    }

    // Helper to format values to Indonesian Rupiah currency
    private String formatRupiah(double value) {
        java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.forLanguageTag("id-ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(value);
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sep.setBackground(new Color(230, 230, 230));
        return sep;
    }

    private String getSelectedJenis() {
        if (rbBebek.isSelected()) return "BEBEK";
        if (rbSport.isSelected()) return "SPORT";
        if (rbMatik.isSelected()) return "MATIK";
        return "";
    }

    private void updateHargaField() {
        String merk = (String) cbMerk.getSelectedItem();
        String jenis = getSelectedJenis();
        if (merk != null && !jenis.isEmpty()) {
            Motor motor = new Motor(merk, jenis);
            txtHarga.setText(formatRupiah(motor.getHarga()));
        }
    }

    private void hitungTransaksi() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pembeli tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String merk = (String) cbMerk.getSelectedItem();
        String jenis = getSelectedJenis();
        String pembayaran = (String) cbPembayaran.getSelectedItem();

        if (merk != null && !jenis.isEmpty() && pembayaran != null) {
            Motor motor = new Motor(merk, jenis);
            Transaksi transaksi = new Transaksi(nama, motor, pembayaran);

            double diskon = transaksi.hitungDiskon();
            double total = transaksi.hitungTotalBayar();

            txtDiskon.setText(formatRupiah(diskon));
            txtTotalBayar.setText(formatRupiah(total));
        }
    }

    private void bayarTransaksi() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pembeli tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String merk = (String) cbMerk.getSelectedItem();
        String jenis = getSelectedJenis();
        String pembayaran = (String) cbPembayaran.getSelectedItem();

        if (merk != null && !jenis.isEmpty() && pembayaran != null) {
            Motor motor = new Motor(merk, jenis);
            Transaksi transaksi = new Transaksi(nama, motor, pembayaran);

            double diskon = transaksi.hitungDiskon();
            double total = transaksi.hitungTotalBayar();

            // Populate text fields in case they weren't calculated yet
            txtDiskon.setText(formatRupiah(diskon));
            txtTotalBayar.setText(formatRupiah(total));

            // Update graphical receipt preview labels
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateStr = sdf.format(new java.util.Date());
            
            lblReceiptNo.setText("TR-" + String.format("%06d", (int)(Math.random() * 1000000)));
            lblReceiptDate.setText(dateStr);
            lblReceiptPelanggan.setText(nama);
            lblReceiptItem.setText(merk + " " + jenis);
            lblReceiptHarga.setText(formatRupiah(motor.getHarga()));
            lblReceiptBayar.setText(pembayaran);
            
            double discPct = 0;
            if (pembayaran.equalsIgnoreCase("TUNAI")) {
                discPct = 20;
            } else {
                if (merk.equalsIgnoreCase("HONDA")) discPct = 8;
                else if (merk.equalsIgnoreCase("YAMAHA")) discPct = 7;
                else if (merk.equalsIgnoreCase("SUZUKI")) discPct = 6;
                else if (merk.equalsIgnoreCase("KAWASAKI")) discPct = 5;
            }
            lblReceiptDiskon.setText("- " + formatRupiah(diskon) + " (" + (int)discPct + "%)");
            lblReceiptTotal.setText(formatRupiah(total));

            // Force layout recalculation and rendering refresh
            revalidate();
            repaint();
        }
    }

    private void resetForm() {
        txtNama.setText("");
        cbMerk.setSelectedIndex(0);
        rbBebek.setSelected(true);
        cbPembayaran.setSelectedIndex(0);
        txtDiskon.setText("");
        txtTotalBayar.setText("");
        updateHargaField();
        
        // Reset graphical receipt labels
        lblReceiptNo.setText("-");
        lblReceiptDate.setText("-");
        lblReceiptPelanggan.setText("-");
        lblReceiptItem.setText("Pilih kendaraan...");
        lblReceiptHarga.setText("Rp 0");
        lblReceiptBayar.setText("-");
        lblReceiptDiskon.setText("Rp 0");
        lblReceiptTotal.setText("Rp 0");

        // Force layout recalculation and rendering refresh
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Load FlatLaf Look and Feel dynamically using Reflection to avoid IDE compilation errors
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

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplikasiPenjualanMotor().setVisible(true);
            }
        });
    }
}
