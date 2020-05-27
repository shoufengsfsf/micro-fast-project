package com.micro.fast.auth.jwt.common.utils;

import com.alibaba.fastjson.JSON;
import com.micro.fast.auth.jwt.common.constant.JwtConstant;
import com.micro.fast.auth.jwt.common.constant.MicroFastAuthJwtExceptionCodeEnum;
import com.micro.fast.auth.jwt.common.exception.MicroFastAuthJwtException;
import com.micro.fast.auth.jwt.common.pojo.JwtContentInfo;
import com.micro.fast.auth.jwt.common.pojo.Token;
import io.jsonwebtoken.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * jwt 工具类
 *
 * @author shoufeng
 */

public class JwtUtils {

    public static final RsaKeyUtils RSA_KEY_UTILS = new RsaKeyUtils();

    public static Token generateUserToken(JwtContentInfo jwtContentInfo, String privateKeyPath, int expire) {

        JwtBuilder jwtBuilder = Jwts.builder()
                //设置主题
                .setSubject(String.valueOf(jwtContentInfo.getUserId()))
                //设置账号
                .claim(JwtConstant.JWT_KEY_ACCOUNT, jwtContentInfo.getAccount())
                //设置用户名称
                .claim(JwtConstant.JWT_KEY_USER_NAME, jwtContentInfo.getUserName())
                //设置用户信息
                .claim(JwtConstant.JWT_KEY_USER_INFO, JSON.toJSONString(jwtContentInfo.getUserInfo()))
                //设置其他信息
                .claim(JwtConstant.JWT_KEY_ANOTHER_INFO, JSON.toJSONString(jwtContentInfo.getOtherInfo()));

        return generateToken(jwtBuilder, privateKeyPath, expire);
    }

    public static Token generateToken(JwtBuilder builder, String privateKeyPath, int expire) throws MicroFastAuthJwtException {
        try {
            //返回的字符串便是我们的jwt串了
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(expire);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String compactJws = builder.setExpiration(date)
                    //设置算法（必须）
                    .signWith(SignatureAlgorithm.RS256, RSA_KEY_UTILS.getPrivateKey(privateKeyPath))
                    //这个是全部设置完成后拼成jwt串的方法
                    .compact();
            return new Token(compactJws, expire);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new MicroFastAuthJwtException(MicroFastAuthJwtExceptionCodeEnum.JWT_GEN_TOKEN_FAIL.getCode(), MicroFastAuthJwtExceptionCodeEnum.JWT_GEN_TOKEN_FAIL.getMessage());
        }
    }

    public static JwtContentInfo getJwtFromToken(String token, String publicKeyPath) throws MicroFastAuthJwtException {
        Jws<Claims> claimsJws = parserToken(token, publicKeyPath);
        Claims body = claimsJws.getBody();
        //用户ID
        String userIdStr = body.getSubject();
        //账号
        String account = Optional.ofNullable((String) body.get(JwtConstant.JWT_KEY_ACCOUNT)).orElse("");
        //过期时间
        Date expireDate = body.getExpiration();
        //用户名
        String userName = Optional.ofNullable((String) body.get(JwtConstant.JWT_KEY_USER_NAME)).orElse("");
        //用户信息
        Map<String, Object> userInfo = Optional
                .ofNullable((Map<String, Object>) JSON.parseObject((String) body.get(JwtConstant.JWT_KEY_USER_INFO)))
                .orElse(new HashMap<>());
        //其他信息
        Map<String, Object> otherInfo = Optional
                .ofNullable((Map<String, Object>) JSON.parseObject((String) body.get(JwtConstant.JWT_KEY_ANOTHER_INFO)))
                .orElse(new HashMap<>());

        Long userId = Long.parseLong(userIdStr);
        return new JwtContentInfo(userId, expireDate, account, userName, userInfo, otherInfo);
    }

    private static Jws<Claims> parserToken(String token, String pubKeyPath) throws MicroFastAuthJwtException {
        try {
            return Jwts.parser().setSigningKey(RSA_KEY_UTILS.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            //过期
            throw new MicroFastAuthJwtException(MicroFastAuthJwtExceptionCodeEnum.JWT_TOKEN_EXPIRED.getCode(), MicroFastAuthJwtExceptionCodeEnum.JWT_TOKEN_EXPIRED.getMessage());
        } catch (SignatureException ex) {
            //签名错误
            throw new MicroFastAuthJwtException(MicroFastAuthJwtExceptionCodeEnum.JWT_SIGNATURE.getCode(), MicroFastAuthJwtExceptionCodeEnum.JWT_SIGNATURE.getMessage());
        } catch (IllegalArgumentException ex) {
            //token 为空
            throw new MicroFastAuthJwtException(MicroFastAuthJwtExceptionCodeEnum.JWT_ILLEGAL_ARGUMENT.getCode(), MicroFastAuthJwtExceptionCodeEnum.JWT_ILLEGAL_ARGUMENT.getMessage());
        } catch (Exception e) {
            throw new MicroFastAuthJwtException(MicroFastAuthJwtExceptionCodeEnum.JWT_PARSER_TOKEN_FAIL.getCode(), MicroFastAuthJwtExceptionCodeEnum.JWT_PARSER_TOKEN_FAIL.getMessage());
        }
    }
}
