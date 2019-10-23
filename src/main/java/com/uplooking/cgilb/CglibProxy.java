package com.uplooking.cgilb;

public class CglibProxy {

    public static void main(String[] args) {
        try {
            BookFacadeProxyCglib bookFacadeProxyCglib = new BookFacadeProxyCglib();
            BookFacadeImpl bookFacade = (BookFacadeImpl) bookFacadeProxyCglib.getProxyInstance(BookFacadeImpl.class);
            System.out.println(bookFacade.addBook("tongjian"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
