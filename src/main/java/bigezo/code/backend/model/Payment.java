package bigezo.code.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long databaseId; // This will be our internal database ID

    @JsonProperty("id")
    private String pesapalId; // This matches Pesapal's JSON structure

    @Column(name = "currency")
    @JsonProperty("currency")
    private String currency;

    @Column(name = "amount")
    @JsonProperty("amount")
    private Double amount;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "callback_url")
    @JsonProperty("callback_url")
    private String callbackUrl;

    @Column(name = "redirect_mode")
    @JsonProperty("redirect_mode")
    private String redirectMode;

    @Column(name = "notification_id")
    @JsonProperty("notification_id")
    private String notificationId;

    @Column(name = "branch")
    @JsonProperty("branch")
    private String branch;

    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    @JsonProperty("billing_address")
    private BillingAddress billingAddress;

    // Tracking fields - not used for Pesapal API
    @Transient
    private Long schoolAdminId;

    @Transient
    private Long studentId;

    public Payment() {
    }

    // Getters and Setters
    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public String getPesapalId() {
        return pesapalId;
    }

    public void setPesapalId(String pesapalId) {
        this.pesapalId = pesapalId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getRedirectMode() {
        return redirectMode;
    }

    public void setRedirectMode(String redirectMode) {
        this.redirectMode = redirectMode;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Long getSchoolAdminId() {
        return schoolAdminId;
    }

    public void setSchoolAdminId(Long schoolAdminId) {
        this.schoolAdminId = schoolAdminId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}