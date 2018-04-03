/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;

public class Transaction {
	
	String fromAddress;
	
	String toAddress;
	
	Integer money;

	public Transaction(String fromAddress, String toAddress, Integer money) {
		super();
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.money = money;
	}
	

}
