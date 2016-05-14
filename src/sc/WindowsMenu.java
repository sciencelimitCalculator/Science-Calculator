package sc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;



public class WindowsMenu extends JFrame implements ActionListener{
	JMenuBar menubar;               //菜单条
	JMenu    menu1,menu2,menu3,subMenu;          //菜单
	JMenuItem item1,item2,item3;         // 菜单项
	//JTextField text;
	JTextField text;
	
	JPanel   panel,commandspanel;
	private final String[] KEYS = { "7", "8", "9", "/", "sqrt", "4", "5", "6",
			"*", "%", "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "=" };
	/** 计算器上的功能键的显示名字 */
	private JButton keys[] = new JButton[KEYS.length];
	/** 计算器上的功能键的按钮 */
	private final String[] COMMAND = { "Backspace", "CE", "C" };
	/** 计算器上键的按钮 */
	private JButton commands[] = new JButton[COMMAND.length];
	/** 计算器左边的M的按钮 */
	
	private boolean firstDigit = true;
	// 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字

	private double resultNum = 0.0;
	// 计算的中间结果。
	
	private String operator = "=";
	// 当前运算的运算符
	
	private boolean operateValidFlag = true;
	// 操作是否合法
	public WindowsMenu(String s,int x,int y,int w,int h){
		init(s);//初始化方法
		setLocation(x,y);
		setSize(w,h);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	void init(String s){
		setTitle(s);
		//setLayout(new FlowLayout()); //居中布局
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,5,3,3));  //设置功能键
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			panel.add(keys[i]);
			keys[i].setForeground(Color.blue);
		}
		commandspanel = new JPanel();
		commandspanel.setLayout(new GridLayout(1,3,3,3));
		
		for(int i=0;i<COMMAND.length;i++){         //设置command按键
			commands[i] = new JButton(COMMAND[i]);
			commandspanel.add(commands[i]);
			commands[i].setForeground(Color.red);
			
		}
		 text = new JTextField();   //输入文本框
		/**
		 *  JScrollPane scl =new JScrollPane(text);
		 *   this.add(scl);
		 */
	       
		 text.setHorizontalAlignment(JTextField.RIGHT); //右对齐
		 
		 text.setEditable(false); //禁止修改
	    
	
		// 做三个画布，一个画布放文本框，一个放commands和按钮，一个整体画布放前面两个画布
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(text,BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout(3,3));
		panel2.add(commandspanel,BorderLayout.NORTH);
		panel2.add(panel, BorderLayout.CENTER);
		
		//整体布局
		
		getContentPane().setLayout(new BorderLayout(3,5));
		getContentPane().add("North",panel1);
		getContentPane().add("Center",panel2);
		
		//菜单部分
		menubar = new JMenuBar();
		menu1 = new JMenu("查看（V）");
		menu2 = new JMenu("编辑（E）");
		menu3 = new JMenu("帮助（H）");
		item1 = new JMenuItem("科学型");
		item2 = new JMenuItem("查看帮助");
		item3 = new JMenuItem("标准型");
		item1.setAccelerator(KeyStroke.getKeyStroke('a'));
		//item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,InputEvent.CTRL_MASK));//ctrl+1快捷键
		item2.setAccelerator(KeyStroke.getKeyStroke("F1"));
		item3.setAccelerator(KeyStroke.getKeyStroke('q'));
		menu1.setMnemonic('V');//设置快捷键alt+V
		menu2.setMnemonic('E');
		menu3.setMnemonic('H');
		menu1.add(item1);
		menu1.addSeparator();//分隔符行
		menu1.add(item3);
		menu3.add(item2);
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);
		setJMenuBar(menubar);
		// 设置监听
		
		for (int i = 0; i < KEYS.length; i++) {
			keys[i].addActionListener(this);
		}
		for(int i=0;i<COMMAND.length;i++){
			commands[i].addActionListener(this);
		}
	}

	//设置退格按键
	private void handleBackspace(){
		
		String t = text.getText();
		int i=t.length();
		if(i>0){
			
         t = t.substring(0, i - 1);
     	     // 退格，将文本最后一个字符去掉
		}
		else if(i==0){
			text.setText("0"); // 没有内容，初始化
			firstDigit = true;
			operator = "=";
			
		}
		else{
			text.setText(t);
		}
		
	}
	@Override
	/**
	 * 事件处理方法
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		String u = e.getActionCommand();
		if(u.equals(COMMAND[0])){
			 handleBackspace(); // 用户按了"Backspace"键
		}
		else if (u.equals(COMMAND[1])) {
			// 用户按了"CE"键
			text.setText("0");
		} else if (u.equals(COMMAND[2])) {
			// 用户按了"C"键
			handleC();
		} else if ("0123456789.".indexOf(u) >= 0) {
			// 用户按了数字键或者小数点键
			handleNumber(u);
			// handlezero(zero);
		} else {
			// 用户按了运算符键
			handleOperator(u);
		}
	}
	/**
	 * 处理数字键被按下的事件
	 * 
	 * @param key
	 */
	private void handleNumber(String key) {
		if (firstDigit) {
			// 输入的第一个数字
			text.setText(key);
		} else if ((key.equals(".")) && (text.getText().indexOf(".") < 0)) {
			// 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
			text.setText(text.getText() + ".");
		} else if (!key.equals(".")) {
			// 如果输入的不是小数点，则将数字附在结果文本框的后面
			text.setText(text.getText() + key);
		}
		// 以后输入的肯定不是第一个数字了
		firstDigit = false;
	}

	/**
	 * 处理C键被按下的事件
	 */
	private void handleC() {
		// 初始化计算器的各种值
		text.setText("0");
		firstDigit = true;
		operator = "=";
	}

	/**
	 * 处理运算符键被按下的事件
	 * 
	 * @param key
	 */
	private void handleOperator(String key) {
		if (operator.equals("/")) {
			// 除法运算
			// 如果当前结果文本框中的值等于0
			if (getNumberFromText() == 0.0) {
				// 操作不合法
				operateValidFlag = false;
				text.setText("除数不能为零");
			} else {
				resultNum /= getNumberFromText();
			}
		} else if (operator.equals("1/x")) {
			// 倒数运算
			if (resultNum == 0.0) {
				// 操作不合法
				operateValidFlag = false;
				text.setText("零没有倒数");
			} else {
				resultNum = 1 / resultNum;
			}
		} else if (operator.equals("+")) {
			// 加法运算
			resultNum += getNumberFromText();
		} else if (operator.equals("-")) {
			// 减法运算
			resultNum -= getNumberFromText();
		} else if (operator.equals("*")) {
			// 乘法运算
			resultNum *= getNumberFromText();
		} else if (operator.equals("sqrt")) {
			// 平方根运算
			resultNum = Math.sqrt(resultNum);
		} else if (operator.equals("%")) {
			// 百分号运算，除以100
			resultNum = resultNum / 100;
		} else if (operator.equals("+/-")) {
			// 正数负数运算
			resultNum = resultNum * (-1);
		} else if (operator.equals("=")) {
			// 赋值运算
			resultNum = getNumberFromText();
		}
		if (operateValidFlag) {
			// 双精度浮点数的运算
			long t1;
			double t2;
			t1 = (long) resultNum;
			t2 = resultNum - t1;
			if (t2 == 0) {
				text.setText(String.valueOf(t1));
			} else {
				text.setText(String.valueOf(resultNum));
			}
		}
		// 运算符等于用户按的按钮
		operator = key;
		firstDigit = true;
		operateValidFlag = true;
	}
	

	/**
	 * 从结果文本框中获取数字
	 * 
	 * @return
	 */
	private double getNumberFromText() {
		double result = 0;
		try {
			result = Double.valueOf(text.getText()).doubleValue();
		} catch (NumberFormatException e) {
		}
		return result;
	}
}
