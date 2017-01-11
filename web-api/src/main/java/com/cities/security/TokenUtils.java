package com.cities.security;

import com.cities.model.user.User;
import com.cities.user.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    static Logger log = Logger.getLogger(TokenUtils.class.getName());

    private final String AUDIENCE_UNKNOWN = "unknown";
    private final String AUDIENCE_TABLET = "tablet";
    private final String AUDIENCE_MOBILE = "mobile";


    private String secret = "ssssshh";
    private Long expiration = 604800L;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long)claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            audience = (String) claims.get("audience");
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate() { return new Date(System.currentTimeMillis()); }

    private Date generateExpirationDate() {return new Date(System.currentTimeMillis() + this.expiration * 8000); }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    public String generateToken(User user) {
        return generateToken(user.getUsername());
    }

    private String generateToken(String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", name);
        claims.put("audience", "web");
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = this.getCreatedDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)) && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            claims.put("created", this.generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserDto user = (UserDto) userDetails;
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getCreatedDateFromToken(token);
        final Date expiration = this.getExpirationDateFromToken(token);
        Boolean result = validationResult(token, user, username, created);
        log.debug("cities => validation result : " +result);
        System.out.println("cities => validation result : " +result);
        return result;
    }

    private boolean validationResult(String token, UserDto user, String username, Date created) {
        log.debug("cities => username.equals(user.getUsername()) : " + username.equals(user.getUsername()));
                log.debug("cities => !(this.isTokenExpired(token)) " + !(this.isTokenExpired(token)));
        System.out.println("cities => username.equals(user.getUsername()) : " + username.equals(user.getUsername()));
        System.out.println("cities => !(this.isTokenExpired(token)) " + !(this.isTokenExpired(token)));
        boolean b = !(isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset().toDate()));
        log.debug("cities => !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())) : " + b);
        System.out.println("cities => !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())) : " + b);
        return username.equals(user.getUsername())
                && !(this.isTokenExpired(token))
                && b;
    }
}
