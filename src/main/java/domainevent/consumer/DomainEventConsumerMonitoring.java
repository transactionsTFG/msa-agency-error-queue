package domainevent.consumer;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

import com.google.gson.Gson;

import domainevent.services.MonitoringServices;
import msa.commons.consts.JMSQueueNames;
import msa.commons.event.Event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@MessageDriven(mappedName = JMSQueueNames.AGENCY_MONITORING_ERROR_QUEUE)
public class DomainEventConsumerMonitoring implements MessageListener {
    private Gson gson;
    private MonitoringServices monitoringServices;
    private static final Logger LOGGER = LogManager.getLogger(DomainEventConsumerMonitoring.class);

    @Override
    public void onMessage(Message msg) {
         try {
            if(msg instanceof TextMessage m) {
                String destination = "";
                if(m.getJMSDestination() instanceof Queue queue) destination = queue.getQueueName();
                    else destination = m.getJMSDestination().toString();
                Event event = this.gson.fromJson(m.getText(), Event.class);
                LOGGER.warn("Monitoreando en Cola {}, Evento Id: {}, Mensaje: {}", JMSQueueNames.AGENCY_MONITORING_ERROR_QUEUE, event.getEventId(), event.getData());
                this.monitoringServices.saveError(destination, event.getEventId(), m.getText());
            }
        } catch (Exception e) {
            LOGGER.error("Error al recibir el mensaje: {}", e.getMessage());
        }
    }

    @Inject
    public void setGson(Gson gson) { this.gson = gson; }
    @Inject
    public void setMonitoringServices(MonitoringServices monitoringServices) { this.monitoringServices = monitoringServices; }
}