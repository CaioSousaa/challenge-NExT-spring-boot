package dev.example.NExT_challenge.service;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.insurance.Insurance;
import dev.example.NExT_challenge.domain.insurance.RequestLifePlanDTO;
import dev.example.NExT_challenge.repositories.ClientRepository;
import dev.example.NExT_challenge.repositories.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ClientRepository clientRepository;


    public int riskQuestionsLifePlan(Client c) {
        int risk_counting = 0;
        Client client = this.clientRepository.findById(c.getId()).orElseThrow();

        if (client.getVehicles() == null || client.getHouses() == null || client.getIncome() <= 0) {
            throw new IllegalArgumentException("you do not have rights to plans");
        } else {
            if(client.getAge() > 60) {
                throw new IllegalArgumentException("your age does not allow you to have this plan");
            } else {
                if(client.getAge() < 30) {
                    risk_counting -= 2;
                } else if (client.getAge() >= 30 && client.getAge() < 40){
                    risk_counting -= 1;
                }

                if(client.getIncome() > 200000) {
                    risk_counting += 1;
                }

                if(client.getDependents() > 0){
                    risk_counting += 1;
                }

                if(client.getMaritalStatus() == Client.MaritalStatus.MARRIED) {
                    risk_counting += 1;
                }
            }
        }

        return risk_counting;
    }

    public Insurance createLifePlan(RequestLifePlanDTO data) {
        Client client = this.clientRepository.findById(data.client_id()).orElseThrow();
        Insurance insurance_life = new Insurance();

        int counting = riskQuestionsLifePlan(client);
        String analysis;

        if(counting <= 0) {
            insurance_life.setAnalysis(Insurance.Analysis.ECONOMIC);
            analysis = "ECONOMIC";
        } else if(counting == 1 || counting == 2) {
            insurance_life.setAnalysis(Insurance.Analysis.REGULAR);
            analysis = "REGULAR";
        } else {
            insurance_life.setAnalysis(Insurance.Analysis.RESPONSIBLE);
            analysis = "RESPONSIBLE";
        }

        String textDescription;

        if(analysis.equals("ECONOMIC")) {
            textDescription = "This plan is tailored for clients who prioritize" +
                              "affordability without compromising on essential services.";
        } else if(analysis.equals("REGULAR")) {
            textDescription = "For clients seeking maximum coverage and comprehensive services, the Regular plan is the top choice.";
        } else {
            textDescription = "The Responsible plan offers a balanced option, combining value with a wider range of coverage to suit various needs.";
        }

        insurance_life.setClient(client);
        insurance_life.setType(Insurance.Type.LIFE);
        insurance_life.setObservation(textDescription);
        insurance_life.setRisk(counting);
        insurance_life.setCreated_at(LocalDateTime.now());

        LocalDateTime createdAt = insurance_life.getCreated_at();
        LocalDateTime validateAt = createdAt.plusDays(30);
        Date validateAtDate = Date.from(validateAt.atZone(ZoneId.systemDefault()).toInstant());

        insurance_life.setValidate_at(validateAtDate);

        this.insuranceRepository.save(insurance_life);

        return insurance_life;

    }

}