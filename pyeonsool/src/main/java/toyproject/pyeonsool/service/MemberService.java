package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.repository.MemberRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public LoginMember findLoginMember(String userId, String password) {
        Member findMember = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 아이디입니다."));

        if (!findMember.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // TODO 예외처리 필요

        return new LoginMember(findMember.getId(), findMember.getNickname());
    }
}
