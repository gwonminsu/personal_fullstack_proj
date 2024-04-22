package kr.ac.cu.joonggo.entity;

import kr.ac.cu.joonggo.repository.NoticeCategoryRepository;
import kr.ac.cu.joonggo.repository.NoticeRepository;
import kr.ac.cu.joonggo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeEntityTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoticeCategoryRepository noticeCategoryRepository;

    @Test
    void createNotice() {
        UserEntity user = userRepository.findByUsername("관리자1");

//        NoticeCategoryEntity noticeCategory = noticeCategoryRepository.save(NoticeCategoryEntity.builder()
//                .name("일반공지").build());

        NoticeCategoryEntity noticeCategory = noticeCategoryRepository.findByName("일반공지");

//        noticeRepository.save(NoticeEntity.builder()
//                .title("AK47 가사")
//                .content("우리 동네는 밤마다 울려 총성\n" +
//                        "Why you scared? 잘 봐봐 난 이렇게 컸어\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "탕탕 그르르 두두두두 탕탕\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "총기 손질 깜빡했다 칵칵칵\n" +
//                        "이 총 하나면 다 나 한테 충성\n" +
//                        "AK47, AK47\n" +
//                        "Ak47 맞고 사망한 외할머니\n" +
//                        "그 말대로 악소리 47번 외치셨지\n" +
//                        "R.I.P, R.I.P. to my sweety granma\n" +
//                        "복수는 나의 것, 그놈은 긴장하라 그래 인마\n" +
//                        "지옥 같았던 가정폭력\n" +
//                        "엄마라는 작자의 수차례 등짝 공격\n" +
//                        "나약했던 과거는 깔끔하게 청산\n" +
//                        "피에는 피 두고 봐 차액은 끝까지 정산\n" +
//                        "But sorry to my mom\n" +
//                        "이제 내가 호강시켜 줄 테니 절대 걱정 마\n" +
//                        "은행 털고, 납치하고, 약팔고 남긴 돈으로\n" +
//                        "요양원 보낼 때까지 달리자 let's get it! ho!\n" +
//                        "자신 있게 올려 총기 합법화 국민청원\n" +
//                        "양심 있음 찬성 그에게 닿게 윤석열\n" +
//                        "필요한 건 오직 한자루의 AK\n" +
//                        "쌈뽕한 내 A.K.A what? K$AP RAMA! 쉿\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "Why you scared? 잘 봐봐 난 이렇게 컸어\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "탕탕 그르르 두두두두 탕탕\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "총기 손질 깜빡했다 칵칵칵\n" +
//                        "이 총 하나면 다 나 한테 충성\n" +
//                        "AK47, AK47\n" +
//                        "내 다리 사이 AK 침실에서 전쟁해\n" +
//                        "연발 헤드라인 조준해 니 여친에게 쏘아대\n" +
//                        "니 여친 흘려 피 알 다 깬 토게피\n" +
//                        "상관 없지 어차피 귀찮아 MBTI P임\n" +
//                        "내 차에 올라 타\n" +
//                        "내 차는 엘 카미노 차 끈지 오래 돼 감 잃어 사고 났지롱\n" +
//                        "낼 카지노 가서 합의금 따지 뭐\n" +
//                        "낼 하기로 했는데 왜 넌 화내노 느그 아버지 뭐 하시노\n" +
//                        "돈을 털어 약을 훔쳐 너를 죽여 여자 태워\n" +
//                        "총을 장전 약을 빨어 탈세 신고 눈을 감어\n" +
//                        "내 친구는 무기징역 조직 보스 난 다 찔러\n" +
//                        "술 마시면 무죄 판결 한국 법이 나를 살려\n" +
//                        "니 여친은 너랑 하며 내 이름을 졸라 불러\n" +
//                        "돌잔치에 너의 딸은 사실 내꺼 I'm your father\n" +
//                        "니 아빠의 장례식에 조의금은 내지 않아\n" +
//                        "머릿고기 가득 채워 비락식혜 3개 챙겨\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "Why you scared? 잘 봐봐 난 이렇게 컸어\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "탕탕 그르르 두두두두 탕탕\n" +
//                        "우리 동네는 밤마다 울려 총성\n" +
//                        "총기 손질 깜빡했다 칵칵칵\n" +
//                        "이 총 하나면 다 나 한테 충성\n" +
//                        "AK47, AK47")
//                .user(user)
//                .category(noticeCategory).build());

//        noticeRepository.save(NoticeEntity.builder()
//                .title("테스트1")
//                .content("테스트")
//                .user(user)
//                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트2")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트3")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트4")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트5")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트6")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트7")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트8")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트9")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());

        noticeRepository.save(NoticeEntity.builder()
                .title("테스트10")
                .content("테스트")
                .user(user)
                .category(noticeCategory).build());
    }


}