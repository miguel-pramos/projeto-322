package com.unicamp.smartshelf.service;

import com.unicamp.smartshelf.model.entity.Book;
import com.unicamp.smartshelf.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    // Buscar todos os livros
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Buscar livro por ID
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    // Salvar um novo livro
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Atualizar um livro existente
    public Book update(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setDescription(bookDetails.getDescription());
                    book.setRead(bookDetails.isRead());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
    }

    // Deletar livro por ID
    public void deleteById(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Livro não encontrado com id: " + id);
        }
    }

    // Buscar livros por status de leitura
    public List<Book> findByReadStatus(boolean read) {
        return bookRepository.findByRead(read);
    }

    // Buscar livros por descrição
    public List<Book> findByDescription(String description) {
        return bookRepository.findByDescriptionContainingIgnoreCase(description);
    }

    // Buscar livros lidos/não lidos por descrição
    public List<Book> findByReadStatusAndDescription(boolean read, String description) {
        return bookRepository.findByReadAndDescriptionContaining(read, description);
    }

    // Marcar livro como lido
    public Book markAsRead(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setRead(true);
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
    }

    // Marcar livro como não lido
    public Book markAsUnread(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setRead(false);
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
    }
}
