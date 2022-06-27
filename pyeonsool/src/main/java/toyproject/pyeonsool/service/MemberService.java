package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;
import toyproject.pyeonsool.repository.AlcoholRepository;
import toyproject.pyeonsool.repository.MemberRepository;
import toyproject.pyeonsool.repository.PreferredAlcoholRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final AlcoholRepository alcoholRepository;

    private final PreferredAlcoholRepository preferredAlcoholRepository;

    public LoginMember findLoginMember(String userId, String password) {
        Member findMember = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 아이디입니다."));

        if (!findMember.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // TODO 예외처리 필요

        return new LoginMember(findMember.getId(), findMember.getNickname());
    }

    //MyPageDto변환
  public List<MyPageDto> findLikeList(Member member){
        List<PreferredAlcohol> preferredAlcohols = PreferredAlcoholRepository.findAllPreferredAlcoholsByMember(member);
        List<MyPageDto> result = preferredAlcohols.stream()
                .map(pa -> new MyPageDto(pa))
                .collect(Collectors.toList());
        return result;

   }

}
