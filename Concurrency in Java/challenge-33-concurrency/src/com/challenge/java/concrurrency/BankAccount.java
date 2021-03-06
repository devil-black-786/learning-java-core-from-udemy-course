package com.challenge.java.concrurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {

    private double balance;
    private String accountNumber;

    private Lock lock;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        boolean status = false;
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance += amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showBalance(status);
    }

    public void withdraw(double amount) {
        boolean status = false;
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    balance -= amount;
                    status = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showBalance(status);
    }

    public void showBalance(boolean transactionStatus) {
        System.out.println(Thread.currentThread().getName() + ':' + this + ":transactionStatus=" + transactionStatus);
    }

    @Override
    public String toString() {
        return "BankAccount:" +
                "{accountNumber='" + accountNumber +
                "', balance=" + balance + '}';
    }
}
