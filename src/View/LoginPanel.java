package View;

import Controller.LoginController;
import Controller.WindowController;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class LoginPanel extends JPanel{
    private JPanel main_panel;
    private JTextField form_name;
    private JPasswordField form_password;
    private JButton submit_button;
    private JPanel form;
    private JLabel Title;
    private JPanel input;
    private JPanel container;
    private JButton register_button;
    private JPanel image_panel;


    public LoginPanel(){

        register_button.setActionCommand("register");

        ImageIcon icon = new ImageIcon("./resources/icons/menu_image.png");
        JLabel image_label = new JLabel(icon);
        image_panel.add(image_label);
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

    private void createUIComponents() {
        image_panel = new JPanel(new GridLayout(1,1));
    }
}
