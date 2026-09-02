import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static final long OWNER_ID = 6699755661L;

    private final List<String> targets = new ArrayList<>();
    private volatile boolean spreading = false;

    @Override
    public String getBotUsername() {
        return "bnkyyjseb_bot";
    }

    @Override
    public String getBotToken() {
        String token = System.getenv("BOT_TOKEN");

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("BOT_TOKEN belum diset!");
        }

        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();

        if (!message.hasText()) {
            return;
        }

        long userId = message.getFrom().getId();
        String text = message.getText().trim();
        long chatId = message.getChatId();

        if (text.equals("/start") || text.equals("/help")) {
            send(chatId,
                    "🤖 JASEB BOT\n\n" +
                    "/id - lihat ID chat\n" +
                    "/add - tambah chat ini sebagai target\n" +
                    "/add CHAT_ID - tambah target dengan ID\n" +
                    "/list - lihat target\n" +
                    "/remove CHAT_ID - hapus target\n" +
                    "/sebar PESAN - mulai sebar\n" +
                    "/stop - hentikan sebar");
            return;
        }

        if (userId != OWNER_ID) {
            return;
        }

        // Lihat ID chat tempat command dikirim
        if (text.equals("/id")) {
            send(chatId, "🆔 Chat ID:\n" + chatId);
            return;
        }

        // /add tanpa ID = otomatis tambah chat tempat command dikirim
        if (text.equals("/add")) {
            String target = String.valueOf(chatId);

            if (!targets.contains(target)) {
                targets.add(target);
                send(chatId,
                        "✅ Chat ini ditambahkan sebagai target.\n\n" +
                        "🆔 ID: " + target);
            } else {
                send(chatId, "⚠️ Chat ini sudah ada di target.");
            }
            return;
        }

        // /add CHAT_ID = tambah ID secara manual
        if (text.startsWith("/add ")) {
            String target = text.substring(5).trim();

            if (target.isEmpty()) {
                send(chatId, "❌ Masukkan CHAT_ID target.");
                return;
            }

            if (!targets.contains(target)) {
                targets.add(target);
                send(chatId, "✅ Target ditambahkan:\n" + target);
            } else {
                send(chatId, "⚠️ Target sudah ada.");
            }
            return;
        }

        if (text.equals("/list")) {
            if (targets.isEmpty()) {
                send(chatId, "📭 Belum ada target.");
            } else {
                StringBuilder result = new StringBuilder("📋 TARGET:\n\n");

                for (int i = 0; i < targets.size(); i++) {
                    result.append(i + 1)
                            .append(". ")
                            .append(targets.get(i))
                            .append("\n");
                }

                send(chatId, result.toString());
            }
            return;
        }

        if (text.startsWith("/remove ")) {
            String target = text.substring(8).trim();

            if (targets.remove(target)) {
                send(chatId, "✅ Target dihapus:\n" + target);
            } else {
                send(chatId, "❌ Target tidak ditemukan.");
            }
            return;
        }

        if (text.startsWith("/sebar ")) {
            String broadcast = text.substring(7).trim();

            if (broadcast.isEmpty()) {
                send(chatId, "❌ Pesan tidak boleh kosong.");
                return;
            }

            if (targets.isEmpty()) {
                send(chatId, "❌ Belum ada target.");
                return;
            }

            if (spreading) {
                send(chatId, "⚠️ Jaseb masih berjalan.");
                return;
            }

            spreading = true;

            new Thread(() -> {
                int success = 0;
                int failed = 0;

                for (String target : new ArrayList<>(targets)) {
                    if (!spreading) {
                        break;
                    }

                    try {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(target);
                        sendMessage.setText(broadcast);

                        execute(sendMessage);
                        success++;

                        Thread.sleep(5000);

                    } catch (TelegramApiException e) {
                        failed++;
                        System.out.println(
                                "Gagal kirim ke " + target + ": " + e.getMessage()
                        );

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                spreading = false;

                send(chatId,
                        "🏁 SELESAI\n\n" +
                        "✅ Berhasil: " + success + "\n" +
                        "❌ Gagal: " + failed);
            }).start();

            send(chatId,
                    "🚀 Jaseb dimulai ke " + targets.size() + " target.");
            return;
        }

        if (text.equals("/stop")) {
            if (spreading) {
                spreading = false;
                send(chatId, "🛑 Jaseb dihentikan.");
            } else {
                send(chatId, "ℹ️ Jaseb sedang tidak berjalan.");
            }
        }
    }

    private void send(long chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(text);

            execute(message);

        } catch (TelegramApiException e) {
            System.out.println(
                    "Gagal mengirim pesan: " + e.getMessage()
            );
        }
    }
}

