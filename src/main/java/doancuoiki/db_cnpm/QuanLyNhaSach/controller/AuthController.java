package doancuoiki.db_cnpm.QuanLyNhaSach.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancuoiki.db_cnpm.QuanLyNhaSach.domain.Account;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.ApiResponse;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.request.ReqLoginDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.dto.response.ResLoginDTO;
import doancuoiki.db_cnpm.QuanLyNhaSach.services.AccountService;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.SecurityUtil;
import doancuoiki.db_cnpm.QuanLyNhaSach.util.error.AppException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Value("${nqoctai.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            AccountService accountService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<ResLoginDTO>> login(@Valid @RequestBody ReqLoginDTO reqLoginDTO) {
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                reqLoginDTO.getUsername(), reqLoginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        Account currentAccountDB = accountService.getUserByEmail(reqLoginDTO.getUsername());

        if (currentAccountDB != null) {
            ResLoginDTO.AccountLogin accountLogin = new ResLoginDTO.AccountLogin(currentAccountDB.getId(),
                    currentAccountDB.getEmail(),
                    currentAccountDB.getUsername(),
                    currentAccountDB.getAvatar(),
                    currentAccountDB.getRole());
            resLoginDTO.setAccount(accountLogin);
        }
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), resLoginDTO);

        resLoginDTO.setAccessToken(access_token);

        String refresh_token = this.securityUtil.createRefreshToken(reqLoginDTO.getUsername(), resLoginDTO);
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        ApiResponse<ResLoginDTO> response = new ApiResponse<ResLoginDTO>();
        response.setData(resLoginDTO);
        response.setMessage("Đăng nhập thành công");
        response.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(response);
    }

    @GetMapping("/auth/account")
    public ResponseEntity<ApiResponse<ResLoginDTO.UserGetAccount>> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        Account currentAccountDB = accountService.getUserByEmail(email);
        ResLoginDTO.AccountLogin accountLogin = new ResLoginDTO.AccountLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();

        if (currentAccountDB != null) {
            accountLogin.setId(currentAccountDB.getId());
            accountLogin.setEmail(currentAccountDB.getEmail());
            accountLogin.setName(currentAccountDB.getUsername());
            accountLogin.setAvatar(currentAccountDB.getAvatar());
            accountLogin.setRole(currentAccountDB.getRole());
            userGetAccount.setAccount(accountLogin);
        }

        ApiResponse<ResLoginDTO.UserGetAccount> response = new ApiResponse<ResLoginDTO.UserGetAccount>();
        response.setData(userGetAccount);
        response.setMessage("Lấy thông tin tài khoản thành công");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<ResLoginDTO>> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token)
            throws AppException {
        if (refresh_token.equals("abc")) {
            throw new AppException("Bạn không có refresh token ở cookie");
        }

        // check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        // get current account
        Account currentAccountDB = accountService.getUserByEmail(email);
        ResLoginDTO resLoginDTO = new ResLoginDTO();
        ResLoginDTO.AccountLogin accountLogin = new ResLoginDTO.AccountLogin(currentAccountDB.getId(),
                currentAccountDB.getEmail(),
                currentAccountDB.getUsername(),
                currentAccountDB.getAvatar(),
                currentAccountDB.getRole());
        resLoginDTO.setAccount(accountLogin);
        String access_token = this.securityUtil.createAccessToken(email, resLoginDTO);
        resLoginDTO.setAccessToken(access_token);

        // create new refresh token
        String new_refresh_token = this.securityUtil.createRefreshToken(email, resLoginDTO);

        // set new refresh token to cookie
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        ApiResponse<ResLoginDTO> response = new ApiResponse<ResLoginDTO>();
        response.setData(resLoginDTO);
        response.setMessage("Refresh token thành công");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(response);

    }

}
