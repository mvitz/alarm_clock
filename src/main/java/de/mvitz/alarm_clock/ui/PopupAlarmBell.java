package de.mvitz.alarm_clock.ui;

import javax.swing.JOptionPane;

import de.mvitz.alarm_clock.contract.AlarmBell;

public final class PopupAlarmBell implements AlarmBell {

    @Override
    public void ring() {
        JOptionPane.showMessageDialog(null, "Wake up!!!");
    }

}
