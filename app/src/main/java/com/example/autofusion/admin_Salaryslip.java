package com.example.autofusion;

public class admin_Salaryslip {
    private String Emp_Email,Start_Date,End_Date;
    long Basic_Pay,Allowance,Deduction,Adjustment,Total_Pay;

    public admin_Salaryslip(String Emp_Email,String Start_Date, String End_Date, long Basic_Pay, long Allowance,long Deduction,long Adjustment,long Total_Pay) {
        this.Emp_Email=Emp_Email;
        this.Start_Date = Start_Date;
        this.End_Date = End_Date;
        this.Basic_Pay = Basic_Pay;
        this.Allowance = Allowance;
        this.Deduction =Deduction;
        this.Adjustment=Adjustment;
        this.Total_Pay=Total_Pay;
    }

    public String getEmpEmail() {
        return Emp_Email;
    }

    public void setEmpEmail(String EmpEmail) {
        this.Emp_Email = EmpEmail;
    }
    public String getStartDate() {
        return Start_Date;
    }

    public void setStartDate(String StartDate) {
        this.Start_Date = StartDate;
    }

    public String getEndDate() {
        return End_Date;
    }

    public void setEndDate(String EndDate) {
        this.End_Date = EndDate;
    }

    public long getBasicPay() {
        return Basic_Pay;
    }

    public long setBasicPay(long BasicPay) {
        this.Basic_Pay = Basic_Pay;
        return BasicPay;
    }

    public long getAllowance() {
        return Allowance;
    }

    public long setAllowance(long Allowance) {
        this.Allowance = Allowance;
        return Allowance;
    }

    public long getDeduction() {
        return Deduction;
    }

    public long setDeduction(long Deduction) {
        this.Deduction = Deduction;
        return Deduction;
    }

    public long getAdjustment() {
        return Adjustment;
    }

    public long setAdjustment(long Adjustment) {
        this.Adjustment = Adjustment;
        return Adjustment;
    }
    public long getTotalPay() {
        return Total_Pay;
    }

    public long setTotalPay(long TotalPay) {
        this.Total_Pay = TotalPay;
        return Total_Pay;
    }
}
