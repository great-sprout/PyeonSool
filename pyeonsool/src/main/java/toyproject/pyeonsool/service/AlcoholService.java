package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;
import toyproject.pyeonsool.repository.AlcoholRepository;
import toyproject.pyeonsool.repository.MemberRepository;
import toyproject.pyeonsool.repository.PreferredAlcoholRepository;

@Service
@RequiredArgsConstructor
public class AlcoholService {

    private final AlcoholRepository alcoholRepository;
    private final PreferredAlcoholRepository preferredAlcoholRepository;
    private final MemberRepository memberRepository;

    public AlcoholDetailsDto getAlcoholDetails(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        // TODO 술, 회원 예외처리 필요

        return AlcoholDetailsDto.of(alcohol, preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol));
    }

    public Long likeAlcohol(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        // TODO 술, 회원 예외처리 필요

        PreferredAlcohol preferredAlcohol = new PreferredAlcohol(member, alcohol);
        preferredAlcoholRepository.save(preferredAlcohol);

        return preferredAlcohol.getId();
    }

    public void dislikeAlcohol(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        // TODO 술, 회원 예외처리 필요

        if (preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol)) {
            preferredAlcoholRepository.removeByMemberAndAlcohol(member, alcohol);
        }
    }
}