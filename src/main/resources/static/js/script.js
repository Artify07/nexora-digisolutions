(() => {
	"use strict";

	const menuButton = document.querySelector(".menu-toggle");
	const header = document.querySelector(".site-header");
	const navigation = document.querySelector(".primary-navigation");

	if (menuButton && header && navigation) {
		const closeMenu = () => {
			header.classList.remove("nav-open");
			menuButton.setAttribute("aria-expanded", "false");
			menuButton.setAttribute("aria-label", "Open navigation menu");
		};

		menuButton.addEventListener("click", () => {
			const isOpen = menuButton.getAttribute("aria-expanded") === "true";
			header.classList.toggle("nav-open", !isOpen);
			menuButton.setAttribute("aria-expanded", String(!isOpen));
			menuButton.setAttribute("aria-label", isOpen ? "Open navigation menu" : "Close navigation menu");
		});

		navigation.addEventListener("click", (event) => {
			if (event.target instanceof Element && event.target.closest("a")) {
				closeMenu();
			}
		});

		document.addEventListener("keydown", (event) => {
			if (event.key === "Escape") {
				closeMenu();
				menuButton.focus();
			}
		});
	}

	const revealItems = document.querySelectorAll(".reveal");
	if ("IntersectionObserver" in window && !window.matchMedia("(prefers-reduced-motion: reduce)").matches) {
		document.documentElement.classList.add("js-enabled");
		const observer = new IntersectionObserver((entries, currentObserver) => {
			entries.forEach((entry) => {
				if (entry.isIntersecting) {
					entry.target.classList.add("is-visible");
					currentObserver.unobserve(entry.target);
				}
			});
		}, { threshold: 0.12, rootMargin: "0px 0px -35px 0px" });

		revealItems.forEach((item) => observer.observe(item));
	} else {
		revealItems.forEach((item) => item.classList.add("is-visible"));
	}

	const filterButtons = document.querySelectorAll(".filter-button");
	const portfolioCards = document.querySelectorAll(".portfolio-card[data-category]");
	const emptyMessage = document.querySelector(".filter-empty");

	filterButtons.forEach((button) => {
		button.addEventListener("click", () => {
			const filter = button.dataset.filter;
			let visibleCount = 0;

			filterButtons.forEach((filterButton) => {
				const selected = filterButton === button;
				filterButton.classList.toggle("is-selected", selected);
				filterButton.setAttribute("aria-pressed", String(selected));
			});

			portfolioCards.forEach((card) => {
				const visible = filter === "all" || card.dataset.category === filter;
				card.hidden = !visible;
				if (visible) visibleCount += 1;
			});

			if (emptyMessage) emptyMessage.hidden = visibleCount > 0;
		});
	});

	document.querySelectorAll("[data-share]").forEach((link) => {
		const pageUrl = encodeURIComponent(window.location.href);
		const shareUrls = {
			linkedin: `https://www.linkedin.com/sharing/share-offsite/?url=${pageUrl}`,
			facebook: `https://www.facebook.com/sharer/sharer.php?u=${pageUrl}`,
			whatsapp: `https://wa.me/?text=${encodeURIComponent(`Discover Nexora DigiSolutions: ${window.location.href}`)}`
		};
		const shareUrl = shareUrls[link.dataset.share];
		if (shareUrl) link.href = shareUrl;
		link.target = "_blank";
		link.rel = "noopener noreferrer";
	});
})();
