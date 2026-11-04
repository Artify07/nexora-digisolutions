package com.nexora.digisolutions.nexora.digisolutions.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactForm {

	@NotBlank(message = "Please enter your name.")
	@Size(max = 100, message = "Name must be 100 characters or fewer.")
	private String name;

	@NotBlank(message = "Please enter your email address.")
	@Email(message = "Please enter a valid email address.")
	@Size(max = 254, message = "Email must be 254 characters or fewer.")
	private String email;

	@Pattern(regexp = "^$|^[+()0-9.\\-\\s]{7,20}$", message = "Please enter a valid phone number.")
	private String phone;

	@NotBlank(message = "Please enter a subject.")
	@Size(max = 120, message = "Subject must be 120 characters or fewer.")
	private String subject;

	@NotBlank(message = "Please tell us a little about your project.")
	@Size(max = 3000, message = "Message must be 3,000 characters or fewer.")
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}