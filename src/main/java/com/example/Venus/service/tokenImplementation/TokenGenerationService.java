package com.example.Venus.service.tokenImplementation;

import com.example.Venus.dto.response.AuthLogResponse;
import com.example.Venus.entities.AuthLog;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.BadRequestException;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.exception.TokenNotFoundException;
import com.example.Venus.repo.TokenRepository;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.utils.JwtUtils;
import com.example.Venus.utils.ProcedureCallUtil;
import com.example.Venus.utils.RandomStringGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author korash waiba
 * @version v1.1
 * @since 12/13/2024
 */
@Service
@RequiredArgsConstructor
public class TokenGenerationService {
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final UsersRepo usersRepo;
    @Value("${encryption.key}")
    private String AES_KEY;

    private final ProcedureCallUtil procedureCallUtil;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public AuthLogResponse generateToken(Users user) {
        String accessToken = jwtUtils.generateToken(user);
        String refreshToken = RandomStringGenerator.generateRandomString(30);

        AuthLog authLog = new AuthLog();
        authLog.setEmail(user.getEmail());
        authLog.setAccessToken(accessToken);
        authLog.setRefreshToken(refreshToken);
        authLog.setAccessTokenIssuedAt(LocalDateTime.now());
        authLog.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(jwtUtils.getAccessTokenTtl()));
        authLog.setRefreshTokenExpiresAt(LocalDateTime.now().plusMinutes(30));
        authLog.setRefreshTokenIssuedAt(LocalDateTime.now());

        tokenRepository.save(authLog);
        return new AuthLogResponse(
                accessToken,
                refreshToken,
                user.getFullName(),
                user.getEmail()
        );

    }


//    public AuthLogResponse generateToken(Users user) throws JsonProcessingException {
//        String accessToken = jwtUtils.generateToken(user);
//        String refreshToken = RandomStringGenerator.generateRandomString(30);
//
//        Map<String, Object> menuActionsResponse = procedureCallUtil.callProc(
//                DATABASE_CONSTANTS.GET_MENU_ACTIONS,
//                namedParameterJdbcTemplate,
//                new Object[]{user.getEmail()}
//        );
//
//        procedureCallUtil.validateProcResponse(menuActionsResponse);
//
//        List<Object> menuActions = procedureCallUtil.transformDbResponseToJsonList(menuActionsResponse.get("menuactions"));
//
//        AuthLog authLog = new AuthLog();
//        authLog.setEmail(user.getEmail());
//        authLog.setAccessToken(accessToken);
//        authLog.setRefreshToken(refreshToken);
//        authLog.setAccessTokenIssuedAt(LocalDateTime.now());
//        authLog.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(jwtUtils.getAccessTokenTtl()));
//        authLog.setRefreshTokenExpiresAt(LocalDateTime.now().plusMinutes(30));
//        authLog.setRefreshTokenIssuedAt(LocalDateTime.now());
//
//        tokenRepository.save(authLog);
//        return new AuthLogResponse(
//                accessToken,
//                refreshToken,
//                user.getFullName(),
//                user.getEmail(),
//                menuActions
//        );
//
//    }

    public AuthLogResponse refreshAccessToken(String refreshToken) throws JsonProcessingException {

        Optional<AuthLog> tokenEntityOpt = tokenRepository.findByRefreshToken(refreshToken);

        if (tokenEntityOpt.isPresent()) {
            AuthLog authLog = tokenEntityOpt.get();

            if (authLog.getRefreshTokenExpiresAt().isBefore(LocalDateTime.now())) {
                throw new TokenNotFoundException("Refresh token has expired.");
            }

            Users tokenGenerationUser = usersRepo.findByEmail(authLog.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for refresh token."));

            return  generateToken(tokenGenerationUser);
        }
        throw new ResourceNotFoundException("resource.not.found",new String[]{"Users"});
    }

    public boolean validateAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(AES_KEY)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        if (claims.getExpiration().before(new Date())) {
            throw new BadRequestException("The access token has expired.");
        }

        return true;
    }

}