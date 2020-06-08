package com.example.handlingformsubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class ScravaSubmitController {

    Set<AthleteSubmission> storage = new TreeSet<>(Comparator.comparingDouble(AthleteSubmission::getScore).reversed());

    @GetMapping("/submit")
    public String scravaSubmit(Model model) {
        model.addAttribute("athleteSubmission", new AthleteSubmission());
        return "submit";
    }

    @PostMapping("/submit")
    public String scravaSubmissionResult(@ModelAttribute AthleteSubmission athleteSubmission, Model model) {
        model.addAttribute("athleteSubmission", athleteSubmission);

        storage.add(athleteSubmission);

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

}
