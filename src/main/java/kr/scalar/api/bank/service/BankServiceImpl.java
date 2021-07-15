package kr.scalar.api.bank.service;

import kr.scalar.api.bank.domain.AccountDTO;
import kr.scalar.api.util.service.*;
import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl extends LambdaUtils implements BankService {

    private final List<AccountDTO> acounts;

    @Override
    public String count() {
        return string.apply(acounts.size());
    }

    @Override
    public List<? extends AccountDTO> findAll() {
        return acounts;
    }

    public BankServiceImpl(){
        acounts = new ArrayList<>();
    }

    @Override
    public void createAccount(AccountDTO account) {
        UtilService utilService = new UtilServiceImpl();
        String accountNumber = utilService.randomNumbers(4, false) +"-"+
                utilService.randomNumbers(4, true)+"-"+
                utilService.randomNumbers(4, true)
                ;
        account.setAccountNumber(accountNumber);
        acounts.add(account);
    }

    @Override
    public String[] findAllAccountNumbers() {
        int count = strToInt.apply(count());
        String[] accountNumbers = new String[count];
        for(int i=0; i < count; i++){
            accountNumbers[i] = acounts.get(i).getAccountNumber();
        }
        return accountNumbers;
    }

    @Override
    public AccountDTO findAccountByAccountNumber(String accountNumber) {
        AccountDTO account = null;
        for(AccountDTO a: acounts){
            if(a.getAccountNumber().equals(accountNumber)){
                account = a;
                break;
            }
        }
        return account;
    }

    @Override
    public String findBalanceByAccountNumber(String accountnumber) {
        String balance = "";
        for(AccountDTO a: acounts){
            balance = a.getAccountNumber().equals(accountnumber)? a.getBalance(): "0";
            break;
        }
        return balance;
    }

    @Override
    public AccountDTO deposit(AccountDTO param) {
        AccountDTO account = findAccountByAccountNumber(param.getAccountNumber());
        int restMoney = strToInt.apply(account.getMoney());
        account.setMoney(restMoney + param.getMoney());
        for(AccountDTO a: acounts){
            if(a.getAccountNumber().equals(account.getAccountNumber())){
                a.setBalance(account.getMoney());
                account = a;
            }
        }
        return account;
    }

    @Override
    public String withdraw(AccountDTO bank) {
        return "";
    }

    @Override
    public void dropAccount(AccountDTO bank) {

    }
}

/*
 int balance = 0;

 public int deposit(int amount){
 balance += amount;
 return balance;
 }
 public int withdraw(int amount){
 balance -= amount;
 return balance;
 }
 public int checkMyBalance(){
 System.out.println("잔액 : " + balance);
 return balance;
 }
 * */
