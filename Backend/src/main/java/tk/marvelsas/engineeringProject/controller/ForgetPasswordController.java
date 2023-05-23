package tk.marvelsas.engineeringProject.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tk.marvelsas.engineeringProject.model.RegistrationRequest;
import tk.marvelsas.engineeringProject.service.ForgetPasswordService;

@RestController
@RequestMapping("api/v1/forgetPassword")
@AllArgsConstructor
public class ForgetPasswordController {


    final public ForgetPasswordService forgetPasswordService;

    @PostMapping
    public String sendForgetPasswordMail(@RequestBody String mail){
       return forgetPasswordService.sendResetPasswordMail(mail);
    }


    @PostMapping("change")
    public String sendForgetPasswordMail(@RequestBody String password, @RequestParam String token){
        return forgetPasswordService.ResetPassword(password,token);
    }

}
