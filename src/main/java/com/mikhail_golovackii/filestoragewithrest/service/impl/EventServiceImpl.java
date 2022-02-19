
package com.mikhail_golovackii.filestoragewithrest.service.impl;

import com.mikhail_golovackii.filestoragewithrest.model.Event;
import com.mikhail_golovackii.filestoragewithrest.repository.EventRepository;
import com.mikhail_golovackii.filestoragewithrest.repository.impl.EventRepositoryImpl;
import com.mikhail_golovackii.filestoragewithrest.service.EventService;
import java.util.List;

public class EventServiceImpl implements EventService{

    private EventRepository repository;

    public EventServiceImpl() {
        this.repository = new EventRepositoryImpl();
    }

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveElement(Event elem) {
        repository.save(elem);
    }

    @Override
    public void updateElement(Event elem) {
        repository.update(elem);
    }

    @Override
    public Event getElementById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Event> getAllElements() {
        return repository.getAll();
    }

    @Override
    public void deleteElement(Event event) {
        repository.delete(event);
    }
}
