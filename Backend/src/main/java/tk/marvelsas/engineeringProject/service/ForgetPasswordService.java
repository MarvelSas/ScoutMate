package tk.marvelsas.engineeringProject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.ConfirmationToken;
import tk.marvelsas.engineeringProject.model.ForgetPasswordToken;
import tk.marvelsas.engineeringProject.repository.AppUserRepository;
import tk.marvelsas.engineeringProject.repository.ForgetPasswordTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgetPasswordService {

    private final AppUserRepository appUserRepository;

    private final AppUserService appUserService;
    private final ForgetPasswordTokenRepository forgetPasswordTokenRepository;

    private final EmailSander emailSander;

    @Value("${application.frontend.url}")
    private String frontendUrl;

    public void saveForgetPasswordToken(ForgetPasswordToken forgetPasswordToken){
        forgetPasswordTokenRepository.save(forgetPasswordToken);
    }


    public ForgetPasswordToken generateForgetPasswordToken(String email){

        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(
                "user with e-mail" + email + " does not exist"));


        String token= UUID.randomUUID().toString();

        ForgetPasswordToken forgetPasswordToken =new ForgetPasswordToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(10),appUser);
        saveForgetPasswordToken(forgetPasswordToken);

        return forgetPasswordToken;
    }


    public String sendResetPasswordMail(String email){

        ForgetPasswordToken forgetPasswordToken = generateForgetPasswordToken(email);

        String link=frontendUrl+"/changepassword?token="+forgetPasswordToken.getToken();

        emailSander.send(email,buildEmail(forgetPasswordToken.getAppUser().getName(),link),"resetPassword");

        return "We will send mail to you if your mail exist in out database";
    }

    public String ResetPassword(String newPasword,String token){

      ForgetPasswordToken forgetPasswordToken = forgetPasswordTokenRepository.findByToken(token).orElseThrow(()->new IllegalStateException(
                "Token Not exist "
        ));
        LocalDateTime expiredAt = forgetPasswordToken.getExpiredAt();
        if (!expiredAt.isBefore(LocalDateTime.now())){
            try {
                AppUser appUser=appUserService.changePassword(forgetPasswordToken.getAppUser().getEmail(),newPasword);
                forgetPasswordToken.setResetPasswordAt(LocalDateTime.now());
                forgetPasswordTokenRepository.save(forgetPasswordToken);

            }catch (Exception e)
            {
                return "Monky have problem";
            }


        }else {return "token is expired";}



        return "Password Change corected";
    }





    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#efc006\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#efc006\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Zresetuj swoje hasło</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Cześć " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Użyj poniższego linku, żeby ustawić nowe hasło: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Ustaw nowe hasło</a> </p></blockquote>\n Link wygaśnie w ciągu 15 minut. <p>Czuwaj!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }



}
