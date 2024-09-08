package knu.univ.cse.server.global.security.dto;

public interface Oauth2ResponseDto {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();

}
