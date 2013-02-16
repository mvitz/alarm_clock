package de.mvitz.alarm_clock.contract;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public interface UI {

    void open();

    void time(DateTime time);

    void remainingTime(Duration remainingTime);

    void expire();

    void addObserver(Observer observer);

    interface Observer {
        void onAlarmSet(DateTime time, Duration duration);
    }

}
