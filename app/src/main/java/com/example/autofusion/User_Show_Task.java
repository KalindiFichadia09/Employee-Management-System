package com.example.autofusion;

public class User_Show_Task {
    private String Emp_Email,Description,End_Date;

    public User_Show_Task(String Description, String End_Date) {
        this.Description = Description;
        this.End_Date = End_Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getEndDate() {
        return End_Date;
    }

    public void setEndDate(String EndDate) {
        this.End_Date = EndDate;
    }
}
