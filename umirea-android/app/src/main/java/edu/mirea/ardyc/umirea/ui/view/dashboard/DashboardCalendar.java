package edu.mirea.ardyc.umirea.ui.view.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.Calendar;

import edu.mirea.ardyc.umirea.R;

public class DashboardCalendar extends LinearLayout {

    private static final String[] RU_MONTH_NAMES = {"Января", "Февраля", "Марта", "Апреля",
            "Мая", "Июня", "Июля", "Августа",
            "Сентября", "Октября", "Ноября", "Декабря"};

    LinearLayout weekOneLayout;
    LinearLayout weekTwoLayout;
    LinearLayout weekThreeLayout;
    LinearLayout weekFourLayout;
    LinearLayout weekFiveLayout;
    LinearLayout weekSixLayout;
    private LinearLayout[] weeks;

    private int currentDateDay, chosenDateDay, currentDateMonth,
            chosenDateMonth, currentDateYear, chosenDateYear,
            pickedDateDay, pickedDateMonth, pickedDateYear;
    int userMonth, userYear;
    private DayClickListener mListener;

    private Calendar calendar;
    LinearLayout.LayoutParams defaultButtonParams;
    private LinearLayout.LayoutParams userButtonParams;

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

    public DashboardCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_calendar, this, true);
        calendar = Calendar.getInstance();

        weekOneLayout = view.findViewById(R.id.calendar_week_1);
        weekTwoLayout = view.findViewById(R.id.calendar_week_2);
        weekThreeLayout = view.findViewById(R.id.calendar_week_3);
        weekFourLayout = view.findViewById(R.id.calendar_week_4);
        weekFiveLayout = view.findViewById(R.id.calendar_week_5);
        weekSixLayout = view.findViewById(R.id.calendar_week_6);
        TextView currentDate = view.findViewById(R.id.current_date);
        TextView currentMonth = view.findViewById(R.id.current_month);

        currentDateDay = chosenDateDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (userMonth != 0 && userYear != 0) {
            currentDateMonth = chosenDateMonth = userMonth;
            currentDateYear = chosenDateYear = userYear;
        } else {
            currentDateMonth = chosenDateMonth = calendar.get(Calendar.MONTH);
            currentDateYear = chosenDateYear = calendar.get(Calendar.YEAR);
        }

        currentDate.setText(String.valueOf(currentDateDay));
        currentMonth.setText(RU_MONTH_NAMES[currentDateMonth]);

        initializeDaysWeeks();
        if (userButtonParams != null) {
            defaultButtonParams = userButtonParams;
        } else {
            defaultButtonParams = getDaysLayoutParams();
        }

        initCalendar();
//        initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay);

    }

    private void initializeDaysWeeks() {
        weeks = new LinearLayout[6];

        weeks[0] = weekOneLayout;
        weeks[1] = weekTwoLayout;
        weeks[2] = weekThreeLayout;
        weeks[3] = weekFourLayout;
        weeks[4] = weekFiveLayout;
        weeks[5] = weekSixLayout;
    }

    private void initCalendar() {
        LocalDate date = LocalDate.now();
        YearMonth ym = YearMonth.of(date.getYear(), date.getMonth());
        int daysInCurrentMonth = ym.lengthOfMonth();
        int daysInPastMonth = ym.minusMonths(1).lengthOfMonth();


        int firstDayOfCurrentMonth = date.withDayOfMonth(1).getDayOfWeek().getValue() - 1;
        System.out.println(date.getDayOfMonth());
        int day = 1;
        for (int week = 0; week < 6; week++) {
            for (int i = 0; i < 7; i++) {
                if (day <= firstDayOfCurrentMonth) {
                    DashboardCalendarDay previousMonthDay = createDashboardDayButton(daysInPastMonth - firstDayOfCurrentMonth + day);
                    previousMonthDay.setTextColor(Color.GRAY);
                    weeks[week].addView(previousMonthDay);
                } else if (day - firstDayOfCurrentMonth <= daysInCurrentMonth) {
                    DashboardCalendarDay currentMonthDay = createDashboardDayButton(day - firstDayOfCurrentMonth);
                    currentMonthDay.setTextColor(Color.WHITE);
                    if (day - firstDayOfCurrentMonth == date.getDayOfMonth()) {
                        currentMonthDay.setBackground(getContext().getDrawable(R.drawable.rounc_calendar_button));
                    }
                    weeks[week].addView(currentMonthDay);
                } else {
                    DashboardCalendarDay nextMonthDay = createDashboardDayButton(day - firstDayOfCurrentMonth - daysInCurrentMonth);
                    nextMonthDay.setTextColor(Color.GRAY);
                    weeks[week].addView(nextMonthDay);
                }
                day++;
            }
        }
    }

    private Button createDayButton(int dayText) {
        Button dayButton = new Button(this.getContext());
        dayButton.setLayoutParams(getDaysLayoutParams());
        dayButton.setText(String.valueOf(dayText));
        dayButton.setBackgroundColor(Color.TRANSPARENT);
        return dayButton;
    }

    private DashboardCalendarDay createDashboardDayButton(int dayText) {
        DashboardCalendarDay d = new DashboardCalendarDay(getContext());
        d.setText(String.valueOf(dayText));
        return d;
    }


    private LinearLayout.LayoutParams getDaysLayoutParams() {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }


    public interface DayClickListener {
        void onDayClick(View view);
    }

    public void setCallBack(DayClickListener mListener) {
        this.mListener = mListener;
    }

}