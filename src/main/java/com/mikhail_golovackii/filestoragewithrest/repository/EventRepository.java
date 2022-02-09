
package com.mikhail_golovackii.filestoragewithrest.repository;

import com.mikhail_golovackii.filestoragewithrest.model.Event;

public interface EventRepository extends BaseRepository<Event, Long>{

    public String getFilePathByFileName(String fileName);

    public Event getEventByFileName(String name);

}
