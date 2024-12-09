package ir.tasktop.taskTop.utils;

public enum Messages {

    WELLCOME("خوش آمدید"),
    UP_WRONG("نام کاربری یا رمز عبور نادرست است"),
    U_ALREADYEXISTS("نام کاربری قبلا ثبت شده است");

    String lable;

    Messages(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return lable;
    }
}
