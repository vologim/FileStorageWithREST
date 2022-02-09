
package com.mikhail_golovackii.filestoragewithrest.repository;

import java.util.List;

public interface BaseRepository <T, Id> {

    public void save(T elem);
    
    public void update(T elem);
    
    public T getById(Id id);
    
    public List<T> getAll();
    
    public void delete(T id);
}
