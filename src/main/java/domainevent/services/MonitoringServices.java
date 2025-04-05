package domainevent.services;

import msa.commons.event.EventId;

public interface MonitoringServices {
    void saveError(String queueDestination, EventId eventId, Object data);
}
