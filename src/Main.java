
import Controller.LoginController;
import Controller.RegisterController;
import Controller.WindowController;
import View.LoginWindow;
import View.RegisterWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //initzialitzem les vistes
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setVisible(true);
                RegisterWindow registerWindow = new RegisterWindow();
                registerWindow.setVisible(false);

                //initzialitzem el controlador principal de finestres
                WindowController windowController = new WindowController(loginWindow,registerWindow);

                //initzialitzem els controladors per cada vista i afegim el controlador a cada vista
                LoginController loginController = new LoginController(loginWindow);
                loginWindow.setUpController(loginController,windowController);

                RegisterController registerController = new RegisterController(registerWindow);
                registerWindow.setUpController(registerController,windowController);
            }
        });
    }
}
