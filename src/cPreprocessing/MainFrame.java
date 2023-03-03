package cPreprocessing;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;
public class MainFrame extends JFrame implements ActionListener{	
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu lexicalMenu;
	private JMenu  grammarMenu;
	private JMenu  calculatorMenu;
	private JMenu helpMenu;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem cutMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;
	private JMenuItem clearMenuItem;
	private JMenuItem lexicalMenuItem;
	private JMenuItem grammarMenuItem;
	private JMenuItem calculatorMenuItem;
	private JMenuItem helpMenuItem;
	private JMenuItem aboutMenuItem;
	private JButton openButton;
	private JButton saveButton;
	private JButton lexicalButton;
	private JButton grammarButton;
	private JButton calculatorButton;
	private JButton cutButton;
	private JButton copyButton;
	private JButton pasteButton;
	private JButton clearButton;
	private JButton exitButton;
	private JSplitPane splitPane0;
	private JSplitPane splitPane1;
	private JPanel leftPanel,rightPanel;
	private JTextArea fileContentTextArea;
	private JTextArea TextArea1;
	private JTextArea TextArea2;
	private JScrollPane scrollPane0;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private LexicalAnalysis lexicalAnalysis;
	public void createMenu(){
		menuBar = new JMenuBar();
		fileMenu = new JMenu("文件");
		editMenu = new JMenu("编辑");
		lexicalMenu = new JMenu("词法分析");
		grammarMenu = new JMenu("语法分析");
		calculatorMenu =new JMenu("表达式计算");
		helpMenu = new JMenu("帮助");
		ImageIcon openIcon=new ImageIcon("/image/open.png");
		openMenuItem = new JMenuItem("打开",openIcon);
		openMenuItem.addActionListener(this);
		ImageIcon saveIcon=new ImageIcon("/image/save.png");
		saveMenuItem = new JMenuItem("保存",saveIcon);
		saveMenuItem.addActionListener(this);
		ImageIcon exitIcon=new ImageIcon("/image/exit.png");
		exitMenuItem = new JMenuItem("退出",exitIcon);
		exitMenuItem.addActionListener(this);
		ImageIcon cutIcon=new ImageIcon("/image/cut.png");
		cutMenuItem = new JMenuItem("剪切",cutIcon);
		ImageIcon copyIcon=new ImageIcon("/image/copy.png");
		copyMenuItem = new JMenuItem("复制",copyIcon);
		ImageIcon pasteIcon=new ImageIcon("/image/paste.png");
		pasteMenuItem = new JMenuItem("粘贴",pasteIcon);
		ImageIcon clearIcon=new ImageIcon("/image/clear.png");
		clearMenuItem = new JMenuItem("清除",clearIcon);
		clearMenuItem.addActionListener(this);
		ImageIcon lexicalIcon=new ImageIcon("/image/grammar.png");
		lexicalMenuItem = new JMenuItem("词法分析",lexicalIcon);
		lexicalMenuItem.addActionListener(this);
		ImageIcon grammarIcon=new ImageIcon("/image/grammar.png");
		grammarMenuItem = new JMenuItem("语法分析",grammarIcon);
		grammarMenuItem.addActionListener(this);
		ImageIcon caculatorIcon=new ImageIcon("/image/caculator.png");
		calculatorMenuItem = new JMenuItem("表达式计算",caculatorIcon);
		calculatorMenuItem.addActionListener(this);
		ImageIcon helpIcon=new ImageIcon("/image/help.png");
		helpMenuItem = new JMenuItem("帮助文档",helpIcon);
		helpMenuItem.addActionListener(this);
		ImageIcon aboutIcon=new ImageIcon("/image/lexer.png");
		aboutMenuItem = new JMenuItem("关于",aboutIcon);
		aboutMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.add(clearMenuItem);
		lexicalMenu.add(lexicalMenuItem);
		grammarMenu.add(grammarMenuItem);
		calculatorMenu.add(calculatorMenuItem);
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(lexicalMenu);
		menuBar.add(grammarMenu);
		menuBar.add(calculatorMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}
	
	public void createToolBar(){
		toolBar=new JToolBar();
		
		openButton=new JButton(new ImageIcon("image/open.png"));
		saveButton=new JButton(new ImageIcon("image/save.png"));
		lexicalButton=new JButton(new ImageIcon("image/lexical.png"));
		grammarButton=new JButton(new ImageIcon("image/grammar.png"));
		calculatorButton=new JButton(new ImageIcon("image/calculator.png"));
		cutButton=new JButton(new ImageIcon("image/cut.png"));
		copyButton=new JButton(new ImageIcon("image/copy.png"));
		pasteButton=new JButton(new ImageIcon("image/paste.png"));
		clearButton=new JButton(new ImageIcon("image/clear.png"));
		exitButton=new JButton(new ImageIcon("image/exit.png"));
		
		openButton.addActionListener(this);
		saveButton.addActionListener(this);
		lexicalButton.addActionListener(this);
		grammarButton.addActionListener(this);
		calculatorButton.addActionListener(this);
		cutButton.addActionListener(this);
		copyButton.addActionListener(this);
		pasteButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		toolBar.add(openButton);
		toolBar.add(saveButton);
		toolBar.add(lexicalButton);
		toolBar.add(grammarButton);
		toolBar.add(calculatorButton);
		toolBar.add(cutButton);
		toolBar.add(copyButton);
		toolBar.add(pasteButton);
		toolBar.add(clearButton);
		toolBar.add(exitButton);
	}
	
	public void openFile(){
		JFileChooser fileChooser=new JFileChooser("打开文件");
        int isOpen=fileChooser.showOpenDialog(null);
        fileChooser.setDialogTitle("打开文件");
		if(isOpen==JFileChooser.APPROVE_OPTION){
        	String path=fileChooser.getSelectedFile().getPath();
        	fileContentTextArea.setText(readFromFile(path));
		}
	}
	
	public String readFromFile(String path){
		File file=new File(path);
		String s=null;
		try {
		try (FileInputStream fin = new FileInputStream(file)) {
			int length=fin.available();
			byte arr[]=new byte[length];
			int len=fin.read(arr);
			s=new String(arr,0,len);
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public void saveFile(){
		JFileChooser fileChooser=new JFileChooser("保存文件");
        int isSave=fileChooser.showSaveDialog(this);
        fileChooser.setDialogTitle("保存文件");
		String selectFileName="";
        if(isSave==JFileChooser.APPROVE_OPTION){
        	File file=fileChooser.getSelectedFile();
        	selectFileName=fileChooser.getName(file);
        	if(selectFileName.length()>0){
        		if(!selectFileName.endsWith(".c")&&!selectFileName.endsWith(".macro")){
        			selectFileName=selectFileName.concat(".macro");
        		}
        		
        		file=fileChooser.getCurrentDirectory();
        		file=new File(file.getPath().concat(File.separator).concat(selectFileName));
        		
        		if(file.exists()){
        			int i=JOptionPane.showConfirmDialog(this, "文件已经存在,是否覆盖?");
        			if(i!=JOptionPane.YES_OPTION){
        				return;
        			}
        		}
        		
        		try {
					file.createNewFile();
					FileWriter out=new FileWriter(file);
					out.write(fileContentTextArea.getText());
					out.close();
					JOptionPane.showMessageDialog(this, "保存成功!");
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}
	}
	
	public void doCut(){
		fileContentTextArea.cut();
	}
	
	public void doCopy(){
		int dot1,dot2;
		
		dot1=fileContentTextArea.getSelectionStart();
		
		dot2=fileContentTextArea.getSelectionEnd();
		
		if(dot1!=dot2){
			fileContentTextArea.copy();
			JOptionPane.showMessageDialog(null, "复制成功!");
		}
	}
	
	public void doPaste(){
		
		Clipboard clipboard=getToolkit().getSystemClipboard();
		
		Transferable content=clipboard.getContents(this);
		
		try {
			if(content.getTransferData(DataFlavor.stringFlavor) instanceof String){
				fileContentTextArea.paste();
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doClear(){
		fileContentTextArea.setText(null);
	}
	
	public void doLexical(){
		String s;
		s=fileContentTextArea.getText();
		if(s.length()>0){
			lexicalAnalysis = new LexicalAnalysis();
        	lexicalAnalysis.initNum();
        	lexicalAnalysis.mainFunc(s);
        	String finalString = "";
        	for(Vector<String> vector : lexicalAnalysis.resVector) {
			if(vector.get(0).equals("\n")){
				continue;
			}
            String temp = "(";
            for(int j = 0; j < vector.size() - 1; j++) {
                temp += (vector.get(j) + "," + " ");
            }
            temp += (vector.get(vector.size() - 1) + ")" + "\n");
            finalString += temp;
        	}
       		finalString = "词法分析结果为:" + "\n" + finalString;
			TextArea1.setText(finalString);
		}else{
			int j;
			j=JOptionPane.showConfirmDialog(this, "请输入程序!");
			if(j!=JOptionPane.YES_OPTION){
				return;
			}
		}
	}

	public void doGrammar(){
		String s;
		s=fileContentTextArea.getText();
		if(s.length()>1){
			GrammarAnalysis checkAllExpression = new GrammarAnalysis(s);
        	checkAllExpression.checkMacro();
        	String finalString = checkAllExpression.resString;
        	finalString = "语法分析结果为:" + "\n" + finalString+"分析完成\n";
			TextArea2.setText(finalString);
		}else{
			int j;
			j=JOptionPane.showConfirmDialog(this, "请输入程序!");
			if(j!=JOptionPane.YES_OPTION){
				return;
			}
		}
	}
	
	public void doCalculate(){
		String s;
		s=fileContentTextArea.getText();
		if(s.length()>1){
			Compute compute=new Compute(s);
			boolean tf=compute.finalCompute();
			String finalString = "";//compute.computeString;
        	finalString = "表达式计算结果为:" + "\n" + compute.computeString+"\n";
			TextArea2.setText(finalString);
			if(tf){
				fileContentTextArea.setText(compute.replaceString);
			}
		}else{
			int j;
			j=JOptionPane.showConfirmDialog(this, "请输入程序!");
			if(j!=JOptionPane.YES_OPTION){
				return;
			}
		}
	}
	
	public void doHelp(){
		try {
			String [] cmd={"cmd","/C","start "+System.getProperty("user.dir")+"\\doc\\help.doc"}; 
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void showAbout(){
		JDialog dialog=new JDialog();
		dialog.setTitle("C语言预处理");
		JLabel label=new JLabel("           基于Java的C语言预处理程序");
		dialog.add(label);
		dialog.setVisible(true);
		dialog.setSize(250, 160);
		dialog.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = dialog.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	public void doExit(){
		
		int isExit=JOptionPane.showConfirmDialog(this, "确认退出吗?");
		if(isExit==JOptionPane.YES_OPTION){
			System.exit(0);
		}else{
			return;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==openMenuItem){
            openFile();
		}else if(e.getSource()==saveMenuItem){
			saveFile();
		}else if(e.getSource()==exitMenuItem){
			doExit();
		}else if(e.getSource()==cutMenuItem){
			doCut();
		}else if(e.getSource()==copyMenuItem){
			doCopy();
		}else if(e.getSource()==pasteMenuItem){
			doPaste();
		}else if(e.getSource()==clearMenuItem){
			doClear();
		}else if(e.getSource()==lexicalMenuItem){
			doLexical();
		}else if(e.getSource()==grammarMenuItem){
			doGrammar();
		}else if(e.getSource()==calculatorMenuItem){
			doCalculate();
		}else if(e.getSource()==helpMenuItem){
			 doHelp();
		}else if(e.getSource()==aboutMenuItem){
			showAbout();
		}else if(e.getSource()==openButton){
			openFile();
		}else if(e.getSource()==saveButton){
			saveFile();
		}else if(e.getSource()==lexicalButton){
			doLexical();
		}else if(e.getSource()==grammarButton){
			doGrammar();
		}else if(e.getSource()==calculatorButton){
			doCalculate();
		}else if(e.getSource()==cutButton){
			doCut();
		}else if(e.getSource()==copyButton){
			doCopy();
		}else if(e.getSource()==pasteButton){
			doPaste();
		}else if(e.getSource()==clearButton){
			doClear();
		}else if(e.getSource()==exitButton){
			doExit();
		}
	}

	public void notice(String notification){
		TextArea2.append(notification);
	}
	public  MainFrame(){
		setTitle("C语言预处理");
		createMenu();
		createToolBar();
		
		leftPanel=new JPanel();
		leftPanel.setLayout(new GridLayout(1,1));
		fileContentTextArea=new JTextArea(100,100);
		fileContentTextArea.setFont(new Font("隶书",Font.BOLD,12));
		scrollPane0=new JScrollPane(fileContentTextArea);
    	scrollPane0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    	scrollPane0.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane0.getViewport().add(fileContentTextArea);
    	scrollPane0.getViewport().setPreferredSize(new Dimension(300,250));
		TextArea1 = new JTextArea(100,100);
    	TextArea1.setFont(new Font("隶书",Font.BOLD,12));
		TextArea1.setEditable(false);
		scrollPane1=new JScrollPane(TextArea1);
    	scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    	scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane1.getViewport().add(TextArea1);
    	scrollPane1.getViewport().setPreferredSize(new Dimension(300,100));
		splitPane0=new JSplitPane();
		splitPane0.setOrientation(0);
		splitPane0.setTopComponent(scrollPane0);
		splitPane0.setBottomComponent(scrollPane1);
		splitPane0.setDividerSize(5);
		leftPanel.add(splitPane0);
		
		rightPanel=new JPanel();
		rightPanel.setLayout(new GridLayout(1,1));
    	TextArea2 = new JTextArea(100,100);
    	TextArea2.setFont(new Font("隶书",Font.BOLD,12));
		TextArea2.setEditable(false);
		scrollPane2=new JScrollPane(TextArea2);
    	scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    	scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane2.getViewport().add(TextArea2);
    	scrollPane2.getViewport().setPreferredSize(new Dimension(300,100));
		rightPanel.add(scrollPane2);

		splitPane1=new JSplitPane();
		splitPane1.setLeftComponent(leftPanel);
		splitPane1.setRightComponent(rightPanel);
		splitPane1.setDividerSize(5);
		
		add("North",toolBar);
		add("Center",splitPane1);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 6, (screenSize.height - frameSize.height) / 6);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(800,600);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}	
}
