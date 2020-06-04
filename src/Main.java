
import Controller.LoginController;
import Controller.RegisterController;
import Controller.WindowController;
import View.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainWindow mainWindow = new MainWindow();
                WindowController windowController = new WindowController(mainWindow);

                LoginController loginController = new LoginController(mainWindow);
                RegisterController registerController = new RegisterController(mainWindow);

                mainWindow.getLoginPanel().setUpController(loginController,windowController);
                mainWindow.getRegisterPanel().setUpController(registerController,windowController);

            }
        });
    }
}
