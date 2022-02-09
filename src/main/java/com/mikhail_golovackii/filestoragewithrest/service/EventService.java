
package com.mikhail_golovackii.filestoragewithrest.service;

import com.mikhail_golovackii.filestoragewithrest.model.Event;

public interface EventService extends BaseService<Event, Long>{

    public String getFilePathByFileName(String fileName);

    public Event getEventByFileName(String name);

}
