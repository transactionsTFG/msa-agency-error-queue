package domainevent.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import domainevent.entity.Monitoring;
import msa.commons.event.EventId;

@Stateless
public class MonitoringServicesImpl implements MonitoringServices {
    private EntityManager entityManager;

    @Override
    public void saveError(String queueDestination, EventId eventId, Object data) {
        Monitoring monitoring = new Monitoring();
        monitoring.setQueueDestination(queueDestination);
        monitoring.setEventId(eventId);
        monitoring.setData(data);
        this.entityManager.persist(monitoring);
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
