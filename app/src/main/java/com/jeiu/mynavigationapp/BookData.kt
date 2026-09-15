package com.jeiu.mynavigationapp

data class Book(val id: Int, var title: String)

object BookRepository {
    private val books = mutableListOf(
        Book(1, "어린 왕자"),
        Book(2, "데미안")
    )

    fun getBooks(): List<Book> = books

    fun getBookById(id: Int): Book? = books.find { it.id == id }

    fun addBook(title: String) {
        val newId = (books.maxOfOrNull { it.id } ?: 0) + 1
        books.add(Book(newId, title))
    }

    fun updateBook(id: Int, newTitle: String) {
        books.find { it.id == id }?.title = newTitle
    }
}