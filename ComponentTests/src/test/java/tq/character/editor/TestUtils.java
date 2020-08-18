package tq.character.editor;

import java.nio.charset.StandardCharsets;

public class TestUtils {
    public static String createUTF16String(String input) {
        return new String(input.getBytes(StandardCharsets.UTF_16LE));
    }
}
