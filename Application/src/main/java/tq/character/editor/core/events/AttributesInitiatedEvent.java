package tq.character.editor.core.events;

import org.springframework.context.ApplicationEvent;

public class AttributesInitiatedEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public AttributesInitiatedEvent(Object source) {
        super(source);
    }
}
