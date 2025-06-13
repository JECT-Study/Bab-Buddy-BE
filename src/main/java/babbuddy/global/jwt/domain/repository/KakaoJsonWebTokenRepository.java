package babbuddy.global.jwt.domain.repository;


import babbuddy.global.jwt.domain.entity.KakaoJsonWebToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoJsonWebTokenRepository extends CrudRepository<KakaoJsonWebToken, String> {
}