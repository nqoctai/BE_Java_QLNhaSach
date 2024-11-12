package doancuoiki.db_cnpm.QuanLyNhaSach.services;


import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Customer;
import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Role;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqUpdateAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResCreateAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResUpdateAccountDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResultPaginationDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.AccountRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.repository.CustomerRepository;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public boolean checkEmailExist(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer createCustomer(Customer rqCustomer) {
        return customerRepository.save(rqCustomer);
    }

    public boolean checkCustomerExist(Long id) {
        return customerRepository.existsById(id);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
//
//    public List<Account> getAllAccount() {
//        return accountRepository.findAll();
//    }
//
    public ResultPaginationDTO getAllCustomerWithPagination(Specification<Customer> spec, Pageable pageable) {
        Page<Customer> pageCustomers = this.customerRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageCustomers.getTotalPages());
        meta.setTotal(pageCustomers.getTotalElements());
        rs.setMeta(meta);
        rs.setResult(pageCustomers.getContent());
        return rs;
    }
//
    public Customer updateCustomer(Customer rqCustomer) throws AppException {
        Customer customerDB = customerRepository.findById(rqCustomer.getId()).orElse(null);
        if (customerDB == null) {
            throw new AppException("Account not found");
        }
        boolean checkEmail = customerRepository.existsByEmail(rqCustomer.getEmail());
        if (checkEmail && !customerDB.getEmail().equals(rqCustomer.getEmail())) {
            throw new AppException("Email đã tồn tại");
        }
        customerDB.setName(rqCustomer.getName());
        customerDB.setPhone(rqCustomer.getPhone());
        customerDB.setAddress(rqCustomer.getAddress());
        customerDB.setEmail(rqCustomer.getEmail());
        return customerRepository.save(customerDB);
    }
//
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
//
//    public Account getUserByEmail(String email) {
//        return accountRepository.findByEmail(email);
//    }
//
//    public boolean isExistUserByEmail(String email) {
//        return accountRepository.existsByEmail(email);
//    }
//
//    // public void updateRefreshToken(String token, String email) {
//    // Account accountDB = this.getUserByEmail(email);
//    // if (accountDB != null) {
//    // accountDB.setRefreshToken(token);
//    // accountRepository.save(accountDB);
//    // }
//    // }
//
//    // public Account getUserByRefreshTokenAndEmail(String token, String email) {
//    // return this.accountRepository.findByRefreshTokenAndEmail(token, email);
//    // }
//
//    public ResCreateAccountDTO convertToResCreateAccountDTO(Account account) {
//        ResCreateAccountDTO res = new ResCreateAccountDTO();
//        res.setEmail(account.getEmail());
//        res.setUsername(account.getUsername());
//        res.setCreatedAt(account.getCreatedAt());
//        return res;
//    }
//
//    public ResUpdateAccountDTO convertToResUpdateAccountDTO(Account account) {
//        ResUpdateAccountDTO res = new ResUpdateAccountDTO();
//        res.setId(account.getId());
//        res.setUsername(account.getUsername());
//        res.setPhone(account.getPhone());
//        res.setEmail(account.getEmail());
//        res.setUpdatedAt(account.getUpdatedAt());
//        return res;
//    }
//
//    public ResAccountDTO convertToResAccountDTO(Account account) {
//        ResAccountDTO res = new ResAccountDTO();
//        res.setId(account.getId());
//        res.setUsername(account.getUsername());
//        res.setEmail(account.getEmail());
//        res.setPhone(account.getPhone());
//        res.setCreatedAt(account.getCreatedAt());
//        res.setUpdatedAt(account.getUpdatedAt());
//
//        Role role = account.getRole();
//        if (role != null) {
//            ResAccountDTO.RoleAccount roleAccount = new ResAccountDTO.RoleAccount();
//            roleAccount.setId(role.getId());
//            roleAccount.setName(role.getName());
//            res.setRole(roleAccount);
//        }
//        return res;
//    }
}
