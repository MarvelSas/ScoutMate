package tk.marvelsas.engineeringProject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tk.marvelsas.engineeringProject.model.ConfirmationToken;
import tk.marvelsas.engineeringProject.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor

public class ConfirmationTokenService {


    private final ConfirmationTokenRepository confirmationTokenRepository;


    public void saveConfirmationToken(ConfirmationToken token){

        confirmationTokenRepository.save(token);
    }

    Optional<ConfirmationToken>getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }


    public int setConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );
    }

    public String setExpiredToken(String token){
        String newToken= UUID.randomUUID().toString();
        confirmationTokenRepository.updateExpiredToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),newToken);
        return newToken;
    }



}
