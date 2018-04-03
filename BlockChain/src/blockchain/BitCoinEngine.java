/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;
import java.util.ArrayList;
import java.util.List;

public class BitCoinEngine {

	public List<Block> blockChain = new ArrayList<Block>();

	private int difficulty = 2;

	public Block blockToBeMinned;

	public List<Transaction> unconfirmedTrasactions = new ArrayList<Transaction>();

	public void addTransaction(Transaction transaction) {
		unconfirmedTrasactions.add(transaction);

	}

	public void releaseBlock() {
		synchronized (this) {
			try {
                                String oldTransactionsTVText = Test.transactionsTV.getText();
                                String newTransactionsTVText   = new String(oldTransactionsTVText);
                                newTransactionsTVText += "\n";
                                newTransactionsTVText += " unConfirmedTransaction size: " + unconfirmedTrasactions.size();
                                newTransactionsTVText += "\n";
				newTransactionsTVText += "blockChain.size() " + blockChain.size();
				if (blockChain.size() == 0) {
					blockToBeMinned = new Block(blockChain.size() + 1 + " - Block", unconfirmedTrasactions, "0");
				} else {
					if (blockToBeMinned == null) {
						blockToBeMinned = new Block(blockChain.size() + 1 + " - Block", unconfirmedTrasactions,
								blockChain.get(blockChain.size() - 1).currentHash);
					}

				}
				unconfirmedTrasactions.clear();
                                newTransactionsTVText += "\n";
                                newTransactionsTVText += "-- Created block '" + blockToBeMinned.name + "' to be mined";
                                newTransactionsTVText += "\n";
				Test.transactionsTV.setText(newTransactionsTVText);
                                this.notifyAll();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

}
