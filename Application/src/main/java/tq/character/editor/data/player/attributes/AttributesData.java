package tq.character.editor.data.player.attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.IntContent;

@Service
@Transactional // TODO This might not be needed
public class AttributesData implements IAttributesData {
    private static final Logger log = LoggerFactory.getLogger(AttributesData.class);

    @Autowired
    private IDataContentRepository contentRepository;

    private IntContent unspentAttributePoints;

    @EventListener
    public void onDatabaseInitiatedEvent(DatabaseInitiatedEvent event) {
        unspentAttributePoints = contentRepository.findByVariableName("modifierPoints");
        log.info("Fetched attributes");
    }

    @Override
    public Integer getUnspentAttributePoints() {
        return unspentAttributePoints.getDataContent();
    }

    @Override
    public void setUnspentAttributePoints(Integer attributePoints) throws IllegalPlayerDataException {
        if (attributePoints < 0) {
            log.error("Could not set attribute points to {}, attribute points must be a positive amount", attributePoints);
            throw new IllegalPlayerDataException("Attribute points can not be a negative amount");
        }
        unspentAttributePoints.setDataContent(attributePoints);
    }
}
