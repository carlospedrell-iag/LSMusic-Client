package View;

import Controller.UserController;
import Model.Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserPanel extends JPanel {
    private JPanel main_panel;
    private JTable user_table;
    private JButton follow_button;
    private JPopupMenu popupMenu;
    private JMenuItem unfollowUser;
    private final int WIDTH = 120;

    private String[] columnNames = {"Username"};

    public UserPanel() {
        user_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        user_table.getTableHeader().setReorderingAllowed(false);
        user_table.getTableHeader().setUI(null);

        main_panel.setPreferredSize(new Dimension(WIDTH,-1));
        main_panel.setMinimumSize(new Dimension(WIDTH,-1));
        main_panel.setMaximumSize(new Dimension(WIDTH,-1));

        Icon addIcon = new ImageIcon("./resources/icons/add.png");
        follow_button.setIcon(addIcon);
        follow_button.setActionCommand("follow");

        //pop-up menu per deixar de seguir
        popupMenu = new JPopupMenu();
        unfollowUser = new JMenuItem("Deixar de Seguir");
        unfollowUser.setActionCommand("unfollow");
        popupMenu.add(unfollowUser);
        user_table.setComponentPopupMenu(popupMenu);

    }

    public void refreshTable(ArrayList<User> users){
        //fem un petit i rapid override de la funcio isCellEditable de DefaultTableModel per desactivar l'edicio de camps
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(columnNames);

        for(User user: users){
            model.addRow(new Object[]{user.getName()});
        }

        user_table.setModel(model);
    }

    public void setUpController(UserController controller){
        user_table.addMouseListener(controller);
        unfollowUser.addActionListener(controller);
        follow_button.addActionListener(controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public JTable getUser_table() {
        return user_table;
    }
}