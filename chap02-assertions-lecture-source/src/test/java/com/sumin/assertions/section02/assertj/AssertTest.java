package com.sumin.assertions.section02.assertj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class AssertTest {

    /* 수업목표. test 작성을 위한 3rd party lib중 AssertJ의 사용법에 대해 이해할 수 있다 */
    /* 설명. 1. 문자열 테스트하기 */

    @Test
    @DisplayName("문자열 테스트하기")
    void testStringValidation() {

        // given
        String expected = "Hello World"; // 상수풀에 들어있음

        // when
        String actual = new String(expected); // 동등 객체

        /* 설명. 서드파티를 사용하면 유용한 테스트 메소드들을 활용할 수 있다. */
        // then
        Assertions.assertThat(actual)
                .isNotEmpty() // 문자열이 비어있지 않은지
                .isNotBlank() // 문자열이 공백이 아닌 문자열이 포함되는지
                .contains("Hello")
                .doesNotContain("hahaha")
                .startsWith("H")
                .endsWith("d")
                .isEqualTo("Hello World");
    }

    /* 설명. 2. 숫자 테스트하기 */
    @Test
    @DisplayName("숫자 테스트하기")
    void testIntegerValidation() {
        double pi = Math.PI;

        Double actual = pi;

        Assertions.assertThat(actual)
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(4)
                .isEqualTo(Math.PI);
    }

    /* 설명. 3. 날짜 테스트하기 */
    @Test
    @DisplayName("날짜 테스트하기")
    void testLocalDateTimeValidation() {
        String birthday = "1999-04-09T16:42:00.000";

        LocalDateTime theDay = LocalDateTime.parse(birthday); // 문자열을 time 패키지 형태의 날짜형으로 변환

        /* 설명. 자바의 날짜 객체로 관리되는 날짜/시간에 대한 테스트 */
        Assertions.assertThat(theDay)
                .hasYear(1999)
                .hasMonth(Month.APRIL)
                .hasMonthValue(4)
                .hasDayOfMonth(9)
                .isBetween("1999-04-08T00:00:00.000", "1999-04-10T00:00:00.000")
                .isBefore(LocalDateTime.now()); // 현재보다 과거인지
    }

    /* 설명. 4. 예외 테스트하기 */
    @Test
    @DisplayName("예외 테스트하기")
    void testExceptionValidation() {
        Throwable thrown = AssertionsForClassTypes.catchThrowable(() -> {
            throw new IllegalArgumentException("잘못된 파라미터를 입력하였습니다");
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("파라미터");
    }

    /* 설명. 5. filter를 이용한 단정문 테스트하기 */
    @Test
    @DisplayName("filtering assertions 테스트하기")

    void testFilteringAssertions() {
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 15);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 50);
        Member member5 = new Member(5, "user05", "임꺽정", 20);

        /* 설명. Arrays.asList 부분은 실제 DB에서 5명의 회원을 조회해서 List 형태로 반환하는 기능이라고 가정 */
        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);

        Assertions.assertThat(members)
                .filteredOn(member -> member.getAge() > 20) // 나이가 20살 초과인 멤버만 필터링
                .containsOnly(member3, member4); // 필터링된 결과가 member3, member4만 있는지
                // 지금은 하나의 객체만 생성되어있어서 동등비교까지 고려할 필요 x

    }

    /* 설명. 6. 객체 프로퍼티 검증하기 */
    @Test
    @DisplayName("객체의 프로퍼티 검증 테스트하기")
    void testPropertyValidation() {
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 15);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 50);
        Member member5 = new Member(5, "user05", "임꺽정", 20);

        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);

        Assertions.assertThat(members)
                .filteredOn("age", 20) // Member 객체의 getAge 를 자동으로 불러옴.
                .containsOnly(member1);

    }
}
