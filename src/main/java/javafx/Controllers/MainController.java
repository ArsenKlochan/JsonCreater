package javafx.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private TextField fldLogin;
    @FXML private TextField fldPassword;
    @FXML private TextField fldConfirm;

    @FXML
    public void registrationOnClick() {
        if (fldPassword.getText()
                .equals(fldConfirm.getText())) {
            System.out.println(" Регистрация нового пользователя: ");
            System.out.println("Login : " + fldLogin.getText());
            System.out.println("Password : " + fldPassword.getText());
        } else {
            System.err.print("Введенные пароли не равны");
        }
    }
    @FXML
    public void cancelOnClick() {
        System.exit(0);
    }
}