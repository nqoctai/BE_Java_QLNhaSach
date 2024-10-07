package doancuoiki.db_cnpm.QuanLyNhaSach.services;

import java.util.List;

import org.springframework.stereotype.Service;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqUpdateAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.AccountRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final RoleService roleService;

    public AccountService(AccountRepository accountRepository, RoleService roleService) {
        this.accountRepository = accountRepository;
        this.roleService = roleService;
    }

    public boolean checkEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }

    public Account createAccount(Account rqAccount) {
        if (rqAccount.getRole() != null) {
            Role role = roleService.getRoleById(rqAccount.getRole().getId());
            rqAccount.setRole(role);
        }
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

    public Account updateAccount(ReqUpdateAccountDTO rqAccount) throws AppException {
        Account accountDB = accountRepository.findById(rqAccount.getId()).orElse(null);
        if (accountDB == null) {
            throw new AppException("Account not found");
        }
        accountDB.setUsername(rqAccount.getUsername());
        accountDB.setPhone(rqAccount.getPhone());
        return accountRepository.save(accountDB);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public Account getUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    // public void updateRefreshToken(String token, String email) {
    // Account accountDB = this.getUserByEmail(email);
    // if (accountDB != null) {
    // accountDB.setRefreshToken(token);
    // accountRepository.save(accountDB);
    // }
    // }

    // public Account getUserByRefreshTokenAndEmail(String token, String email) {
    // return this.accountRepository.findByRefreshTokenAndEmail(token, email);
    // }
}
