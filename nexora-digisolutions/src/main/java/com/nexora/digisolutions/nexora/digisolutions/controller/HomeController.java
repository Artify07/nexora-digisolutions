package com.nexora.digisolutions.nexora.digisolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		return page(model, "home", "index");
	}

	@GetMapping("/about")
	public String about(Model model) {
		return page(model, "about", "about");
	}

	@GetMapping("/services")
	public String services(Model model) {
		return page(model, "services", "services");
	}

	@GetMapping("/portfolio")
	public String portfolio(Model model) {
		return page(model, "portfolio", "portfolio");
	}

	@GetMapping("/privacy")
	public String privacy(Model model) {
		return page(model, "privacy", "privacy");
	}

	@GetMapping("/terms")
	public String terms(Model model) {
		return page(model, "terms", "terms");
	}

	private String page(Model model, String activePage, String template) {
		model.addAttribute("activePage", activePage);
		return template;
	}
}