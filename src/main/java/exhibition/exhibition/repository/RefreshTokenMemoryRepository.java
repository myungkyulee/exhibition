package exhibition.exhibition.repository;

import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RefreshTokenMemoryRepository implements RefreshTokenRepository {

    private static Map<String, Long> store = new ConcurrentHashMap<>();


    @Override
    public void saveRefreshToken(String token, Long id) {
        store.put(token, id);
    }

    @Override
    public void deleteRefreshToken(String token) {
        store.remove(token);
    }

    @Override
    public boolean existToken(String token, Long id) {
        if(!store.containsKey(token)){
            return false;
        }
        if (!store.get(token).equals(id)) {
            throw new ExhibitionException(ErrorCode.INVALID_TOKEN);
        }
        return true;
    }


}
