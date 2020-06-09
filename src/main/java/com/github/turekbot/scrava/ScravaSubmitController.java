package com.github.turekbot.scrava;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Controller
public class ScravaSubmitController {

    Set<AthleteSubmission> storage = new HashSet<>();


    String storagePath = "C:\\Users\\Turek\\Code\\scrava\\storage.xml";

    public static final Comparator<AthleteSubmission> HIGHEST_FIRST = Comparator.comparingDouble(AthleteSubmission::getScore).reversed();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;


    public ScravaSubmitController() {
        loadAthleteDataFromFile();
    }

    @GetMapping("/submit")
    public String scravaSubmit(Model model) {
        model.addAttribute("athleteSubmission", new AthleteSubmission());
        return "submit";
    }

    @PostMapping("/submit")
    public String scravaSubmissionResult(@ModelAttribute AthleteSubmission athleteSubmission, Model model) {
        model.addAttribute("athleteSubmission", athleteSubmission);
        System.out.println(athleteSubmission);
        storage.add(athleteSubmission);

        saveAthleteDataToFile();

        return "result";
    }

    @GetMapping("/")
    public String leaderBoard(Model model) {

        System.out.println("Storage:");
        // Sort list
        List<AthleteSubmission> sortedList = storage.stream()
                .sorted(HIGHEST_FIRST)
                .collect(toList());

        sortedList.forEach(System.out::println);

        List<AthleteSubmission> winners = sortedList
                .parallelStream()
                .limit(3)
                .collect(toList());
        List<AthleteSubmission> losers = sortedList
                .parallelStream()
                .filter(athleteSubmission -> !winners.contains(athleteSubmission))
                .collect(toList());

        model.addAttribute("winners", winners);
        model.addAttribute("losers", losers);
        model.addAttribute("lastUpdated", lastUpdated);

        System.out.println("Model:");
        model.asMap().forEach((key, value) -> {
            System.out.println("Key: " + key);
            System.out.println("Value: " + value);
        });
        System.out.println();

        return "leaderboard";
    }


    public void loadAthleteDataFromFile() {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AthleteSetWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            AthleteSetWrapper wrapper = (AthleteSetWrapper) um.unmarshal(new File(storagePath));

            storage.clear();
            storage.addAll(wrapper.getAthletes());

            lastUpdated = wrapper.getLastUpdated();

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }

    public void saveAthleteDataToFile() {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AthleteSetWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our athlete data.
            AthleteSetWrapper wrapper = new AthleteSetWrapper();
            wrapper.setAthletes(storage);
            wrapper.setLastUpdated(LocalDateTime.now());

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, new File(storagePath));

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }
}
