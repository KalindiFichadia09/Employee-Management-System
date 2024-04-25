package com.example.autofusion;

public class Show_Employee {

    private String Emp_Name, Emp_CEmail, Emp_Mobile, Emp_Dept;

    public Show_Employee(String Emp_Name, String Emp_CEmail, String Emp_Mobile, String Emp_Dept) {
        this.Emp_Name = Emp_Name;
        this.Emp_CEmail = Emp_CEmail;
        this.Emp_Mobile = Emp_Mobile;
        this.Emp_Dept = Emp_Dept;
    }

    public String getEmpName() {
        return Emp_Name;
    }

    public void setEmpName(String EmpName) {
        this.Emp_Name = EmpName;
    }

    public String getEmpCEmail() {
        return Emp_CEmail;
    }

    public void setEmpCEmail(String EmpCEmail) {
        this.Emp_CEmail = EmpCEmail;
    }

    public String getEmpMobile() {
        return Emp_Mobile;
    }

    public void setEmpMobile(String EmpMobile) {
        this.Emp_Mobile = EmpMobile;
    }

    public String getEmpDept() {
        return Emp_Dept;
    }

    public void setEmpDept(String EmpDept) {
        this.Emp_Dept = EmpDept;
    }

}
