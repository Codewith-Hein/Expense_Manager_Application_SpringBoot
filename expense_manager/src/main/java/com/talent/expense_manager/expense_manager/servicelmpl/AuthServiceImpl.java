package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.exception.AccountNotFound;
import com.talent.expense_manager.expense_manager.exception.InvalidEmailException;
import com.talent.expense_manager.expense_manager.exception.InvalidRefreshTokenException;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.RoleRepository;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.LoginRequest;
import com.talent.expense_manager.expense_manager.request.RefreshTokenRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.TokenResponseDto;
import com.talent.expense_manager.expense_manager.response.WalletInfo;
import com.talent.expense_manager.expense_manager.security.JWTService;
import com.talent.expense_manager.expense_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountResponse login(LoginRequest request) {

        LOGGER.info("[{}] login() - ACTION=START EMAIL={}",
                this.getClass().getSimpleName(),
                request.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Account account = (Account) authentication.getPrincipal();


            String accessToken = jwtService.generateAccessToken(account);
            String refreshToken = jwtService.generateRefreshToken(account);


            AccountResponse response = new AccountResponse();

            WalletInfo walletInfo = new WalletInfo(account.getWallet().getBalance(), account.getWallet().getBudget());

            TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken);


            response.setAccountId(account.getAccountId());
            response.setName(account.getName());
            response.setEmail(account.getEmail());
            response.setDateOfBirth(account.getDateOfBirth());
            response.setRole(account.getRole().getName());
            response.setWalletInfo(walletInfo);
            response.setTokenResponseDto(tokenResponseDto);

            LOGGER.info("[{}] LoginAccount() - ACTION=SUCCESS ACCOUNT_ID={} Role={}",
                    this.getClass().getSimpleName(), account.getAccountId(), account.getRole().getName());

            return response;

        } catch (BadCredentialsException ex) {

            LOGGER.error("[{}] login() - ERROR=INVALID_CREDENTIALS EMAIL={}",
                    this.getClass().getSimpleName(),
                    request.getEmail());
            throw new InvalidEmailException("Invalid email or password");
        }

    }


    public AccountResponse createAccount(AccountRequest request) {


        LOGGER.info("[{}] RegisterAccount() - ACTION=START EMAIL={}",
                this.getClass().getSimpleName(),
                request.getEmail());


        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exist");
        }

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        Account account = new Account();

        account.setAccountId(request.getAccountId());
        account.setName(request.getName());
        account.setDateOfBirth(request.getDateOfBirth());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(true);
        account.setRole(defaultRole);


        Wallet wallet = new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setBudget(BigDecimal.ZERO);
        wallet.setAccount(account);

        account.setWallet(wallet);


        Account saveAccount = accountRepository.save(account);

        WalletInfo walletInfo = new WalletInfo(account.getWallet().getBalance(), account.getWallet().getBudget());
        AccountResponse response = new AccountResponse();

        response.setAccountId(account.getAccountId());
        response.setName(account.getName());
        response.setEmail(account.getEmail());
        response.setDateOfBirth(account.getDateOfBirth());
        response.setRole(account.getRole().getName());
        response.setWalletInfo(walletInfo);


        LOGGER.info("[{}] RegisterAccount() - ACTION=SUCCESS ACCOUNT_ID={} Role={}",
                this.getClass().getSimpleName(), account.getAccountId(), account.getRole().getName());


        return response;


    }

    @Override
    public TokenResponseDto refreshToken(RefreshTokenRequest request) {

        LOGGER.info("[{}] RefreshToken() - ACTION=STAR",this.getClass().getSimpleName());

        Account account = accountRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> new AccountNotFound("Account not found"));


        boolean valid = jwtService.validateToken(request.getRefreshToken(), account);
        if (!valid) {
            throw new InvalidRefreshTokenException("Refresh token invalid or expired");
        }


        String newAccessToken = jwtService.generateAccessToken(account);

        TokenResponseDto tokenResponseDto = new TokenResponseDto();

        tokenResponseDto.setAccessToken(newAccessToken);
        tokenResponseDto.setRefreshToken(request.getRefreshToken());

        LOGGER.info("[{}] RefreshToken() - ACTION=SUCCESS ACCOUNT_ID={}",
                this.getClass().getSimpleName(),account.getAccountId());

        return tokenResponseDto;

    }


    ;
}



