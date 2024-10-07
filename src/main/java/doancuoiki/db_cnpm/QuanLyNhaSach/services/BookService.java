package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Book;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Category;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResultPaginationDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    public Book createBook(Book book) {
        Category category = categoryService.getCategoryById(book.getCategory().getId());
        book.setCategory(category);
        return bookRepository.save(book);
    }

    public ResultPaginationDTO getAllBookWithPagination(Specification<Book> spec, Pageable pageable) {
        Page<Book> pageBooks = this.bookRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageBooks.getTotalPages());
        meta.setTotal(pageBooks.getTotalElements());
        rs.setMeta(meta);
        // List<ResAccountDTO> listAccountDTO = pageAccounts.map(account ->
        // convertToResAccountDTO(account)).getContent();
        List<Book> listBook = pageBooks.getContent();
        rs.setResult(listBook);
        return rs;
    }

    public Book updateBook(long id, Book rqBook) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        book.setThumbnail(rqBook.getThumbnail());
        book.setSlider(rqBook.getSlider());
        book.setMainText(rqBook.getMainText());
        book.setAuthor(rqBook.getAuthor());
        book.setPrice(rqBook.getPrice());
        book.setSold(rqBook.getSold());
        book.setQuantity(rqBook.getQuantity());
        Category category = categoryService.getCategoryById(rqBook.getCategory().getId());
        book.setCategory(category);
        return bookRepository.save(book);
    }

    public boolean checkBookExist(Long id) {
        return bookRepository.existsById(id);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
