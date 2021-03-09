package ace.titan.quest.core.events;

import org.springframework.context.ApplicationEvent;

public class DatabaseInitiatedEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DatabaseInitiatedEvent(Object source) {
        super(source);
    }
}
