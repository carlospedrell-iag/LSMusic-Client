package View;

import Controller.RegisterController;
import Controller.WindowController;

import javax.swing.*;

public class RegisterWindow extends JFrame {
    private JPanel main_panel;
    private JPanel container;
    private JButton submit_button;
    private JTextField form_name;
    private JPasswordField form_password;
    private JPasswordField form_confirmpassword;
    private JLabel title;
    private JPanel input;
    private JPanel form;
    private JButton back_button;
    private JTextField form_email;

    public RegisterWindow(){

        setSize(960,540);
        setTitle("LaSalleMusic");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(main_panel);

        submit_button.setActionCommand("submit_register");
        back_button.setActionCommand("login");
    }

    public void setUpController(RegisterController controller, WindowController win_controller) {
        submit_button.addActionListener(controller);

        back_button.addActionListener(win_controller);
    }

    public String getForm_name() {
        return form_name.getText();
    }

    public String getForm_password() {
        return String.valueOf(form_password.getPassword());
    }

    public String getForm_confirmpassword() {
        return String.valueOf(form_confirmpassword.getPassword());
    }

    public String getForm_email() {
        return form_email.getText();
    }
}
