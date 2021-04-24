package com.junyoung.yolo.service;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.MemberRole;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOauth2UserDetailService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName: {}", clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("================================================");
        userRequest.getAdditionalParameters().forEach((k, v) -> log.info("k: {}, v: {}", k,v));

        String email = null;
        String name = null;

        if ("Google".equals(clientName)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
        }
        log.info("EMAIL: {}", email);

        Member member = saveSocialMember(email, name);
        log.info("Member: {}, {}, {}, {}", member.getId(), member.getName(), member.getAge(), member.getRoleKey());

        return super.loadUser(userRequest);
    }

    private Member saveSocialMember(String email, String name) {
        Optional<Member> optionalMember = memberRepository.findById(email);

        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }

        Member member = Member.builder()
                            .id(email)
                            .name(name)
                            .age(30)
                            .build();

        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }
}
