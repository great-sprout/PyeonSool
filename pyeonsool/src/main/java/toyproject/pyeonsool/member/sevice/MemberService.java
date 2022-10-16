package toyproject.pyeonsool.member.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.alcohol.sevice.AlcoholService;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.mykeyword.domain.MyKeyword;
import toyproject.pyeonsool.keyword.repository.KeywordRepository;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.mykeyword.repository.MyKeywordRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static toyproject.pyeonsool.common.exception.form.FormExceptionType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final KeywordRepository keywordRepository;
    private final MyKeywordRepository myKeywordRepository;

    public Long getMemberId(String nickname) {
        return getMemberIdByNickname(nickname);
    }

    private Long getMemberIdByNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        if (optionalMember.isEmpty()) {
            return null;
        }
        return optionalMember.get().getId();
    }

    public LoginMember findLoginMember(String userId, String password) {
        Member findMember = memberRepository.findByUserId(userId).orElseThrow(INCORRECT_USER_ID::getException);
        findMember.validatePassword(password);

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
            throw DUPLICATE_NICKNAME.getException();
        }

        if (memberRepository.existsByUserId(userId)) {
            throw DUPLICATE_USER_ID.getException();
        }

        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw DUPLICATE_PHONE_NUMBER.getException();
        }
    }

    public List<String> getMyKeywordsKOR(Long memberId) {
        List<String> myKeywords = new ArrayList<>(); //keyword List(한글)
        Map<String, String> keywordMap = AlcoholService.createKeywordNameMap(); //keyword Map(key, Value)

        for (String keyword : myKeywordRepository.myKeywordList(memberId)) {
            myKeywords.add(keywordMap.get(keyword)); //영어로 된 key를 통해 value를 가져온다
        }
        return myKeywords;

    }

    public void editMemberKeywords(List<String> keywordNames, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        myKeywordRepository.deleteByMember(member);
        keywordRepository.findKeywordsByNameIn(keywordNames)
                .forEach(keyword -> myKeywordRepository.save(new MyKeyword(member, keyword)));
    }
}
