package dev.example.NExT_challenge.service;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.house.House;
import dev.example.NExT_challenge.domain.insurance.*;
import dev.example.NExT_challenge.domain.vehicle.Vehicle;
import dev.example.NExT_challenge.repositories.ClientRepository;
import dev.example.NExT_challenge.repositories.HouseRepository;
import dev.example.NExT_challenge.repositories.InsuranceRepository;
import dev.example.NExT_challenge.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

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



    public int riskQuestionsDisabilityPlan(Client c) {
        int risk_counting_disability = 0;
        Client client = this.clientRepository.findById(c.getId()).orElseThrow();
        List<House> houses = c.getHouses();
        int counting_houses_mortgaged = 0;

        for(int i = 0; i < houses.size(); i++) {
            if(houses.get(i).getOwnership() == House.OwnerShipStatus.MORTGAGED) {
                counting_houses_mortgaged += 1;
            }
        }



        risk_counting_disability += counting_houses_mortgaged;
        if (client.getVehicles() == null || client.getHouses() == null || client.getIncome() <= 0) {
            throw new IllegalArgumentException("you do not have rights to plans");
        } else {
            if(client.getAge() > 60) {
                throw new IllegalArgumentException("your age does not allow you to have this plan");
            } else {
                if(client.getAge() < 30) {
                    risk_counting_disability -= 2;
                } else if (client.getAge() >= 30 && client.getAge() < 40){
                    risk_counting_disability -= 1;
                }

                if(client.getIncome() > 200000) {
                    risk_counting_disability += 1;
                }

                if(client.getDependents() > 0){
                    risk_counting_disability += 1;
                }

                if(client.getMaritalStatus() == Client.MaritalStatus.MARRIED) {
                    risk_counting_disability -= 1;
                }

            }
        }

        return risk_counting_disability;
    }


    public Insurance createDisabilityPlan(RequestDisabilityPlanDTO data) {
        Client client = this.clientRepository.findById(data.client_id()).orElseThrow();
        Insurance insurance_disability = new Insurance();

        int counting = riskQuestionsDisabilityPlan(client);
        String analysis;

        if(counting <= 0) {
            insurance_disability.setAnalysis(Insurance.Analysis.ECONOMIC);
            analysis = "ECONOMIC";
        } else if(counting == 1 || counting == 2) {
            insurance_disability.setAnalysis(Insurance.Analysis.REGULAR);
            analysis = "REGULAR";
        } else {
            insurance_disability.setAnalysis(Insurance.Analysis.RESPONSIBLE);
            analysis = "RESPONSIBLE";
        }

        String description;

        if(analysis.equals("ECONOMIC")) {
            description = "This plan is tailored for clients who prioritize" +
                    "affordability without compromising on essential services.";
        } else if(analysis.equals("REGULAR")) {
            description = "For clients seeking maximum coverage and comprehensive services, the Regular plan is the top choice.";
        } else {
            description = "The Responsible plan offers a balanced option, combining value with a wider range of coverage to suit various needs.";
        }

        insurance_disability.setClient(client);
        insurance_disability.setType(Insurance.Type.DISABILITY);
        insurance_disability.setObservation(description);
        insurance_disability.setRisk(counting);
        insurance_disability.setCreated_at(LocalDateTime.now());

        LocalDateTime createdAt = insurance_disability.getCreated_at();
        LocalDateTime validateAt = createdAt.plusDays(30);
        Date validateAtDate = Date.from(validateAt.atZone(ZoneId.systemDefault()).toInstant());

        insurance_disability.setValidate_at(validateAtDate);

        this.insuranceRepository.save(insurance_disability);

        return insurance_disability;
    }

    public int questionsRiskHousePlan(Client client, House house) {
        int counting = 0;

        if(client.getHouses() == null || client.getVehicles() == null || client.getIncome() <= 0) {
            throw new IllegalArgumentException("you do not have rights to plans");
        } else {
            if(client.getAge() < 30) {
                counting -= 2;
            } else if (client.getAge() >= 30 && client.getAge() < 40){
                counting -= 1;
            }

            if(client.getIncome() > 200000) {
                counting += 1;
            }

            if(house.getOwnership() == House.OwnerShipStatus.MORTGAGED) {
                counting += 1;
            }

        }

        return counting;
    }

    public Insurance createHousePlan(RequestHousePlanDTO data){
        Client client = this.clientRepository.findById(data.client_id()).orElseThrow();
        House house = this.houseRepository.findById(data.house_id()).orElseThrow();
        Insurance insurance = new Insurance();

        int risk_questions = questionsRiskHousePlan(client, house);
        String analysis;


        if(risk_questions <= 0) {
            insurance.setAnalysis(Insurance.Analysis.ECONOMIC);
            analysis = "ECONOMIC";
        } else if(risk_questions == 1 || risk_questions == 2) {
            insurance.setAnalysis(Insurance.Analysis.REGULAR);
            analysis = "REGULAR";
        } else {
            insurance.setAnalysis(Insurance.Analysis.RESPONSIBLE);
            analysis = "RESPONSIBLE";
        }

        String description;

        if(analysis.equals("ECONOMIC")) {
            description = "This plan is tailored for clients who prioritize" +
                    "affordability without compromising on essential services.";
        } else if(analysis.equals("REGULAR")) {
            description = "For clients seeking maximum coverage and comprehensive services, the Regular plan is the top choice.";
        } else {
            description = "The Responsible plan offers a balanced option, combining value with a wider range of coverage to suit various needs.";
        }

        insurance.setClient(client);
        insurance.setObservation(description);
        insurance.setRisk(risk_questions);
        insurance.setType(Insurance.Type.HOME);
        insurance.setCreated_at(LocalDateTime.now());

        LocalDateTime createdAt = insurance.getCreated_at();
        LocalDateTime validateAt = createdAt.plusDays(30);
        Date validateAtDate = Date.from(validateAt.atZone(ZoneId.systemDefault()).toInstant());

        insurance.setValidate_at(validateAtDate);

        this.insuranceRepository.save(insurance);

        return insurance;
    }


    public int questionsRiskHAutoPlan(Client client, Vehicle vehicle) {
        int counting = 0;

        List<Vehicle> vehicles = client.getVehicles();

        for (int i = 0; i < vehicles.size(); i++) {
            int vehicleYear = vehicles.get(i).getYear();
            int currentYear = LocalDate.now().getYear();

            if (currentYear - vehicleYear < 5) {
                counting++;
            }
        }

        if(client.getHouses() == null || client.getVehicles() == null || client.getIncome() <= 0) {
            throw new IllegalArgumentException("you do not have rights to plans");
        } else {
            if(client.getAge() < 30) {
                counting -= 2;
            } else if (client.getAge() >= 30 && client.getAge() < 40){
                counting -= 1;
            }

            if(client.getIncome() > 200000) {
                counting += 1;
            }

        }

        return counting;
    }

    public Insurance createAutoPlan(InsuranceAutoDTO data){
        Client client = this.clientRepository.findById(data.client_id()).orElseThrow();
        Vehicle vehicle = this.vehicleRepository.findById(data.vehicle_id()).orElseThrow();
        Insurance insurance = new Insurance();

        int risk_questions = questionsRiskHAutoPlan(client, vehicle);
        String analysis;


        if(risk_questions <= 0) {
            insurance.setAnalysis(Insurance.Analysis.ECONOMIC);
            analysis = "ECONOMIC";
        } else if(risk_questions == 1 || risk_questions == 2) {
            insurance.setAnalysis(Insurance.Analysis.REGULAR);
            analysis = "REGULAR";
        } else {
            insurance.setAnalysis(Insurance.Analysis.RESPONSIBLE);
            analysis = "RESPONSIBLE";
        }

        String description;

        if(analysis.equals("ECONOMIC")) {
            description = "This plan is tailored for clients who prioritize" +
                    "affordability without compromising on essential services.";
        } else if(analysis.equals("REGULAR")) {
            description = "For clients seeking maximum coverage and comprehensive services, the Regular plan is the top choice.";
        } else {
            description = "The Responsible plan offers a balanced option, combining value with a wider range of coverage to suit various needs.";
        }

        insurance.setClient(client);
        insurance.setObservation(description);
        insurance.setRisk(risk_questions);
        insurance.setType(Insurance.Type.AUTO);
        insurance.setCreated_at(LocalDateTime.now());

        LocalDateTime createdAt = insurance.getCreated_at();
        LocalDateTime validateAt = createdAt.plusDays(30);
        Date validateAtDate = Date.from(validateAt.atZone(ZoneId.systemDefault()).toInstant());

        insurance.setValidate_at(validateAtDate);

        this.insuranceRepository.save(insurance);

        return insurance;
    }
}
