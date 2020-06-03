package View;

import Controller.LoginController;
import Controller.WindowController;

import javax.swing.*;

public class LoginPanel extends JPanel{
    private JPanel main_panel;
    private JTextField form_name;
    private JPasswordField form_password;
    private JButton submit_button;
    private JPanel form;
    private JLabel Title;
    private JPanel input;
    private JLabel main_title;
    private JPanel container;
    private JPanel title_panel;
    private JButton register_button;


    public LoginPanel(){

        register_button.setActionCommand("register");

    }

    public void setUpController(LoginController controller, WindowController win_controller) {
        submit_button.addActionListener(controller);

        register_button.addActionListener(win_controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public String getForm_name() {
        return form_name.getText();
    }

    public String getForm_password() {
        return String.valueOf(form_password.getPassword());
    }
}
