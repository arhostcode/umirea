package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.net.auth.AuthService;
import edu.mirea.ardyc.umirea.data.net.auth.DashboardService;
import edu.mirea.ardyc.umirea.data.repository.impl.timetable.api.TParser;
import edu.mirea.ardyc.umirea.ui.view.MainActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimetableRemoteRepository extends TimetableRepository {

    private DashboardService service;

    @Override
    public Timetable getData() {
        String server = "http://192.168.118.234:8085";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DashboardService.class);

        Timetable.Builder timetableBuilder = new Timetable.Builder();

        try {
            JsonObject main = service.getBaseGroupSchedule("ИКБО-04-22").execute().body();

            if (main != null) {
                System.out.println(TParser.fromJson(main));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
