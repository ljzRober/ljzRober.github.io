package com.ljz.plat.android.binder;

import com.ljz.plat.android.binder.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}