package com.reader.readerapp.home;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.reader.readerapp.bookuser.BooksByUser;
import com.reader.readerapp.bookuser.BooksByUserRepository;

@Controller
public class HomeController {

	@Autowired
	BooksByUserRepository booksByUserRepository;

	@GetMapping(value = "/")
	public String getHome(@AuthenticationPrincipal OAuth2User principal, Model model) {
		if (principal == null || principal.getAttribute("login") == null) {
			return "index";
		}

		Slice<BooksByUser> booksByUserSlice = booksByUserRepository.findAllById(principal.getAttribute("login"),
				CassandraPageRequest.of(0, 100));
		List<BooksByUser> booksByUser = booksByUserSlice.getContent();

		booksByUser = booksByUser.stream().distinct().map(book -> {
			return book;
		}).collect(Collectors.toList());

		model.addAttribute("books", booksByUser);
		return "home";
	}

}
