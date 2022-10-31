package com.reader.readerapp.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.reader.readerapp.user.UserBook;
import com.reader.readerapp.user.UserBookPrimaryKey;
import com.reader.readerapp.user.UserBookRepository;

@Controller
public class BookController {

	private static final String IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	private static final String IMAGE_EXTN = "-L.jpg";

	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserBookRepository userBookRepository;

	@GetMapping(value = "/books/{bookId}")
	public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {

		String imageUrl = "no-image.png";

		Optional<Book> optionalBook = bookRepository.findById(bookId);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();

			if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
				imageUrl = IMAGE_ROOT + book.getCoverIds().get(0) + IMAGE_EXTN;
			}
			model.addAttribute("imageUrl", imageUrl);
			model.addAttribute("book", book);

			if (principal != null && principal.getAttribute("login") != null) {
				model.addAttribute("login", principal.getAttribute("login"));

				UserBookPrimaryKey userBookPrimaryKey = new UserBookPrimaryKey();
				userBookPrimaryKey.setBookId(optionalBook.get().getId());
				userBookPrimaryKey.setUserId(principal.getAttribute("login"));
				Optional<UserBook> userBookOptional = userBookRepository.findById(userBookPrimaryKey);
				if (userBookOptional.isPresent()) {
					model.addAttribute("userBooks", userBookOptional.get());
				} else {
					model.addAttribute("userBooks", new UserBook());
				}
			}

			return "book";
		}
		return "book-not-found";
	}

}
