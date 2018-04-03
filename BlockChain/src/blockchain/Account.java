/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

public class Account {

	private List<Block> blockChain = new ArrayList<Block>();

	List<String> receivingAddresses = new ArrayList<String>();

	public boolean isBlockMinnedWinner = false;

	Block minningBlock;

	String sendingAddress;

	private Integer balance;

	private BitCoinEngine bitCoinEngine;

	public String accountName;

	public Account(BitCoinEngine bitCoinEngine, String accountname) {
		this.accountName = accountname;
		this.blockChain = bitCoinEngine.blockChain;
		this.bitCoinEngine = bitCoinEngine;
		this.receivingAddresses.add(HashUtil.applySha256(this.accountName));
	}

	public void sendBitcoin(String toAddress, Integer amount) {
                String oldSendBitCoinsText = Test.sendCoinsTV.getText();
                String newSendBitCoinsText   = new String(oldSendBitCoinsText);
                newSendBitCoinsText += "\n";
                newSendBitCoinsText += this.accountName + " sending " + amount + " to " + toAddress;
                newSendBitCoinsText += "\n";
                Test.sendCoinsTV.setText(newSendBitCoinsText);
     		Transaction transaction = new Transaction(sendingAddress, toAddress, amount);
		bitCoinEngine.addTransaction(transaction);

	}

	public String getSendingAddress() {
		return receivingAddresses.get(0);
	}

	public void mineBlock(Block block, int difficulty) throws InterruptedException {
		synchronized (bitCoinEngine) {

			if (this.bitCoinEngine.blockChain == null) {
				this.bitCoinEngine.wait();
			}
			if (bitCoinEngine.blockToBeMinned != null && !bitCoinEngine.blockToBeMinned.isBlockdMined) {
                                String oldNotiTVText = Test.notiTV.getText();
                                String newNotiTVText   = new String(oldNotiTVText);
                                newNotiTVText += "\n";
				newNotiTVText += this.accountName + "-- Started minning block '" + block.name + "' with difficulty +"
						+ difficulty + "..";
                                newNotiTVText += "\n";
                                Test.notiTV.setText(newNotiTVText);
				block.mineBlock(difficulty, this);

				if (isBlockMinnedWinner && !bitCoinEngine.blockChain.contains(bitCoinEngine.blockToBeMinned)) {
                              
                                        String oldMinedTVText = Test.minedTV.getText();
                                        String newTextMinedTVText   = new String(oldMinedTVText);
                                        newTextMinedTVText += "\n" + "|--------------------------------------------|";
					newTextMinedTVText += "\n" + "| " + this.accountName + "-- finished minning block '" + block.name + "'     |";
					newTextMinedTVText += "\n" + "| " + this.accountName + "-- adding Block '" + block.name + "' to BlockChain |";
					newTextMinedTVText += "\n" + "|--------------------------------------------|" + "\n";
                                        Test.minedTV.setText(newTextMinedTVText);
					bitCoinEngine.blockChain.add(block);
					bitCoinEngine.blockToBeMinned = null;

					for (Block b : bitCoinEngine.blockChain) {
						System.out.println(b);
					}
				} else {
					System.out.println(this.accountName + " failed to mine " + block.name);
				}

			} else {
                                String oldNotiTVText = Test.notiTV.getText();
                                String newNotiTVText   = new String(oldNotiTVText);
                                newNotiTVText += "\n";
				newNotiTVText += this.accountName + "-- Waiting for Block to be created..";
                                newNotiTVText += "\n";
                                Test.notiTV.setText(newNotiTVText);
			}
		}
	}
}