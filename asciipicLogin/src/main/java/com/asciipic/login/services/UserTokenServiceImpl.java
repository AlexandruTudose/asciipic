package com.asciipic.login.services;

import com.asciipic.login.models.UserToken;
import com.asciipic.login.repositories.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserTokenServiceImpl implements UserTokenService {


    @Autowired
    UserTokenRepository userTokenRepository;


    @Override
    public UserToken makeToken(Long id) {
        UserToken userToken = userTokenRepository.getUserTokenById(id);
        String token = generateCode();
        UserToken savedUserToken = new UserToken();

        if (userToken == null) {
            UserToken newUserToken = new UserToken();
            newUserToken.setIdUser(id);
            newUserToken.setToken(token);
            savedUserToken = userTokenRepository.save(newUserToken);
        } else {
            userTokenRepository.updateToken(token, id);
            savedUserToken = new UserToken();
            savedUserToken.setIdUser(id);
            savedUserToken.setToken(token);
        }
        return savedUserToken;
    }


    @Override
    public UserToken getUserTokenByToken(String token) {
        UserToken userToken = userTokenRepository.findByToken(token);

        return userToken;
    }

    @Override
    public void deleteUserTokenByToken(String token) {
        userTokenRepository.deleteByToken(token);
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder();
        Random r = new Random();
        int low = 0;
        int high;
        int indx;
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWZYZ";
        high = chars.length();
        for (int i = 0; i < 20; i++) {
            indx = r.nextInt(high - low) + low;
            code.append(chars.charAt(indx));
        }
        return code.toString();
    }
}
