import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//главная форма
public class MainFrame extends JFrame {

    /**
     * Creates new form MainFrame
     */
    JMenuBar mbar; //меню
    JFrame refer, about; //справка и о разработчике

    public MainFrame() {
        initComponents();

        //создание меню
        refer = about = null;
        mbar = new JMenuBar();
        JMenu menu = new JMenu("О программе");
        JMenuItem item1 = new JMenuItem("Справка");
        JMenuItem item2 = new JMenuItem("О разработчике");
        menu.add(item1);
        menu.add(item2);

        //добавить прослушку нажатия на пункты подменю
        item1.addActionListener(new ReferenceForm());
        item2.addActionListener(new AboutForm());

        //замена
        mbar.add(menu);
        setJMenuBar(mbar);
    }

    //справка
    public class ReferenceForm implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (refer != null) {
                refer.dispose();
            }
            refer = new JFrame();
            refer.setVisible(true);
            refer.setSize(new Dimension(400, 400));
            refer.setPreferredSize(new Dimension(300, 300));
            refer.setTitle("Справка");
            refer.setLayout(null);

            JLabel lb0 = new JLabel("<html>'Старт' - запускает или ставит игру на паузу ('Space' - горячая клавиша);</html>");
            lb0.setBounds(10, 10, 300, 50);
            refer.add(lb0);

            JLabel lb1 = new JLabel("<html>'Сброс' - применяет все выставленные настройки к игре и откатывает её содержимое назад.</html>");
            lb1.setBounds(10, 50, 300, 50);
            refer.add(lb1);

            JLabel lb2 = new JLabel("<html>'Цвет змеи/фона' - после выбора цвета, чтобы заметить изменения можно нажать на 'Сброс'.</html>");
            lb2.setBounds(10, 110, 300, 50);
            refer.add(lb2);

            JLabel lb3 = new JLabel("<html>Красным цветом помечены мыши;<br>Зеленым цветом помечена змея;<br>Серым цветом помечены препятствия.</html>");
            lb3.setBounds(10, 180, 300, 60);
            refer.add(lb3);

            JLabel lb4 = new JLabel("<html>Рекорд для игры хранится в файле score.txt в корне программы и если его не будет, то программа автоматически его создаст.</html>");
            lb4.setBounds(10, 260, 300, 70);
            refer.add(lb4);

            refer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    //о разработчике
    public class AboutForm implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (about != null) {
                about.dispose();
            }
            about = new JFrame();
            about.setVisible(true);
            about.setSize(new Dimension(300, 300));
            about.setPreferredSize(new Dimension(300, 300));
            about.setTitle("О разработчике");
            about.setLayout(null);

            JLabel lb = new JLabel("Разработчик: Талипов Р.Н.");
            lb.setBounds(10, 10, 200, 20);
            about.add(lb);

            about.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Змейка");
        setPreferredSize(new java.awt.Dimension(550, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 633, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 446, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new MainFrame();
                f.setVisible(true);
                f.setLayout(null);
                f.setResizable(false);
                GamePanel gp = new GamePanel();
                gp.setBounds(10, 10, 520, 385); //позиция и размеры игры
                gp.setLayout(null);
                gp.setBorder(BorderFactory.createLineBorder(Color.black));
                f.add(gp);
            }
        });
    }

    // Variables declaration - do not modify
    // End of variables declaration
}