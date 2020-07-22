package tq.character.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tq.character.editor.code.save.player.HeaderInfo;
import tq.character.editor.code.save.player.PlayerParser;
import tq.character.editor.code.util.Constants;

import java.io.File;

@SpringBootApplication
public class TqCharacterEditor {
    // TODO Fix up the test cases instead of running it as application code
    private static final Logger log = LoggerFactory.getLogger(TqCharacterEditor.class);

    public static void main(String[] args) {
        SpringApplication.run(TqCharacterEditor.class, args);
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected 1 input arguments, got " + args.length);
        }

        // "src/test/resources/_savegame/Player.chr"
        File playerChr = new File(args[0]);
        PlayerParser playerParser = new PlayerParser(playerChr, "savegame");

        testRead(playerParser);
        testWrite();
    }

    public static void testRead(PlayerParser playerParser) {
        try {
            playerParser.readPlayerChr();
            playerParser.fillBuffer();
            playerParser.buildBlocksTable();
            playerParser.prepareForParse();
            playerParser.prepareBufferForRead();
        } catch (Exception e) {
            log.error(Constants.ERROR_MSG_EXCEPTION, e);
        }

        HeaderInfo headerInfo = playerParser.parseHeader();
    }

    public static void testWrite() {

    }

}
