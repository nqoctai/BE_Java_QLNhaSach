package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import java.util.List;

import org.springframework.stereotype.Service;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean checkEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Account createAccount(Account rqAccount) {
        return accountRepository.save(rqAccount);
    }

    public boolean checkAccountExist(Long id) {
        return accountRepository.existsById(id);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public Account updateAccount(Long id, Account rqAccount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return null;
        }
        account.setUsername(rqAccount.getUsername());
        account.setPassword(rqAccount.getPassword());
        account.setEmail(rqAccount.getEmail());
        account.setPhone(rqAccount.getPhone());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account getUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
