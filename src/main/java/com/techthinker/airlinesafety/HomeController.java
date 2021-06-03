package com.techthinker.airlinesafety;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Autowired
	DataService data;

	@GetMapping("/home")
	public String getHome(Model model)
			throws IOException, InterruptedException {
		model.addAttribute("data", data.getAirlineList());
		model.addAttribute("incidentsComp", data.getIncidentsComp());
		model.addAttribute("fatalitiesComp", data.getFatalitiesComp());
		model.addAttribute("incidents", data.getTotalNumbers().get(0));
		model.addAttribute("accidents", data.getTotalNumbers().get(1));
		model.addAttribute("fatalities", data.getTotalNumbers().get(2));
		model.addAttribute("ratio", data.getRatioComp());
		return "home";
	}
}
