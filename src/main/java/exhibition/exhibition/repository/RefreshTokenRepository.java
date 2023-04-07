package exhibition.exhibition.repository;


public interface RefreshTokenRepository {

    void saveRefreshToken(String token, Long id);
    void deleteRefreshToken(String token);
    boolean existToken(String token, Long id);
}
