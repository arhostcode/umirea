package edu.mirea.ardyc.umirea.data.repository.impl.timetable;

import android.content.Context;

import java.util.List;

import edu.mirea.ardyc.umirea.data.dataSources.dashboard.DashboardDataSource;
import edu.mirea.ardyc.umirea.data.dataSources.dashboard.RemoteDashboardDataSource;
import edu.mirea.ardyc.umirea.data.model.DataResponse;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateLesson;
import edu.mirea.ardyc.umirea.data.model.timetable.date.DateTask;
import edu.mirea.ardyc.umirea.data.repository.Repository;

public class DashboardRepository extends Repository {

    private DashboardDataSource dashboardDataSource;
    private RemoteDashboardDataSource remoteDashboardDataSource;

    public DashboardRepository(Context context, DashboardDataSource dashboardDataSource, RemoteDashboardDataSource remoteDashboardDataSource) {
        super(context);
        this.dashboardDataSource = dashboardDataSource;
        this.remoteDashboardDataSource = remoteDashboardDataSource;
    }

    public DataResponse<List<String>> listGroupsSchedules() {
        return remoteDashboardDataSource.getAllGroups();
    }

    public DataResponse<Timetable> loadDashboard(String userToken) {
        int hash = dashboardDataSource.getDashboardHash();
        int remoteHash = remoteDashboardDataSource.getDashboardHash(userToken);

        if (hash != remoteHash && remoteHash != 0) {
            DataResponse<Timetable> timetableDataResponse = remoteDashboardDataSource.getData(userToken);
            if (!timetableDataResponse.isError()) {
                dashboardDataSource.saveData(timetableDataResponse.getData());
                dashboardDataSource.saveDashboardHash(remoteHash);
            }
            return timetableDataResponse;
        }

        return new DataResponse<>(null);
    }

    public DataResponse<List<DateLesson>> loadAddonLessons(String userToken) {
        DataResponse<List<DateLesson>> timetableDataResponse = remoteDashboardDataSource.getAddonLessons(userToken);
        if (!timetableDataResponse.isError())
            dashboardDataSource.saveAddonLessons(timetableDataResponse.getData());
        return timetableDataResponse;
    }

    public DataResponse<String> addLessons(String userToken, List<DateLesson> dateLessons) {
        return remoteDashboardDataSource.addLessons(userToken, dateLessons);
    }

    public DataResponse<List<DateTask>> loadHomeworks(String userToken) {
        DataResponse<List<DateTask>> homeworks = remoteDashboardDataSource.getHomeworks(userToken);
        if (!homeworks.isError())
            dashboardDataSource.saveHomeworks(homeworks.getData());
        return homeworks;
    }

    public DataResponse<String> addHomework(String userToken, DateTask homework) {
        return remoteDashboardDataSource.addHomework(userToken, homework);
    }

    public DataResponse<List<DateTask>> loadNotes(String userToken) {
        DataResponse<List<DateTask>> notes = remoteDashboardDataSource.getNotes(userToken);
        if (!notes.isError())
            dashboardDataSource.saveTasks(notes.getData());
        return notes;
    }

    public DataResponse<String> addNote(String userToken, DateTask note) {
        return remoteDashboardDataSource.addNote(userToken, note);
    }


    public DataResponse<Timetable> getLocalDashboard() {
        return dashboardDataSource.getData();
    }


    public void removeDashboard() {
        dashboardDataSource.removeData();
    }
}
