/**
 * 
 */
package de.note.app.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * validates an incoming token and register the user by every request
 * 
 * @author ${Arsen Nana}
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtService jwtService;
	@Autowired
	private CustomUerDetailsService customUerDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);

		if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
			String username = jwtService.getUsername(jwt);
			UserDetails userDetails = customUerDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")
				&& !bearerToken.contains("undefined")) {
			return bearerToken.substring(7);
		}
		LOGGER.warn("#### Request does not include authorization");
		return null;
	}

}
