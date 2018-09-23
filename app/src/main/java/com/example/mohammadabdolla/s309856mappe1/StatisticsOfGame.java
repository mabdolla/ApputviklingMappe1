package com.example.mohammadabdolla.s309856mappe1;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class StatisticsOfGame implements Comparable<StatisticsOfGame> {

    private Date date;
    private int total;
    private int rights;
    private int wrongs;


    public StatisticsOfGame(final Date date, final int total, final int rights, final int wrongs) {
        this.date = date;
        this.total = total;
        this.rights = rights;
        this.wrongs = wrongs;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public int getWrongs() {
        return wrongs;
    }

    public void setWrongs(int wrongs) {
        this.wrongs = wrongs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsOfGame that = (StatisticsOfGame) o;
        return total == that.total &&
                rights == that.rights &&
                wrongs == that.wrongs &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, total, rights, wrongs);
    }

    @Override
    public int compareTo(@NonNull StatisticsOfGame that) {
        return that.getDate().compareTo(this.date);
    }

    @Override
    public String toString() {
        return date +
                "," + total +
                "," + rights +
                "," + wrongs;
    }
}
