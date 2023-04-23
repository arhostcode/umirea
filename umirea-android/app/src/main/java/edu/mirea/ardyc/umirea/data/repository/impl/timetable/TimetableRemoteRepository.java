package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import edu.mirea.ardyc.umirea.data.model.timetable.LectionLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.data.model.timetable.data.DateLesson;
import edu.mirea.ardyc.umirea.data.net.auth.DashboardService;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TLesson;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TParser;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TWeek;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimetableRemoteRepository extends TimetableRepository {

    private DashboardService service;

    public TimetableRemoteRepository(Context context) {
        super(context);
    }

    @Override
    public Timetable getData() {

        String server = "http://10.0.2.2:8085";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DashboardService.class);

        Timetable.Builder timetableBuilder = new Timetable.Builder();

        try {
            JsonObject main = service.getBaseGroupSchedule("ИКБО-04-22").execute().body();
            int firstMonth = 2;
            int firstDay = 9;
            int year = 2023;
            if (main != null) {
                List<TWeek> weekList = TParser.fromJson(main);
                int week = 0;
                int dayOfWeek = LocalDate.of(year, firstMonth, firstDay).getDayOfWeek().ordinal();
                while (week < weekList.size()) {
                    TimetableMonth.Builder timetableMonth = new TimetableMonth.Builder(firstMonth, year);
                    YearMonth month = YearMonth.of(year, firstMonth);

                    for (int j = firstDay - 1; j < month.lengthOfMonth(); j++) {
                        int LAST_DAY_OF_WEEK = 7;
                        if (dayOfWeek == LAST_DAY_OF_WEEK) {
                            dayOfWeek = 0;
                            week++;
                        }
                        if (week == weekList.size())
                            break;
                        TimetableDay.Builder tiBuilder = new TimetableDay.Builder().withDate(j + 1, firstMonth, year);
                        List<TLesson> lessons = weekList.get(week).getDays().get(dayOfWeek).getLessons();

                        for (int i = 0; i < lessons.size(); i++) {
                            TLesson tLesson = lessons.get(i);
                            if (tLesson != null)
                                tiBuilder.addLesson(new Lesson.Builder(new LectionLesson()).withName(tLesson.getName()).withRoom(tLesson.getRoom()).withTeacher(tLesson.getTeacherName()).withLessonTime(i).build());
                        }

                        dayOfWeek++;
                        timetableMonth.addDay(tiBuilder.build());
                    }
                    firstDay = 1;
                    timetableBuilder.addMonth(timetableMonth.build());
                    firstMonth++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return timetableBuilder.build();
    }

    @Override
    public List<String> listGroupsSchedules() {
        return null;
    }



    @Override
    public String getBaseScheduleHash() {
        return "0xffasddgo9djdjkf7d";
    }

    @Override
    public String getAddonLessonsHash() {
        return null;
    }

    @Override
    public List<DateLesson> getAddonLessons() {
        return null;
    }


}
