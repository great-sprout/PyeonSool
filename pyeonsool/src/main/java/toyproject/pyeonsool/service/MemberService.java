package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.FileManager;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.domain.Keyword;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.MyKeyword;
import toyproject.pyeonsool.domain.PreferredAlcohol;
import toyproject.pyeonsool.repository.*;
import toyproject.pyeonsool.web.MemberSaveForm;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;

    public LoginMember findLoginMember(String userId, String password) {
        Member findMember = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 아이디입니다."));

        if (!findMember.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // TODO 예외처리 필요

        return new LoginMember(findMember.getId(), findMember.getNickname());
    }

    public Long signup(String nickname, String userId, String password, String phoneNumber, List<String> keywordNames) {
        validateSignup(nickname, userId, phoneNumber);

        Member member = new Member(nickname, userId, password, phoneNumber);
        memberRepository.save(member);

        keywordRepository.findKeywordsByNameIn(keywordNames)
                .forEach(keyword -> myKeywordRepository.save(new MyKeyword(member, keyword)));

        return member.getId();
    }

    private void validateSignup(String nickname, String userId, String phoneNumber) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        if (memberRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("이미 사용중인 핸드폰 번호입니다.");
        }
    }
}
