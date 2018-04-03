package blockchain;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Test {

        static JFrame frame;
        static JTextArea minedTV;
        static JTextArea notiTV;
        static JTextArea sendCoinsTV;
        static JTextArea transactionsTV;
        
	static List<Block> blockChain = new ArrayList<Block>();
	
	public static int difficulty = 4;

	public static void main(String[] args) throws InterruptedException {
            
          makeFrame();
          
	  BitCoinEngine bitCoinEngine = new BitCoinEngine();
	  
	  ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	  scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			bitCoinEngine.releaseBlock();
		}
	   }, 5,5, TimeUnit.SECONDS);
	  
	  Account a = new Account(bitCoinEngine,"A");
	  Account b = new Account(bitCoinEngine,"B");
	  Account c = new Account(bitCoinEngine,"C");
	  Account d = new Account(bitCoinEngine,"D");
	  Account e = new Account(bitCoinEngine,"E");
	  Account f = new Account(bitCoinEngine,"F");
	  
	  
	  ScheduledExecutorService mineBlockExecutor = Executors.newScheduledThreadPool(1);
	  mineBlockExecutor.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			try {
				a.mineBlock(bitCoinEngine.blockToBeMinned,difficulty);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   }, 2,2, TimeUnit.SECONDS);
	  
	  ScheduledExecutorService mineBlockExecutorB = Executors.newScheduledThreadPool(1);
	  mineBlockExecutorB.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			try {
				b.mineBlock(bitCoinEngine.blockToBeMinned,difficulty);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   }, 2,2, TimeUnit.SECONDS);
	  
	  
	  System.out.println("TEST : " + bitCoinEngine.unconfirmedTrasactions.size());
	  
  
	  ScheduledExecutorService executorForTransactions = Executors.newScheduledThreadPool(1);
	  executorForTransactions.scheduleWithFixedDelay(new Runnable() {
		
		@Override
		public void run() {
			a.sendBitcoin(b.getSendingAddress(), 10);
			b.sendBitcoin(c.getSendingAddress(), 15);
			c.sendBitcoin(a.getSendingAddress(), 20);
			d.sendBitcoin(b.getSendingAddress(), 20);
			a.sendBitcoin(c.getSendingAddress(), 20);
			e.sendBitcoin(f.getSendingAddress(), 20);
			
		}
	   }, 2, 2, TimeUnit.SECONDS);

	  System.out.println("TEST : " + bitCoinEngine.unconfirmedTrasactions.size());
	}

	private static boolean isBlockChainValid(List<Block> blockChain) {
		if (blockChain.size() > 1) {
			for (int i = 1; i <= blockChain.size()-1; i++) {
				Block currentBlock = blockChain.get(i-1);
				Block nextBlock = blockChain.get(i);
				if (!(nextBlock.previousHash.equals(currentBlock.currentHash))) {
					return false;
				}
			}
		}
		return true;
	}

        
        public static void makeFrame(){
          frame =  new JFrame("BlockChain");
          frame.getContentPane().setBackground(Color.black);
          
          sendCoinsTV = new JTextArea();
          sendCoinsTV.setBounds(10,10,400,400);
          sendCoinsTV.setBackground(Color.black);
          sendCoinsTV.setEditable(false);
          sendCoinsTV.setLineWrap(true);
          sendCoinsTV.setForeground(Color.red);
          JScrollPane jspsendCoinsTV = new JScrollPane(sendCoinsTV);
          jspsendCoinsTV.setVerticalScrollBarPolicy(
          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          jspsendCoinsTV.setBounds(10,10,400,400);
          frame.add(jspsendCoinsTV);
          
          notiTV = new JTextArea();
          notiTV.setBounds(10,420,400,400);
          notiTV.setBackground(Color.black);
          notiTV.setLineWrap(true);
          notiTV.setEditable(false);
          notiTV.setForeground(Color.blue);
          JScrollPane jspnotiTV = new JScrollPane(notiTV);
          jspnotiTV.setVerticalScrollBarPolicy(
          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          jspnotiTV.setBounds(10,420,400,400);
          frame.add(jspnotiTV);
          
          transactionsTV = new JTextArea("");
          transactionsTV.setBounds(420,10,400,400);
          transactionsTV.setBackground(Color.black);
          transactionsTV.setEditable(false);
          transactionsTV.setLineWrap(true);
          transactionsTV.setForeground(Color.green);
          JScrollPane jsptransactionsTV = new JScrollPane(transactionsTV);
          jsptransactionsTV.setVerticalScrollBarPolicy(
          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          jsptransactionsTV.setBounds(420,10,400,400);
          frame.add(jsptransactionsTV);
          
          minedTV = new JTextArea();//("");
          minedTV.setBackground(Color.black);
          minedTV.setEditable(false);
          minedTV.setLineWrap(true);
          minedTV.setBounds(420,420,400,400);
          minedTV.setForeground(Color.gray);
          JScrollPane jspminedTV = new JScrollPane(minedTV);
          jspminedTV.setVerticalScrollBarPolicy(
          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
          jspminedTV.setBounds(420,420,400,400);
          frame.add(jspminedTV);
          
          frame.setLayout(null);
          frame.setSize(830,860);
          frame.setVisible(true);
        }

}
