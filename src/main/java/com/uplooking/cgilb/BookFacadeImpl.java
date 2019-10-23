package com.uplooking.cgilb;

public class BookFacadeImpl implements BookFacade {
    @Override
    public String addBook(String name) {
        System.out.println("BookFacade..." + name);
        return name;
    }
}
