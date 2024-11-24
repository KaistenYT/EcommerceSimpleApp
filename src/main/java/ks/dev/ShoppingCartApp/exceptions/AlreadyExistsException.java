package ks.dev.ShoppingCartApp.exceptions;

public class AlreadyExistsException extends  RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
