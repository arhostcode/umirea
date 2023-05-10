package ru.ardyc.schedule.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.schedule.entities.NoteEntity;

import java.util.List;

public interface NoteRepository extends CrudRepository<NoteEntity, String> {

    List<NoteEntity> getNoteEntitiesByUserUuid(String userUuid);

    NoteEntity getNoteEntityByDayAndMonthAndYearAndLessonTimeAndUserUuid(Integer day, Integer month, Integer year, Integer lessonTime, String userUuid);


}
