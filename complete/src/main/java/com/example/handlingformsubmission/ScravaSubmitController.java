package com.example.handlingformsubmission;

import org.springframework.beans.factory.annotation.Value;
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
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class ScravaSubmitController {

    Set<AthleteSubmission> storage = new TreeSet<>(Comparator.comparingDouble(AthleteSubmission::getScore).reversed());


String storagePath = "C:\\Users\\Turek\\Code\\scrava\\storage.xml";


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
		storage.forEach(System.out::println);

		model.addAttribute("athletes", storage);

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
                    .newInstance(AthleteListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            AthleteListWrapper wrapper = (AthleteListWrapper) um.unmarshal(new File(storagePath));

            storage.clear();
            storage.addAll(wrapper.getAthletes());

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }

    public void saveAthleteDataToFile() {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(AthleteListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our athlete data.
            AthleteListWrapper wrapper = new AthleteListWrapper();
            wrapper.setAthletes(storage);
            wrapper.setLastUpdated(LocalDateTime.now());

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, new File(storagePath));

        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }
}
