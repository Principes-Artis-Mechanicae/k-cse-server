package knu.univ.cse.server.domain.student.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import knu.univ.cse.server.domain.dues.entity.Dues;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(
        name = "student_number",
        nullable = false,
        length = 15,
        unique = true
    )
    private String studentNumber;

    @Column(
        name = "name",
        length = 50
    )
    private String studentName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private OAuth2UserInfo oAuth2UserInfo; // Fetch Type: Eager

    @OneToOne(
        mappedBy = "student",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Dues dues; // Fetch Type: Eager

    @Builder
    public Student(String studentNumber, String studentName, Role role, OAuth2UserInfo oAuth2UserInfo, Dues dues) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.role = role;
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.dues = dues;
    }
}
