package ru.ardyc.schedule.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.schedule.entities.DatedLessonEntity;

import java.util.List;

public interface DatedLessonsRepository extends CrudRepository<DatedLessonEntity, String> {

    List<DatedLessonEntity> getDatedLessonEntitiesByUserUuid(String userUuid);

    DatedLessonEntity getDatedLessonEntityByDayAndMonthAndYearAndLessonTimeAndUserUuid(Integer day, Integer month, Integer year, Integer lessonTime, String userUuid);

}
