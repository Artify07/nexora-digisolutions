package com.nexora.digisolutions.nexora.digisolutions.controller;

import com.nexora.digisolutions.nexora.digisolutions.model.ContactForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("activePage", "contact");
		model.addAttribute("contactForm", new ContactForm());
		return "contact";
	}

	@PostMapping("/contact")
	public String submitContact(
			@Valid @ModelAttribute("contactForm") ContactForm contactForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("activePage", "contact");
			return "contact";
		}

		try {
    java.nio.file.Path file = java.nio.file.Paths.get("enquiries.csv");
    boolean isNew = !java.nio.file.Files.exists(file);
    String date = java.time.LocalDateTime.now().toString();
    String cleanName = contactForm.getName() == null ? "" : contactForm.getName().replace("\"", "");
    String cleanEmail = contactForm.getEmail() == null ? "" : contactForm.getEmail().replace("\"", "");
    String cleanPhone = contactForm.getPhone() == null ? "" : contactForm.getPhone().replace("\"", "");
    String cleanSubject = contactForm.getSubject() == null ? "" : contactForm.getSubject().replace("\"", "");
    String cleanMessage = contactForm.getMessage() == null ? "" : contactForm.getMessage().replace("\"", "").replace("\n", " ").replace("\r", " ");
    String row = "\"" + date + "\",\"" + cleanName + "\",\"" + cleanEmail + "\",\"" + cleanPhone + "\",\"" + cleanSubject + "\",\"" + cleanMessage + "\"\n";
    if (isNew) {
        java.nio.file.Files.writeString(file, "Date,Name,Email,Phone,Subject,Message\n" + row);
    } else {
        java.nio.file.Files.writeString(file, row, java.nio.file.StandardOpenOption.APPEND);
    }
} catch (Exception e) {
    e.printStackTrace();
}redirectAttributes.addFlashAttribute(
				"deliveryNotice",
				"Your form passed validation, but this website is not configured to deliver messages yet. "
						+ "Please email us directly at vijayanirmala.levanv@gmail.com.");
		return "redirect:/contact";
	}
}