package com.example.autofusion;

public class admin_Show_Leave {

    private String Emp_Email,Leave_Type,Leave_Category,Leave_Start_Date,Leave_End_Date,Leave_Remarks,Leave_Status;

    public admin_Show_Leave(String Emp_Email,String Leave_Type, String Leave_Category,String Leave_Start_Date,String Leave_End_Date,String Leave_Remarks,String Leave_Status) {
        this.Emp_Email = Emp_Email;
        this.Leave_Type = Leave_Type;
        this.Leave_Category = Leave_Category;
        this.Leave_Start_Date = Leave_Start_Date;
        this.Leave_End_Date = Leave_End_Date;
        this.Leave_Remarks = Leave_Remarks;
        this.Leave_Status = Leave_Status;
    }

    public String getEmpEmail() {
        return Emp_Email;
    }
    public void setEmpEmail(String EmpEmail) {
        this.Emp_Email = EmpEmail;
    }

    public String getLeaveType() {
        return Leave_Type;
    }
    public void setLeaveType(String LeaveType) {
        this.Leave_Type = LeaveType;
    }

    public String getLeaveCategory() {
        return Leave_Category;
    }
    public void setLeaveCategory(String LeaveCategory) {
        this.Leave_Category = LeaveCategory;
    }

    public String getStartDate() {
        return Leave_Start_Date;
    }
    public void setStartDate(String LeaveStartDate) {
        this.Leave_Start_Date = LeaveStartDate;
    }

    public String getLeaveEndDate() {
        return Leave_End_Date;
    }
    public void setLeaveEndDate(String LeaveEndDate) {
        this.Leave_End_Date = LeaveEndDate;
    }

    public String getLeaveRemarks() {
        return Leave_Remarks;
    }
    public void setLeaveRemarks(String LeaveRemarks) {
        this.Leave_Remarks = LeaveRemarks;
    }

    public String getLeaveStatus() {
        return Leave_Status;
    }
    public void setLeaveStatus(String LeaveStatus) {
        this.Leave_Status = LeaveStatus;
    }


}
