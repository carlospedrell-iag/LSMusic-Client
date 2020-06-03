
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
                /*
                //initzialitzem les vistes
                LoginPanel loginPanel = new LoginPanel();
                loginPanel.setVisible(true);
                RegisterPanel registerWindow = new RegisterPanel();
                registerWindow.setVisible(false);

                //initzialitzem el controlador principal de finestres
                WindowController windowController = new WindowController(loginPanel,registerWindow);

                //initzialitzem els controladors per cada vista i afegim el controlador a cada vista
                LoginController loginController = new LoginController(loginPanel);
                loginPanel.setUpController(loginController,windowController);

                RegisterController registerController = new RegisterController(registerWindow);
                registerWindow.setUpController(registerController,windowController);
                */

                MainWindow mainWindow = new MainWindow();
                WindowController windowController = new WindowController(mainWindow);

                LoginController loginController = new LoginController(mainWindow.getLoginPanel());
                RegisterController registerController = new RegisterController(mainWindow.getRegisterPanel());

                mainWindow.getLoginPanel().setUpController(loginController,windowController);
                mainWindow.getRegisterPanel().setUpController(registerController,windowController);

            }
        });
    }
}
