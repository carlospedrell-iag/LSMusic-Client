package View;

import Controller.LoginController;
import Controller.WindowController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private Image background_image;
    private Image shadow;

    public LoginPanel(){

        register_button.setActionCommand("register");

        ImageIcon icon = new ImageIcon("./resources/images/menu_image.png");
        JLabel image_label = new JLabel(icon);
        image_panel.add(image_label);

        try{
            background_image = ImageIO.read(new File("./resources/images/background.png"));
            shadow = ImageIO.read(new File("./resources/images/shadow.png"));
        } catch( IOException e){
            e.printStackTrace();
        }

        this.setBackground(new Color(47,50,52));
        this.setLayout(new GridLayout(1,1));
        main_panel.setOpaque(false);
        this.add(main_panel);


    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(47,50,52));
        g.drawRect(0,0,1920,1080);
        g.drawImage(background_image,0,0,1920,1020,null);
        g.drawImage(shadow,container.getX() -52,container.getY() -49,430,480,this);


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
