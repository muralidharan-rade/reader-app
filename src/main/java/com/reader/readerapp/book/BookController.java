package com.reader.readerapp.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

	private static final String IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
	private static final String IMAGE_EXTN = "-L.jpg";

	@Autowired
	BookRepository bookRepository;

	@GetMapping(value = "/books/{bookId}")
	public String getBook(@PathVariable String bookId, Model model) {

		String imageUrl = "no-image.png";

		Optional<Book> optionalBook = bookRepository.findById(bookId);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			
			if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
				imageUrl = IMAGE_ROOT + book.getCoverIds().get(0) + IMAGE_EXTN;
			}
			model.addAttribute("imageUrl", imageUrl);
			model.addAttribute("book", book);
			return "book";
		}
		return "book-not-found";
	}

}
