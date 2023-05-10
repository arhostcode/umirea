package ru.ardyc.schedule.repo;

import org.springframework.data.repository.CrudRepository;
import ru.ardyc.schedule.entities.HomeWorkEntity;

import java.util.List;

public interface HomeworkRepository extends CrudRepository<HomeWorkEntity, String> {

    List<HomeWorkEntity> getHomeWorkEntitiesByGroupUuid(String groupUuid);

    HomeWorkEntity getHomeWorkEntityByDayAndMonthAndYearAndLessonTimeAndGroupUuid(Integer day, Integer month, Integer year, Integer lessonTime, String groupUuid);

}
