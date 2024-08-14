package com.example.FootballManager_back_end.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String username;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      jwt = authHeader.substring(7);
      username = jwtService.extractUsername(jwt);


      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (SignatureException ex) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      ( response).sendError(HttpServletResponse.SC_FORBIDDEN, "No valid signature");

    } catch (MalformedJwtException ex) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      (response).sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid authorization token");

    } catch (ExpiredJwtException ex) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      (response).sendError(HttpServletResponse.SC_FORBIDDEN, "Expired authorization token");

    } catch (UnsupportedJwtException ex) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      ( response).sendError(HttpServletResponse.SC_FORBIDDEN, "Unsupported authorization token ");

    } catch (IllegalArgumentException ex) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      (response).sendError(HttpServletResponse.SC_FORBIDDEN, "JWT claims string is empty");

    }
  }
}
