package tq.character.editor.data.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.UTF16Content;


@Service
@Transactional // TODO This might not be needed
public class PlayerData implements IPlayerData {
    private static final Logger log = LoggerFactory.getLogger(PlayerData.class);
    @Autowired
    private IDataContentRepository contentRepository;
    private UTF16Content myPlayerName;

    @EventListener
    public void onDatabaseInitiatedEvent(DatabaseInitiatedEvent event) {
        myPlayerName = contentRepository.findByVariableName("myPlayerName");
    }

    @Override
    public String getPlayerName() {
        return myPlayerName.getDataContent();
    }

    @Override
    public void setPlayerName(String playerName) {
        myPlayerName.setDataContent(playerName);
    }
}
