import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Calculator extends JFrame {
    
    // create components
    private JTextField input1 = new JTextField();
    private JTextField input2 = new JTextField();
    private JLabel operator = new JLabel("");
    private JLabel equal = new JLabel("=");
    private JLabel result = new JLabel("");
    private JButton add = new JButton("+");
    private JButton sub = new JButton("-");
    private JButton mul = new JButton("*");
    private JButton div = new JButton("/");
    private JButton ok = new JButton("OK");
    
    public JTextField getInput1() {
        return input1;
    }
    
    public JTextField getInput2() {
        return input2;
    }
    
    public JLabel getResultJLabel() {
        return result;
    }
    
    public JButton getAddButton() {
        return add;
    }
    
    public JButton getSubButton() {
        return sub;
    }
    
    public JButton getMulButton() {
        return mul;
    }
    
    public JButton getDivButton() {
        return div;
    }
    
    public JButton getOkButton() {
        return ok;
    }
    
    
    public Calculator() {
        super("Easy Calculator");
        
        // initial the size of window, location and unsizable 
        this.setResizable(false);
        this.setSize(400, 150);
        this.setLocation(200, 200);
        
        // make alignment of center
        input1.setHorizontalAlignment(0);
        input2.setHorizontalAlignment(0);
        operator.setHorizontalAlignment(0);
        equal.setHorizontalAlignment(0);
        result.setHorizontalAlignment(0);
        
        // create lineborder of the JLabel
        operator.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        equal.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        result.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // create the layout of grid
        this.setLayout(new GridLayout(2, 5, 10, 10));
        
        this.add(input1);
        this.add(operator);
        this.add(input2);
        this.add(equal);
        this.add(result);
        this.add(add);
        this.add(sub);
        this.add(mul);
        this.add(div);
        this.add(ok);
        
        // add the event of all of the button
        add.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                operator.setText(e.getActionCommand());
            }
        });
        
        sub.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                operator.setText(e.getActionCommand());
            }
        });
        
        mul.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                operator.setText(e.getActionCommand());
            }
        });

        div.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                operator.setText(e.getActionCommand());
            }
        });
        
        ok.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String format = "%.2f";
                
                Double num1 = new Double(input1.getText());
                Double num2 = new Double(input2.getText());
                
                Double res = 0.0;
                switch (operator.getText()) {
                case "+":
                    res = num1 + num2;
                    break;
                
                case "-":
                    res = num1 - num2;
                    break;
                
                case "*":
                    res = num1 * num2;
                    break;
                
                case "/":
                    res = num1 / num2;
                    break;

                default:
                    break;
                }
                result.setText(String.format(format, res));
            }
        });
        
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        new Calculator();
    }

}
