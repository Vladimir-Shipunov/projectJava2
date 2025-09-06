package com.mpt.journal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passport")
public class PassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false, unique = true)
    private String passportNumber;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = false)
    private String issuedBy;

    @OneToOne(mappedBy = "passport")
    private UserEntity user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}