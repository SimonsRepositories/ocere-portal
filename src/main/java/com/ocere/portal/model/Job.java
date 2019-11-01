package com.ocere.portal.model;

import com.ocere.portal.enums.Currency;
import com.ocere.portal.enums.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Job {

    /**
     * Clone job for next month
     * @param job
     * @param author
     */
    public Job(Job job, User author) {
        this.client = job.getClient();
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.author = author;
        this.productTypes = job.getProductTypes();
        this.currency = job.getCurrency();
        this.description = job.getDescription();
        this.whiteLabel = job.isWhiteLabel();
        this.company = job.getCompany();
        this.website = job.getWebsite();
        this.status = job.getStatus();
        this.owner = job.getOwner();
        this.files = job.getFiles();
        this.seoValue = job.getSeoValue();
        this.targetKeywords = job.getTargetKeywords();
        this.reportingKeywords = job.getReportingKeywords();
        this.seoSearchEngines = job.getSeoSearchEngines();
        this.onPageReview = job.isOnPageReview();
        this.healthCheck = job.isHealthCheck();
        this.linkValue = job.getLinkValue();
        this.linkSearchEngines = job.getLinkSearchEngines();
        this.googleDocLink = job.getGoogleDocLink();
        this.orderFormFile = job.getOrderFormFile();
        this.ppcValue = job.getPpcValue();
        this.campaignTypes = job.getCampaignTypes();
        this.asap = job.isAsap();
        this.campaignLaunchDate = job.getCampaignLaunchDate();
        this.signOffRequired = job.isSignOffRequired();
        this.facebookId = job.getFacebookId();
        this.facebookUrl = job.getFacebookUrl();
        this.setUpRequired = job.isSetUpRequired();
        this.googleAdsId = job.getGoogleAdsId();
        this.monthlyClickSpend = job.getMonthlyClickSpend();
        this.targetAreas = job.getTargetAreas();
        this.landingPageUrls = job.getLandingPageUrls();
        this.adwordsMainGoal = job.getAdwordsMainGoal();
        this.facebookMainGoal = job.facebookMainGoal;
        this.thankYouPageUrl = job.getThankYouPageUrl();
        this.reportingEmail = job.getReportingEmail();
        this.contentValue = job.getContentValue();
        this.contentNumberOfPieces = job.getContentNumberOfPieces();
        this.contentType = job.getContentType();
        this.contentLength = job.getContentLength();
        this.contentTitles = job.getContentTitles();
        this.contentWritingStyle = job.getContentWritingStyle();
        this.contentKeywords = job.getContentKeywords();

        this.month = job.getMonth() + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(job.getStartDate());
        calendar.add(Calendar.MONTH, 1);
        this.startDate = calendar.getTime();
        calendar.setTime(job.getEndDate());
        calendar.add(Calendar.MONTH, 1);
        this.endDate = calendar.getTime();
    }

    public Job() {
        month = 1;
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

    private int month;

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

    @Column(length=10485760)
    private String description;

    private boolean whiteLabel;

    private String company;

    private String website;

    @Enumerated
    private JobStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL
    )
    private Set<DBFile> files;

    public List<DBFile> getSortedFiles() {
        return files.stream().sorted(Comparator.comparing(DBFile::getFileName)).collect(Collectors.toList());
    }

    /*
        SEO
     */

    private double seoValue;

    @Column(length=10485760)
    private String targetKeywords;

    @Column(length=10485760)
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

    @ManyToOne
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    @JoinColumn(name = "order_form_file_id")
    @NotFound(action= NotFoundAction.IGNORE)
    private DBFile orderFormFile;

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

    public boolean isFacebookSelected() {
        return campaignTypes.contains(CampaignType.FacebookImageAds) || campaignTypes.contains(CampaignType.FacebookLeadAds);
    }

    public boolean isSeoSelected() {
        return productTypes.contains(ProductType.SEO);
    }

    public boolean isLinkBuildingSelected() {
        return productTypes.contains(ProductType.LinkBuilding);
    }

    public boolean isPpcSelected() {
        return productTypes.contains(ProductType.PPC);
    }

    public boolean isContentSelected() {
        return productTypes.contains(ProductType.Content);
    }

    /*------------------------
        DATABASE METHODS
    ------------------------*/

    public void saveOrderFormFile(DBFile orderFormFile) {
        if (this.orderFormFile != null) {
            this.orderFormFile.setJobOrderFormFile(null);
        }
        if (orderFormFile != null) {
            orderFormFile.setJobOrderFormFile(this);
        }
        this.orderFormFile = orderFormFile;
    }

    public void saveFiles(Set<DBFile> files) {
        if (this.files != null) {
            this.files.forEach(file -> file.setJob(null));
        }
        files.forEach(file -> file.setJob(this));
        this.files = files;
    }

    /*------------------------
        GETTER AND SETTER
    ------------------------*/

    @Transactional
    public User getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWhiteLabel() {
        return whiteLabel;
    }

    public void setWhiteLabel(boolean whiteLabel) {
        this.whiteLabel = whiteLabel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<DBFile> getFiles() {
        return files;
    }


    public double getSeoValue() {
        return seoValue;
    }

    public void setSeoValue(double seoValue) {
        this.seoValue = seoValue;
    }

    public String getTargetKeywords() {
        return targetKeywords;
    }

    public void setTargetKeywords(String targetKeywords) {
        this.targetKeywords = targetKeywords;
    }

    public String getReportingKeywords() {
        return reportingKeywords;
    }

    public void setReportingKeywords(String reportingKeywords) {
        this.reportingKeywords = reportingKeywords;
    }

    public Set<SearchEngine> getSeoSearchEngines() {
        return seoSearchEngines;
    }

    public void setSeoSearchEngines(Set<SearchEngine> seoSearchEngines) {
        this.seoSearchEngines = seoSearchEngines;
    }

    public boolean isOnPageReview() {
        return onPageReview;
    }

    public void setOnPageReview(boolean onPageReview) {
        this.onPageReview = onPageReview;
    }

    public boolean isHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(boolean healthCheck) {
        this.healthCheck = healthCheck;
    }

    public double getLinkValue() {
        return linkValue;
    }

    public void setLinkValue(double linkValue) {
        this.linkValue = linkValue;
    }

    public Set<SearchEngine> getLinkSearchEngines() {
        return linkSearchEngines;
    }

    public void setLinkSearchEngines(Set<SearchEngine> linkSearchEngines) {
        this.linkSearchEngines = linkSearchEngines;
    }

    public String getGoogleDocLink() {
        return googleDocLink;
    }

    public void setGoogleDocLink(String googleDocLink) {
        this.googleDocLink = googleDocLink;
    }

    public DBFile getOrderFormFile() {
        return orderFormFile;
    }



    public double getPpcValue() {
        return ppcValue;
    }

    public void setPpcValue(double ppcValue) {
        this.ppcValue = ppcValue;
    }

    public Set<CampaignType> getCampaignTypes() {
        return campaignTypes;
    }

    public void setCampaignTypes(Set<CampaignType> campaignTypes) {
        this.campaignTypes = campaignTypes;
    }

    public boolean isAsap() {
        return asap;
    }

    public void setAsap(boolean asap) {
        this.asap = asap;
    }

    public Date getCampaignLaunchDate() {
        return campaignLaunchDate;
    }

    public void setCampaignLaunchDate(Date campaignLaunchDate) {
        this.campaignLaunchDate = campaignLaunchDate;
    }

    public boolean isSignOffRequired() {
        return signOffRequired;
    }

    public void setSignOffRequired(boolean signOffRequired) {
        this.signOffRequired = signOffRequired;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public boolean isSetUpRequired() {
        return setUpRequired;
    }

    public void setSetUpRequired(boolean setUpRequired) {
        this.setUpRequired = setUpRequired;
    }

    public String getGoogleAdsId() {
        return googleAdsId;
    }

    public void setGoogleAdsId(String googleAdsId) {
        this.googleAdsId = googleAdsId;
    }

    public String getMonthlyClickSpend() {
        return monthlyClickSpend;
    }

    public void setMonthlyClickSpend(String monthlyClickSpend) {
        this.monthlyClickSpend = monthlyClickSpend;
    }

    public String getTargetAreas() {
        return targetAreas;
    }

    public void setTargetAreas(String targetAreas) {
        this.targetAreas = targetAreas;
    }

    public String getLandingPageUrls() {
        return landingPageUrls;
    }

    public void setLandingPageUrls(String landingPageUrls) {
        this.landingPageUrls = landingPageUrls;
    }

    public String getAdwordsMainGoal() {
        return adwordsMainGoal;
    }

    public void setAdwordsMainGoal(String adwordsMainGoal) {
        this.adwordsMainGoal = adwordsMainGoal;
    }

    public String getFacebookMainGoal() {
        return facebookMainGoal;
    }

    public void setFacebookMainGoal(String facebookMainGoal) {
        this.facebookMainGoal = facebookMainGoal;
    }

    public String getThankYouPageUrl() {
        return thankYouPageUrl;
    }

    public void setThankYouPageUrl(String thankYouPageUrl) {
        this.thankYouPageUrl = thankYouPageUrl;
    }

    public String getReportingEmail() {
        return reportingEmail;
    }

    public void setReportingEmail(String reportingEmail) {
        this.reportingEmail = reportingEmail;
    }

    public double getContentValue() {
        return contentValue;
    }

    public void setContentValue(double contentValue) {
        this.contentValue = contentValue;
    }

    public int getContentNumberOfPieces() {
        return contentNumberOfPieces;
    }

    public void setContentNumberOfPieces(int contentNumberOfPieces) {
        this.contentNumberOfPieces = contentNumberOfPieces;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentTitles() {
        return contentTitles;
    }

    public void setContentTitles(String contentTitles) {
        this.contentTitles = contentTitles;
    }

    public WritingStyle getContentWritingStyle() {
        return contentWritingStyle;
    }

    public void setContentWritingStyle(WritingStyle contentWritingStyle) {
        this.contentWritingStyle = contentWritingStyle;
    }

    public String getContentKeywords() {
        return contentKeywords;
    }

    public void setContentKeywords(String contentKeywords) {
        this.contentKeywords = contentKeywords;
    }

    public void setFiles(Set<DBFile> files) {
        this.files = files;
    }

    public void setOrderFormFile(DBFile orderFormFile) {
        this.orderFormFile = orderFormFile;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
