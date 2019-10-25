package com.ocere.portal.model;

import com.ocere.portal.enums.*;
import com.ocere.portal.exception.FileStorageException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
public class Job {

    public Job() {
        asap = true;
        seoValue = 0;
        linkValue = 0;
        ppcValue = 0;
        contentValue = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> tickets;

    @ElementCollection(targetClass = ProductType.class)
    @CollectionTable(name = "job_producttype",
            joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "producttype_id")
    private Set<ProductType> productTypes;

    /*---------------------------
        EDITABLE
    ---------------------------*/

    /*
        ALL
     */

    @Enumerated
    private Currency currency;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String description;

    private boolean whiteLabel;

    private String company;

    private String website;

    @Enumerated
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private Set<DBFile> files;

    /*
        SEO
     */

    private double seoValue;

    private String targetKeywords;

    private String reportingKeywords;

    @ElementCollection(targetClass = SearchEngine.class)
    @CollectionTable(name = "job_searchengine",
            joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "seosearchengine_id")
    private Set<SearchEngine> seoSearchEngines;

    private boolean onPageReview;

    private boolean healthCheck;

    /*
        LINK
     */

    private double linkValue;

    @ElementCollection(targetClass = SearchEngine.class)
    @CollectionTable(name = "job_searchengine",
            joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "linksearchengine_id")
    private Set<SearchEngine> linkSearchEngines;

    private String googleDocLink;

    @OneToOne(mappedBy = "jobOrderForm", cascade = CascadeType.ALL)
    private DBFile orderFormFile;

    private void setOrderFormFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            this.orderFormFile =  new DBFile(fileName, file.getContentType(), file.getBytes());

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /*
        PPC
     */

    private double ppcValue;

    @ElementCollection(targetClass = CampaignType.class)
    @CollectionTable(name = "job_campaigntype",
            joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "campaigntype_id")
    private Set<CampaignType> campaignTypes;

    private boolean asap;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date campaignLaunchDate;

    private boolean signOffRequired;

    private String facebookId;

    private String facebookUrl;

    private boolean setUpRequired;

    private String googleAdsId;

    private String monthlyClickSpend;

    private String targetAreas;

    private String landingPageUrls;

    private String adwordsMainGoal;

    private String facebookMainGoal;

    private String thankYouPageUrl;

    private String reportingEmail;

    /*
        CONTENT
     */

    private double contentValue;

    private int contentNumberOfPieces;

    @Enumerated
    private ContentType contentType;

    private int contentLength; /* DROPDOWN */

    private String contentTitles;

    @Enumerated
    private WritingStyle contentWritingStyle;

    private String contentKeywords;

    /*------------------------
        DYNAMIC HANDLER
    ------------------------*/

    public double getTotalValue() {
        return seoValue + linkValue + ppcValue + contentValue;
    }

    /*------------------------
        GETTER AND SETTER
    ------------------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWhiteLabel(boolean whiteLabel) {
        this.whiteLabel = whiteLabel;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFiles(Set<DBFile> files) {
        this.files = files;
    }

    public void setTargetKeywords(String targetKeywords) {
        this.targetKeywords = targetKeywords;
    }

    public void setReportingKeywords(String reportingKeywords) {
        this.reportingKeywords = reportingKeywords;
    }

    public void setSeoSearchEngines(Set<SearchEngine> seoSearchEngines) {
        this.seoSearchEngines = seoSearchEngines;
    }

    public void setOnPageReview(boolean onPageReview) {
        this.onPageReview = onPageReview;
    }

    public void setHealthCheck(boolean healthCheck) {
        this.healthCheck = healthCheck;
    }

    public void setLinkSearchEngines(Set<SearchEngine> linkSearchEngines) {
        this.linkSearchEngines = linkSearchEngines;
    }

    public void setGoogleDocLink(String googleDocLink) {
        this.googleDocLink = googleDocLink;
    }

    public void setOrderFormFile(DBFile orderFormFile) {
        this.orderFormFile = orderFormFile;
    }

    public void setCampaignTypes(Set<CampaignType> campaignTypes) {
        this.campaignTypes = campaignTypes;
    }

    public void setCampaignLaunchDate(Date campaignLaunchDate) {
        this.campaignLaunchDate = campaignLaunchDate;
    }

    public void setSignOffRequired(boolean signOffRequired) {
        this.signOffRequired = signOffRequired;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setSetUpRequired(boolean setUpRequired) {
        this.setUpRequired = setUpRequired;
    }

    public void setGoogleAdsId(String googleAdsId) {
        this.googleAdsId = googleAdsId;
    }

    public void setMonthlyClickSpend(String monthlyClickSpend) {
        this.monthlyClickSpend = monthlyClickSpend;
    }

    public void setTargetAreas(String targetAreas) {
        this.targetAreas = targetAreas;
    }

    public void setLandingPageUrls(String landingPageUrls) {
        this.landingPageUrls = landingPageUrls;
    }

    public void setAdwordsMainGoal(String adwordsMainGoal) {
        this.adwordsMainGoal = adwordsMainGoal;
    }

    public void setFacebookMainGoal(String facebookMainGoal) {
        this.facebookMainGoal = facebookMainGoal;
    }

    public void setThankYouPageUrl(String thankYouPageUrl) {
        this.thankYouPageUrl = thankYouPageUrl;
    }

    public void setReportingEmail(String reportingEmail) {
        this.reportingEmail = reportingEmail;
    }

    public Client getClient() {
        return client;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isWhiteLabel() {
        return whiteLabel;
    }

    public String getCompany() {
        return company;
    }

    public String getWebsite() {
        return website;
    }

    public Status getStatus() {
        return status;
    }

    public Set<DBFile> getFiles() {
        return files;
    }

    public String getTargetKeywords() {
        return targetKeywords;
    }

    public String getReportingKeywords() {
        return reportingKeywords;
    }

    public Set<SearchEngine> getSeoSearchEngines() {
        return seoSearchEngines;
    }

    public boolean isOnPageReview() {
        return onPageReview;
    }

    public boolean isHealthCheck() {
        return healthCheck;
    }

    public Set<SearchEngine> getLinkSearchEngines() {
        return linkSearchEngines;
    }

    public String getGoogleDocLink() {
        return googleDocLink;
    }

    public DBFile getOrderFormFile() {
        return orderFormFile;
    }

    public Set<CampaignType> getCampaignTypes() {
        return campaignTypes;
    }

    public Date getCampaignLaunchDate() {
        return campaignLaunchDate;
    }

    public boolean isSignOffRequired() {
        return signOffRequired;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public boolean isSetUpRequired() {
        return setUpRequired;
    }

    public String getGoogleAdsId() {
        return googleAdsId;
    }

    public String getMonthlyClickSpend() {
        return monthlyClickSpend;
    }

    public String getTargetAreas() {
        return targetAreas;
    }

    public String getLandingPageUrls() {
        return landingPageUrls;
    }

    public String getAdwordsMainGoal() {
        return adwordsMainGoal;
    }

    public String getFacebookMainGoal() {
        return facebookMainGoal;
    }

    public String getThankYouPageUrl() {
        return thankYouPageUrl;
    }

    public String getReportingEmail() {
        return reportingEmail;
    }

    public Set<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public User getOwner() {
        return owner;
    }

    public int getContentNumberOfPieces() {
        return contentNumberOfPieces;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getContentTitles() {
        return contentTitles;
    }

    public WritingStyle getContentWritingStyle() {
        return contentWritingStyle;
    }

    public String getContentKeywords() {
        return contentKeywords;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setContentNumberOfPieces(int contentNumberOfPieces) {
        this.contentNumberOfPieces = contentNumberOfPieces;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentTitles(String contentTitles) {
        this.contentTitles = contentTitles;
    }

    public void setContentWritingStyle(WritingStyle contentWritingStyle) {
        this.contentWritingStyle = contentWritingStyle;
    }

    public void setContentKeywords(String contentKeywords) {
        this.contentKeywords = contentKeywords;
    }

    public boolean isAsap() {
        return asap;
    }

    public void setAsap(boolean asap) {
        this.asap = asap;
    }

    public double getSeoValue() {
        return seoValue;
    }

    public double getLinkValue() {
        return linkValue;
    }

    public double getPpcValue() {
        return ppcValue;
    }

    public double getContentValue() {
        return contentValue;
    }

    public void setSeoValue(double seoValue) {
        this.seoValue = seoValue;
    }

    public void setLinkValue(double linkValue) {
        this.linkValue = linkValue;
    }

    public void setPpcValue(double ppcValue) {
        this.ppcValue = ppcValue;
    }

    public void setContentValue(double contentValue) {
        this.contentValue = contentValue;
    }
}
