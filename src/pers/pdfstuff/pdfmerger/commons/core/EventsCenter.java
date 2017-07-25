package pers.pdfstuff.pdfmerger.commons.core;

import java.util.logging.Logger;

import com.google.common.eventbus.EventBus;

import pers.pdfstuff.pdfmerger.commons.events.BaseEvent;

/**
 * Handles logging and dispatching of events posted
 * @author Naren
 *
 */
public class EventsCenter {
   
    private static final Logger logger = LogsCenter.getLogger(EventsCenter.class);
    private static EventsCenter instance;
    private final EventBus eventBus;
    
    public EventsCenter() {
        eventBus = new EventBus();
    }
    
    public static EventsCenter getInstance() {
        if (instance == null) {
            instance = new EventsCenter();
        }
        return instance;
    }
    
    public void registerEventHandler(Object o) {
        eventBus.register(o);
    }
    
    public void post(BaseEvent event) {
        logger.info("[Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        eventBus.post(event);
    }
}
