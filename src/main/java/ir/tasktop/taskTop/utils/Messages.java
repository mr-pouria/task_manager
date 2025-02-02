package ir.tasktop.taskTop.utils;

public enum Messages {

    WELLCOME("خوش آمدید"),
    UP_WRONG("نام کاربری یا رمز عبور نادرست است"),
    U_ALREADYEXISTS("نام کاربری قبلا ثبت شده است"),
    V_CODE_EXPIRED("کد اعتبارسنجی نادرست یا منقضی شده است"),
    INTERNAL_SERVER_ERROR("خطا در ارتباط با سرور"),
    AUTHENTICATION_FAILED("خطا در احراز هویت"),
    SENT_BEFORE("کد تایید برای این شماره موبایل از قبل فرستاده شده است");
    String lable;

    Messages(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return lable;
    }
}
