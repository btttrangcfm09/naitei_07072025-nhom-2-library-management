package com.group2.library_management.exception;

public class BookInstanceNotFoundException extends ResourceNotFoundException{
    public BookInstanceNotFoundException(Integer id){
        super(String.format(id + " Không tồn tại."));
    }
}
