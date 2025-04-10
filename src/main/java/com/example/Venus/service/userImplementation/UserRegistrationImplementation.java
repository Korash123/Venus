package com.example.Venus.service.userImplementation;

import com.example.Venus.dto.request.ForgotPasswordRequest;
import com.example.Venus.dto.request.LogoutDto;
import com.example.Venus.dto.request.RegisterRequestDto;
import com.example.Venus.dto.response.AuthLogResponse;
import com.example.Venus.entities.AuthLog;
import com.example.Venus.entities.Users;
import com.example.Venus.exception.BadRequestException;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.exception.TokenNotFoundException;
import com.example.Venus.repo.TokenRepository;
import com.example.Venus.repo.UsersRepo;
import com.example.Venus.service.UserRegistration;
import com.example.Venus.service.tokenImplementation.TokenGenerationService;
import com.example.Venus.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationImplementation implements UserRegistration {

    private final UsersRepo usersRepo;
    private final EmailUtils emailUtils;
    private final SymmetricEncryptionUtil symmetricEncryptionUtil;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerationService tokenGenerationService;
    private final TokenRepository tokenRepository;
    private final JwtUtils jwtUtils;

    @Value("${encryption.key}")
    private String AES_KEY;
    @Value("${reset-link.expiration-time}")
    private long resetLinkExpiration;
    @Override
    public void registerUser(RegisterRequestDto registerRequestDto) throws Exception {

        String vbmId = "VBM-" + UniqueIdGenerator.generateUniqueId();
        String createdDate = LocalDateTime.now().toString();

        String dataToEncrypt = new ObjectMapper().writeValueAsString(Map.of(
                "vbmId", vbmId,
                "created_date", createdDate
        ));

//        String encryptedData = symmetricEncryptionUtil.encrypt(
//                dataToEncrypt, AES_KEY, SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
//        );

//        String tempPassword = RandomStringGenerator.generateRandomString(8);
        Users user = new Users();

        user.setEmail(registerRequestDto.getEmail());
        user.setFullName(registerRequestDto.getFullName());
        user.setPhone(registerRequestDto.getPhone());
        user.setIsActive(true);
        user.setIsVerified(true);
        user.setRoleId(registerRequestDto.getRoleId());
        user.setVbmId(vbmId);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        usersRepo.save(user);


//        String resetLink = "http://localhost:5173/rstpwd?data=" + encryptedData;
//        emailUtils.sendResetLinkEmail(
//                registerRequestDto.getEmail(), user.getFullName(), resetLink, resetLinkExpiration, true
//        );

    }

//    @Override
//    public void validateResetLink(ValidateRspwdData validateRspwdData) throws JsonProcessingException {
//        String decryptedData;
//
//        try {
//            decryptedData = symmetricEncryptionUtil.decrypt(
//                    validateRspwdData.getEncData(),
//                    AES_KEY,
//                    SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
//            );
//
//        } catch (Exception ex) {
//            throw new BadRequestException("Invalid encrypted data provided.");
//        }
//
//        Map<String, String> payload = new ObjectMapper().readValue(decryptedData, Map.class);
//
//        String vbmId = payload.get("vbmId");
//        String createdDate = payload.get("created_date");
//
//        if (vbmId == null || createdDate == null) {
//            throw new BadRequestException("Missing required fields in reset link data.");
//        }
//
//        // Check if the reset link has expired
//        LocalDateTime creationDateTime = LocalDateTime.parse(createdDate);
//        if (creationDateTime.plusHours(resetLinkExpiration).isBefore(LocalDateTime.now())) {
//            throw new BadRequestException("The reset link has expired.");
//        }
//    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
        Users user = usersRepo.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("User with this email does not exist."));

        String vbmId = user.getVbmId();
        String createdDate = LocalDateTime.now().toString();

        String dataToEncrypt = new ObjectMapper().writeValueAsString(Map.of(
                "vbmId", vbmId,
                "created_date", createdDate
        ));

        String encryptedData = symmetricEncryptionUtil.encrypt(
                dataToEncrypt,
                AES_KEY,
                SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
        );

        // Example frontend reset password URL
        String resetLink = "http://localhost:5173/rstpwd?data=" + URLEncoder.encode(encryptedData, StandardCharsets.UTF_8);

        emailUtils.sendResetLinkEmail(
                user.getEmail(),
                user.getFullName(),
                resetLink,
                resetLinkExpiration,
                true
        );

        log.info("Password reset link sent to email: {}", forgotPasswordRequest.getEmail());
    }


    @Override
    public AuthLogResponse resetPassword(String encryptedData, String newPassword) throws Exception {

        String decodedData = URLDecoder.decode(encryptedData, StandardCharsets.UTF_8);

        String decryptedData = symmetricEncryptionUtil.decrypt(
                decodedData,
                AES_KEY,
                SymmetricEncryptionUtil.EncryptionAlgorithm.AES_CBC
        );

        Map<String, String> payload = new ObjectMapper().readValue(decryptedData, Map.class);

        String vbmId = payload.get("vbmId");
        String createdDate = payload.get("created_date");

        if (vbmId == null || createdDate == null) {
            throw new BadRequestException("Missing required fields in reset link data.");
        }
        LocalDateTime creationDateTime = LocalDateTime.parse(createdDate);
        if (creationDateTime.plusHours(resetLinkExpiration).isBefore(LocalDateTime.now())) {
            throw new BadRequestException("The reset link has expired.");
        }

        Users user = usersRepo.findByVbmId(vbmId)
                .orElseThrow(() -> new BadRequestException("User not found."));

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepo.save(user);

        return tokenGenerationService.generateToken(user);
    }

    @Override
    public AuthLogResponse loginUser(RegisterRequestDto requestDto) throws JsonProcessingException {

        Users fetchingUser = usersRepo.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + requestDto.getEmail()));

        if (!passwordEncoder.matches(requestDto.getPassword(), fetchingUser.getPassword())) {
            throw new BadRequestException("Invalid password for email: " + requestDto.getEmail());
        }


        return generateAuthLogResponse (fetchingUser);
    }

    private AuthLogResponse generateAuthLogResponse(Users fetchingUser) throws JsonProcessingException {
        AuthLogResponse tokenResponse = tokenGenerationService.generateToken(fetchingUser);
        return new AuthLogResponse(
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken(),
                tokenResponse.getFullName(),
                tokenResponse.getEmail()
        );
    }

    public AuthLogResponse refreshAccessToken(String refreshToken) throws JsonProcessingException {
        return tokenGenerationService.refreshAccessToken(refreshToken);
    }

    public void logout(LogoutDto logoutDto) {

        String email = jwtUtils.extractUsername(logoutDto.getJwtToken());

        AuthLog authLog = tokenRepository.findByEmailAndAccessToken(email, logoutDto.getJwtToken())
                .orElseThrow(() -> new TokenNotFoundException("Token not found for email: " + email));

        authLog.setIsLogout(true);
        tokenRepository.save(authLog);

        log.info("User with email {} has successfully logged out.", email);
    }



}
