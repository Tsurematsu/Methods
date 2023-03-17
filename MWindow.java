
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Queue;


public class MWindow {
    static JComponent Temp_Component2;

    private static String Window_Title="";
    private static ArrayList<MetaPanel> list_Panels = new ArrayList<>();

    public static class MetaPanel extends JPanel{
        private String Title;
        private String Name;
        private Integer Width;
        private Integer Height;
        
        public MetaPanel(String Ticket) {
            this.setName(Ticket);
            this.Name = Ticket;
            this.setVisible(true);
            this.setEnabled(true);
            this.setBackground(Color.white);
            this.setLayout(null);
        }
        public String getTitle() {return Title;}
        public String getName() {return Name;}
        public int getWidth() {return Width;}
        public int getHeight() {return Height;}
        public void setTitle(String title) {Title = title;}
        public void setName(String name) {Name = name;}
        public void setWidth(Integer width) {Width = width;}
        public void setHeight(Integer height) {Height = height;}
    }

    public static JFrame this_Window;
    public static MetaPanel this_panel;

    public static JFrame start(String title) {
        Window_Title = title;
        this_Window = new JFrame();
        this_Window.setSize(700,600);
        this_Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// se detiene prog
        this_Window.setVisible(true); // hace visible la ventana
        this_Window.setBackground(Color.white);
        this_Window.setResizable(false);

        this_Window.setLocationRelativeTo(null);
        this_Window.setResizable(false); // evita se pueda modificar el tamaño de ventana
        this_Window.setTitle(title); // Le pone un titulo a la ventana
        this_Window.setBackground(Color.white);

        // this_window.setLayout(null); // elimina toda plantilla.


        return this_Window;
    }

    public static void Home_Window(){
        this_Window.setTitle(Window_Title);
    }

    private static Void SelectPanel(String T_name, Runnable Header){
        MetaPanel IsPanel = null;
        for (MetaPanel metaPanel : list_Panels) {
            metaPanel.setVisible(false);
            if (metaPanel.getName().equals(T_name) && IsPanel==null) {IsPanel=metaPanel;}
        }
        if (IsPanel==null) {
            MakePanel(T_name, Header);
        }else{
            IsPanel.setVisible(true);
            this_Window.setTitle(IsPanel.getTitle());
            this_Window.setSize(IsPanel.getWidth(), IsPanel.getHeight());
        }
        this_Window.setLocationRelativeTo(null);
        return null;
    }


    // private static final Color DeafulColor = new Color(25, 25, 25);
    private static final Color DeafulColor = new Color(255, 255, 255);

    private static Void MakePanel(String T_name, Runnable Header){return MakePanel(T_name, Header, DeafulColor);}
    private static Void MakePanel(String T_name, Runnable Header, Color Color){
        this_panel = new MetaPanel(T_name);
        Header.run();
        this_panel.setTitle(this_Window.getTitle());
        this_panel.setWidth(this_Window.getSize().width);
        this_panel.setHeight(this_Window.getSize().height);
        this_panel.setBackground(Color);
        list_Panels.add(this_panel);
        this_Window.add(this_panel);
        return null;
    }

    public static Runnable AddPanel(Object Ticket, Runnable Header, Runnable Feet) {
        if (list_Panels.size() <= 0) {MakePanel(Ticket.getClass().getName(), Header);}
        
        return ()->{SelectPanel(Ticket.getClass().getName(), Header); Feet.run();};
    }


    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public class controls {

        public static JFrame AddWindow(String Titulo){
            return AddWindow(Titulo, new JFrame());
        }
        public static JFrame AddWindow(String Titulo, JFrame standartInput){
            // JFrame standartInput=new JFrame();
            standartInput.requestFocus();
            this_Window.setEnabled(false);

            standartInput.setTitle(Titulo);
            standartInput.setLocation(10,10);
            standartInput.setSize(300,300);
            standartInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            standartInput.setLayout(null);
            standartInput.getContentPane().setBackground(Color.white);
            standartInput.getContentPane().setLayout(null);
            standartInput.setLocationRelativeTo(null);
            standartInput.setResizable(false);
            standartInput.setVisible(true);
            standartInput.addWindowListener(new WindowListener(){
                public void windowOpened(WindowEvent e) {}
                public void windowClosing(WindowEvent e) {
                    // Close Window
                    try {
                        this_Window.setEnabled(true);
                        this_Window.requestFocus();
                    } catch (Exception o) {
                        // TODO: handle exception
                    }
                }
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
            });
            return standartInput;
        }

        private static final Color DeafulColorButton = new Color(255, 255, 255);
        // private static final Color DeafulColorButton = new Color(30,30,30);
        public static JButton AddButton(String text, Runnable evento){return AddButton(text, evento, DeafulColorButton);}
        public static JButton AddButton(String text, Runnable evento, Color color){
            JButton element = new JButton();
            // this_panel.add(element);
            element.setLocation(10,10);
            element.setSize(100,50);
            element.setBackground(Color.WHITE);
            element.setBackground(color);
            element.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    evento.run();
                }
            });
            element.setText("<html>" + text + "</html>");
            // this_panel.repaint();
            return element;
        }

        public static JPanel AddImage(String Ruta){
            JPanel standart = new ObjectImage(Ruta);
            standart.setLocation(10,10);
            // standart.setSize(width, height);
            standart.setLayout(null);
            return standart;
        }

        static class ObjectImage extends JPanel{
            String ruta = "";
            Toolkit t = Toolkit.getDefaultToolkit ();
            Image imagen;
            public ObjectImage(String ruta) {
                this.ruta = ruta;
                imagen = t.getImage (ruta);
            }
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(imagen, 1, 1, this.getSize().width, this.getSize().height, this);
                this.repaint();
            }

        }

        static public JLabel AddLabel(String text){
            JLabel Standart = new JLabel();
            Standart.setHorizontalAlignment(SwingConstants.CENTER);
            Standart.setBounds(0,0, 150,25);
            Standart.setText(text);
            Temp_Component2 = Standart;
            return Standart;
        }

        static public JTextField AddTextField(String text){
                JTextField Standart = new JTextField();
                Standart.setBounds(0,0, 150,25);
                // Standart.setText(text);
                placeholder(text, Standart);
                Temp_Component2 = Standart;
                return Standart;
        }

        static public JTextField AddTextNumer(String text){
            JTextField Standart = new JTextField();
            Standart.setBounds(0,0, 150,25);
            Standart.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                int key = e.getKeyChar();
                boolean numeros = (key >= 48 && key <= 57);
                if (!numeros)
                {
                    e.consume();
                }

                if (Standart.getText().trim().length() == 10) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        placeholder(text, Standart);
        Temp_Component2 = Standart;
        return Standart;
    }

        static public JPasswordField AddPassword(String text){
            JPasswordField Standart = new JPasswordField();
            Standart.setBounds(0,0, 150,25);
            // Standart.setText(text);
            placeholder(text, Standart);
            Temp_Component2 = Standart;
            return Standart;
        }

        static public JTextArea AddTextArea(String text){
            JTextArea Standart = new JTextArea();
            Standart.setBounds(0,0, 150,25);
            Standart.setText(text);
            Temp_Component2 = (JComponent) Standart;
            return Standart;
        }

        static public JComboBox AddCombobox(String text){
            JComboBox Standart = new JComboBox();
            Standart.setBounds(0,0, 150,25);
            Standart.addItem(text);
            Temp_Component2 = Standart;
            return Standart;
        }

        static public JPanel AddPanel(Integer x, Integer y){
            JPanel Standart = new JPanel();
            Standart.setBounds(x,y, 500,500);
            Standart.setLayout(null);
            Temp_Component2 = Standart;
            return Standart;
        }

        static public JLabel[] AddTextShow(String text, String text1, Integer x, Integer y){
        JLabel Standart[] = {
            new JLabel(text),
            new JLabel(text1),
        };
        
        Standart[0].setBounds(x,y, 160,20);
        Standart[1].setBounds(x,y, 160,20);
        Standart[0].setBackground(new Color(186, 251, 148 ));
        Standart[1].setBackground(new Color(186, 251, 148 ));
        Linear.LinearListY(Standart[0], Standart[1]);
        return Standart;
    }

        static public JComponent[] AddTextAreaShow(String text, String text1, Integer x, Integer y){
            JComponent Standart[] = {
                new JLabel(text),
                new JTextArea(text1),
                new JScrollPane(),
            };
            
            Standart[0].setBounds(x,y, 160,20);
            Standart[0].setBackground(new Color(186, 251, 148 ));
            
            Standart[2] = new JScrollPane(Standart[1]);
            Standart[2].setBounds(x,y, 160,60);
            Standart[2].setBackground(new Color(186, 251, 148 ));
            
            JTextArea a = (JTextArea) Standart[1]; 
            a.setEditable(false);

            Linear.LinearListY(Standart[0], Standart[2]);
            Standart[2].setLocation(Standart[2].getLocation().x, Standart[2].getLocation().y + 10);
            return Standart;
        }
        
        static public ObjetTable AddTable(){
            return AddTable(new String[]{""});
        }
        static public ObjetTable AddTable(String[] Columns){
            ObjetTable element = new ObjetTable(ToArray(Columns));
            // this_panel.add(element.Contenedor);
            // this_panel.repaint();
            return element;
        }

        public static class  ObjetTable {
            public ArrayList<ArrayList<String>> TablaArray = new ArrayList<>();
            public DefaultTableModel Modelo = new DefaultTableModel();
            public JTable Tabla = new JTable(Modelo){public boolean isCellEditable(int rowIndex, int vColIndex) {return false;}};
            public JScrollPane Contenedor = new JScrollPane(Tabla);
            ObjetTable(ArrayList<String> Columnas){
                Contenedor.setBounds(10,10,200,200);
                Tabla.setBackground(Color.white);
                SyncColum(Columnas);
            };
    
            public void setLocation(Integer x, Integer y){Contenedor.setLocation(x, y);}
            public void setSize(Integer width, Integer height){Contenedor.setSize(width, height);}
    
            public punto getLocation(){return new punto(Contenedor.getLocation().x, Contenedor.getLocation().y);} 
            public Dimension getSize(){return Contenedor.getSize();} 
    
            void ResetAll(){
                JScrollPane guardado = new JScrollPane();
                guardado.setBounds(Contenedor.getBounds());
                Modelo = new DefaultTableModel();
                Tabla.setModel(Modelo);
                Contenedor.setBounds(guardado.getBounds());
            }
    
            void ResetConcent(){
                Modelo.getDataVector().clear();
                for (int i = 0; i < Tabla.getRowCount(); i++) {
                    Modelo.removeRow(i);
                }
            }
    
            public void SyncColum(String[] Columnas){SyncColum(ToArray(Columnas));}
            public void SyncColum(ArrayList<String> Columnas){
                ResetAll(); 
                for (int i = 0; i < Columnas.size(); i++) {
                    Modelo.addColumn(Columnas.get(i));
                }
            }


            public void SyncRow(String[][] Filas){SyncRow(ToArray(Filas));}
            public void SyncRow(ArrayList<ArrayList<String>> Filas){
                ResetConcent();
                TablaArray=Filas;
                for (int i = 0; i < Filas.size(); i++) {
                    Modelo.addRow(Filas.get(i).toArray());
                }
            }


            public void SyncAll(String[][] Todo){SyncAll(ToArray(Todo));}
            public void SyncAll(ArrayList<ArrayList<String>> Todo){
                SyncColum(Todo.get(0));
                Todo.remove(Todo.get(0));
                SyncRow(Todo);
            }


        }

        public static <T> ArrayList<T> convert(Queue<T> queue) {
            ArrayList<T> list = new ArrayList<>();
            list.addAll(queue);
            return list;
        }

        public static ArrayList<String> ToArray(String[] input){
            return new ArrayList<String>(Arrays.asList(input));
        }

        public static ArrayList<ArrayList<String>> ToArray(String[][] arr) {
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                ArrayList<String> innerList = new ArrayList<>();
                for (int j = 0; j < arr[i].length; j++) {
                    innerList.add(arr[i][j]);
                }
                result.add(innerList);
            }
            return result;
        }

        public static ArrayList<String> ToArray(Stack<String> stack) {
            ArrayList<String> result = new ArrayList<>();
            while (!stack.empty()) {
                result.add(stack.pop());
            }
            return result;
        }

        // Standart.Modelo.addRow(new String[]{"dato1","dato2", "dato3"});
        // Standart.Tabla.setFont(new java.awt.Font("Tahoma", 0, 30));
        // Standart.Tabla.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 20));
        // Standart.Tabla.getTableHeader().setBackground(Color.white);
        // Standart.Tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(10, 40));
        // Standart.Tabla.getColumnModel().getColumn(0).setMaxWidth(30);
        // Standart.Tabla.setRowHeight(100);
        // Standart.Tabla.setBackground(Color.white);

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

        static class pila{
            public static Stack<String> ToList(ArrayList<String> list) {
                Stack<String> result = new Stack<>();
                for (int i = 0; i < list.size(); i++) {
                    result.push(list.get(i));
                }
                return result;
            }
        }

        public static String[][] ToList(ArrayList<ArrayList<String>> list) {
            String[][] result = new String[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                ArrayList<String> innerList = list.get(i);
                result[i] = innerList.toArray(new String[innerList.size()]);
            }
            return result;
        }

        public static ArrayList<String> ToList(String[] arr) {
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                result.add(arr[i]);
            }
            return result;
        }
    







        // XXXXXXXXXXXXXXXXXXXXX
        public static class punto{
            public Integer x=0;
            public Integer y=0;
            punto(Integer x, Integer y){
                this.x=x; 
                this.y=y;
            };
        }

        // XX
    public static class Linear{

        public static void LinearListY(JComponent comp1, JComponent comp2){
        comp2.setLocation(comp1.getLocation().x, comp1.getLocation().y + comp1.getSize().height - 5);
        }
        public static void LinearListX(JComponent comp1, JComponent comp2){
            comp2.setLocation(comp1.getLocation().x + comp1.getSize().width - 5, comp1.getLocation().y);
        }

        public static ArrayList<String> ToArray(String[] input){
            return new ArrayList<String>(Arrays.asList(input));
        }

        public static Integer Mitad(JComponent input){
            return this_Window.getSize().width/2 - input.getSize().width/2;
        }

        public static void setMitad(JComponent input){
            input.setLocation(this_Window.getSize().width/2 - input.getSize().width/2, input.getLocation().y);
        }

        public static class Next {
            public static Void set_x(JComponent input, Integer sep){
                Temp_Component2.setLocation(input.getSize().width + input.getLocation().x + sep, Temp_Component2.getLocation().y);
                return null;
            }
            public static Void set_y(JComponent input, Integer sep){
                Temp_Component2.setLocation(Temp_Component2.getLocation().x, input.getSize().height + input.getLocation().y + sep);
                return null;
            }
            public static Integer x(JComponent input){return x(input, 0);}
            public static Integer x(JComponent input, Integer sep){
                return input.getLocation().x + input.getSize().width + sep; 
            }
            public static Integer y(JComponent input){return y(input, 0);}
            public static Integer y(JComponent input, Integer sep){
                return input.getLocation().y + input.getSize().height + sep; 
            }
        }

        public static void X(ArrayList<JComponent> lista,Integer sep, Integer X, Integer Y){
            X(lista, sep, X, Y, null);
        }
        public static void X(ArrayList<JComponent> lista,Integer sep, Integer X, Integer Y, Integer width){
            Integer AdX = X;    
            if (!(width==null)) {
                Integer sumaW=0;
                for (int i = 0; i < lista.size(); i++) {sumaW += lista.get(i).getSize().width + sep;}
                AdX = ((width/2)-(sumaW/2)) + X;    
            }
            
            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).setLocation(AdX, Y);
                AdX += lista.get(i).getSize().width + sep;
            }
        }
        public static void Y(ArrayList<JComponent> lista,Integer sep, Integer X, Integer Y){
            Y(lista, sep, X, Y, null);
        }
        public static void Y(ArrayList<JComponent> lista,Integer sep, Integer X, Integer Y, Integer height){
            Integer AdY = Y;  
            if (!(height==null)) {
                Integer sumaH=0;
                for (int i = 0; i < lista.size(); i++) {sumaH += lista.get(i).getSize().height + sep;}
                AdY = ((height/2)-(sumaH/2)) + Y;  
            }

            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).setLocation(X, AdY);
                AdY += lista.get(i).getSize().height + sep;
            }
        }
        
    }
        // XX


        public static void setFont(JButton label1, Integer size){
            label1.setFont(new java.awt.Font("Tahoma", 0, size));
        };

        public static void setFont(JLabel label1, Integer size){
            label1.setFont(new java.awt.Font("Tahoma", 0, size));
        };

        public static void setFont(JTextComponent label1, Integer size){
            label1.setFont(new java.awt.Font("Tahoma", 0, size));
        };

        public static void setColor(JComponent input, Integer R, Integer G, Integer B){
            input.setBackground(new Color(R, G, B));
        };



        // XXXXXXXXXXXX
    }


    


    public static void placeholder(String texto, JTextComponent elemento){
        TextPrompt placeholder = new TextPrompt(texto, elemento);
        
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
    }

    // XXXXXXXXXXXXXXXX
    public static class Computer {
        public static String Ruta = "./";
        public static void Write(Object input){Write(input, Ruta);}
        public static Object Read(){return Read(Ruta);}

        public static void Delete(){
            Delete(Ruta);
        }
        public static void Delete(String ruta){
            File FILER = new File(ruta);
            FILER.delete();
        }

        public static void Write(Object input, String ruta){
            try {
                ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(ruta));
                write.writeObject(input);
                write.close();
            } catch (Exception e) {
                System.out.println("Verifique si su objeto es Serializable (  implements Serializable )");
            }
        }
        public static Object Read(String ruta){
            Object retorno = null;
            try {
                ObjectInputStream read = new ObjectInputStream(new FileInputStream(ruta));
                retorno = read.readObject();
                read.close();
            } catch (Exception e) {
                System.out.println("Verifique si su objeto es Serializable (  implements Serializable )");
            }
            return retorno;
        }
    }
    // XXXXXXXXXXXXXXXX

    public static void println(Object input){System.out.println(input);}
    public static void print(Object input){System.out.println(input);}


    public class message{

        public static void msg(String text){
            msg(text, JOptionPane.ERROR_MESSAGE);
        }
    
        public static Integer msg_Option(String Mensaje,String[] text){
            Integer retorno=-1;
            String select =(String) JOptionPane.showInputDialog(null,Mensaje, "Option message",JOptionPane.QUESTION_MESSAGE,null,text, text[0]);
            if (select!=null) {
                for (int i = 0; i < text.length; i++) {
                    if (select.equals(text[i])) {
                        retorno = i;
                    }
                }
            }
            return (retorno); 
        }


        public static Integer msgOp(String text){
            return msgOp(text, "Alert");
        }
    
        public static boolean msgOp_yes(String text){
            return msgOp(text, "Alert")==JOptionPane.OK_OPTION;
        }
    
        public static Integer msgOp(String text, String title){
            return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION);
        }
    
        public static boolean msgOp_yes(String text, String title){
            return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION;
        }
    
        
        public static void msg(String text, Integer select){
            JOptionPane.showMessageDialog(null, text, "Security" ,select);
        }
    }

    public class ConversionUtils {

        public static Integer toInteger(String str) {
            return Integer.parseInt(str);
        }
    
        public static Integer toInteger(Double d) {
            return d.intValue();
        }
    
        public static Integer toInteger(Boolean b) {
            return b ? 1 : 0;
        }
    
        public static Integer toInteger(Character c) {
            return (int) c;
        }
    
        public static Double toDouble(String str) {
            return Double.parseDouble(str);
        }
    
        public static Double toDouble(Integer i) {
            return Double.valueOf(i);
        }
    
        public static Double toDouble(Boolean b) {
            return b ? 1.0 : 0.0;
        }
    
        public static Double toDouble(Character c) {
            return (double) (int) c;
        }
    
        public static Boolean toBoolean(String str) {
            return Boolean.parseBoolean(str);
        }
    
        public static Boolean toBoolean(Integer i) {
            return i != 0;
        }
    
        public static Boolean toBoolean(Double d) {
            return d != 0.0;
        }
    
        public static Boolean toBoolean(Character c) {
            return c == 't' || c == 'T' || c == 'y' || c == 'Y';
        }
    
        public static Character toCharacter(String str) {
            return str.charAt(0);
        }
    
        public static Character toCharacter(Integer i) {
            return (char) (int) i;
        }
    
        public static String toString(Integer i) {
            return String.valueOf(i);
        }
    
        public static String toString(Double d) {
            return String.valueOf(d);
        }
    
        public static String toString(Boolean b) {
            return String.valueOf(b);
        }
    
        public static String toString(Character c) {
            return String.valueOf(c);
        }
    }


    public static Double Random_Num(Double Minimo, Double Maximo){
        return Math.random()*(Minimo-Maximo)+Maximo;
    }

    public static Date FechaActual(){
        return new Date();
    };
    



    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX







    
        /**
     *  The TextPrompt class will display a prompt over top of a text component when
     *  the Document of the text field is empty. The Show property is used to
     *  determine the visibility of the prompt.
     *
     *  The Font and foreground Color of the prompt will default to those properties
     *  of the parent text component. You are free to change the properties after
     *  class construction.
     */
    public static class TextPrompt extends JLabel
    implements FocusListener, DocumentListener
    {
    public enum Show
    {
        ALWAYS,
        FOCUS_GAINED,
        FOCUS_LOST;
    }

    private JTextComponent component;
    private Document document;

    private Show show;
    private boolean showPromptOnce;
    private int focusLost;

    public TextPrompt(String text, JTextComponent component)
    {
        this(text, component, Show.ALWAYS);
    }

    public TextPrompt(String text, JTextComponent component, Show show)
    {
        this.component = component;
        setShow( show );
        document = component.getDocument();

        setText( text );
        setFont( component.getFont() );
        setForeground( component.getForeground() );
        setBorder( new EmptyBorder(component.getInsets()) );
        setHorizontalAlignment(JLabel.LEADING);

        component.addFocusListener( this );
        document.addDocumentListener( this );

        component.setLayout( new BorderLayout() );
        component.add( this );
        checkForPrompt();
    }

    /**
     *  Convenience method to change the alpha value of the current foreground
     *  Color to the specifice value.
     *
     *  @param alpha value in the range of 0 - 1.0.
     */
    public void changeAlpha(float alpha)
    {
        changeAlpha( (int)(alpha * 255) );
    }

    /**
     *  Convenience method to change the alpha value of the current foreground
     *  Color to the specifice value.
     *
     *  @param alpha value in the range of 0 - 255.
     */
    public void changeAlpha(int alpha)
    {
        alpha = alpha > 255 ? 255 : alpha < 0 ? 0 : alpha;

        Color foreground = getForeground();
        int red = foreground.getRed();
        int green = foreground.getGreen();
        int blue = foreground.getBlue();

        Color withAlpha = new Color(red, green, blue, alpha);
        super.setForeground( withAlpha );
    }

    /**
     *  Convenience method to change the style of the current Font. The style
     *  values are found in the Font class. Common values might be:
     *  Font.BOLD, Font.ITALIC and Font.BOLD + Font.ITALIC.
     *
     *  @param style value representing the the new style of the Font.
     */
    public void changeStyle(int style)
    {
        setFont( getFont().deriveFont( style ) );
    }

    /**
     *  Get the Show property
     *
     *  @return the Show property.
     */
    public Show getShow()
    {
        return show;
    }

    /**
     *  Set the prompt Show property to control when the promt is shown.
     *  Valid values are:
     *
     *  Show.AWLAYS (default) - always show the prompt
     *  Show.Focus_GAINED - show the prompt when the component gains focus
     *      (and hide the prompt when focus is lost)
     *  Show.Focus_LOST - show the prompt when the component loses focus
     *      (and hide the prompt when focus is gained)
     *
     *  @param show a valid Show enum
     */
    public void setShow(Show show)
    {
        this.show = show;
    }

    /**
     *  Get the showPromptOnce property
     *
     *  @return the showPromptOnce property.
     */
    public boolean getShowPromptOnce()
    {
        return showPromptOnce;
    }

    /**
     *  Show the prompt once. Once the component has gained/lost focus
     *  once, the prompt will not be shown again.
     *
     *  @param showPromptOnce  when true the prompt will only be shown once,
     *                         otherwise it will be shown repeatedly.
     */
    public void setShowPromptOnce(boolean showPromptOnce)
    {
        this.showPromptOnce = showPromptOnce;
    }

    /**
     *	Check whether the prompt should be visible or not. The visibility
    *  will change on updates to the Document and on focus changes.
    */
    private void checkForPrompt()
    {
        //  Text has been entered, remove the prompt

        if (document.getLength() > 0)
        {
            setVisible( false );
            return;
        }

        //  Prompt has already been shown once, remove it

        if (showPromptOnce && focusLost > 0)
        {
            setVisible(false);
            return;
        }

        //  Check the Show property and component focus to determine if the
        //  prompt should be displayed.

        if (component.hasFocus())
        {
            if (show == Show.ALWAYS
            ||  show ==	Show.FOCUS_GAINED)
                setVisible( true );
            else
                setVisible( false );
        }
        else
        {
            if (show == Show.ALWAYS
            ||  show ==	Show.FOCUS_LOST)
                setVisible( true );
            else
                setVisible( false );
        }
    }

    //  Implement FocusListener

    public void focusGained(FocusEvent e)
    {
        checkForPrompt();
    }

    public void focusLost(FocusEvent e)
    {
        focusLost++;
        checkForPrompt();
    }

    //  Implement DocumentListener

    public void insertUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }

    public void removeUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }

    public void changedUpdate(DocumentEvent e) {}
    }











    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


}
