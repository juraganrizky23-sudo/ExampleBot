import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    // =========================
    // KONFIGURASI OWNER
    // =========================

    private static final long OWNER_ID = 6699755661L;

    // Nama bot Telegram
    private static final String BOT_USERNAME = "BOT_NAME_HERE";

    // Token dari @BotFather
    private static final String BOT_TOKEN = "BOT_TOKEN_HERE";

    // Jeda antar target (milidetik)
    private static final long DELAY = 5000;

    // Daftar target
    private final List<String> targets = new ArrayList<String>();

    // Status sebar
    private volatile boolean spreading = false;


    // =========================
    // IDENTITAS BOT
    // =========================

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    // =========================
    // PESAN MASUK
    // =========================

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();

        if (message == null || !message.hasText()) {
            return;
        }

        // Hanya owner yang boleh mengontrol bot
        if (message.getFrom() == null ||
                message.getFrom().getId() != OWNER_ID) {

            SendMsg(
                    message,
                    "❌ Lu bukan owner bot."
            );

            return;
        }

        String text = message.getText().trim();

        if (text.equals("/start")) {

            showMenu(message);

        } else if (text.equals("/help")) {

            showHelp(message);

        } else if (text.startsWith("/add ")) {

            addTarget(message, text.substring(5).trim());

        } else if (text.equals("/list")) {

            listTargets(message);

        } else if (text.startsWith("/remove ")) {

            removeTarget(message, text.substring(8).trim());

        } else if (text.equals("/stop")) {

            stopSpread(message);

        } else if (text.startsWith("/sebar ")) {

            startSpread(
                    message,
                    text.substring(7).trim()
            );

        } else {

            SendMsg(
                    message,
                    "❓ Perintah tidak dikenal.\n\n" +
                    "Ketik /help untuk melihat perintah."
            );
        }
    }


    // =========================
    // MENU
    // =========================

    private void showMenu(Message message) {

        SendMsg(
                message,
                "🤖 BOT JASEB\n\n" +

                "📢 /sebar <pesan>\n" +
                "➕ /add <chat_id>\n" +
                "📋 /list\n" +
                "➖ /remove <chat_id>\n" +
                "🛑 /stop\n" +
                "❓ /help\n\n" +

                "Owner ID: " + OWNER_ID
        );
    }


    private void showHelp(Message message) {

        SendMsg(
                message,
                "📖 CARA PAKAI\n\n" +

                "1️⃣ Tambah target:\n" +
                "/add -100123456789\n\n" +

                "2️⃣ Lihat target:\n" +
                "/list\n\n" +

                "3️⃣ Sebar pesan:\n" +
                "/sebar Halo semuanya!\n\n" +

                "4️⃣ Hentikan proses:\n" +
                "/stop\n\n" +

                "5️⃣ Hapus target:\n" +
                "/remove -100123456789\n\n" +

                "⚠️ Bot hanya bisa mengirim ke chat " +
                "yang memang memberikan izin kepada bot."
        );
    }


    // =========================
    // TAMBAH TARGET
    // =========================

    private void addTarget(
            Message message,
            String target) {

        if (target.length() == 0) {

            SendMsg(
                    message,
                    "❌ Format salah.\n\n" +
                    "Contoh:\n" +
                    "/add -100123456789"
            );

            return;
        }

        if (targets.contains(target)) {

            SendMsg(
                    message,
                    "⚠️ Target sudah ada."
            );

            return;
        }

        targets.add(target);

        SendMsg(
                message,
                "✅ Target berhasil ditambahkan.\n\n" +
                "Target: " + target +
                "\nTotal target: " + targets.size()
        );
    }


    // =========================
    // LIST TARGET
    // =========================

    private void listTargets(Message message) {

        if (targets.isEmpty()) {

            SendMsg(
                    message,
                    "📭 Belum ada target."
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        result.append("📋 DAFTAR TARGET\n\n");

        for (int i = 0; i < targets.size(); i++) {

            result.append(i + 1)
                    .append(". ")
                    .append(targets.get(i))
                    .append("\n");
        }

        result.append("\nTotal: ")
                .append(targets.size());

        SendMsg(
                message,
                result.toString()
        );
    }


    // =========================
    // HAPUS TARGET
    // =========================

    private void removeTarget(
            Message message,
            String target) {

        if (targets.remove(target)) {

            SendMsg(
                    message,
                    "🗑️ Target berhasil dihapus.\n\n" +
                    target
            );

        } else {

            SendMsg(
                    message,
                    "❌ Target tidak ditemukan."
            );
        }
    }


    // =========================
    // MULAI SEBAR
    // =========================

    private void startSpread(
            Message message,
            String content) {

        if (targets.isEmpty()) {

            SendMsg(
                    message,
                    "❌ Belum ada target.\n\n" +
                    "Tambahkan dengan:\n" +
                    "/add <chat_id>"
            );

            return;
        }

        if (content.length() == 0) {

            SendMsg(
                    message,
                    "❌ Pesan kosong.\n\n" +
                    "Contoh:\n" +
                    "/sebar Halo semuanya!"
            );

            return;
        }

        if (spreading) {

            SendMsg(
                    message,
                    "⚠️ Proses sebar masih berjalan.\n" +
                    "Gunakan /stop untuk menghentikannya."
            );

            return;
        }

        spreading = true;

        final List<String> targetCopy =
                new ArrayList<String>(targets);

        final String finalContent = content;

        SendMsg(
                message,
                "🚀 SEBAR DIMULAI\n\n" +
                "Total target: " +
                targetCopy.size() +
                "\nJeda: 5 detik"
        );


        // Jalankan proses sebar di thread terpisah
        new Thread(new Runnable() {

            @Override
            public void run() {

                int success = 0;
                int failed = 0;

                for (String target : targetCopy) {

                    if (!spreading) {
                        break;
                    }

                    try {

                        SendMessage sendMessage =
                                new SendMessage();

                        sendMessage.setChatId(target);
                        sendMessage.setText(finalContent);

                        execute(sendMessage);

                        success++;

                        System.out.println(
                                "✅ Berhasil: " + target
                        );

                    } catch (TelegramApiException e) {

                        failed++;

                        System.out.println(
                                "❌ Gagal: " + target
                        );

                        e.printStackTrace();
                    }


                    // Jeda antar target
                    if (spreading) {

                        try {

                            Thread.sleep(DELAY);

                        } catch (InterruptedException e) {

                            Thread.currentThread()
                                    .interrupt();

                            break;
                        }
                    }
                }


                boolean stopped =
                        !spreading;

                spreading = false;


                System.out.println(
                        "=========================="
                );

                System.out.println(
                        "SEBAR SELESAI"
                );

                System.out.println(
                        "Berhasil: " + success
                );

                System.out.println(
                        "Gagal: " + failed
                );

                System.out.println(
                        "=========================="
                );
            }

        }).start();
    }


    // =========================
    // STOP SEBAR
    // =========================

    private void stopSpread(Message message) {

        if (!spreading) {

            SendMsg(
                    message,
                    "ℹ️ Tidak ada proses sebar yang berjalan."
            );

            return;
        }

        spreading = false;

        SendMsg(
                message,
                "🛑 Proses sebar dihentikan."
        );
    }


    // =========================
    // KIRIM PESAN
    // =========================

    public void SendMsg(
            Message message,
            String text) {

        SendMessage sendMessage =
                new SendMessage();

        sendMessage.setChatId(
                message.getChatId().toString()
        );

        sendMessage.setText(text);

        try {

            execute(sendMessage);

        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
    }
			}
