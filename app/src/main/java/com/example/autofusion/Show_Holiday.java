package com.example.autofusion;

public class Show_Holiday {
    private String Id, Holiday_Name, Start_Date, End_Date;

    public Show_Holiday(String Id, String Holiday_Name, String Start_Date, String End_Date) {
        this.Id = Id;
        this.Holiday_Name = Holiday_Name;
        this.Start_Date = Start_Date;
        this.End_Date = End_Date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getHolidayName() {
        return Holiday_Name;
    }

    public void setHolidayName(String HolidayName) {
        this.Holiday_Name = HolidayName;
    }

    public String getStartDate() {
        return Start_Date;
    }

    public void setStartDate(String StartDate) {
        this.Start_Date = StartDate;
    }

    public String setEndDate() {
        return End_Date;
    }

    public void setEndDate(String EndDate) {
        this.End_Date = End_Date;
    }
}
