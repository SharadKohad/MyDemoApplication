package model;

public class Recharge_History_Model
{
    String Member_ID;
    String Rech_Type;
    String Operator;
    String Mobile;
    String Mobile_Service_No;
    String Rech_Amount;
    String request_no;
    String rech_status;
    String transaction_no;
    String Created_Dt;

    public String getMember_ID() {
        return Member_ID;
    }

    public void setMember_ID(String member_ID) {
        Member_ID = member_ID;
    }

    public String getRech_Type() {
        return Rech_Type;
    }

    public void setRech_Type(String rech_Type) {
        Rech_Type = rech_Type;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile_Service_No() {
        return Mobile_Service_No;
    }

    public void setMobile_Service_No(String mobile_Service_No) {
        Mobile_Service_No = mobile_Service_No;
    }

    public String getRech_Amount() {
        return Rech_Amount;
    }

    public void setRech_Amount(String rech_Amount) {
        Rech_Amount = rech_Amount;
    }

    public String getRequest_no() {
        return request_no;
    }

    public void setRequest_no(String request_no) {
        this.request_no = request_no;
    }

    public String getRech_status() {
        return rech_status;
    }

    public void setRech_status(String rech_status) {
        this.rech_status = rech_status;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }

    public String getCreated_Dt() {
        return Created_Dt;
    }

    public void setCreated_Dt(String created_Dt) {
        Created_Dt = created_Dt;
    }
}
