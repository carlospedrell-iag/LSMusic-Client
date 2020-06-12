package View;

import Model.Entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserPanel extends JPanel {
    private JPanel main_panel;
    private JTable user_table;
    private final int WIDTH = 100;

    private String[] columnNames = {"Username"};

    public UserPanel() {
        user_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        user_table.getTableHeader().setReorderingAllowed(false);
        user_table.getTableHeader().setUI(null);

        main_panel.setPreferredSize(new Dimension(WIDTH,-1));
        main_panel.setMinimumSize(new Dimension(WIDTH,-1));
        main_panel.setMaximumSize(new Dimension(WIDTH,-1));

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

    public JPanel getMain_panel() {
        return main_panel;
    }

}