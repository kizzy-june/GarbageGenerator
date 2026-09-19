package de.kizzyjune;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

public final class GarbageGeneratorMain implements KeyListener {
    private final JTextArea textArea = new JTextArea("");
    @SuppressWarnings({ "JavaPrintToLogpoint", "SpellCheckingInspection" })
    void main(final String[] args) throws IOException, FontFormatException, InterruptedException {
        int argsIndex = 0;
        boolean dbg = false;
        while (argsIndex != args.length) {
            if (args[argsIndex].equals("debug")) {
                dbg = true;
                break;
            }
            argsIndex++;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> IO.println("Shutting down...")));
        final boolean isDevBuild = true;
        IO.println("GarbageGenerator version 1.0 is starting...");
        if (isDevBuild) IO.println("This is a dev version, you might encounter bugs or crashes");
        final StringBuilder sb = new StringBuilder();
        sb.append("Garbage text generator :3");
        if (isDevBuild) sb.append(" - dev version");
        final JFrame window = new JFrame(sb.toString());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1920 / 2, 1080 / 2);
        window.setLocationRelativeTo(null);
        textArea.setLineWrap(true);
        window.add(textArea);
        window.setResizable(false);
        window.setVisible(true);
        textArea.setVisible(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#FF505F"));
        Thread.sleep(1000);
        setFont(textArea, "F25 Bank Printer", true);
        setTextTypewriterEffect("GarbageGenerator.start();", textArea);
        Thread.sleep(2000);
        clearText(textArea);
        Thread.sleep(2000);
        setFont(textArea, "Halogen", false);
        final char[] chars = {
                'ý', 'þ', 'ÿ', '¸', '¹', 'º', '¿', 'ü',
                'À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç',
                'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï',
                'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', '×',
                'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'Þ', 'ß',
                'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç',
                'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï',
                'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', '÷',
                'ø', 'ù', 'ú', 'û'
        };
        short current = 0;
        while (current != 1124) {
            char rndmChar = chars[ThreadLocalRandom.current().nextInt(0, chars.length)];
            textArea.append(String.valueOf(rndmChar));
            Thread.sleep(5);
            current++;
        }
        Thread.sleep(2000);
        debugThrowable("none", textArea);
        setFont(textArea, "F25 Bank Printer", true);
        clearText(textArea);
        setTextTypewriterEffect("GarbageGenerator.exit();", textArea);
        Thread.sleep(1000);
        clearText(textArea);
        Thread.sleep(1000);
        byte counter = 0;
        while (counter != 11) {
            clearText(textArea);
            Thread.sleep(15);
            textArea.setText("_");
            Thread.sleep(15);
            counter++;
        }
        if (!dbg) System.exit(0);
        IO.println("Debug mode activated");
        textArea.requestFocusInWindow();
        textArea.addKeyListener(this);
    }

    private static void setTextTypewriterEffect(final String text, final JTextArea textArea) {
        final char[] chars = text.toCharArray();
        final short length = (short) chars.length;
        short index = 0;
        while (index != length) {
            textArea.append(String.valueOf(chars[index]));
            try {
                Thread.sleep(15);
            } catch (final InterruptedException ignored) {
            }
            index++;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private static void setFont(final JTextArea textArea, final String fontName,
            final boolean replaceSpaceWithUnderscore) throws IOException, FontFormatException {
        final String alternative = (replaceSpaceWithUnderscore) ? fontName.replace(' ', '_') : fontName;
        InputStream is = GarbageGeneratorMain.class.getResourceAsStream("resources/fonts/" + alternative + ".otf");
        if (is == null) {
            handleThrowable(new FileNotFoundException("Requested font " + alternative + " not found"), "", textArea,
                    true);
        }
        Font font1 = Font.createFont(Font.TRUETYPE_FONT, is);
        Font fontOut = font1.deriveFont(28F);
        textArea.setFont(fontOut);
    }

    private static void clearText(final JTextArea textArea) {
        textArea.setText("");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void handleThrowable(final Throwable t, final String msg, final JTextArea textArea,
            final boolean fontError) {
        clearText(textArea);
        final boolean isError = (t instanceof Error);
        final boolean isException = (t instanceof Exception);
        final boolean justThrowable = (!isError && !isException);
        textArea.setBackground(Color.RED);
        final StringBuilder sb = new StringBuilder();
        sb.append("Throwable thrown!");
        sb.append("\n");
        if (fontError) {
            textArea.setFont(null);
            Font current = textArea.getFont();
            Font changeSize = current.deriveFont(32F);
            textArea.setFont(changeSize);
        }
        if (!msg.isEmpty()) {
            sb.append(msg);
            sb.append("\n");
        } else {
            sb.append(t.getMessage());
            sb.append("\n");
        }
        if (!justThrowable) {
            final String type = (isError) ? "error" : "exception";
            sb.append("Type: ");
            sb.append(type);
            sb.append("\n");
        }
        if (justThrowable) {
            sb.append("Type: throwable");
            sb.append("\n");
        }
        sb.append("See console for stacktrace");
        setTextTypewriterEffect(sb.toString(), textArea);
        t.printStackTrace();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ignored) {
        }
        clearText(textArea);
        setTextTypewriterEffect("There's no way you waited this long", textArea);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ignored) {
        }
        IO.println("ok bruh");
        System.exit(1);
    }

    @SuppressWarnings("SameParameterValue")
    private static void debugThrowable(final String throwableName, final JTextArea textArea) {
        switch (throwableName) {
            case "error":
                handleThrowable(new Error("Test error"), "Test error", textArea, false);
                break;
            case "exception":
                handleThrowable(new RuntimeException("Test exception"), "Test exception", textArea, false);
                break;
            case "throwable":
                handleThrowable(new Throwable("Test throwable"), "Test throwable", textArea, false);
                break;
            case "none":
                return;
            default:
                handleThrowable(new IllegalArgumentException(
                        "Argument for debugThrowable has to be error, exception, throwable or none,\ninstead was "
                                + throwableName),
                        "", textArea, false);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        new Thread(() -> { if (e.getKeyCode() == KeyEvent.VK_F1) debugThrowable("throwable",textArea);
        else if (e.getKeyCode() == KeyEvent.VK_F2) debugThrowable("exception",textArea);
        else if (e.getKeyCode() == KeyEvent.VK_F3) debugThrowable("error",textArea);
        else if (e.getKeyCode() == KeyEvent.VK_F4) debugThrowable("invalid",textArea);
        else if (e.getKeyCode() == KeyEvent.VK_F5) setTextAreaEditable();
    }).start();
    }

    @Override
    public void keyReleased(final KeyEvent e) {

    }
    private void setTextAreaEditable() {
        if (!textArea.isEditable()) textArea.setEditable(true);
        else textArea.setEditable(false);
    }
}