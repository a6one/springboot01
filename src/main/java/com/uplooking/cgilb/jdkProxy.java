package com.uplooking.cgilb;

public class jdkProxy {

    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade bookFacade = (BookFacade) proxy.getProxyInstance(new BookFacadeImpl());
        System.out.println(bookFacade.addBook("tongjian"));
    }
}
