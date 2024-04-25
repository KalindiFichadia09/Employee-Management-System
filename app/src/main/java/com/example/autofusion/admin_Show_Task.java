package com.example.autofusion;

public class admin_Show_Task {
    private String Emp_Email,Description,End_Date;

    public admin_Show_Task(String Emp_Email,String Description, String End_Date) {
        this.Emp_Email=Emp_Email;
        this.Description = Description;
        this.End_Date = End_Date;
    }

    public String getEmpEmail() {
        return Emp_Email;
    }

    public void setEmpEmail(String EmpEmail) {
        this.Emp_Email = EmpEmail;
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
