
package com.mikhail_golovackii.filestoragewithrest.service;

import java.util.List;

public interface BaseService <T, Id> {

    public void saveElement(T elem);
    
    public void updateElement(T elem);
    
    public T getElementById(Id id);
    
    public List<T> getAllElements();
    
    public void deleteElement(T elem);
}
