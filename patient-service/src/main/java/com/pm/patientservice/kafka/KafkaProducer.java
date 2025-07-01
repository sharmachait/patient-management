package com.pm.patientservice.kafka;

import com.pm.patientservice.repository.PatientEventRepository;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.List;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final PatientEventRepository patientRepository;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate, PatientEventRepository patientRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.patientRepository = patientRepository;
    }

    @Transactional
    @Scheduled(cron = "${kafka.producer.cron.expression}")
    public void sendEvent() {
        List<com.pm.patientservice.model.PatientEvent> patients = patientRepository.findAll();
        for(com.pm.patientservice.model.PatientEvent p: patients){
            PatientEvent event = PatientEvent.newBuilder()
                    .setPatientId(p.getPatientId().toString())
                    .setName(p.getName())
                    .setEmail(p.getEmail())
                    .setEventType(p.getEventType())
                    .build();
            try{
                kafkaTemplate.send("patient", event.toByteArray()).get();
                patientRepository.delete(p);

            } catch (Exception e) {
                log.error("Error sending PatientCreated event: {}", event);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
