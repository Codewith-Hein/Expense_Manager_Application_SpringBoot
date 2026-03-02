package com.talent.expense_manager.expense_manager.security;

import com.talent.expense_manager.expense_manager.exception.JwtTokenExpiredException;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final AccountRepository accountRepository;

  private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorized");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.split("Bearer ")[1];
            String email;

            try {
                email = jwtService.extractUsername(token);
            } catch (JwtTokenExpiredException ex) {
               handlerExceptionResolver.resolveException(request,response,null,ex);
                return;
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Account account = accountRepository.findByEmail(email).orElseThrow();

                if (jwtService.validateToken(token, account)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    account,
                                    null,
                                    account.getAuthorities()
                            );





                    SecurityContextHolder.getContext().setAuthentication(authToken); // 🔥 MUST

                    System.out.println("Is Authenticated: " +
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .isAuthenticated());

                    System.out.println("===== JWT FILTER AUTH =====");
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getAuthorities()
                            .forEach(a -> System.out.println(a.getAuthority()));
                    System.out.println("===========================");


                }
            }

            filterChain.doFilter(request, response);

    }
}
