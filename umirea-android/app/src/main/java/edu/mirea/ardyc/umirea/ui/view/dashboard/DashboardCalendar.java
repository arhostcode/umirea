package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import edu.mirea.ardyc.umirea.R;
import edu.mirea.ardyc.umirea.databinding.DashboardCalendarBinding;

public class DashboardCalendar extends LinearLayout {

    private static final String[] RU_MONTH_NAMES = {"Января", "Февраля", "Марта", "Апреля",
            "Мая", "Июня", "Июля", "Августа",
            "Сентября", "Октября", "Ноября", "Декабря"};

    DashboardCalendarBinding dashboardCalendarBinding;
    private TextView currentDateView;
    private TextView currentMonthView;
    private int chosenDay, chosenMonth, year;
    private int currentDay, currentMonth;

    private LinearLayout[] weeks;

    public DashboardCalendar(Context context) {
        super(context);
        init(context);
    }

    public DashboardCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        dashboardCalendarBinding = DashboardCalendarBinding.inflate(LayoutInflater.from(context), this, true);
        dashboardCalendarBinding.left.setOnClickListener((v) -> {
            chosenMonth--;
            setCalendarMonth(year, Month.of(chosenMonth));
        });
        dashboardCalendarBinding.right.setOnClickListener((v) -> {
            chosenMonth++;
            setCalendarMonth(year, Month.of(chosenMonth));
        });
        initCurrentDate();
        initializeDaysWeeks();
        initCalendar();
    }


    private void initCurrentDate() {
        LocalDate date = LocalDate.now();
        currentDateView = dashboardCalendarBinding.currentDate;
        currentMonthView = dashboardCalendarBinding.currentMonth;
        currentDateView.setText(String.valueOf(date.getDayOfMonth()));
        currentMonthView.setText(RU_MONTH_NAMES[date.getMonth().getValue() - 1]);
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
        chosenDay = date.getDayOfMonth();
        chosenMonth = date.getMonth().getValue();
        currentDay = date.getDayOfMonth();
        currentMonth = date.getMonth().getValue();
        year = date.getYear();
        setCalendarMonth(date.getYear(), date.getMonth());
    }

    private void setCalendarMonth(int year, Month month) {
        currentDateView.setText(String.valueOf(chosenDay));
        currentMonthView.setText(RU_MONTH_NAMES[chosenMonth - 1]);
        LocalDate date = LocalDate.of(year, month, 1);
        YearMonth ym = YearMonth.of(date.getYear(), date.getMonth());
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
                    if (day - firstDayOfCurrentMonth == currentDay && currentMonth == ym.getMonth().getValue()) {
                        currentMonthDay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.current_day_shape, null));
                    }
                    currentMonthDay.addLesson(R.drawable.calendar_circle_lection);
                    currentMonthDay.addLesson(R.drawable.calendar_circle_lection);
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

    private void changeChosenDay(int chosenDay, int chosenMonth, int year) {
        currentDateView.setText(String.valueOf(chosenDay));
        currentMonthView.setText(RU_MONTH_NAMES[chosenMonth - 1]);
        LocalDate date = LocalDate.of(year, this.chosenMonth, 1);
        YearMonth ym = YearMonth.of(date.getYear(), date.getMonth());
        int daysInCurrentMonth = ym.lengthOfMonth();
        int firstDayOfCurrentMonth = date.withDayOfMonth(1).getDayOfWeek().getValue() - 1;
        int day = 1;
        for (int week = 0; week < 6; week++) {
            for (int i = 0; i < 7; i++) {
                if (day - firstDayOfCurrentMonth <= daysInCurrentMonth) {
                    if (day - firstDayOfCurrentMonth == this.chosenDay && this.chosenMonth == ym.getMonth().getValue()) {
                        if (day - firstDayOfCurrentMonth != currentDay) {
                            weeks[week].getChildAt(i).setBackground(null);
                        }
                    }
                    if (day - firstDayOfCurrentMonth == chosenDay && chosenMonth == ym.getMonth().getValue()) {
                        weeks[week].getChildAt(i).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounc_calendar_button, null));
                        System.out.println(day);
                    }
                }
                day++;
            }
        }
        this.chosenDay = chosenDay;
        this.chosenMonth = chosenMonth;
    }

    private DashboardCalendarDay createDashboardDayButton(int day, int month) {
        DashboardCalendarDay calendarDay = new DashboardCalendarDay(getContext());
        calendarDay.setText(String.valueOf(day));
        return calendarDay;
    }

    private DashboardCalendarDay createDashboardDayButtonWithAction(int day, int month) {
        DashboardCalendarDay calendarDay = new DashboardCalendarDay(getContext());
        calendarDay.setOnClickListener((view) -> {
            changeChosenDay(day, month, year);
        });
        calendarDay.setText(String.valueOf(day));
        return calendarDay;
    }


}