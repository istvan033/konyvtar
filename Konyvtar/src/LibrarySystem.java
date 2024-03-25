import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LibrarySystem extends JFrame implements ActionListener {
    private DefaultListModel<String> model1, model2;
    private JList<String> list1, list2;
    private JButton visszahozvaButton, bezarButton;
    private Map<String, ArrayList<String>> kölcsönzések;

    public LibrarySystem() {
        setTitle("Könyvtár");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setResizable(false);

        kölcsönzések = new HashMap<>();
        kölcsönzések.put("John Doe", new ArrayList<>(Arrays.asList("Harry Potter", "Lord of the Rings")));
        kölcsönzések.put("Jane Smith", new ArrayList<>(Arrays.asList("To Kill a Mockingbird", "1984")));

        model1 = new DefaultListModel<>();
        for (String név : kölcsönzések.keySet()) {
            model1.addElement(név);
        }
        list1 = new JList<>(model1);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedPerson = list1.getSelectedValue();
                    if (selectedPerson != null) {
                        ArrayList<String> könyvek = kölcsönzések.get(selectedPerson);
                        if (könyvek != null) {
                            model2.clear();
                            for (String könyv : könyvek) {
                                model2.addElement(könyv);
                            }
                        }
                    }
                }
            }
        });

        model2 = new DefaultListModel<>();
        list2 = new JList<>(model2);
        Dimension preferredSize = new Dimension(list1.getPreferredSize().width / 2, list1.getPreferredSize().height / 2);
        list2.setPreferredSize(preferredSize); // List2 méretének beállítása feleakkorára

        visszahozvaButton = new JButton("Visszahozva");
        visszahozvaButton.addActionListener(this);

        bezarButton = new JButton("Bezár");
        bezarButton.addActionListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        listPanel.add(new JScrollPane(list1));
        listPanel.add(new JScrollPane(list2));
        panel.add(listPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(visszahozvaButton);
        buttonPanel.add(bezarButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visszahozvaButton) {
            String selectedBook = list2.getSelectedValue();
            if (selectedBook != null) {
                model2.removeElement(selectedBook);
            }
        } else if (e.getSource() == bezarButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LibrarySystem();
            }
        });
    }
}
