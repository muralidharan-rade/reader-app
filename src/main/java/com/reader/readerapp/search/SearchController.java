package com.reader.readerapp.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class SearchController {

	private final static String BASE_URI = "https://openlibrary.org/search.json";
	private static final String IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

	private WebClient webClient;

	public SearchController(WebClient.Builder builder) {
		webClient = builder.baseUrl(BASE_URI).build();
	}

	@GetMapping(value = "/books/search")
	public String searchBooks(@RequestParam String searchQuery, Model model) {

		Mono<SearchResult> monoString = this.webClient.get().uri("?q={searchQuery}", searchQuery).retrieve()
				.bodyToMono(SearchResult.class);
		SearchResult searchResult = monoString.block();

		searchResult.getDocs().stream().forEach(t -> {
			t.setKey(t.getKey().replaceAll("/works/", ""));
			if (t.getCover_i() != null) {
				t.setCover_i(IMAGE_ROOT + t.getCover_i() + "-M.jpg");
			} else {
				t.setCover_i("/no-image.png");
			}
		});

		model.addAttribute("searchResults", searchResult.getDocs());
		return "search";
	}

}
