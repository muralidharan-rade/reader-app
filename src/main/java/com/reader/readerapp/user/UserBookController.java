package com.reader.readerapp.user;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserBookController {

	@Autowired
	UserBookRepository userBookRepository;

	@PostMapping(value = "/addUserBook")
	public ModelAndView addUserBook(Model model, @RequestBody MultiValueMap<String, String> formData,
			@AuthenticationPrincipal OAuth2User principal) {
		UserBook userBook = new UserBook();
		userBook.setReadingStatus(formData.getFirst("readingStatus"));
		userBook.setRating(Integer.parseInt(formData.getFirst("rating")));
		userBook.setStartDate(LocalDate.parse(formData.getFirst("startDate")));
		userBook.setEndDate(LocalDate.parse(formData.getFirst("endDate")));
		
		UserBookPrimaryKey primaryKey = new UserBookPrimaryKey();
		primaryKey.setBookId(formData.getFirst("bookId"));
		primaryKey.setUserId(principal.getAttribute("login"));
		userBook.setKey(primaryKey);
		
		userBookRepository.save(userBook);
		
		return new ModelAndView("redirect:/books/" + formData.getFirst("bookId"));
	}

}
