package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.data.model.timetable.Lesson;
import edu.mirea.ardyc.umirea.data.model.timetable.Timetable;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableDay;
import edu.mirea.ardyc.umirea.data.model.timetable.TimetableMonth;
import edu.mirea.ardyc.umirea.databinding.DashboardCalendarBinding;

public class DashboardCalendar extends LinearLayout {

    private static final String[] RU_MONTH_NAMES = {"Января", "Февраля", "Марта", "Апреля",
            "Мая", "Июня", "Июля", "Августа",
            "Сентября", "Октября", "Ноября", "Декабря"};

    private Timetable timetable = null;

    private DashboardCalendarBinding dashboardCalendarBinding;
    private TextView currentDateTextView;
    private TextView currentMonthTextView;
    private int chosenDay, chosenMonth, year;
    private int currentDay, currentMonth;

    private OnClickCalendarDay onClickCalendarDay;

    private DashboardCalendarDay currentCalendarDayView, chosenCalendarDayView;

    private LinearLayout[] weeks;

    public DashboardCalendar(Context context) {
        super(context);
    }

    public DashboardCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public void init(Context context) {
        dashboardCalendarBinding = DashboardCalendarBinding.inflate(LayoutInflater.from(context), this, true);
        dashboardCalendarBinding.left.setOnClickListener((v) -> {
            if (chosenMonth > 1) {
                chosenMonth--;
            } else {
                year--;
                chosenMonth = 12;
            }
            chosenDay = 1;
            setCalendarMonth(year, Month.of(chosenMonth));
            onClickCalendarDay.click(chosenCalendarDayView);
        });
        dashboardCalendarBinding.right.setOnClickListener((v) -> {
            if (chosenMonth < 12) {
                chosenMonth++;
            } else {
                year++;
                chosenMonth = 1;
            }
            chosenDay = 1;
            setCalendarMonth(year, Month.of(chosenMonth));
            onClickCalendarDay.click(chosenCalendarDayView);
        });
        initCurrentDate();
        initializeDaysWeeks();
        initCalendar();
    }


    private void initCurrentDate() {
        LocalDate date = LocalDate.now();
        currentDateTextView = dashboardCalendarBinding.currentDate;
        currentMonthTextView = dashboardCalendarBinding.currentMonth;
        currentDateTextView.setText(String.valueOf(date.getDayOfMonth()));
        currentMonthTextView.setText(RU_MONTH_NAMES[date.getMonth().getValue() - 1]);
    }

    private void initializeDaysWeeks() {
        weeks = new LinearLayout[6];

        weeks[0] = dashboardCalendarBinding.calendarWeek1;
        weeks[1] = dashboardCalendarBinding.calendarWeek2;
        weeks[2] = dashboardCalendarBinding.calendarWeek3;
        weeks[3] = dashboardCalendarBinding.calendarWeek4;
        weeks[4] = dashboardCalendarBinding.calendarWeek5;
        weeks[5] = dashboardCalendarBinding.calendarWeek6;
    }

    private void initCalendar() {
        LocalDate date = LocalDate.now();
        currentDay = date.getDayOfMonth();
        currentMonth = date.getMonth().getValue();

        chosenDay = currentDay;
        chosenMonth = currentMonth;

        year = date.getYear();
        setCalendarMonth(date.getYear(), date.getMonth());
    }

    public void reInit(Timetable timetable) {
        this.timetable = timetable;
        setCalendarMonth(year, Month.of(chosenMonth));
    }

    private void setCalendarMonth(int year, Month month) {
        updateCalendarText(chosenDay, chosenMonth);
        LocalDate date = LocalDate.of(year, month, 1);
        YearMonth ym = YearMonth.of(date.getYear(), date.getMonth());

        TimetableMonth monthModel = null;
        if (timetable != null)
            monthModel = timetable.getForMonthYear(month.getValue(), year);

        int daysInCurrentMonth = ym.lengthOfMonth();
        int daysInPastMonth = ym.minusMonths(1).lengthOfMonth();
        int firstDayOfCurrentMonth = date.withDayOfMonth(1).getDayOfWeek().getValue() - 1;

        int day = 1;
        for (int week = 0; week < 6; week++) {
            weeks[week].removeAllViews();
            for (int i = 0; i < 7; i++) {
                if (day <= firstDayOfCurrentMonth) {
                    DashboardCalendarDay previousMonthDay = createDashboardDayButton(daysInPastMonth - firstDayOfCurrentMonth + day, chosenMonth);
                    previousMonthDay.setTextColor(Color.GRAY);
                    weeks[week].addView(previousMonthDay);
                } else if (day - firstDayOfCurrentMonth <= daysInCurrentMonth) {
                    DashboardCalendarDay currentMonthDay = createDashboardDayButtonWithAction(day - firstDayOfCurrentMonth, chosenMonth);
                    currentMonthDay.setTextColor(Color.WHITE);

                    if (monthModel != null) {
                        TimetableDay d = monthModel.getDay(day - firstDayOfCurrentMonth);
                        if (d != null) {
                            currentMonthDay.setDay(d);
                        }
                    }
                    if (day - firstDayOfCurrentMonth == currentDay && currentMonth == ym.getMonth().getValue()) {
                        currentMonthDay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.current_day_shape, null));
                        currentCalendarDayView = currentMonthDay;
                        if (onClickCalendarDay != null && (chosenCalendarDayView == null || chosenCalendarDayView.getDay() == null || chosenCalendarDayView.getDay().getMonth() != chosenMonth))
                            onClickCalendarDay.click(currentMonthDay);
                    }

                    if (day - firstDayOfCurrentMonth == chosenDay && chosenMonth == ym.getMonth().getValue()) {
                        currentMonthDay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.round_calendar_button, null));
                        chosenCalendarDayView = currentMonthDay;
                        if (onClickCalendarDay != null) {
                            onClickCalendarDay.click(currentMonthDay);
                        }
                    }
                    weeks[week].addView(currentMonthDay);
                } else {
                    DashboardCalendarDay nextMonthDay = createDashboardDayButton(day - firstDayOfCurrentMonth - daysInCurrentMonth, chosenMonth);
                    nextMonthDay.setTextColor(Color.GRAY);
                    weeks[week].addView(nextMonthDay);
                }
                day++;
            }
        }
    }

    private void changeChosenDay(DashboardCalendarDay day, int chosenDay, int chosenMonth) {
        updateCalendarText(chosenDay, chosenMonth);
        day.setBackground(getDrawableById(R.drawable.round_calendar_button));
        if (chosenCalendarDayView == currentCalendarDayView) {
            chosenCalendarDayView.setBackground(getDrawableById(R.drawable.current_day_shape));
        } else {
            if (!(this.chosenDay == chosenDay && chosenMonth == this.chosenMonth))
                chosenCalendarDayView.setBackground(null);
        }
        chosenCalendarDayView = day;
        this.chosenDay = chosenDay;
        this.chosenMonth = chosenMonth;
    }

    private DashboardCalendarDay createDashboardDayButton(int day, int month) {
        DashboardCalendarDay calendarDay = new DashboardCalendarDay(getContext());
        calendarDay.setText(String.valueOf(day));
        return calendarDay;
    }

    private void updateCalendarText(int day, int month) {
        currentDateTextView.setText(String.valueOf(day));
        currentMonthTextView.setText(RU_MONTH_NAMES[month - 1]);
    }

    private DashboardCalendarDay createDashboardDayButtonWithAction(int day, int month) {
        DashboardCalendarDay calendarDay = new DashboardCalendarDay(getContext());
        calendarDay.setOnClickListener((view) -> {
            changeChosenDay((DashboardCalendarDay) view, day, month);
            if (onClickCalendarDay != null)
                onClickCalendarDay.click(calendarDay);
        });
        calendarDay.setText(String.valueOf(day));
        return calendarDay;
    }

    private Drawable getDrawableById(int id) {
        return ResourcesCompat.getDrawable(getResources(), id, null);
    }

    public void setOnClickCalendarDay(OnClickCalendarDay onClickCalendarDay) {
        this.onClickCalendarDay = onClickCalendarDay;
        onClickCalendarDay.click(chosenCalendarDayView);
    }

    public interface OnClickCalendarDay {
        void click(DashboardCalendarDay calendarDay);
    }

}