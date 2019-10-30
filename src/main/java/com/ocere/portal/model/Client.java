package com.ocere.portal.model;

import com.ocere.portal.enums.ClientStatus;
import com.ocere.portal.enums.PaymentTerms;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private Set<Job> jobs;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL
    )
    private Set<Note> notes;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private String companyName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String website;
    private String tier;
    private String jobTitle;
    private String contactUsPage;
    private String country;
    private String county;
    private double totalSpending;
    private double monthlySpending;

    @Enumerated(EnumType.STRING)
    private PaymentTerms paymentTerm;

    private String targetMonthlyValue;

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public double getMonthlySpending() {
        return monthlySpending;
    }

    public void setMonthlySpending(double monthlySpending) {
        this.monthlySpending = monthlySpending;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PaymentTerms getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerms paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getContactUsPage() {
        return contactUsPage;
    }

    public void setContactUsPage(String contactUsPage) {
        this.contactUsPage = contactUsPage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return addressLine1;
    }

    public void setStreet(String street) {
        this.addressLine1 = street;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTargetMonthlyValue() {
        return targetMonthlyValue;
    }

    public void setTargetMonthlyValue(String targetMonthlyValue) {
        this.targetMonthlyValue = targetMonthlyValue;
    }
}
